package luubieunghi.lbn.booklib.UI.ReadBook;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import luubieunghi.lbn.booklib.R;

public class BookReadingThirdPageFrm extends Fragment {

    public BookReadingThirdPageFrm() {
        //
    }

    BookReadingDefineFrm defineFrm;
    BookReadingHighlightNoteFrm highlightNoteFrm;
    View view;
    TextView textView;
    CustomActionMode callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        defineFrm = new BookReadingDefineFrm();
        highlightNoteFrm = new BookReadingHighlightNoteFrm(getContext());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third_page_frm, container, false);
        textView = view.findViewById(R.id.frm_3_tw);
        if (textView == null) {
            Log.e("Frm_3:", "On create Root View NULL!");
        } else
            Log.e("Frm_3:", "On create Root View NOT NULL!");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        callback = new CustomActionMode(this.getContext()) {
            int tmp_id_item = 0;

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.custom_one) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.third_page_layout, highlightNoteFrm);
                    tmp_id_item = 1;
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.custom_two) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.third_page_layout, defineFrm);
                    tmp_id_item = 2;
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.custom_three) {
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                if(tmp_id_item==1) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(highlightNoteFrm);
                    transaction.commit();
                }
                else
                    if(tmp_id_item==2)
                {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(defineFrm);
                    transaction.commit();
                }
            }
        };
        textView.setCustomSelectionActionModeCallback(callback);
    }

    @Override
    public String toString() {
        String str_return = null;
        if (view == null) {
            str_return = "Frm_3: Root View NULL!";
        } else
            str_return = "Frm_3: Root View is created!";
        Log.e("Frm_3:", str_return);
        return str_return;
    }

}
