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

    @Query("Select * from book where not exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> 0))")
    public abstract List<Book> getAllNewBook();

    @Query("Select * from book where not exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> 0)) and book.bTypeID = :typeID")
    public abstract List<Book> getAllNewBookWithBookType(long typeID);

    @Query("Select * from book where exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> 0 and bookfile.bRead <> bookfile.bTotal))")
    public abstract List<Book> getAllInProgressBook();

    @Query("Select * from book where exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> 0 and bookfile.bRead <> bookfile.bTotal)) and book.bTypeID = :typeID")
    public abstract List<Book> getAllInProgressBookWithBookType(long typeID);

    @Query("Select * from book where not exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> bookfile.bTotal))")
    public abstract List<Book> getAllFinishedBook();

    @Query("Select * from book where not exists (Select * from bookfile where bookfile.bookid = book.bookid and (bookfile.bRead <> bookfile.bTotal)) and book.bTypeID = :typeID")
    public abstract List<Book> getAllFinishedBookWithBookType(long typeID);

    @Query("Select distinct * from book where booktitle like '%'||:subTitle||'%'")
    public abstract List<Book> searchBookTitle(String subTitle);

    @Query("Select distinct book.* from book join bookauthor on book.bookID = bookauthor.bookID " +
            "join author on bookauthor.authorID = author.authorID where author.authorName like '%'||:author||'%'")
    public abstract List<Book> searchBookFromAuthorName(String author);

    @Query("Select distinct book.* from book join bookidentitynum on book.bookID = bookidentitynum.bookID " +
            "where bookidentitynum.IDValue like '%'||:ids||'%'")
    public abstract List<Book> searchBookFromIDs(String ids);

    @Query("Select distinct book.* from book join booktag on book.bookID = booktag.bookID " +
            "join tag on booktag.tagID = tag.tagID where tag.tagContent like '%'||:tag||'%'")
    public abstract List<Book> searchBookFromTag(String tag);

    @Query("Update bookfile set bRead = bTotal where bookfile.bookID = :bookID")
    public abstract void markBookRead(long bookID);

    @Query("Update bookfile set bRead = 0 where bookfile.bookID = :bookID")
    public abstract void markBookUnread(long bookID);

    @Transaction
    public List<Book> searchBookAllPossibleField(String searchString){
        List<Book> result = new ArrayList<>();
        //Tìm tất cả các sách có tựa đề trùng khớp.
        List<Book> matchedTitle = searchBookTitle(searchString);
        //Tìm tất cả các sách có tác giả trùng khớp.
        List<Book> matchedAuthor = searchBookFromAuthorName(searchString);
        //Tìm tất cả các sách có ID trùng khớp.
        List<Book> matchedIds = searchBookFromIDs(searchString);
        //
        return result;
    }

    @Insert
    public abstract long insertBook(Book book);

    @Update
    public abstract void updateBook(Book book);

    @Delete
    public abstract void deleteBook(Book book);

    @Query("DELETE FROM book")
    public abstract void nukeTable();
}
