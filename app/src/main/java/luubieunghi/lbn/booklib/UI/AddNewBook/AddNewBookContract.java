package luubieunghi.lbn.booklib.UI.AddNewBook;

import java.time.LocalDate;

public interface AddNewBookContract {
    interface AddNewBookMVPView{
        void BookAddedSuccess();
        void BookAddedFailure();
    }
    interface AddNewBookMVPPresenter{
        void AddBook(String pathToCover, String title, String author, Integer rating,
                     String ids, String tags, String language, String publisher, LocalDate publishDate, String description);
    }
}
