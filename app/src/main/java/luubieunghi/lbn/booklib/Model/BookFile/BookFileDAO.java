package luubieunghi.lbn.booklib.Model.BookFile;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class BookFileDAO {

    @Query("SELECT * FROM bookfile WHERE bookfile.bookID = :bookID")
    public abstract List<BookFile> getAllFilesOfBook(int bookID);

    @Query("DELETE FROM bookfile WHERE bookfile.bookID = :bookID")
    public abstract void removeAllFilesOfBook(long bookID);

    @Transaction
    public void deleteAllFilesOfBookThenInsert(BookFile ...bookfiles){
        if (bookfiles == null || bookfiles.length <= 0)
            return;
        removeAllFilesOfBook(bookfiles[0].getBookID());
        insertBookFile(bookfiles);
    }

    @Insert
    public abstract void insertBookFile(BookFile... bookFiles);

    @Insert
    public abstract void insertBookFile(List<BookFile> bookFileList);

    @Update
    public abstract void updateBookFile(BookFile newBookFile);

    @Delete
    public abstract void deleteBookFile(BookFile bookFile);

    @Query("DELETE FROM bookfile")
    public abstract void nukeTable();
}
