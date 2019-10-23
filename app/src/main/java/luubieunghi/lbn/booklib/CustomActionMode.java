package luubieunghi.lbn.booklib;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

public class CustomActionMode implements ActionMode.Callback {
    private final Context context;

    public CustomActionMode(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        menu.clear();
        mode.getMenuInflater().inflate(R.menu.menu_custom, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.custom_one) {

            return true;
        } else if (item.getItemId() == R.id.custom_two) {
            return true;
        } else if (item.getItemId() == R.id.custom_three) {

            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
