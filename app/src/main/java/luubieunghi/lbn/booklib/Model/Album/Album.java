package luubieunghi.lbn.booklib.Model.Album;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Album")
public class Album implements Serializable {
    @PrimaryKey @NonNull
    private String albumID;

    @ColumnInfo(name = "AlbumName")
    private String albumName;

    @ColumnInfo(name = "ImagePath")
    private String imagePath;


    public Album(String albumID, String albumName, String imagePath) {
        this.albumID = albumID;
        this.albumName = albumName;
        this.imagePath = imagePath;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
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
