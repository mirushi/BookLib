package luubieunghi.lbn.booklib.UI.ReadBook;

import android.content.Context;
import android.view.View;

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
