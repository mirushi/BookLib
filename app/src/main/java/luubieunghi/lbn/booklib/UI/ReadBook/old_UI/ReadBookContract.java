package luubieunghi.lbn.booklib.UI.ReadBook.old_UI;

public class ReadBookContract {
    interface ReadBookView {
        void setUpViews();
        void generalSetUp();
    }

    interface ReadBookPresenter {
        void updateViewPager();
    }
}
