package luubieunghi.lbn.booklib.UI.ReadBook.old_UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import luubieunghi.lbn.booklib.R;

public class BookReadingDefineFrm extends Fragment implements DefineFrmContract.DefineFrmView {

    Context context;
    View view;
    String word;

    public BookReadingDefineFrm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_define_frm, container, false);
        setUpView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setWord(String text) {
        this.word = text;
    }
}
