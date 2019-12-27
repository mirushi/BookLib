package luubieunghi.lbn.booklib.UI.ReadBook.new_UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.folioreader.model.locators.ReadLocator;
import com.folioreader.ui.fragment.HighlightFragment;

import java.util.List;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.TestFragment;
import luubieunghi.lbn.booklib.UI.CustomAlertDialog.BookLoadingAlertDialog;
import luubieunghi.lbn.booklib.Utility.Others.AppExecutors;

public class HighlightManagementActivity extends AppCompatActivity {
    // minh co path => luu vao bookId laf ok

    List<BookFile> listBookFile;
    BookLoadingAlertDialog dialog;
    String link = "";
    Book book;
    long book_ID;
    String mBFileID;
    String mBFileTitle;
    String filePath;
    String LOG_TAG = "HIGHLIGHTMNG_FRM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highlight_management_activity);
        dialog = new BookLoadingAlertDialog(this);

        receiveData();
    }

    private void receiveData() {
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra("book");
        book_ID = book.getBookID();

        dialog.showDialog();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                listBookFile = BookDatabase.getInstance(HighlightManagementActivity.this).BookFileDAO().getAllFilesOfBook(book_ID);
                if (listBookFile.size() <= 0)
                    return;

                mBFileID = listBookFile.get(0).getBFileID();
                mBFileTitle = listBookFile.get(0).getBFileTitle();

                dialog.hideDialog();
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        setUpView();
                    }
                });
            }
        });
    }

    private void setUpView() {
        if (mBFileID != null) {
            HighlightFragment highlightFragment = HighlightFragment.newInstance(mBFileID, "book");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.test_layout, highlightFragment);
            //TestFragment test = new TestFragment();
          //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
          //  ft.replace(R.id.test_layout, test);
            ft.commit();
        }
    }

}
