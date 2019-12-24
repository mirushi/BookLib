package luubieunghi.lbn.booklib.UI.ReadBook.old_UI;

public class DefineFrmContract {
    interface DefineFrmView
    {
        void setUpView();
        void setWord(String text);
    }

    interface DefineFrmPresenter
    {
        void showDefine();
    }
}
