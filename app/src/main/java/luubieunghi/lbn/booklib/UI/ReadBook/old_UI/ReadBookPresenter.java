package luubieunghi.lbn.booklib.UI.ReadBook.old_UI;

import android.content.Context;

public class ReadBookPresenter implements ReadBookContract.ReadBookPresenter {
    Context context;
    ReadBookPresenter(Context context)
    {
        this.context = context;
    }

    @Override
    public void updateViewPager() {
        //update view pager
    }
}
