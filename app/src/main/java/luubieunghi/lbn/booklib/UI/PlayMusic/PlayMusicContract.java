package luubieunghi.lbn.booklib.UI.PlayMusic;

public class PlayMusicContract {
    interface IPlayMusicView{
        void addControls();
        void addEvents();
        void updateResourceButtonPlay();
        void ConfigGesturesListener();
        void showPopupMenu();
        void openListSongIntent();
    }
    interface IPlayMusicPresenter{

        void sendMail();
        void share();
        void playMusicService();
    }
}
