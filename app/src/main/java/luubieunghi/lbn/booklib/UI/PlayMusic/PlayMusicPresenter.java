package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import luubieunghi.lbn.booklib.UI.OpenListSong.OpenListSong;

public class PlayMusicPresenter implements PlayMusicContract.IPlayMusicPresenter {

    private Context context;
    public PlayMusicPresenter(Context context){
        this.context=context;
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
    public void playMusicService() {
        Intent intent=new Intent(context, MyService.class);
        intent.setAction("Action_Play");
        context.startService(intent);
    }

}
