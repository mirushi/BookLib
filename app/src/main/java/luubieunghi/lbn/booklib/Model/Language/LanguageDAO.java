package luubieunghi.lbn.booklib.Model.Language;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LanguageDAO {
    @Insert
    void Insert(Language language);

    @Delete
    void Delete(Language language);

    @Update
    void Update(Language language);

    @Query("Select * from language")
    List<Language> getAllStoredLanguages();

    @Query("Select * from language where Language.langName like '%' || :subLangName || '%'")
    List<Language> searchForLanguages(String subLangName);

    @Query("Select * from language where Language.langName = :langName")
    List<Language> searchForExactLanguageName(String langName);

    @Query("DELETE FROM LANGUAGE")
    void nukeTable();
}
