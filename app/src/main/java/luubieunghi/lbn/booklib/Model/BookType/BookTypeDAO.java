package luubieunghi.lbn.booklib.Model.BookType;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class BookTypeDAO {

    @Query("SELECT * FROM bookType")
    public abstract List<BookType> getAllBookTypes();

    @Insert
    //Lưu ý : Đừng insert ở bất kỳ đâu trừ callback khi khởi tạo.
    public abstract long Insert(BookType bookType);

    //Lưu ý : Đừng delete ở bất kỳ đâu trừ callback khi khởi tạo.
    @Delete
    public abstract void Delete(BookType bookType);

    @Transaction
    public long InsertIfNotExists(BookType bookType){
        BookType detect = getExactBookType(bookType.getBTypeName());
        if (detect != null){
            return detect.getBTypeID();
        }
        else{
            return Insert(bookType);
        }
    }

    //Lưu ý : Đừng delete ở bất kỳ đâu trừ callback khi khởi tạo.
    @Query("DELETE FROM bookType")
    public abstract void deleteAllBookTypes();

    @Query("Select * from bookType where bookType.bTypeName = :bookTypeName")
    public abstract BookType getExactBookType(String bookTypeName);

    @Query("DELETE FROM bookType")
    public abstract void nukeTable();

}
