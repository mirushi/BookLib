package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

public class BookListingPresenter implements BookListingContract.BookListingMVPPresenter {

    BookListingFragment view;
    Context context;

    public BookListingPresenter(BookListingContract.BookListingMVPView view){
        //Presenter này chỉ hoạt động duy nhất với view BookListingFragment mà thôi.
        this.view = (BookListingFragment)view;
        this.context = this.view.getActivity();
    }

    @Override
    public void LoadBookList(BookListingReadProgressFilter filter) {
        view.SetLoadingDialog(true);
        if (filter == BookListingReadProgressFilter.NEW){
            view.freshBookRecyclerViewAdapter.clear();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Book> newBookList = BookDatabase.getInstance(context).BookDAO().getAllNewBook();
                    //Đụng tới view là phải để thread chính xử lý.
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            view.AddMultipleBookToView(newBookList, BookListingReadProgressFilter.NEW);
                            view.SetLoadingDialog(false);
                        }
                    });
                }
            });
        }
        else if (filter == BookListingReadProgressFilter.READING){
            view.inProgressBookRecyclerViewAdapter.clear();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Book> readingBookList = BookDatabase.getInstance(context).BookDAO().getAllInProgressBook();
                    //Đụng tới view là phải để thread chính xử lý.
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            view.AddMultipleBookToView(readingBookList, BookListingReadProgressFilter.READING);
                            view.SetLoadingDialog(false);
                        }
                    });
                }
            });
        }
        else if (filter == BookListingReadProgressFilter.FINISHED){
            view.finishedBookRecyclerViewAdapter.clear();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Book> finishedBookList = BookDatabase.getInstance(context).BookDAO().getAllFinishedBook();
                    //Đụng tới view là phải để thread chính xử lý.
                    AppExecutors.getInstance().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            view.AddMultipleBookToView(finishedBookList, BookListingReadProgressFilter.FINISHED);
                            view.SetLoadingDialog(false);
                        }
                    });
                }
            });
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
