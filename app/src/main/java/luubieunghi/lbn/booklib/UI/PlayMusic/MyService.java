package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.PlayAudio.PlayAudio;

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
        //mediaPlayer= MediaPlayer.create(getBaseContext(),);
        //mediaPlayer=MediaPlayer.create(getBaseContext(), Uri.parse(song.getFilePath()));
        mediaPlayer.setVolume(volume,volume);
        createEqualizer();
        notificationLayout=new RemoteViews(getPackageName(), R.layout.custome_notification);
    }

    public static void createEqualizer() {
        equalizer.setEnabled(true);
        equalizer.setBandLevel((short)0,(short)70000);
        equalizer.setBandLevel((short)1,(short)70000);
        equalizer.setBandLevel((short)2,(short)70000);
        equalizer.setBandLevel((short)3,(short)70000);
        equalizer.setBandLevel((short)4,(short)70000);
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        //lấy action của intent để xử lí
        String action=intent.getAction();
        final Song song=(Song)intent.getSerializableExtra("song");
        final boolean isMusic=intent.getBooleanExtra("isMusic",false);
        //xử lí chơi nhạc
        if(isMusic)
        {
            if((song!=null)&&(!song.getSongID().equals(PlayMusic.currentSong.getSongID())))
            {
                mediaPlayer=MediaPlayer.create(getBaseContext(), Uri.parse(song.getFilePath()));
            }
        }
        else {
//            xư lí chơi audio books
//            BookFile bf=(BookFile) intent.getSerializableExtra("book_file");
//            mediaPlayer=MediaPlayer.create(getBaseContext(),Uri.parse(bf.getBFilePath()));
        }
        //tùy theo action mà xử lí
        if(action!=null){
            if(action.equals("Action_Stop")){
                stop_MyService();
            }
            else {
                showNotification();
                if(action.equals("Action_Play")){
                    play_MediaPlayer();
                }
                if(action.equals("Action_Next")){
                    next_MediaPlayer();
                }
                if(action.equals("Action_Previous")){
                    previous_MediaPlayer();
                }
                if(action.equals("Action_Shuffle")){
                    shuffle_MediaPlayer();
                }
                if(action.equals("Action_Repeat")){
                    repeat_MediaPlayer();
                }
            }
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                PlayAudio.currentFile.setBRead(PlayAudio.currentFile.getBTotal());
//                BookDatabase.getInstance(getBaseContext()).BookFileDAO().updateBookFile(PlayAudio.currentFile);
//                int order=PlayAudio.currentFile.getBFileOrder()+1;
//                if(order>=PlayAudio.bfs.size()){
//                    stop_MyService();
//                }
//                else{
//                    for(BookFile bf:PlayAudio.bfs){
//                        if(bf.getBFileOrder()==order)
//                            PlayAudio.currentFile=bf;
//                    }
//                    mediaPlayer.stop();
//                    mediaPlayer=MediaPlayer.create(getBaseContext(),Uri.parse(PlayAudio.currentFile.getBFilePath()));
//                }

                if(isMusic){
                   next_MediaPlayer();
                    return;
                }
                else{

                }
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                PlayMusic.setSeekBarProgress(percent);
            }
        });

        return START_STICKY;
    }

    private void repeat_MediaPlayer() {
    }

    private void shuffle_MediaPlayer() {
    }

    private void previous_MediaPlayer() {
        int index=PlayMusic.dsBaiHat.indexOf(PlayMusic.currentSong)-1;
        if(index<0){
            index=PlayMusic.dsBaiHat.size();
        }
        PlayMusic.currentSong=PlayMusic.dsBaiHat.get(index);
        if(mediaPlayer.isPlaying()){
            MyService.mediaPlayer.stop();
            MyService.mediaPlayer.release();
            MyService.mediaPlayer= MediaPlayer.create(getBaseContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
            play_MediaPlayer();
        }
        else {
            MyService.mediaPlayer.stop();
            MyService.mediaPlayer.release();
            MyService.mediaPlayer= MediaPlayer.create(getBaseContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
        }
    }

    private void next_MediaPlayer() {
        int index=PlayMusic.dsBaiHat.indexOf(PlayMusic.currentSong)+1;
        if(index>=PlayMusic.dsBaiHat.size()){
            index=0;
        }
        PlayMusic.currentSong=PlayMusic.dsBaiHat.get(index);
        if(mediaPlayer.isPlaying()){
            MyService.mediaPlayer.stop();
            MyService.mediaPlayer.release();
            MyService.mediaPlayer= MediaPlayer.create(getBaseContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
            play_MediaPlayer();
        }
        else {
            MyService.mediaPlayer.stop();
            MyService.mediaPlayer.release();
            MyService.mediaPlayer= MediaPlayer.create(getBaseContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
        }
    }

    // bắt đầu phát nhạc
    private void play_MediaPlayer() {
        if(mediaPlayer.isPlaying())
        {
            if(PlayMusic.btn_img_Play!=null)
                PlayMusic.setBtn_PlayResource(true);
            if(PlayAudio.btn_play!=null)
                PlayAudio.setBtn_Play_Resource(true);
           mediaPlayer.pause();
        }
        else {
            if(PlayMusic.btn_img_Play!=null)
                PlayMusic.setBtn_PlayResource(false);
            if(PlayAudio.btn_play!=null)
                PlayAudio.setBtn_Play_Resource(false);
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
           mediaPlayer.start();
        }
    }

    // xóa thông báo và dừng mediaplayer
    private void stop_MyService() {
        //đóng notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
        mediaPlayer.pause();
        PlayMusic.setBtn_PlayResource(true);
    }

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
        //thay đổi resource cho nút play
        if(mediaPlayer.isPlaying()){
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_play_arrow_white_24dp);
        }
        else{
            notificationLayout.setImageViewResource(R.id.btn_play_notification,R.drawable.ic_pause_circle_outline_white_24dp);
        }

        //tạo pending intent cho lúc chơi media
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
