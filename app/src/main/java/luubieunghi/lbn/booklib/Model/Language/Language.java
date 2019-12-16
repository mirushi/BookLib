package luubieunghi.lbn.booklib.Model.Language;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "language")
public class Language {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "langID")
    private int langID;

    @ColumnInfo(name = "langName")
    private String langName;

    public Language(int langID, String langName) {
        this.langID = langID;
        this.langName = langName;
    }

    public int getLangID() {
        return langID;
    }

    public void setLangID(int langID) {
        this.langID = langID;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }
}
