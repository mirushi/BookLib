package luubieunghi.lbn.booklib.UI.AddNewBook;

public interface AddNewBookContract {
    interface AddNewBookMVPView{
        void BookAddedSuccess();
        void BookAddedFailure(String reason);
    }
    interface AddNewBookMVPPresenter{
        void AddBook();
        void LoadLanguages();
        void LoadServerList();
        void LoadPublisherList();
        void AddPublisher(String publisherName);
        void AddLanguage(String langName);
    }
}
