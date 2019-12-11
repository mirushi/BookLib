package luubieunghi.lbn.booklib.Model.Book;

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
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import luubieunghi.lbn.booklib.R;

@Entity (tableName = "book")
public class Book {
    //Các biến dùng cho RPL.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookid")
    private int bookID = 0;

    @ColumnInfo(name = "booktitle")
    private String bookTitle;

    @ColumnInfo(name = "bcoverpath")
    private String bookCoverPath;

    @ColumnInfo(name = "bfilepath")
    private String bookFilePath;

    @ColumnInfo(name = "rating")
    private int rating;

    @ColumnInfo(name = "langid")
    private int langID;

    @ColumnInfo(name = "publisherid")
    private int publisherID;

    @ColumnInfo(name = "publishdate")
    private LocalDate publishDate;

    @ColumnInfo(name = "description")
    private String description;

    //Biến dùng để lưu giữ hình ảnh cover cho sách.
    @Ignore
    Bitmap bookImage;
    //Biến lưu giữ context.
    @Ignore
    Context context;

    public Book(int bookID, String bookCoverPath, String bookFilePath, String bookTitle, int rating,
                int langID, int publisherID, LocalDate publishDate, String description){

        //Gán giá trị bởi những biến được truyền vào.
        this.bookID = bookID;
        this.bookCoverPath = bookCoverPath;
        this.bookFilePath = bookFilePath;
        this.bookTitle = bookTitle;
        this.rating = rating;
        this.langID = langID;
        this.publisherID = publisherID;
        this.publishDate = publishDate;
        this.description = description;

        File imgFile = null;
        if (bookCoverPath != null)
            imgFile = new File(bookCoverPath);
        //Nếu đường dẫn đến file chứa cover sách hợp lệ.
        if (imgFile != null && imgFile.exists())
            this.bookImage = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            //Nếu đường dẫn không hợp lệ.
        else
        {
            Drawable icon_book = ContextCompat.getDrawable(context, R.drawable.icon_book);
            this.bookImage = drawableToBitmap(icon_book);
        }
        this.bookTitle = bookTitle;
    }

    //Đây là constructor có bookID.
    @Ignore
    public Book(Context context, int bookID, String bookCoverPath, String bookFilePath, String bookTitle, int rating,
                int langID, int publisherID, LocalDate publishDate, String description){
        this(bookID, bookCoverPath, bookFilePath,
                bookTitle, rating, langID, publisherID, publishDate, description);
        this.context = context;
    }

    //Constructor không có bookID.
    @Ignore
    public Book (Context context, String bookCoverPath, String bookFilePath, String bookTitle, int rating,
                 int langID, int publisherID, LocalDate publishDate, String description){
        //Khởi tạo mà không có bookID thì ta cho bằng 0;
        this(0, bookCoverPath, bookFilePath,
                bookTitle, rating, langID, publisherID, publishDate, description);
        this.context = context;
    }

    //Hàm chuyển từ Drawable qua Bitmap, phục vụ cho việc hiển thị ảnh trong asset của mình đã có.
    @Ignore
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

    //Hàm này chỉ để test thôi.
    @Ignore
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

    //Getters và Setters bên dưới.

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookCoverPath() {
        return bookCoverPath;
    }

    public void setBookCoverPath(String bookCoverPath) {
        this.bookCoverPath = bookCoverPath;
    }

    public String getBookFilePath() {
        return bookFilePath;
    }

    public void setBookFilePath(String bookFilePath) {
        this.bookFilePath = bookFilePath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLangID() {
        return langID;
    }

    public void setLangID(int langID) {
        this.langID = langID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
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

    public Bitmap getBookImage() {
        return bookImage;
    }

    public void setBookImage(Bitmap bookImage) {
        this.bookImage = bookImage;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
