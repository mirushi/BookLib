package luubieunghi.lbn.booklib.UI.PlayAudio;

public class PlayAudioContract {
    interface IPlayAudioView{
        void addControls();
        void addEvents();
        void setUp();
        void showEqualizerDialog();
        void showPlaySpeedDialog();
        void showTimerDialog();
        void setTimerText(String s);
    }
    interface IPlayAudioPresenter{
        void sendMail();
        void share();
        void setEqualizer(int chanel1, int chanel2, int chanel3, int chanel4, int chanel5);
        void resetEqualizer();
        void increaseVolume();
        void decreaseVolume();
        void start_stop();
        void start_Timer(int timer);
        void stop_Timer();
        void skip_previous_10s();
        void skip_previous_1m();
        void skip_next_10s();
        void skip_next_1m();
    }
}
