package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.UI.Main.BookFilterType;

public interface BookListingContract {
    interface BookListingMVPView {
        void AddOneBookToView(Book book, BookListingReadProgressFilter filter);
        void AddMultipleBookToView(List<Book> books, BookListingReadProgressFilter filter);
        void SetLoadingDialog(boolean show);
    }
    interface BookListingMVPPresenter{
        void LoadBookList(BookListingReadProgressFilter filter, BookFilterType bookFilterType);
        void StartReading(Book book);
        void ViewDetailedInfo(Book book);
        void ViewBookmark(Book book);
        void ViewHighlight(Book book);
        void MarkRead(Book book);
        void DeleteBook(Book book);
    }
}
