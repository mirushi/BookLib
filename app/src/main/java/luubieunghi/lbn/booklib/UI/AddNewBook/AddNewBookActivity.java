package luubieunghi.lbn.booklib.UI.AddNewBook;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hbisoft.pickit.PickiT;
import com.hbisoft.pickit.PickiTCallbacks;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import luubieunghi.lbn.booklib.BookLib;
import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Author.Author;
import luubieunghi.lbn.booklib.Model.BookIdentityNum.BookIdentityNum;
import luubieunghi.lbn.booklib.Model.Language.Language;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Model.Tag.Tag;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.About.RatingView;
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

public class AddNewBookActivity extends AppCompatActivity implements AddNewBookContract.AddNewBookMVPView, PickiTCallbacks {

    //Biến định nghĩa requestCode cho việc chọn ảnh bìa của sách.
    final int requestCodeForBookCoverPathSelect = 317;

    //Biến dùng để hiển thị hộp thoại bảo người dùng chờ.
    final BookLoadingAlertDialog waitDialog = new BookLoadingAlertDialog(this);

    //Giữ tất cả component của View.
    Toolbar toolbar;
    ImageView bookCover;
    MaterialButton mbDateSelectionButton;
    EditText txtBookTitle;
    AutoCompleteTextView txtAuthor;
    RatingView ratingView;
    AutoCompleteTextView txtIDs;
    AutoCompleteTextView txtTags;
    SmartMaterialSpinner spinnerLanguage;
    AutoCompleteTextView txtPublisher;
    TextInputEditText txtPublishingDate;
    LocalDate ldPublishingDate;
    EditText txtDescription;

    //Giữ URI đến file được người dùng chọn.
    ArrayList<String> pathToFiles = new ArrayList<>();
    //Giữ URI đến file ảnh được người dùng chọn làm bìa sách.
    String pathToBookCover;

    //Giữ một chuỗi phân biệt xem đây là loại sách gì (Ebook, Audio Book).
    String bookType = "";

    Calendar calSelectedPublishingDate;
    private int mYear, mMonth, mDay;

    AddNewBookContract.AddNewBookMVPPresenter presenter;

    PickiT pickiT;

    private void SetupAutoCompleteTextView(){
        //Gán luôn auto complete cho author.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Author> authors = BookDatabase.getInstance(AddNewBookActivity.this).AuthorDAO().getAuthorList();
                List<String> authorsName = new ArrayList<>();
                for (Author author : authors){
                    authorsName.add(author.getAuthorName());
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewBookActivity.this,android.R.layout.select_dialog_item,authorsName);
                        txtAuthor.setThreshold(1);
                        txtAuthor.setAdapter(adapter);
                    }
                });
            }
        });
        //Gán luôn auto complete cho id.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<BookIdentityNum> idList = BookDatabase.getInstance(AddNewBookActivity.this).BookIdentityNumDAO().getAllBookIDs();
                List<String> ids = new ArrayList<>();
                for (BookIdentityNum id : idList){
                    ids.add(id.getIDName() + ":");
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewBookActivity.this,android.R.layout.select_dialog_item,ids);
                        txtIDs.setThreshold(1);
                        txtIDs.setAdapter(adapter);
                    }
                });
            }
        });

        //Gán luôn autocomplete cho tags.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Tag> tags = BookDatabase.getInstance(AddNewBookActivity.this).TagDAO().getAllTags();
                List<String> tagsName = new ArrayList<>();
                for (Tag tag : tags){
                    tagsName.add(tag.getTagContent());
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewBookActivity.this,android.R.layout.select_dialog_item,tagsName);
                        txtTags.setThreshold(1);
                        txtTags.setAdapter(adapter);
                    }
                });
            }
        });

        //Gán luôn autocomplete cho publisher.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Publisher> publishers = BookDatabase.getInstance(AddNewBookActivity.this).PublisherDAO().getAllPublisherStored();
                List<String> publishersName = new ArrayList<>();
                for (Publisher publisher : publishers){
                    publishersName.add(publisher.getPublisherName());
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddNewBookActivity.this,android.R.layout.select_dialog_item,publishersName);
                        txtPublisher.setThreshold(1);
                        txtPublisher.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);

        //Ánh xạ.
        AssignValues();

        //Ánh xạ xong thì ta cài gợi ý cho các AutoCompleteTextView.
        SetupAutoCompleteTextView();

        //Cấu hình Toolbar
        ConfigToolbar();

        //Cài đặt listeners cho các components.
        ConfigListeners();

        pickiT = new PickiT(this, this);

        //Test xem URI được truyền đúng chưa.
        //Toast.makeText(this, "URI = " + pathToFile, Toast.LENGTH_SHORT).show();
        for (String pathToFile : pathToFiles){
            Log.d("PATH_TO_FILE_STRING", pathToFile);
//            Log.d("PATH_TO_FILE_AB_PATH", Environment.getExternalStorageDirectory().getAbsolutePath());
        }
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
            Uri uri = data.getData();
            pickiT.getPath(uri, Build.VERSION.SDK_INT);
            //Load lại ảnh dựa trên sự lựa chọn của người dùng.
            Glide.with(this).load(uri).fitCenter().into(bookCover);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void AssignValues()
    {
        toolbar = (Toolbar)findViewById(R.id.activity_add_new_book_toolbar);
        bookCover = (ImageView)findViewById(R.id.activity_add_new_book_imgView_book_image);
        mbDateSelectionButton = (MaterialButton)findViewById(R.id.activity_add_new_book_btn_choose_publishing_date);
        txtBookTitle = (EditText)findViewById(R.id.activity_add_new_book_edt_title);
        txtAuthor = (AutoCompleteTextView) findViewById(R.id.activity_add_new_book_edt_author);
        ratingView = (RatingView)findViewById(R.id.activity_add_new_book_ratingview_rating);
        txtIDs = (AutoCompleteTextView) findViewById(R.id.activity_add_new_book_edt_ids);
        txtTags = (AutoCompleteTextView) findViewById(R.id.activity_add_new_book_edt_tags);
        spinnerLanguage = (SmartMaterialSpinner)findViewById(R.id.activity_add_new_book_spinner_language);
        txtPublisher = (AutoCompleteTextView) findViewById(R.id.activity_add_new_book_edt_publisher);
        txtPublishingDate = (TextInputEditText)findViewById(R.id.activity_add_new_book_edt_publishing_date);
        ldPublishingDate = LocalDate.now();
        txtDescription = (EditText) findViewById(R.id.activity_add_new_book_edt_description);

        //Gán giá trị bắt đầu mặc định cho việc chọn ngày là ngày hôm nay.
        Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DATE);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        //Gán giá trị từ đường dẫn người dùng chọn file.
        try
        {
            pathToFiles = getIntent().getExtras().getStringArrayList("EXTRA_BOOK_URI");
            bookType = getIntent().getExtras().getString("BOOK_TYPE");
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }

        //Thêm ngôn ngữ hiện có vào spinner luôn nào.
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Language> languages = BookDatabase.getInstance(AddNewBookActivity.this).LanguageDAO().getAllStoredLanguages();
                List<String> languagesName = new ArrayList<>();
                for (Language language : languages){
                    languagesName.add(language.getLangName());
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        spinnerLanguage.setItem(languagesName);
                        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
            }
        });

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
        //Khi hoàn thành rồi thì phát tính hiệu để fragment biết mà update lại danh sách các quyển sách.
        Intent intent = new Intent("book_list_updated");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    @Override
    public void BookAddedFailure(String reason) {
        //Hiển thị thông báo có lỗi xảy ra khi thêm sách.
        Toast.makeText(this, "Something wrong." + reason, Toast.LENGTH_LONG).show();
    }

    public String getBookType(){
        return bookType;
    }


    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        pathToBookCover = path;
    }
}
