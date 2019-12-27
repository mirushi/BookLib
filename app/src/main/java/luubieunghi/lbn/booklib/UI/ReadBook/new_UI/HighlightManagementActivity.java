package luubieunghi.lbn.booklib.UI.ReadBook.new_UI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.folioreader.ui.fragment.HighlightFragment;

import luubieunghi.lbn.booklib.R;

public class HighlightManagementActivity extends AppCompatActivity {
    // minh co path => luu vao bookId laf ok

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlight_management_activity);
        HighlightFragment highlightFragment = HighlightFragment.newInstance("urn:uuid:402eb356-5978-4c0e-9a23-1e63d8be1380", "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.test_layout, highlightFragment);
        ft.commit();
    }
}
