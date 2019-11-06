package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BookPageManagementAdapter;
import luubieunghi.lbn.booklib.adapter.ViewPagerAdapter;
import luubieunghi.lbn.booklib.model.Book;

public class BookPageManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> dsForm;
    private BookPageManagementAdapter adapter;
    private Toolbar toolbar;

    ImageButton btn_img_Back_Main_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page_management);
        addControls();
        addEvents();
        setUp();

    }

    private void setUp() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.book_page_management_menu);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        dsForm=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            //dsForm.add("Page "+(i+1));
            dsForm.add(getResources().getString(R.string.content)+getResources().getString(R.string.content));
        }
        adapter=new BookPageManagementAdapter(BookPageManagement.this,dsForm);
        recyclerView.setAdapter(adapter);
    }

    private void addEvents() {

        //bắt sự kiện theo từng item menu clicked
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_item_text_format_book_page_management:
                    {
                        break;
                    }
                    case R.id.menu_item_share_book_page_management:{
                        break;
                    }
                    case R.id.menu_item_feedback_book_page_management:{
                        break;
                    }
                    case R.id.menu_item_setting_book_page_management:{
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });

        btn_img_Back_Main_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookPageManagement.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });



    }

    private void addControls() {
        toolbar=findViewById(R.id.toolbar_book_page_management);
        recyclerView=findViewById(R.id.recyclerview_book_page_management);

        btn_img_Back_Main_Activity=findViewById(R.id.btn_img_back_book_page_management);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.book_page_management_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
