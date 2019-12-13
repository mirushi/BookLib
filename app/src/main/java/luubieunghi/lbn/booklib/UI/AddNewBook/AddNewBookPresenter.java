package luubieunghi.lbn.booklib.UI.AddNewBook;

import android.content.Context;
import android.net.Uri;

import java.time.LocalDate;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;

public class AddNewBookPresenter implements AddNewBookContract.AddNewBookMVPPresenter {

    AddNewBookActivity view;
    BookDatabase bookDB;

    public AddNewBookPresenter(AddNewBookActivity addNewBookActivity){
        this.view = addNewBookActivity;
        bookDB = BookDatabase.getInstance(addNewBookActivity);
    }

    @Override
    public void AddBook() {
        //Thao tác thêm sách vào CSDL.
        //Đầu tiên ta phải lấy thông tin ở View ra.
        Uri bookUri = view.pathToFile;
        String title = view.txtBookTitle.getText().toString();
        String author = view.txtAuthor.getText().toString();
        int rating = view.ratingView.getRating();
        String ids = view.txtIDs.getText().toString();
        String tags = view.txtTags.getText().toString();
        String language = view.spinnerLanguage.getSelectedItem().toString();
        String publisher = view.txtPublisher.getText().toString();
        LocalDate publishDate = view.ldPublishingDate;
        String description = view.txtDescription.getText().toString();

        //Chưa có bookCoverPath.
        String bookCoverPath = "";
        String bookFilePath = bookUri.toString();

    }

    @Override
    public void LoadLanguages() {

    }

    @Override
    public void LoadServerList() {

    }
}
