package luubieunghi.lbn.booklib.UI.ReadBook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import luubieunghi.lbn.booklib.Model.Note;
import luubieunghi.lbn.booklib.R;

public class BookReadingHighlightNoteFrm extends Fragment implements HighlightNoteFrmContract.HighlightNoteView {
    Context context;
    View view;
    ImageButton imgBtn_red;
    ImageButton imgBtn_green;
    ImageButton imgBtn_blue;
    ImageButton imgBtn_yellow;
    EditText editText_Note;
    Button btn_DeleteNote;
    Button btn_SaveNote;
    Note note;

    public BookReadingHighlightNoteFrm(Context context) {
        this.context = context;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_highlight_note, container, false);
        setUpView();
        setUpEvent();
        return view;
    }

    @Override
    public void setUpView() {
        imgBtn_green = view.findViewById(R.id.btn_highlight_frm_green);
        imgBtn_yellow = view.findViewById(R.id.btn_highlight_frm_yellow);
        imgBtn_red = view.findViewById(R.id.btn_highlight_frm_red);
        imgBtn_blue = view.findViewById(R.id.btn_highlight_frm_blue);
        editText_Note = view.findViewById(R.id.edittext_highlight_frm);
        btn_DeleteNote = view.findViewById(R.id.note_delete_btn);
        btn_SaveNote = view.findViewById(R.id.note_save_btn);


    }

    @Override
    public void setUpEvent() {

    }

}
