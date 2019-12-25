package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.UI.OpenListSong.OpenListSong;

public class PlayMusicPresenter implements PlayMusicContract.IPlayMusicPresenter {

    private Context context;
    public PlayMusicPresenter(Context context){
        this.context=context;
    }


    @Override
    public void nextSong() {
        Intent it=new Intent(context,MyService.class);
        it.setAction("Action_Next");
        it.putExtra("isMusic", true);
        context.startService(it);
    }

    @Override
    public void previousSong() {
        Intent it=new Intent(context,MyService.class);
        it.setAction("Action_Previous");
        it.putExtra("isMusic", true);
        context.startService(it);
    }

    @Override
    public void sendMail() {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"booklibdevteam@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Comment About Booklib App");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello Booklib Dev Team\n");
        try {
            context.startActivity(Intent.createChooser(i, "Send Mail"));
        } catch (Exception ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void share() {

        Intent i =new Intent(Intent.ACTION_SEND);
        i.setType("text/*");
        i.putExtra(Intent.EXTRA_TEXT,"This song is so great");
        i.putExtra(Intent.EXTRA_TITLE,"Share Song");
        try{
            context.startActivity(Intent.createChooser(i,"Share with ..."));
        }
        catch (Exception ex){
            Toast.makeText(context,"There are no app suitable for sharing",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void playMusicService(Song song) {
        Intent intent=new Intent(context, MyService.class);
        if(MyService.mediaPlayer.isPlaying())
            intent.setAction("Action_Stop");
        else{
            intent.setAction("Action_Play");
        }
        intent.putExtra("isMusic",true);
        intent.putExtra("song",song);
        context.startService(intent);
    }

}
