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
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

public class AddNewBookPresenter implements AddNewBookContract.AddNewBookMVPPresenter {

    //Delimer dùng để chia khi người dùng nhập nhiều chuỗi.
    final String regexDim = ",";
    final String idSplitRegexDim = ":";

    AddNewBookActivity view;
    BookDatabase bookDB;

    List<Language> languageList;
    List<Publisher> publisherList;

    ArrayList<String> viewBookFilePath;
    String viewBookCoverPath;
    String viewTitle;
    String bookType;
    long bTypeID;
    String viewAuthor;
    int viewRating;
    String viewIds;
    String viewTags;
    String viewLanguage;
    String viewPublisher;
    LocalDate viewPublishDate;
    String viewDescription;

    Book existingBook;

    public AddNewBookPresenter(AddNewBookActivity addNewBookActivity){
        this.view = addNewBookActivity;
        bookDB = BookDatabase.getInstance(addNewBookActivity);
    }

    @Override
    public void AddBook() {
        //Thao tác thêm sách vào CSDL.
        //Đầu tiên ta phải lấy thông tin ở View ra.
        this.viewBookFilePath = view.pathToFiles;
        this.viewBookCoverPath = view.pathToBookCover;
        this.viewTitle = view.txtBookTitle.getText().toString();
        this.bookType = view.getBookType();

        this.viewAuthor = view.txtAuthor.getText().toString();
        this.viewRating = view.ratingView.getRating();
        this.viewIds = view.txtIDs.getText().toString();
        this.viewTags = view.txtTags.getText().toString();
        this.viewLanguage = view.spinnerLanguage.getSelectedItem().toString();
        this.viewPublisher = view.txtPublisher.getText().toString();
        this.viewPublishDate = view.ldPublishingDate;
        this.viewDescription = view.txtDescription.getText().toString();

        this.existingBook = view.getExistingBook();

        //Sau đó, ta phải kiểm tra xem thông tin người dùng nhập vào có hợp lệ hay không.

        //Sau khi kiểm tra mọi thứ hợp lệ ta tiến hành thêm sách vào DB.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if (bookType.equals("EBOOK"))
                    bTypeID = BookDatabase.getInstance(view).getEbookId();
                else
                    bTypeID = BookDatabase.getInstance(view).getAudioBookId();

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

                //Kiểm tra xem nếu sách đã tồn tại, tức là người dùng đang update.
                //Vì vậy, phải xoá đi sách cũ trước khi thêm sách vào lại.
                if (existingBook != null){
                    bookDB.BookDAO().deleteBook(existingBook);
                }


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
    public void LoadExistingBookDetails() {
        final Book existingBook = view.getExistingBook();
        final BookDatabase bookDB = BookDatabase.getInstance(view);
        BookLoadingAlertDialog dialog = new BookLoadingAlertDialog(view);
        dialog.showDialog();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Load tựa đề.
                final String title = existingBook.getBookTitle();
                //Load tác giả.
                final List<Author> bookAuthorList = bookDB.BookAuthorDAO().getAuthorsOfBook(existingBook.getBookID());
                StringBuilder stringBuilder = new StringBuilder();
                if (bookAuthorList.size() > 0)
                    stringBuilder.append(bookAuthorList.get(0).getAuthorName());
                for (int i=1; i<bookAuthorList.size();++i){
                    Author author = bookAuthorList.get(i);
                    stringBuilder.append(",");
                    stringBuilder.append(author.getAuthorName());
                }
                String authorsString = stringBuilder.toString();

                //Load id.
                final List<BookIdentityNum> bookIdentityNumsList = bookDB.BookIdentityNumDAO().getBookIDs(existingBook.getBookID());
                stringBuilder = new StringBuilder();
                if (bookIdentityNumsList.size() > 0)
                    stringBuilder.append(bookIdentityNumsList.get(0).getIDName() + ":" + bookIdentityNumsList.get(0).getIDValue());
                for (int i=1;i<bookIdentityNumsList.size();++i){
                    BookIdentityNum bookIdentityNum = bookIdentityNumsList.get(i);
                    stringBuilder.append(",");
                    stringBuilder.append(bookIdentityNum.getIDName() + ":" + bookIdentityNum.getIDValue());
                }
                String IDsString = stringBuilder.toString();

                //Load tags.
                final List<Tag> bookTagList = bookDB.BookTagDAO().getAllTagsOfBook(existingBook.getBookID());
                stringBuilder = new StringBuilder();
                if (bookTagList.size() > 0)
                    stringBuilder.append(bookTagList.get(0).getTagContent());
                for (int i=1;i<bookTagList.size();++i){
                    Tag tag = bookTagList.get(i);
                    stringBuilder.append(",");
                    stringBuilder.append(tag.getTagContent());
                }
                String tagsString = stringBuilder.toString();

                //Load ngôn ngữ.
                final Language bookLa = bookDB.LanguageDAO().getBookLanguage(existingBook.getBookID());
                //Load Nhà Xuất Bản.
                final Publisher publisher = bookDB.PublisherDAO().getBookPublisher(existingBook.getBookID());
                //Load ngày xuất bản.
                final LocalDate publishingDate = existingBook.getPublishDate();
                //Load mô tả.
                final String description = existingBook.getDescription();

                //Load đánh giá.
                final int rating = existingBook.getRating();

                //Load path đến BookCover.
                final String pathToBookCover = existingBook.getBookCoverPath();

                //Load loại sách (ebook/audio book).
                final long bTypeID = existingBook.getBTypeID();

                //Load file.
                final List<BookFile> bookFileList = bookDB.BookFileDAO().getAllFilesOfBook(existingBook.getBookID());
                ArrayList<String> bookPath = new ArrayList<>();
                for (BookFile bookfile : bookFileList){
                    bookPath.add(bookfile.getBFilePath());
                }

                //Sau khi đã chuẩn bị đủ nguyên liệu, ta tiến hành set view 1 lần trên thread chính.
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        view.txtBookTitle.setText(title);
                        view.txtAuthor.setText(authorsString);
                        view.txtIDs.setText(IDsString);
                        view.txtTags.setText(tagsString);
                        //view.spinnerLanguage.set
                        view.txtPublisher.setText(publisher.getPublisherName());
                        view.txtPublishingDate.setText(publishingDate.toString());
                        view.txtDescription.setText(description);
                        view.ratingView.setRating(rating);
                        view.setPathToBookCover(pathToBookCover);
                        if (bTypeID == bookDB.getEbookId())
                            view.setBookType("EBOOK");
                        else
                            view.setBookType("AUDIO_BOOK");
                        List<String> langList = view.spinnerLanguage.getItem();
                        for (int i = 0;i<langList.size();++i){
                            if (langList.get(i).equals(bookLa.getLangName())){
                                view.spinnerLanguage.setSelection(i);
                                break;
                            }
                        }
                        view.setFilePath(bookPath);
                        dialog.hideDialog();
                    }
                });
            }
        });
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
