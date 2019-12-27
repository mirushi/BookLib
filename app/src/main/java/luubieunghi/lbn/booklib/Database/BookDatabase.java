package luubieunghi.lbn.booklib.Database;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Author.AuthorDAO;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.Book.BookDAO;
import luubieunghi.lbn.booklib.Model.BookAuthor.BookAuthor;
import luubieunghi.lbn.booklib.Model.BookAuthor.BookAuthorDAO;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.Model.BookFile.BookFileDAO;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNum;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNumDAO;
import luubieunghi.lbn.booklib.Model.BookTag.BookTag;
import luubieunghi.lbn.booklib.Model.BookTag.BookTagDAO;
import luubieunghi.lbn.booklib.Model.BookType.BookType;
import luubieunghi.lbn.booklib.Model.BookType.BookTypeDAO;
import luubieunghi.lbn.booklib.Model.Language.Language;
import luubieunghi.lbn.booklib.Model.Language.LanguageDAO;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Model.Publisher.PublisherDAO;
import luubieunghi.lbn.booklib.Model.Tag.Tag;
import luubieunghi.lbn.booklib.Model.Tag.TagDAO;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;
import luubieunghi.lbn.booklib.Utility.TypeConverter.LocalDateConverter;

@Database(entities = {Book.class, Author.class, BookAuthor.class,
        BookIdentityNum.class, BookTag.class, Language.class, Publisher.class,Tag.class,
        BookFile.class, BookType.class}, version = 6)
@TypeConverters({LocalDateConverter.class})
public abstract class BookDatabase extends RoomDatabase {
    private static final String DB_NAME = "book_db";
    private static BookDatabase bookDatabaseInstance;

    private static long EBOOK_ID;
    private static long AUDIO_BOOK_ID;

    public static synchronized BookDatabase getInstance(Context context){
        if (bookDatabaseInstance == null){
            bookDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, DB_NAME)
                    .addCallback(rdc).fallbackToDestructiveMigration().build();
        }
        //nukeAllTable();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                bookDatabaseInstance.beginTransaction();
                bookDatabaseInstance.endTransaction();
            }
        });
        return bookDatabaseInstance;
    }

    private static void nukeAllTable(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                bookDatabaseInstance.BookTypeDAO().nukeTable();
                bookDatabaseInstance.LanguageDAO().nukeTable();
                bookDatabaseInstance.BookDAO().nukeTable();
                bookDatabaseInstance.PublisherDAO().nukeTable();
                bookDatabaseInstance.AuthorDAO().nukeTable();
                bookDatabaseInstance.BookAuthorDAO().nukeTable();
                bookDatabaseInstance.BookFileDAO().nukeTable();
                bookDatabaseInstance.BookIdentityNumDAO().nukeTable();
                bookDatabaseInstance.BookTagDAO().nukeTable();
                bookDatabaseInstance.TagDAO().nukeTable();
                bookDatabaseInstance.LanguageDAO().Insert(new Language(0,"English"));
                bookDatabaseInstance.LanguageDAO().Insert(new Language(0, "Tiếng Việt"));
                bookDatabaseInstance.BookTypeDAO().Insert(new BookType(0, "Ebook"));
                bookDatabaseInstance.BookTypeDAO().Insert(new BookType(1, "Audio Book"));

            }
        });
    }

    private static RoomDatabase.Callback rdc = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //Hành động sẽ được thực hiện mỗi khi database được mở.
            //Hành động sẽ được thực hiện khi database được tạo mới lần đầu.
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    bookDatabaseInstance.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            //Chúng ta sẽ thêm vào hai ngôn ngữ mặc định khi DB được tạo (tiếng Anh / tiếng Việt).
                            bookDatabaseInstance.LanguageDAO().InsertIfNotExists(new Language(0,"English"));
                            bookDatabaseInstance.LanguageDAO().InsertIfNotExists(new Language(0, "Tiếng Việt"));

                            //Sau đó chúng ta sẽ thêm vào 2 loại sách (Sách Ebook và Sách nói).
                            bookDatabaseInstance.BookTypeDAO().InsertIfNotExists(new BookType(0, "Ebook"));
                            bookDatabaseInstance.BookTypeDAO().InsertIfNotExists(new BookType(1, "Audio Book"));
                            UpdateBookID();
                        }
                    });
                }
            });
        }

        private void UpdateBookID(){
            //Chúng ta thực hiện cập nhật lại Ebook ID và Audio Book ID để phân biệt 2 thứ đó.
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<BookType> bookTypes = bookDatabaseInstance.BookTypeDAO().getAllBookTypes();
                    for (BookType type : bookTypes){
                        if (type.getBTypeName().equals("Ebook")){
                            EBOOK_ID = type.getBTypeID();
                        }
                        else{
                            AUDIO_BOOK_ID = type.getBTypeID();
                        }
                    }
                }
            });
        }
    };

    public long getEbookId(){
        return EBOOK_ID;
    }

    public long getAudioBookId(){
        return AUDIO_BOOK_ID;
    }

    public abstract BookDAO BookDAO();
    public abstract BookFileDAO BookFileDAO();
    public abstract AuthorDAO AuthorDAO();
    public abstract BookAuthorDAO BookAuthorDAO();
    public abstract BookTypeDAO BookTypeDAO();
    public abstract BookIdentityNumDAO BookIdentityNumDAO();
    public abstract BookTagDAO BookTagDAO();

    public abstract LanguageDAO LanguageDAO();
    public abstract PublisherDAO PublisherDAO();
    public abstract TagDAO TagDAO();

}
