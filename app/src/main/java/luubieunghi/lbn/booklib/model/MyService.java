package luubieunghi.lbn.booklib.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.openListSong;

public class MyService extends Service {

    private static final String chanel_ID="MUSIC";
    private static final String chanel_Name="PLAY MUSIC";

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer=new MediaPlayer();
        mediaPlayer= MediaPlayer.create(getBaseContext(), R.raw.van_su_tuy_duyen);
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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
            }
        }
        return START_STICKY;
    }

    private void play_MediaPlayer(Intent intent) {
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
            else {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
            }
        }
        else{
            mediaPlayer=new MediaPlayer();
            mediaPlayer= MediaPlayer.create(getBaseContext(),R.raw.van_su_tuy_duyen);
        }

    }

    private void stop_MyService(Intent intent) {
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1);
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer=null;
        }
    }

    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(this,NotificationReciever.class);
        intent.putExtra("control_id",id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }


    private void createNotificationChanel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(chanel_ID,chanel_Name, NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void showNotification() {
        createNotificationChanel();

        Intent intent=new Intent(this, openListSong.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,0);

        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), R.layout.custome_notification);

        notificationLayout.setOnClickPendingIntent(R.id.btn_close_notification,
                onButtonNotificationClick(R.id.btn_close_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_play_notification,
                onButtonNotificationClick(R.id.btn_play_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_previous_notification,
                onButtonNotificationClick(R.id.btn_previous_notification));
        notificationLayout.setOnClickPendingIntent(R.id.btn_next_notification,
                onButtonNotificationClick(R.id.btn_next_notification));


        Notification customeNotification= new NotificationCompat.Builder(getBaseContext(),chanel_ID)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setSmallIcon(R.drawable.icon_f)
                .setPriority(Notification.PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        //show notification
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,customeNotification);
    }


}
