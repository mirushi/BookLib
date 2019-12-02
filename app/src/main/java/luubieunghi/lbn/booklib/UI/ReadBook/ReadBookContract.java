package luubieunghi.lbn.booklib.UI.ReadBook;

public class ReadBookContract {
    interface ReadBookView {
        void setUpViews();
        void generalSetUp();
    }

    interface ReadBookPresenter {
        void updateViewPager();
    }
}
