package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import java.time.LocalDate;
import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.Book;
import luubieunghi.lbn.booklib.UI.AddNewBook.AddNewBookContract;

public class BookListingPresenter implements BookListingContract.BookListingMVPPresenter {

    BookListingFragment view;

    public BookListingPresenter(BookListingContract.BookListingMVPView view){
        //Presenter này chỉ hoạt động duy nhất với view BookListingFragment mà thôi.
        this.view = (BookListingFragment)view;
    }

    @Override
    public void LoadBookList(BookListingReadProgressFilter filter) {
        if (filter == BookListingReadProgressFilter.NEW){
            //Dummy data làm mẫu. Làm thực thì ở đây sẽ lấy danh sách trên CSDL.
            ArrayList<Book> newBooks = new ArrayList<>();
            newBooks.add(new Book(view.getContext(), "", "New Book 01"));
            newBooks.add(new Book(view.getContext(), "", "New Book 02"));
            newBooks.add(new Book(view.getContext(), "", "New Book 03"));
            newBooks.add(new Book(view.getContext(), "", "New Book 04"));
            newBooks.add(new Book(view.getContext(), "", "New Book 05"));
            newBooks.add(new Book(view.getContext(), "", "New Book 06"));
            view.AddMultipleBookToView(newBooks, BookListingReadProgressFilter.NEW);
        }
        else if (filter == BookListingReadProgressFilter.READING){
            //Dummy data làm mẫu. Làm thực thì ở đây sẽ lấy danh sách trên CSDL.
            ArrayList<Book> readingBooks = new ArrayList<>();
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 01"));
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 02"));
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 03"));
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 04"));
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 05"));
            readingBooks.add(new Book(view.getContext(), "", "Reading Book 06"));
            view.AddMultipleBookToView(readingBooks, BookListingReadProgressFilter.READING);
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            //Dummy data làm mẫu. Làm thực thì ở đây sẽ lấy danh sách trên CSDL.
            ArrayList<Book> finishedBooks = new ArrayList<>();
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 01"));
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 02"));
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 03"));
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 04"));
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 05"));
            finishedBooks.add(new Book(view.getContext(), "", "Finished Book 06"));
            view.AddMultipleBookToView(finishedBooks, BookListingReadProgressFilter.FINISHED);
        }
    }

    @Override
    public void StartReading(Book book) {
        //Mở sách bắt đầu đọc.
    }

    @Override
    public void ViewDetailedInfo(Book book) {
        //Mở activity xem thông tin chi tiết của sách.
    }

    @Override
    public void ViewBookmark(Book book) {
        //Mở trình quản lý Bookmark của sách.
    }

    @Override
    public void ViewHighlight(Book book) {
        //Mở trình quản lý highlight của sách.
    }

    @Override
    public void MarkRead(Book book) {
        //Đánh dấu sách là đã được đọc.
    }

    @Override
    public void DeleteBook(Book book) {
        //Xoá sách.
    }
}
