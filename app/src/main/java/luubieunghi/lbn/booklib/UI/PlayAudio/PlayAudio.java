package luubieunghi.lbn.booklib.UI.PlayAudio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.PlayMusic.MyService;
import luubieunghi.lbn.booklib.UI.PlayMusic.PlayMusic;

import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.equalizer;

public class PlayAudio extends AppCompatActivity implements PlayAudioContract.IPlayAudioView{

    private Toolbar toolbar;
    private Button btn_equalizer, btn_timer, btn_play_speed, btn_increase_volume, btn_decrease_volume;
    private Button btn_skip_previous_10s, btn_skip_previous_1m, btn_skip_next_10s,btn_skip_next_1m;

    private PlayAudioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        addControls();
        setUp();
        addEvents();
        presenter=new PlayAudioPresenter(this, PlayAudio.this);
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
        sb1.setProgress(equalizer.getBandLevel((short)0));
        sb2.setProgress(equalizer.getBandLevel((short)1));
        sb3.setProgress(equalizer.getBandLevel((short)2));
        sb4.setProgress(equalizer.getBandLevel((short)3));
        sb5.setProgress(equalizer.getBandLevel((short)4));
        Button btn_reset_equalizer=view.findViewById((R.id.btn_reset_equalizer));
        Button btn_OK=view.findViewById(R.id.btn_OK_equalizer_dialog);

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
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void showPlaySpeedDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        final View view=getLayoutInflater().inflate(R.layout.equalizer_dialog,null);

        Button btn_OK=view.findViewById(R.id.btn_OK_playback_speed_dialog);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RadioGroup rg=view.findViewById(R.id.radio_group);
        dialog.setView(view);
        dialog.show();
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
    }


}
