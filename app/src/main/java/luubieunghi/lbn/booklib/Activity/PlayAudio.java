package luubieunghi.lbn.booklib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;



import luubieunghi.lbn.booklib.R;

public class PlayAudio extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        
        addControls();
        init();
        addEvents();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.play_music_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvents() {

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

    }

    private void init() {

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.play_music_menu);
    }

    private void addControls() {
        toolbar=findViewById(R.id.toolbar_listaudio);
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
