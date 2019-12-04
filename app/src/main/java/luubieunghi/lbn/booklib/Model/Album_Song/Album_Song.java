package luubieunghi.lbn.booklib.Model.Album_Song;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Album_Song")
public class Album_Song {

    @PrimaryKey
    private String AlbumID;
    @PrimaryKey
    private String songID;

    public Album_Song(){};

    public Album_Song(String albumID, String songID) {
        AlbumID = albumID;
        this.songID = songID;
    }

    public String getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(String albumID) {
        AlbumID = albumID;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }
}
