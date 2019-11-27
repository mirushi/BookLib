package luubieunghi.lbn.booklib.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Model.BaiHat;

public class BaiHatAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    private List objects;
    public BaiHatAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        ImageView img_HinhBaiHat_Item_Song=row.findViewById(R.id.img_hinhbaihat_item_song);
        TextView txt_TenBaiHat_Item_Song=row.findViewById(R.id.txt_tenbaihat_item_song);
        TextView txt_TenCaSi_Item_Song=row.findViewById(R.id.txt_tencasi_item_song);

        BaiHat baiHat=(BaiHat)this.objects.get(position);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            img_HinhBaiHat_DanhSachBaiHat.setAccessibilityPaneTitle(baiHat.getHinh());
//        }
        txt_TenBaiHat_Item_Song.setText(baiHat.getTenBaiHat());
        txt_TenCaSi_Item_Song.setText(baiHat.getTenCaSi());
        return row;
    }
}
