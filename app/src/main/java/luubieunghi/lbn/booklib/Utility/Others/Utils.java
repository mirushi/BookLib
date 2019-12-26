package luubieunghi.lbn.booklib.Utility.Others;

import android.content.res.Resources;
import android.util.TypedValue;

import luubieunghi.lbn.booklib.BookLib;

public class Utils {
    public static int fromDPtoPX(int dp){
        Resources r = BookLib.getAppContext().getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return px;
    }
}
