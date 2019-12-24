package luubieunghi.lbn.booklib.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import luubieunghi.lbn.booklib.Model.SettingItemModel;
import luubieunghi.lbn.booklib.R;

public class SettingsItemAdapter extends ArrayAdapter<SettingItemModel> {

    private Context context;
    private int resource;
    private List<SettingItemModel> settingItems;

    public SettingsItemAdapter(@NonNull Context context, int resource, @NonNull List<SettingItemModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.settingItems = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imgSymbol = (ImageView)convertView.findViewById(R.id.item_setting_imgView_symbol);
            viewHolder.txtViewTitle = (TextView)convertView.findViewById(R.id.item_setting_txtView_title);
            viewHolder.txtViewDescription = (TextView)convertView.findViewById(R.id.item_setting_txtView_description);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        SettingItemModel settingItem = settingItems.get(position);
        viewHolder.imgSymbol.setImageDrawable(settingItem.getImage());
        viewHolder.txtViewTitle.setText(settingItem.getTitle());
        viewHolder.txtViewDescription.setText(settingItem.getDescription());

        return convertView;
    }
    public class ViewHolder{
        ImageView imgSymbol;
        TextView txtViewTitle;
        TextView txtViewDescription;
    }
}
