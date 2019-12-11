package luubieunghi.lbn.booklib.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Author.AuthorDAO;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Book.BookDAO;
import luubieunghi.lbn.booklib.Utility.TypeConverter.LocalDateConverter;

@Database(entities = {Book.class, Author.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDAO bookDAO();
    public abstract AuthorDAO authorDAO();
}
