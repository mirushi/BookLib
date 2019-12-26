package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.AudioDatabase;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.Main.AppWideGesturesListener;
import luubieunghi.lbn.booklib.UI.Main.MainActivity;

import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.mediaPlayer;


public class PlayMusic extends AppCompatActivity implements  PlayMusicContract.IPlayMusicView{

    LinearLayout layout_Play_Music, lv_img;
    private ImageView img;
    private TextView txt_TenBaiHat, txt_TenCaSi, txt_ToTalTime;
    private TextView txt_CurrentTime;
    private SeekBar seekBar_Time;
    private Button btn_img_Menu, btn_img_Next, btn_img_Previous, btn_img_Shuffle, btn_img_Repeat;
    private PlayMusicPresenter presenter;
    public Song song=null;
    private Handler handler=new Handler();

    public static Song currentSong=null;
    public static Button btn_img_Play=null;
    public static ArrayList<Song> dsBaiHat=null;
    public static boolean isRepeat=false, isShuffle=false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        song=(Song)getIntent().getSerializableExtra("song");
        AudioDatabase database=AudioDatabase.getInstance(this);
        dsBaiHat=new ArrayList<Song>();
        dsBaiHat.addAll(database.song_dao().getAll());
        //BUG TO Ở ĐÂY
        //nếu không truyền bài hát vào thì lấy bài hát đầu tiên
        if(song==null){
            //database.song_dao().insert(new Song("BH1","Bài hát 1","/sdcard/Download/BH1.mp3",0,"/sdcard/Download/BH1.png","Ca sĩ 1"));
            List<Song> songs= database.song_dao().getByIDs("BH1");
            currentSong =songs.get(0);
            song=currentSong;
            Intent intent=new Intent(getApplicationContext(),MyService.class);
            intent.putExtra("song",song);
            intent.putExtra("isMusic", true);
            startService(intent);
        }
        //nếu đã truyền bài hát vào
        else {
            //nếu bài hát hiện tại không null thì so sánh xem bài hát truyền vào có giống bài hát hiện tại không
            if(!(currentSong==null))
            {
                if(currentSong.getSongID().equals(song.getSongID())){

                }
                else{
                    currentSong=song;
                    Intent intent=new Intent(getApplicationContext(),MyService.class);
                    intent.putExtra("song",song);
                    intent.putExtra("isMusic", true);
                    startService(intent);
                }
            }
            //nếu bài hát hiện tại là null thì tạo mới rồi play luôn
            else{
                currentSong=song;
                Intent intent=new Intent(getApplicationContext(),MyService.class);
                intent.putExtra("song",song);
                intent.putExtra("isMusic", true);
                startService(intent);
            }

        }
        addControls();
        //txt_CurrentTime.setText(mediaPlayer.getCurrentPosition());
        txt_TenBaiHat.setText(currentSong.getSongName());
        txt_TenCaSi.setText(currentSong.getArtistNames());
        //img.setImageBitmap(Bitmap.createBitmap(BitmapFactory.decodeFile(currentSong.getImagePath())));
        PlayMusic.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    seekBar_Time.setMax(mediaPlayer.getDuration());
                    txt_ToTalTime.setText(intToTime(mediaPlayer.getDuration()));
                    int percent=mediaPlayer.getCurrentPosition();
                    setSeekBarProgress(percent);
                }
                handler.postDelayed(this,1000);
            }
        });
        ConfigGesturesListener();
        addEvents();
        presenter=new PlayMusicPresenter(getApplicationContext());
    }

    @Override
    public void addEvents() {

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
              if(fromUser){
                 if(mediaPlayer!=null){
                     mediaPlayer.seekTo(seekBar.getProgress());
                     txt_CurrentTime.setText(intToTime(seekBar.getProgress()));
                 }
              }
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
                seekBar_Time.setProgress(0);
                txt_CurrentTime.setText("00:00");
                presenter.nextSong();
            }
        });

        updateResourceButtonPlay();

        btn_img_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar_Time.setProgress(0);
                txt_CurrentTime.setText("00:00");
               presenter.previousSong();
            }
        });

        btn_img_Repeat.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                isRepeat=!isRepeat;
                if(isRepeat){
                    isShuffle=false;
                    btn_img_Repeat.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80b7cf")));
                    btn_img_Shuffle.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
                }
                else {
                    btn_img_Repeat.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
                }
            }
        });

        btn_img_Shuffle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                isShuffle=!isShuffle;
                if(isShuffle){
                    isRepeat=false;
                    btn_img_Shuffle.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80b7cf")));
                    btn_img_Repeat.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
                }
                else {
                    btn_img_Shuffle.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
                }
            }
        });
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

    public static void setBtn_PlayResource(boolean play){
        if(play==true)
            btn_img_Play.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        if (play==false)
            btn_img_Play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24dp);
    }

    public void setSeekBarProgress(int value){
        seekBar_Time.setProgress(value);
        String text= intToTime(value);
        txt_CurrentTime.setText(text);
    }

    private static String intToTime(int input){
        String result="";
        int h=input/60000/60;
        int m=(input-h*60000*60)/60000;
        int s=(input-h*60000*60-m*60000)/1000;
        if(h!=0) {
            if(h<10)
                result+="0";
            result+=h+":";
        }
        if(m!=0){
            if(m<10)
                result+="0";
            result+=m+":";
        }
        else {
            result+="00:";
        }
        if(s<10)
            result+="0";
        result+=s;
        return result;
    }

}
