package luubieunghi.lbn.booklib.Model.Song;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//dùng để test cho list view của activity list song

@Entity(tableName = "Song")
public class Song implements Serializable {

    @PrimaryKey @NonNull
    private String songID;

    @ColumnInfo(name = "SongName")
    private String songName;

    @ColumnInfo(name = "FilePath")
    private String filePath;

    @ColumnInfo(name = "Duration")
    private int duration;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;

    @ColumnInfo(name = "ArtistNames")
    private String artistNames;

    public Song(String songID, String songName, String filePath, int duration, String imagePath, String artistNames) {
        this.songID = songID;
        this.songName = songName;
        this.filePath = filePath;
        this.duration = duration;
        this.imagePath = imagePath;
        this.artistNames = artistNames;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getArtistNames() {
        return artistNames;
    }

    public void setArtistNames(String artistNames) {
        this.artistNames = artistNames;
    }
}
