package luubieunghi.lbn.booklib;

import android.app.Application;
import android.content.Context;

public class BookLib extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        BookLib.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BookLib.context;
    }
}
