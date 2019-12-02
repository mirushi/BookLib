package luubieunghi.lbn.booklib.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import luubieunghi.lbn.booklib.UI.Main.BookFilterType;
import luubieunghi.lbn.booklib.UI.Main.BookListingFragment.BookListingFragment;

public class BookListingPagerAdapter extends FragmentPagerAdapter {

    final int NUM_PAGE = 3;

    public BookListingPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return BookListingFragment.newInstance(BookFilterType.ALL);
            case 1:
                return BookListingFragment.newInstance(BookFilterType.EBOOKONLY);
            case 2:
                return BookListingFragment.newInstance(BookFilterType.AUDIOBOOKONLY);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGE;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Tất cả";
            case 1:
                return "Ebook";
            case 2:
                return "Sách nói";
        }
        return null;
    }
}
