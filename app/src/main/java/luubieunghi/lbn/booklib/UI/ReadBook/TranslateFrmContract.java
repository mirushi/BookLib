package luubieunghi.lbn.booklib.UI.ReadBook;

public class TranslateFrmContract {
    interface TranslateFrmView {
        void setUpViews();

        void setUpEvents();

        void setUp(String string);
    }

    interface TranslateFrmPresenter {
        void updateViews();
    }
}
