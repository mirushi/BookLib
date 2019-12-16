package luubieunghi.lbn.booklib.UI.PlayMusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.List;


import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.Main.AppWideGesturesListener;
import luubieunghi.lbn.booklib.UI.Main.MainActivity;
import luubieunghi.lbn.booklib.UI.OpenListSong.OpenListSong;

import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.mediaPlayer;


public class PlayMusic extends AppCompatActivity implements  PlayMusicContract.IPlayMusicView{

    LinearLayout layout_Play_Music, lv_img;
    private ImageView img;
    private TextView txt_TenBaiHat, txt_TenCaSi, txt_CurrentTime, txt_ToTalTime;
    private SeekBar seekBar_Time;
    private Button btn_img_Menu, btn_img_Next, btn_img_Previous, btn_img_Shuffle, btn_img_Repeat;
    private static Button btn_img_Play;
    private PlayMusicPresenter presenter;
    public static Song currentSong=null;
    public Song song=null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        song=(Song)getIntent().getSerializableExtra("song");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        Intent intent=getIntent();
        //BUG TO Ở ĐÂY
        AudioDatabase database=AudioDatabase.getInstance(this);
        //database.song_dao().insert(new Song("BH1","Bài hát 1","/sdcard/Download/BH1.mp3",0,"/sdcard/Download/BH1.png","Ca sĩ 1"));
        List<Song> songs= database.song_dao().getByIDs("BH1");
        //currentSong =songs.get(0);
        mediaPlayer= MediaPlayer.create(getBaseContext(),R.raw.van_su_tuy_duyen);
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        addControls();
        //txt_CurrentTime.setText(mediaPlayer.getCurrentPosition());
        txt_ToTalTime.setText(mediaPlayer.getDuration()+"");
        seekBar_Time.setMax(mediaPlayer.getDuration());
        ConfigGesturesListener();
        addEvents();
        SyncTime syncTime=new SyncTime();
        new Thread(syncTime).start();
        presenter=new PlayMusicPresenter(this);
    }


    class SyncTime implements Runnable {
        @Override
        public void run() {
            while (mediaPlayer.isPlaying()){
                txt_CurrentTime.setText(mediaPlayer.getCurrentPosition()+"");
            }
        }
    }

    @Override
    public void addEvents() {
        //block swipe navigationbar
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,GravityCompat.END);
        //gán context cho navigation
        //nav_menu.setNavigationItemSelectedListener(this);

        //open navigationbar-->menu setting
        btn_img_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });

        //notification and play under background
        btn_img_Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.playMusicService(song);
            }
        });



        seekBar_Time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(seekBar.getProgress());
                txt_CurrentTime.setText(seekBar.getProgress()+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //degug mở list song do chưa vuốt được
        btn_img_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListSongIntent();
            }
        });
        updateResourceButtonPlay();
    }

    @Override
    public void updateResourceButtonPlay() {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying())
            PlayMusic.setBtn_PlayResource(false);
        else
            PlayMusic.setBtn_PlayResource(true);
    }

    @Override
    public void ConfigGesturesListener()
    {
        layout_Play_Music.setOnTouchListener(new AppWideGesturesListener(PlayMusic.this){
            @Override
            public void SwipeDownFromTop()
            {
                Intent intent = new Intent(PlayMusic.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_top_in, R.anim.slide_top_out);
            }
        });

    }

    @Override
    public void showPopupMenu() {
        PopupMenu popupMenu=new PopupMenu(PlayMusic.this,btn_img_Menu);
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
        //bắt sự kiện một item được click
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_email:{
                        presenter.sendMail();
                        break;
                    }
                    case R.id.nav_share: {
                        presenter.share();
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

    //tìm id của các control
    @Override
    public void addControls() {
        //show only
        img=findViewById(R.id.img_disc);
        txt_TenBaiHat=findViewById(R.id.txt_TenBaiHat);
        txt_TenCaSi=findViewById(R.id.txt_TenCaSi);
        //SeekBar
        seekBar_Time=findViewById(R.id.seekBar_Time);
        txt_CurrentTime=findViewById(R.id.txt_CurrentTime);
        txt_ToTalTime=findViewById(R.id.txt_ToTalTime);
        //Navigation
        //drawer=findViewById(R.id.drawer);
        btn_img_Menu=findViewById(R.id.btn_img_menu);
        //nav_menu=findViewById(R.id.nav_menu);
        //Control Music
        btn_img_Play=findViewById(R.id.btn_img_play);
        btn_img_Next=findViewById(R.id.btn_img_next);
        btn_img_Previous=findViewById(R.id.btn_img_previous);
        btn_img_Repeat=findViewById(R.id.btn_img_repeat);
        btn_img_Shuffle=findViewById(R.id.btn_img_shuffle);
        layout_Play_Music=findViewById(R.id.layout_play_music);

        //gestureDetector = new GestureDetector(PlayMusic.this,PlayMusic.this);
        layout_Play_Music=findViewById(R.id.layout_play_music);
        lv_img=findViewById(R.id.lv_img);

    }

    @Override
    public void openListSongIntent() {
        Intent intent=new Intent(PlayMusic.this, OpenListSong.class);
        startActivity(intent);
    }

    public static void setBtn_PlayResource(boolean play){
        if(play==true)
            btn_img_Play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        if (play==false)
            btn_img_Play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }

}
