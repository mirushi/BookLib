package luubieunghi.lbn.booklib.UI.AddNewBook;

import android.net.Uri;

import java.time.LocalDate;
import java.util.List;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Language.Language;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddNewBookPresenter implements AddNewBookContract.AddNewBookMVPPresenter {

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
        final Uri bookUri = view.pathToFile;
        final Uri bookCoverUri = view.pathToBookCover;
        final String title = view.txtBookTitle.getText().toString();
        final String author = view.txtAuthor.getText().toString();
        final int rating = view.ratingView.getRating();
        final String ids = view.txtIDs.getText().toString();
        final String tags = view.txtTags.getText().toString();
        final String language = view.spinnerLanguage.getSelectedItem().toString();
        final String publisher = view.txtPublisher.getText().toString();
        final LocalDate publishDate = view.ldPublishingDate;
        final String description = view.txtDescription.getText().toString();

        //Sau đó, ta phải kiểm tra xem thông tin người dùng nhập vào có hợp lệ hay không.

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<Language> langSearchResult = bookDB.LanguageDAO().searchForExactLanguageName(language);
                if (langSearchResult == null || langSearchResult.size() == 0){
                    AddLanguage(language);
                    langSearchResult = bookDB.LanguageDAO().searchForExactLanguageName(language);
                }
                int langID = langSearchResult.get(0).getLangID();

                List<Publisher> publisherSearchResult = bookDB.PublisherDAO().searchForExactPublisher(publisher);
                if (publisherSearchResult == null || publisherSearchResult.size() == 0){
                    AddPublisher(publisher);
                    publisherSearchResult = bookDB.PublisherDAO().searchForExactPublisher(publisher);
                }
                int publisherID = publisherSearchResult.get(0).getPublisherID();

                String bookCoverPath = bookCoverUri.getPath();
                String bookFilePath = bookUri.getPath();

                Book book = new Book(view, bookCoverPath,
                        title, rating, langID, publisherID, publishDate, description, 0);

                bookDB.BookDAO().insertBook(book);
                //Sau khi thêm sách vào, chúng ta còn phải xử lý IDs, Tags và Authors trong mối quan hệ nhiều nhiều.
                //Các tác giả của sách.
                //Các IDs của sách.

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
}
