package luubieunghi.lbn.booklib.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import luubieunghi.lbn.booklib.UI.Main.BookFilterType;
import luubieunghi.lbn.booklib.UI.Main.BookListingFragment.BookListingFragment;

public class BookListingPagerAdapter extends FragmentPagerAdapter {

    final int NUM_PAGE = 3;
    BookListingFragment allBookFragment = null;
    BookListingFragment eBookOnlyFragment = null;
    BookListingFragment audioBookOnlyFragment = null;

    int currentPosition = 0;

    public BookListingPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public BookListingFragment getItem(int position) {
        switch (position)
        {
            case 0:{
                if (allBookFragment == null)
                    allBookFragment = BookListingFragment.newInstance(BookFilterType.ALL);
                currentPosition = 0;
                return allBookFragment;
            }
            case 1:{
                if (eBookOnlyFragment == null)
                    eBookOnlyFragment = BookListingFragment.newInstance(BookFilterType.EBOOKONLY);
                currentPosition = 1;
                return eBookOnlyFragment;
            }
            case 2:{
                if (audioBookOnlyFragment == null)
                    audioBookOnlyFragment = BookListingFragment.newInstance(BookFilterType.AUDIOBOOKONLY);
                currentPosition = 2;
                return audioBookOnlyFragment;
            }
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

    public int getCurrentPosition(){
        return currentPosition;
    }


}
