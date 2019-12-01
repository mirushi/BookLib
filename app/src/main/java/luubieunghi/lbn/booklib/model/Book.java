package luubieunghi.lbn.booklib.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import luubieunghi.lbn.booklib.R;

public class Book {
    Bitmap bookImage;
    String bookTitle;
    ArrayList<String> bookAuthors;
    int rating;
    ArrayList<String> ids;
    ArrayList<String> tags;
    String language;
    String publisher;
    LocalDate publishDate;
    String description;

    Context context;

    public Book(Context context, String _bookImagePath, String _bookTitle){
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

        //Set những cái còn lại thành null hết.
    }

    public Book (Context context, String _bookImagePath, String _bookTitle,
                 ArrayList<String> _bookAuthors, int _rating, ArrayList<String> _ids, ArrayList<String> _tags,
                 String _language, String _publisher, LocalDate _publishDate, String _description){

        //Gán giá trị bởi những biến được truyền vào.
        bookAuthors = _bookAuthors;
        rating = _rating;
        ids = _ids;
        tags = _tags;
        language = _language;
        publisher = _publisher;
        publishDate = _publishDate;
        description = _description;

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

    public ArrayList<String> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(ArrayList<String> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
