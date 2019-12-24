package luubieunghi.lbn.booklib.UI.CustomAlertDialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import luubieunghi.lbn.booklib.R;

public class BookLoadingAlertDialog {
    Activity activity;
    Dialog dialog;
    public BookLoadingAlertDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        if (dialog == null)
        {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            //Chỉnh Cancelable = false để nó không thể bị cancel trong lúc chờ load.
            dialog.setCancelable(false);

            dialog.setContentView(R.layout.custom_book_processing_layout);

            ImageView gifImageView = dialog.findViewById(R.id.custom_book_processing_layout_loading_imageView);

            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(gifImageView);

            //Load image lên bằng Glide.

            Glide.with(activity)
                    .load(R.drawable.book_loading_dialog_bg)
                    .placeholder(R.drawable.book_loading_dialog_bg)
                    .centerCrop()
                    .into(imageViewTarget);
        }

        //Hiển thị dialog lên sau khi cấu hình mọi thứ xong.
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }
}
