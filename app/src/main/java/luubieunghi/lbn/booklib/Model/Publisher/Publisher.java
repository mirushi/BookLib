package luubieunghi.lbn.booklib.Model.Publisher;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "publisher")
public class Publisher {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "publisherID")
    private long publisherID;

    @ColumnInfo(name = "publisherName")
    private String publisherName;

    public Publisher(long publisherID, String publisherName) {
        this.publisherID = publisherID;
        this.publisherName = publisherName;
    }

    public long getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(long publisherID) {
        this.publisherID = publisherID;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
