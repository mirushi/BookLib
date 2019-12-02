package luubieunghi.lbn.booklib.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Book.BookDAO;

//@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDAO bookDAO();
}
