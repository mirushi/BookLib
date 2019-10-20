package luubieunghi.lbn.booklib.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;

import androidx.core.content.ContextCompat;
import luubieunghi.lbn.booklib.R;

public class Book {
    Bitmap bookImage;
    String bookTitle;
    Context context;
    public Book (Context context, String _bookImagePath, String _bookTitle){
        File imgFile = null;
        if (_bookImagePath != null)
            imgFile = new File(_bookImagePath);
        //Nếu đường dẫn đến file chứa cover sách hợp lệ.
        if (imgFile != null && imgFile.exists())
            bookImage = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        //Nếu đường dẫn không hợp lệ.
        else
        {
            Drawable icon_book = ContextCompat.getDrawable(context, R.drawable.icon_book);
            bookImage = drawableToBitmap(icon_book);
        }
        bookTitle = _bookTitle;
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            if (bitmapDrawable.getBitmap() != null)
            {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0)
        {
            bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
        }
        else
        {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap getBookImage() {
        return bookImage;
    }

    public void setBookImage(Bitmap bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
