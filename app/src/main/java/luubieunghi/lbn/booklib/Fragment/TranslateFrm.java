package luubieunghi.lbn.booklib.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import luubieunghi.lbn.booklib.R;

public class TranslateFrm extends Fragment {

    public TranslateFrm() {
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
        return inflater.inflate(R.layout.fragment_translate_frm, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
