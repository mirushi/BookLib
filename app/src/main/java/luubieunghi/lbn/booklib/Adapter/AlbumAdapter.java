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
import luubieunghi.lbn.booklib.Model.Album.Album;

public class AlbumAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    private List objects;
    public AlbumAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
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
        ImageView img_HinhAlbum_Item_Album=row.findViewById(R.id.img_hinhalbum_item_album);
        TextView txt_TenAlbum_Item_Album=row.findViewById(R.id.txt_tenalbbum_item_album);

        Album album=(Album)this.objects.get(position);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            img_HinhBaiHat_DanhSachBaiHat.setAccessibilityPaneTitle(baiHat.getHinh());
//        }
        txt_TenAlbum_Item_Album.setText(album.getAlbumName());
        return row;
    }
}

