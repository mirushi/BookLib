package luubieunghi.lbn.booklib.Model.CurrentSong;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import luubieunghi.lbn.booklib.Model.Song.Song;

@Entity(tableName = "CurrentSong")
public class CurrentSong implements Serializable {
    @PrimaryKey @NonNull
    private String songID;

    @ColumnInfo(name = "CurrentPosition")
    private int currentPosition;

    public CurrentSong(String songID, int currentPosition) {
        this.songID = songID;
        this.currentPosition = currentPosition;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
