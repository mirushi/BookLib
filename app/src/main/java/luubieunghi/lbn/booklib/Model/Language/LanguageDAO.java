package luubieunghi.lbn.booklib.Model.Language;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class LanguageDAO {
    @Insert
    public abstract long Insert(Language language);

    @Delete
    public abstract void Delete(Language language);

    @Update
    public abstract void Update(Language language);

    @Transaction
    public long InsertIfNotExists(Language language){
        List<Language> detect = searchForExactLanguageName(language.getLangName());
        if (detect.size() > 0){
            return detect.get(0).getLangID();
        }
        else{
            return Insert(language);
        }
    }

    @Query("Delete from language where language.langName = :lName")
    public abstract void deleteExactLanguageName(String lName);

    @Query("Select * from language")
    public abstract List<Language> getAllStoredLanguages();

    @Query("Select * from language where Language.langName like '%' || :subLangName || '%'")
    public abstract List<Language> searchForLanguages(String subLangName);

    @Query("Select * from language where Language.langName = :langName")
    public abstract List<Language> searchForExactLanguageName(String langName);

    @Query("DELETE FROM LANGUAGE")
    public abstract void nukeTable();
}
