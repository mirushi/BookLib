package luubieunghi.lbn.booklib.UI.PlayAudio;

public class PlayAudioContract {
    interface IPlayAudioView{
        void addControls();
        void addEvents();
        void setUp();
        void showEqualizerDialog();
        void showPlaySpeedDialog();
        void showTimerDialog();
    }
    interface IPlayAudioPresenter{
        void sendMail();
        void share();
    }
}
