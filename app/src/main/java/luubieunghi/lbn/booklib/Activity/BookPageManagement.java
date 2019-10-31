package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Fragment.Frm_1;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.adapter.BookPageManagementAdapter;
import luubieunghi.lbn.booklib.adapter.ViewPagerAdapter;
import luubieunghi.lbn.booklib.model.Book;

public class BookPageManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> dsForm;
    private BookPageManagementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page_management);
        recyclerView=findViewById(R.id.recyclerview_book_page_management);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        dsForm=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            dsForm.add("Page "+(i+1));
        }
        adapter=new BookPageManagementAdapter(BookPageManagement.this,dsForm);
        recyclerView.setAdapter(adapter);
    }
}
