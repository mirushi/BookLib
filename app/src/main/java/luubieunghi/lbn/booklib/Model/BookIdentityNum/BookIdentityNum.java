package luubieunghi.lbn.booklib.Model.BookIdentityNum;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import luubieunghi.lbn.booklib.Model.Book.Book;

@Entity(tableName = "bookidentitynum",
        primaryKeys = {"bookID", "IDName"},
        foreignKeys = @ForeignKey(entity = Book.class, parentColumns = "bookID", childColumns = "bookID"))
public class BookIdentityNum {
    @ColumnInfo(name = "bookID")
    private int bookID;

    @NonNull
    @ColumnInfo(name = "IDName")
    private String IDName;

    @ColumnInfo(name = "IDValue")
    private String IDValue;

    public BookIdentityNum(int bookID, @NonNull String IDName, String IDValue) {
        this.bookID = bookID;
        this.IDName = IDName;
        this.IDValue = IDValue;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    @NonNull
    public String getIDName() {
        return IDName;
    }

    public void setIDName(@NonNull String IDName) {
        this.IDName = IDName;
    }

    public String getIDValue() {
        return IDValue;
    }

    public void setIDValue(String IDValue) {
        this.IDValue = IDValue;
    }
}
