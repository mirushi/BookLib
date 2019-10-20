package luubieunghi.lbn.booklib.adapter;

import android.app.Activity;
import android.os.Build;
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
import luubieunghi.lbn.booklib.model.BaiHat;

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
        ImageView img_HinhBaiHat_DanhSachBaiHat=row.findViewById(R.id.img_hinhbaihat_baihat);
        TextView txt_TenBaiHat_DanhSachBaiHat=row.findViewById(R.id.txt_tenbaihat_danhsachbaihat);
        TextView txt_TenCaSi_DanhSachBaiHat=row.findViewById(R.id.txt_tencasi_danhsachbaihat);

        BaiHat baiHat=(BaiHat)this.objects.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            img_HinhBaiHat_DanhSachBaiHat.setAccessibilityPaneTitle(baiHat.getHinh());
        }
        txt_TenBaiHat_DanhSachBaiHat.setText(baiHat.getTenBaiHat());
        txt_TenCaSi_DanhSachBaiHat.setText(baiHat.getTenCaSi());
        return row;
    }
}
