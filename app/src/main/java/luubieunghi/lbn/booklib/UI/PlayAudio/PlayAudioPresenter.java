package luubieunghi.lbn.booklib.UI.PlayAudio;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Toast;

import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.equalizer;
import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.mediaPlayer;
import static luubieunghi.lbn.booklib.UI.PlayMusic.MyService.volume;

public class PlayAudioPresenter implements PlayAudioContract.IPlayAudioPresenter {
    private Context context;
    private PlayAudioContract.IPlayAudioView view;
    private CountDownTimer countDownTimer;
    private boolean isRunningTimer=false;
    private int leftinterval=1000;
    public PlayAudioPresenter(Context context, PlayAudioContract.IPlayAudioView view){
        this.context=context;
        this.view=view;
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
    public void setEqualizer(int chanel1, int chanel2, int chanel3, int chanel4, int chanel5) {
        equalizer.setBandLevel((short)0,(short)chanel1);
        equalizer.setBandLevel((short)1,(short)chanel2);
        equalizer.setBandLevel((short)2,(short)chanel3);
        equalizer.setBandLevel((short)3,(short)chanel4);
        equalizer.setBandLevel((short)4,(short)chanel5);
    }

    @Override
    public void resetEqualizer() {
        equalizer.setBandLevel((short)0,(short)0);
        equalizer.setBandLevel((short)1,(short)0);
        equalizer.setBandLevel((short)2,(short)0);
        equalizer.setBandLevel((short)3,(short)0);
        equalizer.setBandLevel((short)4,(short)0);
    }

    @Override
    public void increaseVolume() {
        volume+=0.1f;
        if(volume>1.0f)
            volume=1.0f;
        mediaPlayer.setVolume(volume,volume);
    }

    @Override
    public void decreaseVolume() {
        volume-=0.1f;
        if(volume<0.0f)
            volume=0.0f;
        mediaPlayer.setVolume(volume,volume);
    }

    @Override
    public void start_stop() {
        if(isRunningTimer){
            stop_Timer();
        }
        else {
            view.showTimerDialog();
        }

    }

    @Override
    public void start_Timer(int timer) {
        leftinterval=timer;
        countDownTimer=new CountDownTimer(timer,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftinterval-=1000;
                updateTimer();
            }

            @Override
            public void onFinish() {
                System.exit(0);
            }
        }.start();
        isRunningTimer=true;
    }

    private void updateTimer() {
        int minutes=(int)leftinterval/60000;
        int seconds=(int)leftinterval%60000/1000;
        String timeLeftText=minutes+":"+seconds;
        view.setTimerText(timeLeftText);
    }

    @Override
    public void stop_Timer() {
        countDownTimer.cancel();
        isRunningTimer=false;
        view.setTimerText("");
    }

    @Override
    public void skip_previous_10s() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
    }

    @Override
    public void skip_previous_1m() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-600000);
    }

    @Override
    public void skip_next_10s() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
    }

    @Override
    public void skip_next_1m() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+600000);
    }


}
