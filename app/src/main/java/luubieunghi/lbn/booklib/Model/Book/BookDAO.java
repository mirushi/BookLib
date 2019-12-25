package luubieunghi.lbn.booklib.Model.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.Model.BookFile.BookFileDAO;

@Dao
public abstract class BookDAO {
    @Query("Select * from book")
    public abstract List<Book> getAllBook();

    @Query("Select distinct book.* from book inner join bookfile ON book.bookID = bookfile.bookID where bookfile.bRead = 0")
    public abstract List<Book> getAllNewBook();

    @Query("Select distinct book.* from book inner join bookfile ON book.bookID = bookfile.bookID where bookfile.bRead <> 0")
    public abstract List<Book> getAllInProgressBook();

    @Query("Select distinct book.* from book inner join bookfile on book.bookID = bookfile.bookID where bookfile.bRead = bookfile.bTotal")
    public abstract List<Book> getAllFinishedBook();

    @Query("Select distinct * from book where booktitle like '%'||:subTitle||'%'")
    public abstract List<Book> searchBookTitle(String subTitle);

    @Insert
    public abstract long insertBook(Book book);

    @Update
    public abstract void updateBook(Book book);

    @Delete
    public abstract void deleteBook(Book book);

    @Query("DELETE FROM book")
    public abstract void nukeTable();
}
