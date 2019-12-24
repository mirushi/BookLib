package luubieunghi.lbn.booklib.UI.ReadBook;

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
