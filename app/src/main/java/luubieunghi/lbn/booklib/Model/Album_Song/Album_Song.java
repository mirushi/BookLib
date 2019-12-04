package luubieunghi.lbn.booklib.Model.Album_Song;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "Album_Song", primaryKeys = {"AlbumID","SongID"})
public class Album_Song {

    @NonNull
    private String AlbumID;

    @NonNull
    private String SongID;

    public Album_Song(String AlbumID, String SongID) {
        this.AlbumID = AlbumID;
        this.SongID = SongID;
    }

    public String getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(String AlbumID) {
        this.AlbumID = AlbumID;
    }

    public String getSongID() {
        return SongID;
    }

    public void setSongID(String SongID) {
        this.SongID = SongID;
    }
}
