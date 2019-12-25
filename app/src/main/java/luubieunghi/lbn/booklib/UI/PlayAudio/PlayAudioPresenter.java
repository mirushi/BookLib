package luubieunghi.lbn.booklib.UI.PlayAudio;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.UI.PlayMusic.MyService;

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

    //gửi mail
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

    //chia sẻ trên các ứng dụng có thể chia sẻ
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

    //set lại giá trị các tần số
    @Override
    public void setEqualizer(int chanel1, int chanel2, int chanel3, int chanel4, int chanel5) {
        equalizer.setBandLevel((short)0,(short)chanel1);
        equalizer.setBandLevel((short)1,(short)chanel2);
        equalizer.setBandLevel((short)2,(short)chanel3);
        equalizer.setBandLevel((short)3,(short)chanel4);
        equalizer.setBandLevel((short)4,(short)chanel5);
        System.out.println(equalizer.getBandFreqRange((short)0)[0]);
        System.out.println(equalizer.getBandFreqRange((short)0)[1]);
        int a=1;
    }

    //reset giá trị các tần số về 0
    @Override
    public void resetEqualizer() {
        equalizer.setBandLevel((short)0,(short)70000);
        equalizer.setBandLevel((short)1,(short)70000);
        equalizer.setBandLevel((short)2,(short)70000);
        equalizer.setBandLevel((short)3,(short)70000);
        equalizer.setBandLevel((short)4,(short)70000);
    }

    // tăng âm lượng
    @Override
    public void increaseVolume() {
        volume+=0.1f;
        if(volume>1.0f)
            volume=1.0f;
        mediaPlayer.setVolume(volume,volume);
    }

    //giảm âm lượng
    @Override
    public void decreaseVolume() {
        volume-=0.1f;
        if(volume<0.0f)
            volume=0.0f;
        mediaPlayer.setVolume(volume,volume);
    }

    //kiểm tra xem đang bật hay tắt timer hẹn giờ tắt ứng dụng
    @Override
    public void start_stop() {
        if(isRunningTimer){
            stop_Timer();
        }
        else {
            view.showTimerDialog();
        }

    }

    //bắt đầu hẹn giờ
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

    //update timer text
    private void updateTimer() {
        int minutes=(int)leftinterval/60000;
        int seconds=(int)leftinterval%60000/1000;
        String timeLeftText=minutes+":"+seconds;
        view.setTimerText(timeLeftText);
    }

    //dừng hẹn giờ
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
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-60000);
    }

    @Override
    public void skip_next_10s() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
    }

    @Override
    public void skip_next_1m() {
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+60000);
    }

    @Override
    public void play() {
        Intent it=new Intent(context,MyService.class);
        //it.putExtra("book_file",PlayAudio.currentFile);
        it.setAction("Action_Play");
        context.startService(it);
    }

    @Override
    public void next() {
        int order=PlayAudio.currentFile.getBFileOrder()+1;
        if(order>=PlayAudio.bfs.size()){
            Intent it=new Intent(context,MyService.class);
            it.setAction("Action_Stop");
            context.startService(it);
        }
        else {
            for(BookFile bf:PlayAudio.bfs){
                if(bf.getBFileOrder()==order){
                    PlayAudio.currentFile=bf;
                    break;
                }
            }
            play();
        }
    }

    @Override
    public void previous() {
        int order=PlayAudio.currentFile.getBFileOrder()-1;
        if(order<0){
            PlayAudio.currentFile=PlayAudio.bfs.get(0);
        }
        else {
            for(BookFile bf:PlayAudio.bfs){
                if(bf.getBFileOrder()==order){
                    PlayAudio.currentFile=bf;
                    break;
                }
            }
        }
        play();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setMediaSpeed(float speed) {
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
    }


}
