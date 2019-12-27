package luubieunghi.lbn.booklib.UI.PlayMusic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.IBinder;

import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import luubieunghi.lbn.booklib.Database.BookDatabase;
import luubieunghi.lbn.booklib.Model.Book.Book;
import luubieunghi.lbn.booklib.Model.BookFile.BookFile;
import luubieunghi.lbn.booklib.Model.Publisher.Publisher;
import luubieunghi.lbn.booklib.Model.Song.Song;
import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.PlayAudio.PlayAudio;

public class MyService extends Service {

    private static final String chanel_ID="MUSIC";
    private static final String chanel_Name="PLAY MUSIC";
    public  static  float volume=0.5f;
    public static  MediaPlayer mediaPlayer=new MediaPlayer();
    public static Equalizer equalizer=null;
    public static  RemoteViews notificationLayout;
    boolean isMusic;
    public static boolean isMono=false;
    public  static  boolean isLeft=false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //tạo một mediaplayer trong đây
    @Override
    public void onCreate() {
        super.onCreate();
        if(equalizer==null)
            equalizer=new Equalizer(0, mediaPlayer.getAudioSessionId());
        mediaPlayer.setVolume(volume,volume);
        createEqualizer();
        notificationLayout=new RemoteViews(getPackageName(), R.layout.custome_notification);
    }

    public static void createEqualizer() {
        if(equalizer==null)
            equalizer=new Equalizer(0, mediaPlayer.getAudioSessionId());
        equalizer.setEnabled(true);
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        //lấy action của intent để xử lí
        String action=intent.getAction();
        final Song song=(Song)intent.getSerializableExtra("song");
        final BookFile bf=(BookFile) intent.getSerializableExtra("book_file");
        final Book b=(Book)intent.getSerializableExtra("book");
        isMusic=intent.getBooleanExtra("isMusic",false);
        //xử lí chơi nhạc
        if(isMusic)
        {
            if((song!=null)&&(!song.getSongID().equals(PlayMusic.currentSong.getSongID())))
            {
                if(mediaPlayer!=null){
                    //mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer=null;
                }
                mediaPlayer=MediaPlayer.create(getApplicationContext(), Uri.parse(song.getFilePath()));
            }
        }
        //xư lí chơi audio books
        else {
            if(bf!=null){
                if(PlayAudio.currentFile==null){
                    if(mediaPlayer!=null){
                        //mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                    System.out.println(bf.getBFilePath());
                    mediaPlayer=MediaPlayer.create(getApplicationContext(), Uri.parse(bf.getBFilePath()));
                    PlayAudio.currentFile=bf;
                }
                else {
                    if(bf.getBFilePath().equals(PlayAudio.currentFile.getBFilePath())){
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                    }
                    else{
                        if(mediaPlayer!=null){
                            //mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer=null;
                        }
                        System.out.println(bf.getBFilePath());
                        mediaPlayer=MediaPlayer.create(getApplicationContext(), Uri.parse(bf.getBFilePath()));
                        PlayAudio.currentFile=bf;
                    }
                }
            }
        }
        //tùy theo action mà xử lí
        if(action!=null){
            if(action.equals("Action_Stop")){
                stop_MyService();
            }
            else {
                if(action.equals("Action_Play")){
                    if(isMusic){
                       if(song!=null){
                           notificationLayout.setTextViewText(R.id.txt_tenbaihat_notification,song.getSongName());
                           notificationLayout.setTextViewText(R.id.txt_tencasi_notification,song.getArtistNames());
                       }
                    }
                    else {
                        if(b!=null){
                            notificationLayout.setTextViewText(R.id.txt_tenbaihat_notification,b.getBookTitle());
                            Publisher p=BookDatabase.getInstance(getApplicationContext()).PublisherDAO().getBookPublisher(b.getBookID());
                            notificationLayout.setTextViewText(R.id.txt_tencasi_notification,p.getPublisherName());
                        }
                    }
                    showNotification();
                    play_MediaPlayer();
                }
                if(action.equals("Action_Next")){
                    next_MediaPlayer();
                }
                if(action.equals("Action_Previous")){
                    previous_MediaPlayer();
                }
            }
        }
        //xử lí chơi hết nhạc
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                    onComplete(mp,isMusic);
//                }
                onComplete(mp,isMusic);
            }
        });

        return START_STICKY;
    }

    private void xuLiRepeat(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
        mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                    onComplete(mp,isMusic);
//                }
                onComplete(mp,isMusic);
            }
        });
        play_MediaPlayer();
        PlayMusic.isRepeat=false;
        PlayMusic.btn_img_Repeat.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255,255,255)));
        return;
    }

    private void xuLiShuffle(){
        int max=PlayMusic.dsBaiHat.size();
        Random r=new Random();
        int index=r.nextInt(max);
        PlayMusic.currentSong=PlayMusic.dsBaiHat.get(index);
        MyService.mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
        mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                    onComplete(mp,isMusic);
//                }
                onComplete(mp,isMusic);
            }
        });
        play_MediaPlayer();
        return;
    }

    private void xuLiChoiNhacTiep(){
        int index=PlayMusic.dsBaiHat.indexOf(PlayMusic.currentSong)+1;
        if(index>=PlayMusic.dsBaiHat.size()){
            index=0;
        }
        PlayMusic.currentSong=PlayMusic.dsBaiHat.get(index);
        MyService.mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
        mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//                if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                    onComplete(mp,isMusic);
//                }
                onComplete(mp,isMusic);
            }
        });
        play_MediaPlayer();
        return;
    }

    private void xuLiAudio(){
        PlayAudio.currentFile.setBRead(mediaPlayer.getDuration());
        PlayAudio.currentFile.setBTotal(mediaPlayer.getDuration());
        BookDatabase.getInstance(getBaseContext()).BookFileDAO().updateBookFile(PlayAudio.currentFile);
        int order=PlayAudio.currentFile.getBFileOrder()+1;
        if(order>=PlayAudio.bfs.size()){
            stop_MyService();
        }
        else{
            for(BookFile bf:PlayAudio.bfs){
                if(bf.getBFileOrder()==order)
                    PlayAudio.currentFile=bf;
            }
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayAudio.currentFile.getBFilePath()));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                        onComplete(mp,false);
//                    }
                    onComplete(mp,isMusic);
                }
            });
            play_MediaPlayer();
        }
    }

    private void onComplete(MediaPlayer mp, boolean isMusic){
        mediaPlayer.seekTo(0);
        if(isMusic){
            if(PlayMusic.isRepeat){
                xuLiRepeat();
                return;
            }
            else if(PlayMusic.isShuffle){
                xuLiShuffle();
                return;
            }
            else {
                xuLiChoiNhacTiep();
                return;
            }
        }
        else{
            BookDatabase bd=BookDatabase.getInstance(getApplicationContext());
            PlayAudio.currentFile.setBTotal(mediaPlayer.getDuration());
            if(PlayAudio.currentFile.getBRead()!=PlayAudio.currentFile.getBTotal())
                PlayAudio.currentFile.setBRead(mediaPlayer.getDuration());
            bd.BookFileDAO().updateBookFile(PlayAudio.currentFile);
            xuLiAudio();
        }
    }

    private void previous_MediaPlayer() {
        int index=PlayMusic.dsBaiHat.indexOf(PlayMusic.currentSong)-1;
        if(index<0){
            index=PlayMusic.dsBaiHat.size()-1;
        }
        PlayMusic.currentSong=PlayMusic.dsBaiHat.get(index);
        if(mediaPlayer.isPlaying()){
            MyService.mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                        onComplete(mp,isMusic);
//                    }
                    onComplete(mp,isMusic);
                }
            });
            play_MediaPlayer();
        }
        else {
            MyService.mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
//                    if(mediaPlayer.getCurrentPosition()==mediaPlayer.getDuration()){
//                        onComplete(mp,isMusic);
//                    }
                    onComplete(mp,isMusic);
                }
            });
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
            MyService.mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
            play_MediaPlayer();
        }
        else {
            MyService.mediaPlayer.stop();
            MyService.mediaPlayer.release();
            MyService.mediaPlayer= MediaPlayer.create(getApplicationContext(), Uri.parse(PlayMusic.currentSong.getFilePath()));
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
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.cancel(1);
        mediaPlayer.pause();
        if(isMusic){
            PlayMusic.setBtn_PlayResource(true);
        }
        else {
            PlayAudio.setBtn_Play_Resource(true);
        }
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
        Intent intent=new Intent(getApplicationContext(), MyService.class);//1
        intent.putExtra("isMusic",isMusic );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);//1
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
