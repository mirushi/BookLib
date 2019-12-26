package luubieunghi.lbn.booklib.UI.ReadBook.old_UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import luubieunghi.lbn.booklib.R;

public class BookReadingTranslateFrm extends Fragment implements TranslateFrmContract.TranslateFrmView {
    String source;
    private Spinner sourceSpinner;
    private Spinner toTranslateSpinner;
    View view;
    Context context;

    public BookReadingTranslateFrm(Context context) {
        this.context = context;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_translate_frm, container, false);
        setUpViews();
        setUpEvents();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUpViews() {
        sourceSpinner = view.findViewById(R.id.source_spinner);
        toTranslateSpinner = view.findViewById(R.id.to_translated_spinner);
        setAdapterSpinner();

    }

    @Override
    public void setUpEvents() {
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), sourceSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toTranslateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(view.getContext(), sourceSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void setUp(String string) {
        this.source = string;
    }

    private void setAdapterSpinner() {

        //list of language
        ArrayList<String> source_language_list = new ArrayList<>();
        source_language_list.add("English");
        source_language_list.add("Spanish");
        source_language_list.add("Vietnamese");


        ArrayAdapter sourceSpinnerAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, source_language_list);
        sourceSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //for sourceSpinner
        sourceSpinner.setAdapter(sourceSpinnerAdapter);
        //override handle for item of sourceSpinner

        //for toTranslateSpinner
        toTranslateSpinner.setAdapter(sourceSpinnerAdapter);
        //override handle for item of toTranslateSpinner

    }

}
