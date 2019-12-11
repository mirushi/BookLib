package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import luubieunghi.lbn.booklib.R;

public class MyService extends Service {

    private static final String chanel_ID="MUSIC";
    private static final String chanel_Name="PLAY MUSIC";
    public   static  float volume=0.5f;
    public static  MediaPlayer mediaPlayer=new MediaPlayer();
    public static Equalizer equalizer=new Equalizer(0, mediaPlayer.getAudioSessionId());
    public static  RemoteViews notificationLayout;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //tạo một mediaplayer trong đây
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer= MediaPlayer.create(getBaseContext(), R.raw.van_su_tuy_duyen);
        mediaPlayer.setVolume(volume,volume);
        createEqualizer();
        notificationLayout=new RemoteViews(getPackageName(), R.layout.custome_notification);
    }

    private void createEqualizer() {

        equalizer.setEnabled(true);
        equalizer.setBandLevel((short)0,equalizer.getBandLevel((short)0));
        equalizer.setBandLevel((short)1,equalizer.getBandLevel((short)1));
        equalizer.setBandLevel((short)2,equalizer.getBandLevel((short)2));
        equalizer.setBandLevel((short)3,equalizer.getBandLevel((short)3));
        equalizer.setBandLevel((short)4,equalizer.getBandLevel((short)4));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //lấy action của intent để xử lí
        String action=intent.getAction();
        if(action!=null){
            if(action.equals("Action_Stop")){
                stop_MyService(intent);
            }
            else {
                showNotification();
                if(action.equals("Action_Play")){
                    play_MediaPlayer(intent);
                }
                if(action.equals("Action_Next")){
                    next_MediaPlayer(intent);
                }
                if(action.equals("Action_Previous")){
                    previous_MediaPlayer(intent);
                }
                if(action.equals("Action_Shuffle")){
                    shuffle_MediaPlayer(intent);
                }
                if(action.equals("Action_Repeat")){
                    repeat_MediaPlayer(intent);
                }
            }
        }
        return START_STICKY;
    }

    private void repeat_MediaPlayer(Intent intent) {
    }

    private void shuffle_MediaPlayer(Intent intent) {
    }

    private void previous_MediaPlayer(Intent intent) {
    }

    private void next_MediaPlayer(Intent intent) {

    }

    // bắt đầu phát nhạc
    private void play_MediaPlayer(Intent intent) {
        if(mediaPlayer.isPlaying())
        { PlayMusic.setBtn_PlayResource(true);
           mediaPlayer.pause();
        }
        else {
            PlayMusic.setBtn_PlayResource(false);
           mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
           mediaPlayer.start();
        }
    }

    // xóa thông báo và dừng mediaplayer
    private void stop_MyService(Intent intent) {
        //đóng notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
        //tắt nhạc
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            PlayMusic.setBtn_PlayResource(true);
        }
    }

//    public void dongBoImageResource(){
//        if(mediaPlayer.isPlaying()){
//            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_play_arrow_black_24dp);
//            PlayMusic.setBtn_PlayResource(true);
//        }
//        else {
//            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_pause_circle_outline_black_24dp);
//            PlayMusic.setBtn_PlayResource(false);
//        }
//
//    }

    //tạo intent sync giữa notification và activity
    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(getBaseContext(), NotificationReciever.class);//1
        intent.putExtra("control_id",id);
        return PendingIntent.getBroadcast(getBaseContext(), id, intent, 0);//1
    }

    //tạo chanel cho notification
    private void createNotificationChanel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(chanel_ID,chanel_Name, NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // hiển thị notification
    private void showNotification() {
        createNotificationChanel();
        Intent intent=new Intent(getBaseContext(), MyService.class);//1
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(getBaseContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);//1
        Notification customNotification= new NotificationCompat.Builder(getBaseContext(),chanel_ID)//1
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setSmallIcon(R.drawable.icon_f)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        if(mediaPlayer.isPlaying()){
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_play_arrow_white_24dp);
        }
        else{
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_pause_circle_outline_white_24dp);
        }

        notificationLayout.setOnClickPendingIntent(R.id.btn_close_notification,
                onButtonNotificationClick(R.id.btn_close_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_play_notification,
                onButtonNotificationClick(R.id.btn_play_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_previous_notification,
                onButtonNotificationClick(R.id.btn_previous_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_next_notification,
                onButtonNotificationClick(R.id.btn_next_notification));

        //show notification
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,customNotification);
    }
}
