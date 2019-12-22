package luubieunghi.lbn.booklib.Model.Author;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class AuthorDAO {

    @Query("Select * from author")
    public abstract List<Author> getAuthorList();

    //Tìm danh sách tác giả dựa vào chuỗi.
    @Query("Select * from author where authorName like '%'||:subName||'%'")
    public abstract List<Author> searchForAuthor(String subName);

    @Query("Select * from author where authorName = :name")
    public abstract Author searchForExactAuthor(String name);

    @Insert
    public abstract long insertAuthor(Author author);

    @Update
    public abstract void updateAuthor(Author author);

    @Delete
    public abstract void deleteAuthor(Author author);

    @Query("DELETE FROM author")
    public abstract void nukeTable();

    @Transaction
    public List<Author> insertIfNotExistAndReturnAuthors(String[] authorsName){
        List<Author> result = new ArrayList<>();
        for (String authorName : authorsName){
            Author author = searchForExactAuthor(authorName);
            //Nếu tác giả không tồn tại thì ta phải insert vào CSDL.
            if (author == null){
                author = new Author(0, authorName);
                Long authorID = insertAuthor(author);
                author.setAuthorID(authorID);
            }
            //Sau đó thêm tác giả vào kết quả trả về.
            result.add(author);
        }
        //Hết vòng lặp rồi thì trả về các Author mà nó Query được.
        return result;
    }
}
