package luubieunghi.lbn.booklib.UI.BookPageManagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import luubieunghi.lbn.booklib.Adapter.PageManagementRecyclerViewAdapter;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.Main.MainActivity;

public class PageManagementActivity extends AppCompatActivity implements PageManagementContract.PageManagermentView {
    ImageButton btn_img_Back;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_management);

        generalSetUp();
        setUpView();
        setUpEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_page_management, menu);
        return true;
    }

    @Override
    public void generalSetUp() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void setUpView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_management_tool_bar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.page_management_recycler_view);
        btn_img_Back = findViewById(R.id.toolbar_btn_img_back);
        searchView = findViewById(R.id.toolbar_search_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<PageManagementRecyclerViewItem> pageList = new ArrayList<>();
        pageList.add(new PageManagementRecyclerViewItem("1", R.drawable.shadow_replace));
        pageList.add(new PageManagementRecyclerViewItem("2", R.drawable.shadow_replace));
        pageList.add(new PageManagementRecyclerViewItem("3", R.drawable.shadow_replace));
        PageManagementRecyclerViewAdapter pageManagementRecyclerViewAdapter = new PageManagementRecyclerViewAdapter(pageList, getApplicationContext());
        recyclerView.setAdapter(pageManagementRecyclerViewAdapter);


    }

    @Override
    public void setUpEvent() {
        btn_img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageManagementActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
