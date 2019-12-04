package luubieunghi.lbn.booklib.UI.PlayAudio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



import luubieunghi.lbn.booklib.R;

public class PlayAudio extends AppCompatActivity implements PlayAudioContract.IPlayAudioView{

    private Toolbar toolbar;
    private ImageButton btn_equalizer, btn_timer;
    private Button btn_play_speed;

    private PlayAudioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        addControls();
        setUp();
        addEvents();
        presenter=new PlayAudioPresenter(this);
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
                showTimerDialog();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        Button btn_OK=view.findViewById(R.id.btn_OK_equalizer_dialog);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void showPlaySpeedDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        View view=getLayoutInflater().inflate(R.layout.playback_speed_dialog,null);

        Button btn_OK=view.findViewById(R.id.btn_OK_playback_speed_dialog);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void showTimerDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(PlayAudio.this).create();

        View view=getLayoutInflater().inflate(R.layout.timer_dialog,null);

        Button btn_OK=view.findViewById(R.id.btn_OK_timer_dialog);
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void addControls() {
        toolbar=findViewById(R.id.toolbar_listaudio);
        btn_equalizer=findViewById(R.id.btn_equalizer_play_audio);
        btn_play_speed=findViewById(R.id.btn_play_speed_play_audio);
        btn_timer=findViewById(R.id.btn_timer_play_audio);
    }



}
