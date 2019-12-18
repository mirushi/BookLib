package luubieunghi.lbn.booklib.Model.BookType;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookTypeDAO {

    @Query("SELECT * FROM bookType")
    List<BookType> getAllBookTypes();

    @Insert
    //Lưu ý : Đừng insert ở bất kỳ đâu trừ callback khi khởi tạo.
    void Insert(BookType bookType);

    //Lưu ý : Đừng delete ở bất kỳ đâu trừ callback khi khởi tạo.
    @Delete
    void Delete(BookType bookType);

    //Lưu ý : Đừng delete ở bất kỳ đâu trừ callback khi khởi tạo.
    @Query("DELETE FROM bookType")
    void deleteAllBookTypes();

    @Query("DELETE FROM bookType")
    void nukeTable();

}
