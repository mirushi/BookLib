package luubieunghi.lbn.booklib.Model.BookFile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import luubieunghi.lbn.booklib.Model.Book.Book;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "bookfile",
        indices = {@Index(value = {"bookID"}, unique = true)},
        foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = Book.class,parentColumns = "bookID", childColumns = "bookID")})

public class BookFile {

    //Các biến dùng cho RSL.
    @ColumnInfo(name = "bookID")
    private long bookID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bFileOrder")
    private int bFileOrder = 0;

    @ColumnInfo(name = "bFilePath")
    private String bFilePath;

    @ColumnInfo(name = "bRead")
    private long bRead;

    @ColumnInfo(name = "bTotal")
    private long bTotal;

    public BookFile(long bookID, int bFileOrder, String bFilePath, long bRead, long bTotal) {
        this.bookID = bookID;
        this.bFileOrder = bFileOrder;
        this.bFilePath = bFilePath;
        this.bRead = bRead;
        this.bTotal = bTotal;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public int getBFileOrder() {
        return bFileOrder;
    }

    public void setBFileOrder(int bFileOrder) {
        this.bFileOrder = bFileOrder;
    }

    public String getBFilePath() {
        return bFilePath;
    }

    public void setBFilePath(String bFilePath) {
        this.bFilePath = bFilePath;
    }

    public long getBRead() {
        return bRead;
    }

    public void setBRead(long bRead) {
        this.bRead = bRead;
    }

    public long getBTotal() {
        return bTotal;
    }

    public void setBTotal(long bTotal) {
        this.bTotal = bTotal;
    }
}
