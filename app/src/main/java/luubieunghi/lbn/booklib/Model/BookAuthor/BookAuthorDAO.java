package luubieunghi.lbn.booklib.Model.BookAuthor;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.Book.Book;

@Dao
public interface BookAuthorDAO {

    @Insert
    void Insert(BookAuthor bookAuthor);

    @Insert
    void Insert(BookAuthor ...bookAuthors);

    @Insert
    void Insert(List<BookAuthor> bookAuthors);

    @Delete
    void Delete(BookAuthor bookAuthor);

    @Query("SELECT author.authorID, author.authorName from author inner join bookAuthor " +
            "ON author.authorID = bookAuthor.authorID " +
            "where bookAuthor.bookID = :bookID")
    List<Author> getAuthorsOfBook(final int bookID);

    @Query("Select * from bookAuthor inner join book " +
            "ON book.bookid = bookauthor.bookID " +
            "WHERE bookAuthor.authorID = :authorID")
    List<Book> getBooksWrittenByAuthor(final int authorID);

    @Query("DELETE FROM bookAuthor")
    void nukeTable();
}
