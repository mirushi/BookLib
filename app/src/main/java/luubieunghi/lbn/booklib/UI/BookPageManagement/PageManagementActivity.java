package luubieunghi.lbn.booklib.UI.BookPageManagement;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.PageManagementRecyclerViewAdapter;

public class PageManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_management_tool_bar);
        setSupportActionBar(toolbar);
        initiateValue();
    }

    public void initiateValue()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.page_management_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<PageManagementRecyclerViewItem> pageList = new ArrayList<>();
        pageList.add(new PageManagementRecyclerViewItem("1",R.drawable.shadow_replace));
        pageList.add(new PageManagementRecyclerViewItem("2",R.drawable.shadow_replace));
        pageList.add(new PageManagementRecyclerViewItem("3",R.drawable.shadow_replace));
        PageManagementRecyclerViewAdapter pageManagementRecyclerViewAdapter = new PageManagementRecyclerViewAdapter(pageList, getApplicationContext());
        recyclerView.setAdapter(pageManagementRecyclerViewAdapter);
        if(android.os.Build.VERSION.SDK_INT >=21)
        {
            Window window =this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        double fitSizeY = getScreenHeight()*0.6;
        //WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(getScreenWidth(), (int)fitSizeY);
        //recyclerView.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_page_management, menu);
        return true;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
