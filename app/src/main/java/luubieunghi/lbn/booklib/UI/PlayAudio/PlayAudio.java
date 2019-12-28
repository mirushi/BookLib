package luubieunghi.lbn.booklib.UI.PlayAudio;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.PlayMusic.MyService;

import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.equalizer;
import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.isLeft;
import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.isMono;
import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.mediaPlayer;

public class PlayAudio extends AppCompatActivity implements PlayAudioContract.IPlayAudioView{

    private Toolbar toolbar;
    private Button btn_equalizer, btn_timer, btn_play_speed, btn_increase_volume, btn_decrease_volume;
    private Button btn_skip_previous_10s, btn_skip_previous_1m, btn_skip_next_10s,btn_skip_next_1m;
    private Button btn_next, btn_previous;
    private Handler handler=new Handler();
    private TextView txt_read, txt_percent, txt_left, txt_current_time, txt_total_current_time, txt_audio_book_name;
    private SeekBar seekBar_current, seekBar_total;
    private PlayAudioPresenter presenter;
    private Button []buttons;
    private String playbackSpeed="";

    public static  Button btn_play=null;
    public static List<BookFile> bfs=null;
    public static BookFile currentFile=null;
    public static long current_time=0;
    private long max_time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        addControls();
        setUp();
        addEvents();
        MyService.createEqualizer();
        BookDatabase bd=BookDatabase.getInstance(this);
        Book b=(Book)getIntent().getSerializableExtra("book");
        ArrayList<Book> dsb=new ArrayList<>();
        dsb.addAll(bd.BookDAO().getAllBook());
        bfs= bd.BookFileDAO().getAllFilesOfBook(b.getBookID());
        for(int i=0;i<bfs.size();i++) {
            BookFile bf = bfs.get(i);
            max_time+=bf.getBTotal();
        }
        for(int i=0;i<bfs.size();i++) {
            BookFile bf = bfs.get(i);
            if (((int) bf.getBRead() != (int) bf.getBTotal())) {
                mediaPlayer.seekTo((int)bf.getBRead());
                Intent intent = new Intent(PlayAudio.this, MyService.class);
                intent.putExtra("book_file", bf);
                intent.putExtra("book", b);
                intent.setAction("Action_Play");
                startService(intent);
                break;
            }
            else if(i==bfs.size()-1){
                bf=bfs.get(0);
                Intent intent = new Intent(PlayAudio.this, MyService.class);
                intent.putExtra("book_file", bf);
                intent.putExtra("book", b);
                intent.setAction("Action_Play");
                startService(intent);
                break;
            }
            else {
                current_time+=bf.getBTotal();
            }
        }
        if(b!=null){
            txt_audio_book_name.setText(b.getBookTitle());
        }

        PlayAudio.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
                   setReadText();
                }
                handler.postDelayed(this,1000);
            }
        });

        presenter=new PlayAudioPresenter(PlayAudio.this, PlayAudio.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void addEvents() {

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
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

        btn_equalizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEqualizerDialog();
            }
        });

        btn_play_speed.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                showPlaySpeedDialog();
            }
        });

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.start_stop();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_increase_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.increaseVolume();
            }
        });

        btn_decrease_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.decreaseVolume();
            }
        });

        btn_skip_previous_10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.skip_previous_10s();
            }
        });

        btn_skip_previous_1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.skip_previous_1m();
            }
        });

        btn_skip_next_10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.skip_next_10s();
            }
        });

        btn_skip_next_1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.skip_next_1m();
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.play();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.next();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.previous();
            }
        });

        seekBar_current.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser||(!mediaPlayer.isPlaying())){
                   if(mediaPlayer!=null){
                       mediaPlayer.seekTo(progress);
                       setReadText();
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


    }

    @Override
    public void setUp() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    public void showEqualizerDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        View view=getLayoutInflater().inflate(R.layout.equalizer_dialog,null);
        final SeekBar sb1=view.findViewById(R.id.sb_chanel1);
        final SeekBar sb2=view.findViewById(R.id.sb_chanel2);
        final SeekBar sb3=view.findViewById(R.id.sb_chanel3);
        final SeekBar sb4=view.findViewById(R.id.sb_chanel4);
        final SeekBar sb5=view.findViewById(R.id.sb_chanel5);

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                equalizer.setBandLevel((short)0,(short)(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                equalizer.setBandLevel((short)1,(short)(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                equalizer.setBandLevel((short)2,(short)(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                equalizer.setBandLevel((short)3,(short)(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                equalizer.setBandLevel((short)4,(short)(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb1.setProgress(equalizer.getBandLevel((short)0));
        sb2.setProgress(equalizer.getBandLevel((short)1));
        sb3.setProgress(equalizer.getBandLevel((short)2));
        sb4.setProgress(equalizer.getBandLevel((short)3));
        sb5.setProgress(equalizer.getBandLevel((short)4));
        Button btn_reset_equalizer=view.findViewById((R.id.btn_reset_equalizer));
        Button btn_OK=view.findViewById(R.id.btn_OK_equalizer_dialog);
        CheckBox chk_mono=view.findViewById(R.id.chk_mono);
        SeekBar seekBar_mono=view.findViewById(R.id.seek_bar_mono);

        btn_reset_equalizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetEqualizer();
                sb1.setProgress(0);
                sb2.setProgress(0);
                sb3.setProgress(0);
                sb4.setProgress(0);
                sb5.setProgress(0);
            }
        });

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chanel1=sb1.getProgress();
                int chanel2=sb2.getProgress();
                int chanel3=sb3.getProgress();
                int chanel4=sb4.getProgress();
                int chanel5=sb5.getProgress();
                presenter.setEqualizer(chanel1,chanel2,chanel3,chanel4,chanel5);
                dialog.dismiss();
            }
        });


        chk_mono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(seekBar_current.getProgress()==-1){
                        mediaPlayer.setVolume(MyService.volume,0);
                    }
                    else{
                        mediaPlayer.setVolume(0,MyService.volume);
                        seekBar_mono.setProgress(1);
                    }
                    isMono=true;
                }
                else{
                    mediaPlayer.setVolume(MyService.volume,MyService.volume);
                    seekBar_mono.setProgress(0);
                    isMono=false;
                }
            }
        });

        seekBar_mono.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==1){
                    mediaPlayer.setVolume(0,MyService.volume);
                    isMono=true;
                    isLeft=false;
                    chk_mono.setChecked(true);
                }
                else if(progress==-1) {
                    mediaPlayer.setVolume(MyService.volume,0);
                    isMono=true;
                    isLeft=true;
                    chk_mono.setChecked(true);
                }
                else {
                    mediaPlayer.setVolume(MyService.volume,MyService.volume);
                    isMono=false;
                    isLeft=false;
                    chk_mono.setChecked(false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if(isMono){
            chk_mono.setChecked(true);
            if(isLeft) {
                seekBar_mono.setProgress(-1);
            }
            else{
                seekBar_mono.setProgress(1);
            }
        }
        dialog.setView(view);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void showPlaySpeedDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        View view=getLayoutInflater().inflate(R.layout.playback_speed_dialog,null);

        configPlaySpeedDialog(view, dialog);

        dialog.setView(view);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configPlaySpeedDialog(View view, final AlertDialog dialog){
        final String[] text = {""};
        Button btn_OK=view.findViewById(R.id.btn_OK_playback_speed_dialog);
        Button btn_0_5x=view.findViewById(R.id.btn_0_5x);
        Button btn_0_6x=view.findViewById(R.id.btn_0_6x);
        Button btn_0_7x=view.findViewById(R.id.btn_0_7x);
        Button btn_0_8x=view.findViewById(R.id.btn_0_8x);
        Button btn_0_9x=view.findViewById(R.id.btn_0_9x);
        Button btn_1_0x=view.findViewById(R.id.btn_1_0x);
        Button btn_1_1x=view.findViewById(R.id.btn_1_1x);
        Button btn_1_2x=view.findViewById(R.id.btn_1_2x);
        Button btn_1_3x=view.findViewById(R.id.btn_1_3x);
        Button btn_1_4x=view.findViewById(R.id.btn_1_4x);
        Button btn_1_5x=view.findViewById(R.id.btn_1_5x);
        Button btn_1_6x=view.findViewById(R.id.btn_1_6x);
        Button btn_1_7x=view.findViewById(R.id.btn_1_7x);
        Button btn_1_8x=view.findViewById(R.id.btn_1_8x);
        Button btn_1_9x=view.findViewById(R.id.btn_1_9x);
        Button btn_2_0x=view.findViewById(R.id.btn_2_0x);
        Button btn_2_2x=view.findViewById(R.id.btn_2_2x);
        Button btn_2_4x=view.findViewById(R.id.btn_2_4x);
        Button btn_2_6x=view.findViewById(R.id.btn_2_6x);
        Button btn_2_8x=view.findViewById(R.id.btn_2_8x);
        Button btn_3_0x=view.findViewById(R.id.btn_3_0x);

        final List<Button> btns=new ArrayList<>();
        btns.add(btn_0_5x);
        btns.add(btn_0_6x);
        btns.add(btn_0_7x);
        btns.add(btn_0_8x);
        btns.add(btn_0_9x);
        btns.add(btn_1_0x);
        btns.add(btn_1_1x);
        btns.add(btn_1_2x);
        btns.add(btn_1_3x);
        btns.add(btn_1_4x);
        btns.add(btn_1_5x);
        btns.add(btn_1_6x);
        btns.add(btn_1_7x);
        btns.add(btn_1_8x);
        btns.add(btn_1_9x);
        btns.add(btn_2_0x);
        btns.add(btn_2_2x);
        btns.add(btn_2_4x);
        btns.add(btn_2_6x);
        btns.add(btn_2_8x);
        btns.add(btn_3_0x);
        resetButtonColor();
        try{
            float s=mediaPlayer.getPlaybackParams().getSpeed();
            if(s<=2)
            {
                btns.get((int)((s-0.5)/0.1)).setTextColor(Color.parseColor("#489494"));
            }
            else {
                btns.get((int)((s/0.2)+5)).setTextColor(Color.parseColor("#489494"));
            }
            playbackSpeed=s+"";
            text[0]=playbackSpeed+"x";
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for(final Button btn:btns){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttons = new Button[btns.size()];
                    buttons = btns.toArray(buttons);
                    resetButtonColor(buttons);
                    btn.setTextColor(Color.parseColor("#489494"));
                    text[0] =btn.getText().toString();
                    playbackSpeed= text[0].replace("x","");
                }
            });
        }

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                btn_play_speed.setText(text[0]);
                try{
                    presenter.setMediaSpeed(Float.parseFloat(playbackSpeed));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

    }

    @Override
    public void showTimerDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        View view=getLayoutInflater().inflate(R.layout.timer_dialog,null);

        final RadioGroup rg=view.findViewById(R.id.radio_group);
        Button btn_OK=view.findViewById(R.id.btn_OK_timer_dialog);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rg.getCheckedRadioButtonId()){
                    case R.id.btn_10m:{
                        presenter.start_Timer(600000);
                        break;
                    }
                    case R.id.btn_15m:{
                        presenter.start_Timer(900000);
                        break;
                    }
                    case R.id.btn_30m:{
                        presenter.start_Timer(1800000);
                        break;
                    }
                    case R.id.btn_1h:{
                        presenter.start_Timer(3600000);
                        break;
                    }
                }
                dialog.dismiss();
            }
        });


        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void setTimerText(String s) {
        btn_timer.setText(s);
    }

    @Override
    public void resetButtonColor(Button... buttons) {
        for(Button btn:buttons){
            btn.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public void addControls() {
        toolbar=findViewById(R.id.toolbar_listaudio);

        btn_equalizer=findViewById(R.id.btn_equalizer_play_audio);
        btn_play_speed=findViewById(R.id.btn_play_speed_play_audio);
        btn_timer=findViewById(R.id.btn_timer_play_audio);
        btn_decrease_volume=findViewById(R.id.btn_decrease_volume);
        btn_increase_volume=findViewById(R.id.btn_increase_volume);

        btn_skip_previous_10s=findViewById(R.id.btn_skip_previous_10s);
        btn_skip_previous_1m=findViewById(R.id.btn_skip_previous_1m);
        btn_skip_next_10s=findViewById(R.id.btn_skip_next_10s);
        btn_skip_next_1m=findViewById(R.id.btn_skip_next_1m);

        btn_play=findViewById(R.id.btn_img_play_play_audio);
        btn_previous=findViewById(R.id.btn_img_previous_play_audio);
        btn_next=findViewById(R.id.btn_img_next_play_audio);

        txt_read=findViewById(R.id.txt_read);
        txt_percent=findViewById(R.id.txt_percent);
        txt_left=findViewById(R.id.txt_left);
        txt_current_time=findViewById(R.id.txt_current_time);
        txt_total_current_time=findViewById(R.id.txt_total_current_time);
        txt_audio_book_name=findViewById(R.id.txt_audio_book_name);

        seekBar_current=findViewById(R.id.seek_bar_current_time);
        seekBar_total=findViewById(R.id.seek_bar_total_time);

        updateResourceButtonPlay();
    }

    public void updateResourceButtonPlay() {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying())
            setBtn_Play_Resource(false);
        else
            setBtn_Play_Resource(true);
    }

    public static void setBtn_Play_Resource(boolean play){
        if(btn_play==null)
            return;
        if(play==true)
            btn_play.setBackgroundResource(R.drawable.ic_play_arrow_black_48dp);
        if (play==false) {
            btn_play.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_48dp);
        }
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

    private void setReadText(){
        int read=mediaPlayer.getCurrentPosition();
        int max=mediaPlayer.getDuration();
        if(max_time==0)
            max_time=max;
        seekBar_total.setMax((int)max_time);
        seekBar_total.setProgress((int)(current_time+read));
        seekBar_current.setMax(max);
        seekBar_current.setProgress(read);
        txt_read.setText("Read "+intToTime((int)current_time+read)+" of "+intToTime((int)max_time));
        txt_left.setText("Left "+intToTime((int)(max_time-current_time-read)));
        txt_percent.setText((int)(((float)read/(float)max)*100)+"%");
        txt_current_time.setText(intToTime(read));
        txt_total_current_time.setText(intToTime(max));
    }

    @Override
    protected void onDestroy() {
        BookDatabase bd=BookDatabase.getInstance(this);
        PlayAudio.currentFile.setBTotal(mediaPlayer.getDuration());
        PlayAudio.currentFile.setBRead(mediaPlayer.getCurrentPosition());
        bd.BookFileDAO().updateBookFile(PlayAudio.currentFile);
        super.onDestroy();
    }
}
