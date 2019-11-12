package luubieunghi.lbn.booklib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import luubieunghi.lbn.booklib.R;

public class AddNewBookActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        ConfigToolbar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_new_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void ConfigToolbar()
    {
        toolbar = (Toolbar)findViewById(R.id.activity_add_new_book_toolbar);
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
}
