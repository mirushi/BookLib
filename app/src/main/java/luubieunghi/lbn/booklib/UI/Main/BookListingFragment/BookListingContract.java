package luubieunghi.lbn.booklib.UI.Main.BookListingFragment;

import java.util.ArrayList;
import luubieunghi.lbn.booklib.Model.Book;

public interface BookListingContract {
    interface BookListingMVPView {
        void AddOneBookToView(Book book, BookListingReadProgressFilter filter);
        void AddMultipleBookToView(ArrayList<Book> books, BookListingReadProgressFilter filter);
    }
    interface BookListingMVPPresenter{
        void LoadBookList(BookListingReadProgressFilter filter);
        void StartReading(Book book);
        void ViewDetailedInfo(Book book);
        void ViewBookmark(Book book);
        void ViewHighlight(Book book);
        void MarkRead(Book book);
        void DeleteBook(Book book);
    }
}
