package luubieunghi.lbn.booklib.Model.Book;

import java.util.List;

import androidx.room.Query;

public interface BookDAO {
    @Query("SELECT * FROM BOOK")
    List<Book> getAll();
}
