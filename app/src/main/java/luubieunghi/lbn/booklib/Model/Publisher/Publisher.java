package luubieunghi.lbn.booklib.Model.Publisher;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "publisher")
public class Publisher {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "publisherID")
    private int publisherID;

    @ColumnInfo(name = "publisherName")
    private String publisherName;

    public Publisher(int publisherID, String publisherName) {
        this.publisherID = publisherID;
        this.publisherName = publisherName;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
