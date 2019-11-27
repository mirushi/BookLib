package luubieunghi.lbn.booklib.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import luubieunghi.lbn.booklib.R;

public class AddNewBookActivity extends AppCompatActivity {

    Toolbar toolbar;
    MaterialButton mbDateSelectionButton;
    TextInputEditText txtPublishingDate;
    Calendar calSelectedPublishingDate;
    private int mYear, mMonth, mDay;

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void AssignValues()
    {
        toolbar = (Toolbar)findViewById(R.id.activity_add_new_book_toolbar);
        mbDateSelectionButton = (MaterialButton)findViewById(R.id.activity_add_new_book_btn_choose_publishing_date);
        txtPublishingDate = (TextInputEditText)findViewById(R.id.activity_add_new_book_edt_publishing_date);

        //Gán giá trị bắt đầu mặc định cho việc chọn ngày là ngày hôm nay.
        Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DATE);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);
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
                    Intent intent = new Intent(AddNewBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void ConfigListeners()
    {
        mbDateSelectionButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(AddNewBookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

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
                            }
                        }, mYear, mMonth, mDay);

                dpd.show();
            }
        });
    }
}
