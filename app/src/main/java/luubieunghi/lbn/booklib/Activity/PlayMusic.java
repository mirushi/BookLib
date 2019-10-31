package luubieunghi.lbn.booklib.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.service.AppWideGesturesListener;
import luubieunghi.lbn.booklib.service.MyService;

import static luubieunghi.lbn.booklib.service.MyService.mediaPlayer;
import static luubieunghi.lbn.booklib.service.MyService.notificationLayout;


public class PlayMusic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GestureDetector.OnGestureListener {

    private DrawerLayout drawer;
    private ImageView img;
    private TextView txt_TenBaiHat, txt_TenCaSi, txt_CurrentTime, txt_ToTalTime;
    private SeekBar seekBar_Time;
    private ImageButton btn_img_Menu, btn_img_Next, btn_img_Previous, btn_img_Shuffle, btn_img_Repeat;
    private static FloatingActionButton btn_img_Play;
    private NavigationView nav_menu;

    // các biến dùng cho lấy hướng vuốt
    private final float max_Distance_X=100;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        addControls();
        addEvents();
    }
    private void addEvents() {
        //block swipe navigationbar
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,GravityCompat.END);
        //gán context cho navigation
        nav_menu.setNavigationItemSelectedListener(this);

        //open navigationbar-->menu setting
        btn_img_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_img_Menu_Clicked(v);
            }
        });

        //notification and play under background
        btn_img_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtn_img_Play_Clicked(v);
            }
        });
        //degug mở list song do chưa vuốt được
        btn_img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListSongIntent();
            }
        });
    }

    private void setBtn_img_Next_Clicked(View v){

    }

    //hàm Onclick của btn_img_menu
    private void setBtn_img_Menu_Clicked(View v){
        // show navigation
        //drawer.openDrawer(GravityCompat.END);

        //tạo và hiển thị một menu
        PopupMenu popupMenu=new PopupMenu(PlayMusic.this,btn_img_Menu);
        popupMenu.getMenuInflater().inflate(R.menu.play_music_menu,popupMenu.getMenu());

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

    //hàm Onclick của btn_img_Play
    private void setBtn_img_Play_Clicked(View v){
        if(mediaPlayer.isPlaying()){
            btn_img_Play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        else {
            btn_img_Play.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
        }
        Intent intent=new Intent(PlayMusic.this, MyService.class);
        intent.setAction("Action_Play");
        startService(intent);
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
            Toast.makeText(PlayMusic.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PlayMusic.this,"Thear are no app suitable for sharing",Toast.LENGTH_LONG).show();
        }
    }

    public void startListSongIntent(){
        Intent intent=new Intent(PlayMusic.this, openListSong.class);
        startActivity(intent);
    }

    //tìm id của các control
    private void addControls() {
        //show only
        img=findViewById(R.id.img_disc);
        txt_TenBaiHat=findViewById(R.id.txt_TenBaiHat);
        txt_TenCaSi=findViewById(R.id.txt_TenCaSi);
        //SeekBar
        seekBar_Time=findViewById(R.id.seekBar_Time);
        txt_CurrentTime=findViewById(R.id.txt_CurrentTime);
        txt_ToTalTime=findViewById(R.id.txt_ToTalTime);
        //Navigation
        drawer=findViewById(R.id.drawer);
        btn_img_Menu=findViewById(R.id.btn_img_menu);
        nav_menu=findViewById(R.id.nav_menu);
        //Control Music
        btn_img_Play=findViewById(R.id.btn_img_play);
        btn_img_Next=findViewById(R.id.btn_img_next);
        btn_img_Previous=findViewById(R.id.btn_img_previous);
        btn_img_Repeat=findViewById(R.id.btn_img_repeat);
        btn_img_Shuffle=findViewById(R.id.btn_img_shuffle);
        gestureDetector = new GestureDetector(PlayMusic.this);
    }


    //lấy chiều vuốt để hiển thị list song
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //check what item selected in menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float distanceX=e1.getX()-e2.getX();

        boolean result=false;

        if(Math.abs(distanceX)>max_Distance_X){
            startListSongIntent();
            result=true;
        }
        return result;
    }

    public static void setBtn_PlayResource(boolean play){
        if(play==true&&btn_img_Play!=null)
            btn_img_Play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        if (play==false&&btn_img_Play!=null)
            btn_img_Play.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }
}
