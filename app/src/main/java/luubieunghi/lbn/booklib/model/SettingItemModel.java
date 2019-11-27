package luubieunghi.lbn.booklib.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class SettingItemModel {

    private String title;
    private String description;
    private Drawable image;

    public SettingItemModel(String title, String description, Drawable drawable){
        this.title = title;
        this.description = description;
        this.image = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
