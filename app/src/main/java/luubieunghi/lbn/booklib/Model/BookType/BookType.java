package luubieunghi.lbn.booklib.Model.BookType;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookType")
public class BookType {
    @PrimaryKey
    @ColumnInfo(name = "bTypeID")
    private long bTypeID;

    @ColumnInfo(name = "bTypeName")
    private String bTypeName;

    public BookType(long bTypeID, String bTypeName) {
        this.bTypeID = bTypeID;
        this.bTypeName = bTypeName;
    }

    public long getBTypeID() {
        return bTypeID;
    }

    public void setBTypeID(long bTypeID) {
        this.bTypeID = bTypeID;
    }

    public String getBTypeName() {
        return bTypeName;
    }

    public void setBTypeName(String bTypeName) {
        this.bTypeName = bTypeName;
    }
}
