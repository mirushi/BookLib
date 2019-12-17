package luubieunghi.lbn.booklib.UI.AddNewBook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.About.RatingView;
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;

public class AddNewBookActivity extends AppCompatActivity implements AddNewBookContract.AddNewBookMVPView {

    //Biến định nghĩa requestCode cho việc chọn ảnh bìa của sách.
    final int requestCodeForBookCoverPathSelect = 317;

    //Biến dùng để hiển thị hộp thoại bảo người dùng chờ.
    final BookLoadingAlertDialog waitDialog = new BookLoadingAlertDialog(this);

    //Giữ tất cả component của View.
    Toolbar toolbar;
    ImageView bookCover;
    MaterialButton mbDateSelectionButton;
    EditText txtBookTitle;
    EditText txtAuthor;
    RatingView ratingView;
    EditText txtIDs;
    EditText txtTags;
    Spinner spinnerLanguage;
    EditText txtPublisher;
    TextInputEditText txtPublishingDate;
    LocalDate ldPublishingDate;
    EditText txtDescription;

    //Giữ URI đến file được người dùng chọn.
    Uri pathToFile;
    //Giữ URI đến file ảnh được người dùng chọn làm bìa sách.
    Uri pathToBookCover;

    Calendar calSelectedPublishingDate;
    private int mYear, mMonth, mDay;

    AddNewBookContract.AddNewBookMVPPresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        //Ánh xạ.
        AssignValues();

        //Cấu hình Toolbar
        ConfigToolbar();

        //Cài đặt listeners cho các components.
        ConfigListeners();

        //Test xem URI được truyền đúng chưa.
        Toast.makeText(this, "URI = " + pathToFile, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeForBookCoverPathSelect && resultCode == RESULT_OK){
            //Lấy đường dẫn đến ảnh người dùng chọn.
            pathToBookCover = data.getData();
            //Load lại ảnh dựa trên sự lựa chọn của người dùng.
            Glide.with(this).load(pathToBookCover).fitCenter().into(bookCover);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AssignValues()
    {
        toolbar = (Toolbar)findViewById(R.id.activity_add_new_book_toolbar);
        bookCover = (ImageView)findViewById(R.id.activity_add_new_book_imgView_book_image);
        mbDateSelectionButton = (MaterialButton)findViewById(R.id.activity_add_new_book_btn_choose_publishing_date);
        txtBookTitle = (EditText)findViewById(R.id.activity_add_new_book_edt_title);
        txtAuthor = (EditText)findViewById(R.id.activity_add_new_book_edt_author);
        ratingView = (RatingView)findViewById(R.id.activity_add_new_book_ratingview_rating);
        txtIDs = (EditText)findViewById(R.id.activity_add_new_book_edt_ids);
        txtTags = (EditText)findViewById(R.id.activity_add_new_book_edt_tags);
        spinnerLanguage = (Spinner)findViewById(R.id.activity_add_new_book_spinner_language);
        txtPublisher = (EditText)findViewById(R.id.activity_add_new_book_edt_publisher);
        txtPublishingDate = (TextInputEditText)findViewById(R.id.activity_add_new_book_edt_publishing_date);
        ldPublishingDate = LocalDate.now();
        txtDescription = (EditText)findViewById(R.id.activity_add_new_book_edt_description);

        //Gán giá trị bắt đầu mặc định cho việc chọn ngày là ngày hôm nay.
        Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DATE);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        //Gán giá trị từ đường dẫn người dùng chọn file.
        pathToFile = getIntent().getExtras().getParcelable("EXTRA_BOOK_URI");

        //Tạo mới Presenter luôn.
        presenter = new AddNewBookPresenter(this);
    }

    private void ConfigToolbar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm sách mới");
        toolbar.inflateMenu(R.menu.menu_add_new_book);

        //OnClickListener cho toolbar.
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add_new_book_finished);
                {
                    waitDialog.showDialog();
                    presenter.AddBook();
                }
                return false;
            }
        });
    }

    private void ConfigListeners()
    {
        //Cài đặt listener cho nút chọn ngày.
        mbDateSelectionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddNewBookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtPublishingDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                                //Gán lại ngày tháng luôn để lần sau có chỉnh thì chỉnh tiếp từ ngay tháng trước.
                                mDay = dayOfMonth;
                                mMonth = monthOfYear;
                                mYear = year;
                                ldPublishingDate = LocalDate.of(year, monthOfYear, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);

                dpd.show();
            }
        });

        //Cài đặt listener cho cover khi bấm vào hình.
        bookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Khi bấm vào bìa sách, sẽ cho thay đổi bìa sách.
                Intent intentOpenBookCoverSelect = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentOpenBookCoverSelect, "Select book cover image"),requestCodeForBookCoverPathSelect);
            }
        });
    }

    @Override
    public void BookAddedSuccess() {
        //Hiển thị thông báo sách được thêm thành công.
        Toast.makeText(this, "Book added succcessfully !", Toast.LENGTH_LONG);
        waitDialog.hideDialog();
        finish();
    }

    @Override
    public void BookAddedFailure(String reason) {
        //Hiển thị thông báo có lỗi xảy ra khi thêm sách.
        Toast.makeText(this, "Something wrong." + reason, Toast.LENGTH_LONG).show();
    }


}
