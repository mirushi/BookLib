package luubieunghi.lbn.booklib.UI.Setting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Adapter.SettingsItemAdapter;
import luubieunghi.lbn.booklib.Model.SettingItemModel;

public class SettingsActivity extends AppCompatActivity {

    ListView lsViewSettings;
    SettingItemModel simSync;
    SettingItemModel simSelectLanguages;
    SettingItemModel simDictionarySettings;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Ánh xạ giá trị.
        AssignValues();

        //Load listView item.
        LoadSettingsItem();
    }

    private void LoadSettingsItem() {
        ArrayList<SettingItemModel> settingsItem = new ArrayList<>();

        Drawable syncIcon = getResources().getDrawable(R.drawable.ic_sync_black_24dp);
        simSync = new SettingItemModel("Quản lý đồng bộ", "Quản lý cài đặt đồng bộ sách giữa các thiết bị", syncIcon);

        Drawable selectLanguageIcon = getResources().getDrawable(R.drawable.ic_language);
        simSelectLanguages = new SettingItemModel("Lựa chọn ngôn ngữ của bạn", "Lựa chọn ngôn ngữ sử dụng cho từ điển", selectLanguageIcon);

        Drawable dictionarySettingsIcon = getResources().getDrawable(R.drawable.ic_dictionary);
        simDictionarySettings = new SettingItemModel("Cài đặt từ điển", "Cài đặt của từ điển", dictionarySettingsIcon);

        settingsItem.add(simSync);
        settingsItem.add(simSelectLanguages);
        settingsItem.add(simDictionarySettings);

        SettingsItemAdapter adapter = new SettingsItemAdapter(this, R.layout.item_setting, settingsItem);
        lsViewSettings.setAdapter(adapter);
    }

    private void AssignValues() {
        lsViewSettings = (ListView)findViewById(R.id.activity_settings_lsView_settings);
    }
}
