package luubieunghi.lbn.booklib.Model.Language;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language")
public class Language {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "langID")
    private long langID;

    @ColumnInfo(name = "langName")
    private String langName;

    public Language(long langID, String langName) {
        this.langID = langID;
        this.langName = langName;
    }

    public long getLangID() {
        return langID;
    }

    public void setLangID(long langID) {
        this.langID = langID;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
}
