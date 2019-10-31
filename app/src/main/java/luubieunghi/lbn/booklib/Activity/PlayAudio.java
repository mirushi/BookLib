package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import luubieunghi.lbn.booklib.R;

public class PlayAudio extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton btn_Img_Menu_Play_Audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        
        addControls();
        init();
        addEvents();
        
    }

    private void addEvents() {

        btn_Img_Menu_Play_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_Img_Menu_Play_AudioClicked();
            }
        });
    }

    private void setBtn_Img_Menu_Play_AudioClicked() {
        PopupMenu popupMenu=new PopupMenu(PlayAudio.this,btn_Img_Menu_Play_Audio);
        popupMenu.getMenuInflater().inflate(R.menu.play_music_menu,popupMenu.getMenu());
//        try {
//            Field[] fields = popupMenu.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                if ("mPopup".equals(field.getName())) {
//                    field.setAccessible(true);
//                    Object menuPopupHelper = field.get(popupMenu);
//                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
//                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
//                    setForceIcons.invoke(menuPopupHelper, true);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //bắt sự kiện một item đucợ click
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_email:{
                        sendMail();
                        break;
                    }
                    case R.id.nav_share: {
                        share();
                        break;
                    }
                    case R.id.nav_setting:{

                    }
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void init() {
    }

    private void addControls() {
        toolbar=findViewById(R.id.toolbar_listaudio);
        btn_Img_Menu_Play_Audio=findViewById(R.id.btn_img_menu_right_play_audio);
    }

    //gửi mail về booklib devteam
    public void sendMail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"booklibdevteam@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Comment About Booklib App");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello Booklib Dev Team\n");
        try {
            startActivity(Intent.createChooser(i, "Send Mail"));
        } catch (Exception ex) {
            Toast.makeText(PlayAudio.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    //share post lên fackbook cá nhân
    private void share(){

        Intent i =new Intent(Intent.ACTION_SEND);
        i.setType("text/*");
        i.putExtra(Intent.EXTRA_TEXT,"This song is so great");
        i.putExtra(Intent.EXTRA_TITLE,"Share Song");
        try{
            startActivity(Intent.createChooser(i,"Share with ..."));
        }
        catch (Exception ex){
            Toast.makeText(PlayAudio.this,"There are no app suitable for sharing",Toast.LENGTH_LONG).show();
        }
    }

}
