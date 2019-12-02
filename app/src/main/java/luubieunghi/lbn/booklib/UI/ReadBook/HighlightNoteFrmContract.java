package luubieunghi.lbn.booklib.UI.ReadBook;

import luubieunghi.lbn.booklib.Model.Note;

public class HighlightNoteFrmContract {
    interface HighlightNoteView
    {
        void setUpView();
        void setUpEvent();
    }
    interface HighlightNoteFrmPresenter
    {
        void setSelectedTextHighlight();
        void setNote(Note note);
        void saveNote(Note note);
    }

}
