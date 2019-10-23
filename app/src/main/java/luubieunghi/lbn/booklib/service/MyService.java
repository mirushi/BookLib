package luubieunghi.lbn.booklib.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import luubieunghi.lbn.booklib.Activity.PlayMusic;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.Activity.openListSong;

public class MyService extends Service {

    private static final String chanel_ID="MUSIC";
    private static final String chanel_Name="PLAY MUSIC";
    public static  MediaPlayer mediaPlayer=new MediaPlayer();
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
        notificationLayout=new RemoteViews(getPackageName(), R.layout.custome_notification);
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
        dongBoImageResource();
        if(mediaPlayer.isPlaying())
           mediaPlayer.pause();
        else {
           mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
           mediaPlayer.start();
        }
    }

    // xóa thông báo và dừng mediaplayer
    private void stop_MyService(Intent intent) {
        //đóng notification
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        //tắt nhạc
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
        // đồng bộ image resource button play
        PlayMusic.setBtn_PlayResource(true);
    }

    public void dongBoImageResource(){
        if(mediaPlayer.isPlaying()){
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_play_arrow_black_24dp);
            PlayMusic.setBtn_PlayResource(true);
        }
        else {
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_pause_circle_outline_black_24dp);
            PlayMusic.setBtn_PlayResource(false);
        }

    }

    //tạo intent sync giữa notification và activity
    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(getBaseContext(), NotificationReciever.class);
        intent.putExtra("control_id",id);
        return PendingIntent.getBroadcast(getBaseContext(), id, intent, 0);
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
        Intent intent=new Intent(getBaseContext(), PlayMusic.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(getBaseContext(),0,intent,0);
        Notification customNotification= new NotificationCompat.Builder(getBaseContext(),chanel_ID)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setSmallIcon(R.drawable.icon_f)
                .setPriority(Notification.PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        if(mediaPlayer.isPlaying()){
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_play_arrow_black_24dp);
        }
        else{
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_pause_circle_outline_black_24dp);
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
