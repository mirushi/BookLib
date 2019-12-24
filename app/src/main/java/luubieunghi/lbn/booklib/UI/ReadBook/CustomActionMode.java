package luubieunghi.lbn.booklib.UI.ReadBook;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import luubieunghi.lbn.booklib.R;

public class CustomActionMode implements ActionMode.Callback {
    private final Context context;
    FragmentManager fragmentManager;
    private BookReadingDefineFrm defineFrm;
    private BookReadingTranslateFrm translateFrm;
    private BookReadingHighlightNoteFrm highlightNoteFrm;


    public CustomActionMode(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        menu.clear();
        mode.getMenuInflater().inflate(R.menu.menu_custom, menu);

        //show define frm
        FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.first_page_layout, defineFrm);
        transaction.addToBackStack(null);
        transaction.commit();

        //delete default item
        for (int i = 0; i < menu.size(); i++) {

            validResIdList.add(menu.getItem(i).getItemId());
        }


        return true;
    }

    private List<Integer> validResIdList = new ArrayList<>();

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        List<Integer> toRemoveResId = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {

            if (menu.getItem(i).getItemId() != (validResIdList.get(0))
                    && menu.getItem(i).getItemId() != (validResIdList.get(1))
                    && menu.getItem(i).getItemId() != (validResIdList.get(2))
                    && menu.getItem(i).getItemId() != (validResIdList.get(3))
                    && menu.getItem(i).getItemId() != (validResIdList.get(4))
                    && menu.getItem(i).getItemId() != (validResIdList.get(5))
                    && menu.getItem(i).getItemId() != (validResIdList.get(6))
                    && menu.getItem(i).getItemId() != (validResIdList.get(7))
                    && menu.getItem(i).getItemId() != (validResIdList.get(8))
            ) {

                toRemoveResId.add(menu.getItem(i).getItemId());
            }
        }

        for (Integer resId : toRemoveResId) {
            menu.removeItem(resId);
        }

        return true;
    }


    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.custom_one) {
            FragmentTransaction transaction;
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.first_page_layout, highlightNoteFrm);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        } else if (item.getItemId() == R.id.custom_two) {
            Toast.makeText(context, "This(these) word(s) has(have) copied to clipboard!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.custom_three) {
            FragmentTransaction transaction;
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.first_page_layout, translateFrm);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //kill define frm when destroy action mode
        if (!defineFrm.isRemoving()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(defineFrm);
            transaction.commit();
        }

    }

    FragmentManager getFragmentManager() {
        return this.fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setDefineFrm(BookReadingDefineFrm frm)
    {
        this.defineFrm = frm;
    }
   public void setHighlightNoteFrm(BookReadingHighlightNoteFrm frm)
   {
       this.highlightNoteFrm = frm;
   }
    public void setTranslateFrm(BookReadingTranslateFrm frm)
    {
        this.translateFrm = frm;
    }

}

