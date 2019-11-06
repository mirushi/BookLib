package luubieunghi.lbn.booklib.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector;
import luubieunghi.lbn.booklib.R;



public class AppWideGesturesListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private final int swingVelocity = 500;

    public AppWideGesturesListener(Context ctx)
    {
        gestureDetector = new GestureDetector(ctx, new GesturesListener(ctx));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     *Hiện thực hàm này ở các Activity có thể chuyển qua trình nghe nhạc bằng cách vuốt từ dưới lên.
     * Intent intent = new Intent(ActivityHienTai.class, PlayMusic.class);
     * startActivity(intent);
     * activity.overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
     */
    public void SwipeUpFromBottom()
    {

    }

    public void SwipeDownFromTop()
    {

    }

    public class GesturesListener extends GestureDetector.SimpleOnGestureListener
    {
        Context context;
        Activity activity;
        int screenHeight;

        public GesturesListener(Context context)
        {
            this.context = context;
            //Kiểm tra nếu context là một activity thì ta lấy nó dùng luôn.
            if (context instanceof Activity)
                activity = (Activity)context;

            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            screenHeight = metrics.heightPixels;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            double firstY = 0;
            double secondY = 0;
            if (e1 != null)
                firstY = e1.getRawY();
//            if (e2 != null)
//                secondY = e2.getY();
            //double distant = firstY - secondY;

            //Nếu điểm bắt đầu ở dưới cùng của màn hình.
            if (inRange(firstY, screenHeight, 50))
            {
                //Velocity âm tức là vuốt từ dưới lên trên.
                if (velocityY < -swingVelocity)
                {
                    //Vuốt từ dưới lên.
                    SwipeUpFromBottom();
                    return true;
                }
            }
            //Điểm bắt đầu trên cùng màn hình (xử lý việc vuốt từ trên xuống).
            else if (inRange(firstY, 0, 200))
            {
                //Velocity dương tứ là vuốt từ trên xuống.
                if (velocityY > swingVelocity)
                {
                    //Vuốt từ trên xuống.
                    SwipeDownFromTop();
                    return true;
                }
            }
            return false;
        }

        private boolean inRange (double value, double comValue, double range)
        {
            if (comValue - range <= value && comValue + range >= value)
                return true;
            return false;
        }
    }

}

