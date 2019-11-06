package luubieunghi.lbn.booklib.Fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import luubieunghi.lbn.booklib.Activity.PageManagementActivity;
import luubieunghi.lbn.booklib.CustomActionMode;
import luubieunghi.lbn.booklib.R;

public class BookReadingFirstPageFrm extends Fragment {

    private BookReadingDefineFrm defineFrm;
    private BookReadingTranslateFrm translateFrm;
    private BookReadingHighlightNoteFrm highlightNoteFrm;
    protected View view;
    protected ImageView clickTextViewBackGround;
    protected TextView readTextView;
    private CustomActionMode callback;
    protected Animation anim_ZoomOut;

    public BookReadingFirstPageFrm() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_page_frm, container, false);
        initiateValue();
        if (readTextView == null) {
            Log.e("Frm_1:", "On create Root View NULL!");
        } else
            Log.e("Frm_1:", "On create Root View NOT NULL!");
        return view;
    }

    private void initiateValue() {
        defineFrm = new BookReadingDefineFrm();
        highlightNoteFrm = new BookReadingHighlightNoteFrm();
        translateFrm = new BookReadingTranslateFrm();
        readTextView = view.findViewById(R.id.frm_1_read_textview);
        clickTextViewBackGround = view.findViewById(R.id.frm_1_imgview);
        anim_ZoomOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.text_view_zoom_out);
    }

    @Override
    public void onResume() {
        super.onResume();
//xu ly chon mot tu / nhom tu
        onTextLongTouched();
//xu ly chon vao page
        onTextViewClicked();
    }

    @Override
    public String toString() {
        String str_return = null;
        if (view == null) {
            str_return = "Frm_1: Root View NULL!";
        } else
            str_return = "Frm_1: Root View is created!";
        Log.e("Frm_1:", str_return);
        return str_return;
    }

    private void onTextLongTouched() {
        callback = new CustomActionMode(this.getContext()) {
            int tmp_id_item = 0;

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                super.onCreateActionMode(mode, menu);
                FragmentTransaction transaction;
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.first_page_layout, defineFrm);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.custom_one) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_layout, highlightNoteFrm);
                    tmp_id_item = 1;
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.custom_two) {
                    Toast.makeText(view.getContext(), "This(these) word(s) has(have) copied to clipboard!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.custom_three) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_layout, translateFrm);
                    tmp_id_item = 2;
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                if (tmp_id_item == 1) {
                    tmp_id_item = 10;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(highlightNoteFrm);
                    transaction.commit();
                } else if (tmp_id_item == 2) {
                    tmp_id_item = 20;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(defineFrm);
                    transaction.commit();
                }
            }
        };
        readTextView.setCustomSelectionActionModeCallback(callback);

    }

    protected void onTextViewClicked() {
        readTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Animation for image view:
                // set image when select text on textview
                //clickTextViewBackGround.setVisibility(View.VISIBLE);
                readTextView.setTextIsSelectable(false);
                //zoom out => reset background + intent
                readTextView.startAnimation(anim_ZoomOut);
                readTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        readTextView.setVisibility(View.GONE);
                        Intent intent = new Intent(view.getContext(), PageManagementActivity.class);
                        startActivity(intent);
                        Bundle bundle = ActivityOptions.makeCustomAnimation(view.getContext(), R.anim.anim_nothing, R.anim.anim_nothing).toBundle();
                        view.getContext().startActivity(intent, bundle);

                    }
                }, 500);
//                Intent intent = new Intent(view.getContext(), PageManagementActivity.class);
//                startActivity(intent);
               // Bundle bundle = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.anim_nothing, R.anim.text_view_zoom_out).toBundle();
               // view.getContext().startActivity(intent, bundle);

            }
        });
    }


}
