package luubieunghi.lbn.booklib.Model.Book;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDAO {
    @Query("Select * from book")
    List<Book> getAllBook();

    @Query("Select * from book where booktitle like '%'||:subTitle||'%'")
    List<Book> searchBookTitle(String subTitle);

    @Insert
    void insertBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);

    @Query("DELETE FROM book")
    void nukeTable();
}
