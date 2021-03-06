package luubieunghi.lbn.booklib.Model.Tag;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tag", indices = {@Index(value = "tagContent", unique = true)})
public class Tag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tagID")
    private long tagID;

    public Tag(long tagID, String tagContent) {
        this.tagID = tagID;
        this.tagContent = tagContent;
    }

    public long getTagID() {
        return tagID;
    }

    public void setTagID(long tagID) {
        this.tagID = tagID;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    @ColumnInfo(name = "tagContent")
    private String tagContent;
}
