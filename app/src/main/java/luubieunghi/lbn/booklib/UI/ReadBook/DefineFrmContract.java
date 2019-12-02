package luubieunghi.lbn.booklib.UI.ReadBook;

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
