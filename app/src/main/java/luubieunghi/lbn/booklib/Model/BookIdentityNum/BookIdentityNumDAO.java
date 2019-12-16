package luubieunghi.lbn.booklib.Model.BookIdentityNum;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookIdentityNumDAO {
    @Insert
    void Insert(BookIdentityNum bookIdentityNum);

    @Delete
    void Delete(BookIdentityNum bookIdentityNum);

    @Update
    void Update(BookIdentityNum bookIdentityNum);

    @Query("SELECT * from bookidentitynum where bookidentitynum.bookID = :bookID")
    List<BookIdentityNum> getBookIDs(int bookID);
}
