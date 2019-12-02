package luubieunghi.lbn.booklib.UI.About;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import luubieunghi.lbn.booklib.R;

public class AboutActivity extends AppCompatActivity {

    RatingView ratingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Ánh xạ.
        ratingView = (RatingView)findViewById(R.id.activity_about_ratingView_rating);

        RatingView.OnRateListener listener = new RatingView.OnRateListener() {
            @Override
            public void onRatingChanged() {
                if (ratingView.getRating() < 5){
                    //Gửi mail.
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/rfc822"); //Ký hiệu của email.
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"17520794@gm.uit.edu.vn"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Nhận xét về sản phẩm BookLib");
                    intent.putExtra(Intent.EXTRA_TEXT, "Sau khi sử dụng sản phẩm, tôi thấy có những điều muốn chia sẻ như sau : ");
                    try{
                        startActivity(Intent.createChooser(intent, "Gửi mail..."));
                    }catch (ActivityNotFoundException ex){
                        Toast.makeText(AboutActivity.this, "Xin lỗi nhưng thiết bị của bạn không cài sẵn bất kỳ trình soạn email nào để gửi phản hồi",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    try{
                        //Mở Google Play để người dùng đánh giá ứng dụng.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://"));
                        startActivity(intent);
                    }catch(ActivityNotFoundException ex){
                        //Mở liên kết dẫn đến sản phẩm để người dùng đánh giá.
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/"));
                        startActivity(intent);
                    }
                }
            }
        };
        ratingView.setOnRateListener(listener);
    }
}
