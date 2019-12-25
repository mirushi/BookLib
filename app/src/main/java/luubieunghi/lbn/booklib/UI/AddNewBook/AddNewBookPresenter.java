package luubieunghi.lbn.booklib.UI.AddNewBook;

import android.net.Uri;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.BookLib;
import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.BookAuthor.BookAuthor;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNum;
import luubieunghi.lbn.booklib.Model.BookTag.BookTag;
import luubieunghi.lbn.booklib.Model.Language.Language;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Model.Tag.Tag;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

public class AddNewBookPresenter implements AddNewBookContract.AddNewBookMVPPresenter {

    //Delimer dùng để chia khi người dùng nhập nhiều chuỗi.
    final String regexDim = ",";
    final String idSplitRegexDim = ":";

    AddNewBookActivity view;
    BookDatabase bookDB;

    List<Language> languageList;
    List<Publisher> publisherList;

    public AddNewBookPresenter(AddNewBookActivity addNewBookActivity){
        this.view = addNewBookActivity;
        bookDB = BookDatabase.getInstance(addNewBookActivity);
    }

    @Override
    public void AddBook() {
        //Thao tác thêm sách vào CSDL.
        //Đầu tiên ta phải lấy thông tin ở View ra.
        final ArrayList<String> viewBookFilePath = view.pathToFiles;
        final String viewBookCoverPath = view.pathToBookCover;
        final String viewTitle = view.txtBookTitle.getText().toString();
        final String bookType = view.getBookType();
        long bTypeID;
        if (bookType.equals("EBOOK"))
            bTypeID = BookDatabase.getInstance(view).getEbookId();
        else
            bTypeID = BookDatabase.getInstance(view).getAudioBookId();

        final String viewAuthor = view.txtAuthor.getText().toString();
        final int viewRating = view.ratingView.getRating();
        final String viewIds = view.txtIDs.getText().toString();
        final String viewTags = view.txtTags.getText().toString();
        final String viewLanguage = view.spinnerLanguage.getSelectedItem().toString();
        final String viewPublisher = view.txtPublisher.getText().toString();
        final LocalDate viewPublishDate = view.ldPublishingDate;
        final String viewDescription = view.txtDescription.getText().toString();

        //Sau đó, ta phải kiểm tra xem thông tin người dùng nhập vào có hợp lệ hay không.


        //Sau khi kiểm tra mọi thứ hợp lệ ta tiến hành thêm sách vào DB.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<Language> langSearchResult = bookDB.LanguageDAO().searchForExactLanguageName(viewLanguage);
                if (langSearchResult == null || langSearchResult.size() == 0){
                    AddLanguage(viewLanguage);
                    langSearchResult = bookDB.LanguageDAO().searchForExactLanguageName(viewLanguage);
                }
                long langID = langSearchResult.get(0).getLangID();

                List<Publisher> publisherSearchResult = bookDB.PublisherDAO().searchForExactPublisher(viewPublisher);
                if (publisherSearchResult == null || publisherSearchResult.size() == 0){
                    AddPublisher(viewPublisher);
                    publisherSearchResult = bookDB.PublisherDAO().searchForExactPublisher(viewPublisher);
                }
                long publisherID = publisherSearchResult.get(0).getPublisherID();

                String bookCoverPath = viewBookCoverPath;


                Book book = new Book(view, bookCoverPath,
                        viewTitle, viewRating, langID, publisherID, viewPublishDate, viewDescription, bTypeID);

                long bookID = bookDB.BookDAO().insertBook(book);
                //Sau khi thêm sách vào, chúng ta còn phải xử lý IDs, Tags và Authors trong mối quan hệ nhiều nhiều.

                //Các tác giả của sách.

                //Lấy tất cả tác giả mà người dùng nhập vào.
                String[] authorsName = viewAuthor.split(regexDim);
                if (authorsName.length == 0){
                    InformViewErrorAddingBook("Xin hãy kiểm tra lại trường tác giả của sách");
                    return;
                }
                List<Author> authors = bookDB.AuthorDAO().insertIfNotExistAndReturnAuthors(authorsName);
                List<BookAuthor> bookAuthors = new ArrayList<>();
                for (Author author : authors){
                    //Thêm tất cả tác giả mà người dùng nhập.
                    bookAuthors.add(new BookAuthor(bookID, author.getAuthorID()));
                }

                //Các IDs của sách.
                String[] ids = viewIds.split(regexDim);

                if (ids.length == 0)
                    InformViewErrorAddingBook("Xin hãy kiểm tra lại trường ID của sách");
                List<BookIdentityNum> bookIdentityNums = new ArrayList<>();
                for (String id : ids){
                    //keyAndValue[0] là key, keyAndValue[1] là value.
                    String[] keyAndValue = id.split(idSplitRegexDim);
                    bookIdentityNums.add(new BookIdentityNum
                            (bookID, keyAndValue[0], keyAndValue[1]));
                }

                //Các files của sách.
                List<BookFile> bookFileList = new ArrayList<>();
                for (int i=0;i<viewBookFilePath.size();++i){
                    String filePath = viewBookFilePath.get(i);
                    BookFile bookFile = new BookFile(bookID, i, filePath, 0,0, null);
                    bookFileList.add(bookFile);
                }

                //Các tags của sách.
                String[] tagsName = viewTags.split(regexDim);
                //Lấy tất cả các Tags của sách.
                List<Tag> tags = bookDB.TagDAO().insertIfNotExistAndReturnTags(tagsName);
                List<BookTag> bookTags = new ArrayList<>();
                for (Tag tag : tags){
                    //Thêm tất cả các tags cho sách mà người dùng nhập.
                    bookTags.add(new BookTag(bookID, tag.getTagID()));
                }

                //Xử lý DB.
                bookDB.BookIdentityNumDAO().Insert(bookIdentityNums);
                bookDB.BookAuthorDAO().Insert(bookAuthors);
                bookDB.BookFileDAO().insertBookFile(bookFileList);
                bookDB.BookTagDAO().Insert(bookTags);

                //Cái gì đụng tới View là phải để cho Thread chính xử lý.
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        view.BookAddedSuccess();
                    }
                });
            }
        });
    }

    @Override
    public void LoadLanguages() {
        languageList = bookDB.LanguageDAO().getAllStoredLanguages();
    }

    @Override
    public void LoadServerList() {

    }

    @Override
    public void LoadPublisherList(){

    }

    @Override
    public void AddLanguage(String langName){
        bookDB.LanguageDAO().Insert(new Language(0, langName));
    }

    @Override
    public void AddPublisher(String publisherName) {
        bookDB.PublisherDAO().Insert(new Publisher(0, publisherName));
    }

    private void InformViewErrorAddingBook(final String reason){

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                view.BookAddedFailure(reason);
            }
        });
    }

}
