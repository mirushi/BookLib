package luubieunghi.lbn.booklib.Model.CurrentSong;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import luubieunghi.lbn.booklib.Model.Song.Song;

@Dao
public interface CurrentSong_DAO {
    @Query("select * from CurrentSong")
    CurrentSong getCurrentSong();

    @Query("delete from CurrentSong")
    int deleteAll();

    @Insert
    void insert(CurrentSong currentSong);
}
