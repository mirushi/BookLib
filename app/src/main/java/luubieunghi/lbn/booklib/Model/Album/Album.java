package luubieunghi.lbn.booklib.Model.Album;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Album")
public class Album implements Serializable {
    @PrimaryKey
    private String AlbumID;

    @ColumnInfo(name = "AlbumName")
    private String albumName;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;

    public Album(){};

    public Album(String albumID, String albumName, String imagePath) {
        AlbumID = albumID;
        this.albumName = albumName;
        this.imagePath = imagePath;
    }

    public String getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(String albumID) {
        AlbumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
