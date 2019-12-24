package luubieunghi.lbn.booklib.Model.Author;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AuthorDAO {

    @Query("Select * from author")
    List<Author> getAuthorList();

    //Tìm danh sách tác giả dựa vào chuỗi.
    @Query("Select * from author where authorName like '%'||:subName||'%'")
    List<Author> searchForAuthor(String subName);

    @Insert
    void insertAuthor(Author author);

    @Update
    void updateAuthor(Author author);

    @Delete
    void deleteAuthor(Author author);

    @Query("DELETE FROM author")
    void nukeTable();
}
