package luubieunghi.lbn.booklib.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Author.AuthorDAO;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Book.BookDAO;
import luubieunghi.lbn.booklib.Model.BookAuthor.BookAuthor;
import luubieunghi.lbn.booklib.Model.BookAuthor.BookAuthorDAO;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNum;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNumDAO;
import luubieunghi.lbn.booklib.Model.BookTag.BookTag;
import luubieunghi.lbn.booklib.Model.BookTag.BookTagDAO;
import luubieunghi.lbn.booklib.Model.Language.Language;
import luubieunghi.lbn.booklib.Model.Language.LanguageDAO;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Model.Publisher.PublisherDAO;
import luubieunghi.lbn.booklib.Model.Tag.Tag;
import luubieunghi.lbn.booklib.Model.Tag.TagDAO;
import luubieunghi.lbn.booklib.Utility.TypeConverter.LocalDateConverter;

@Database(entities = {Book.class, Author.class, BookAuthor.class,
        BookIdentityNum.class, BookTag.class, Language.class, Publisher.class,Tag.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class BookDatabase extends RoomDatabase {
    private static final String DB_NAME = "book_db";
    private static BookDatabase bookDatabaseInstance;
    public static synchronized BookDatabase getInstance(Context context){
        if (bookDatabaseInstance == null){
            bookDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return bookDatabaseInstance;
    }

    public abstract BookDAO BookDAO();
    public abstract AuthorDAO AuthorDAO();
    public abstract BookAuthorDAO BookAuthorDAO();
    public abstract BookIdentityNumDAO BookIdentityNumDAO();
    public abstract BookTagDAO BookTagDAO();
    public abstract LanguageDAO LanguageDAO();
    public abstract PublisherDAO PublisherDAO();
    public abstract TagDAO TagDAO();
}
