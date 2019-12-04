package luubieunghi.lbn.booklib.Model.Song;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//dùng để test cho list view của activity list song

@Entity(tableName = "Song")
public class Song implements Serializable {

    @PrimaryKey
    private String SongID;

    @ColumnInfo(name = "SongName")
    private String songName;

    @ColumnInfo(name = "FilePath")
    private String filePath;

    @ColumnInfo(name = "Duration")
    private int duration;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;

    @ColumnInfo(name = "Artists")
    private String artists;

    public Song() {
    }

    public Song(String songID, String songName, String filePath, int duration, String imagePath, String artists) {
        SongID = songID;
        this.songName = songName;
        this.filePath = filePath;
        this.duration = duration;
        this.imagePath = imagePath;
        this.artists = artists;
    }

    public String getSongID() {
        return SongID;
    }

    public void setSongID(String songID) {
        SongID = songID;
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

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
}
