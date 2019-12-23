package luubieunghi.lbn.booklib.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
import luubieunghi.lbn.booklib.Model.Song.Song;

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

        Song song =(Song)this.objects.get(position);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            img_HinhBaiHat_DanhSachBaiHat.setAccessibilityPaneTitle(song.getHinh());
//        }


        txt_TenBaiHat_Item_Song.setText(song.getSongName());
        txt_TenCaSi_Item_Song.setText(song.getArtistNames());
        img_HinhBaiHat_Item_Song.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(song.getImagePath())));
        return row;
    }

    public void resizeImage(Image image){

    }
}
