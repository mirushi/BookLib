package luubieunghi.lbn.booklib.Model.BookType;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookType")
public class BookType {
    @PrimaryKey
    @ColumnInfo(name = "bTypeID")
    private int bTypeID;

    @ColumnInfo(name = "bTypeName")
    private String bTypeName;

    public BookType(int bTypeID, String bTypeName) {
        this.bTypeID = bTypeID;
        this.bTypeName = bTypeName;
    }

    public int getBTypeID() {
        return bTypeID;
    }

    public void setBTypeID(int bTypeID) {
        this.bTypeID = bTypeID;
    }

    public String getBTypeName() {
        return bTypeName;
    }

    public void setBTypeName(String bTypeName) {
        this.bTypeName = bTypeName;
    }
}
