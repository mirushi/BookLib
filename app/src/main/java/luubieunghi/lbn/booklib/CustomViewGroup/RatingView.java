package luubieunghi.lbn.booklib.CustomViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import luubieunghi.lbn.booklib.R;

public class RatingView extends LinearLayout {

    Context mContext;
    int mNumStars;
    int mRating;

    boolean ratingIsChanging;

    OnRateListener onRateListener;
    boolean allowRating;

    private final CheckBox.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (!allowRating)
                return;
            if (ratingIsChanging)
                return;

            int buttonId = buttonView.getId();
            updateCheckedStars(buttonId + 1);
        }
    };

    public RatingView(Context context) {
        super(context);
        init(context, null);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        if (context != null && attrs != null)
        {
            this.mContext = context;
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(Gravity.CENTER);
        }

        //Nhận attribute từ XML tryền vào khi inflate view.
        if (attrs != null)
        {
            final TypedArray styleAttributesArray = context.obtainStyledAttributes(attrs, R.styleable.RatingView);
            mNumStars = styleAttributesArray.getInteger(R.styleable.RatingView_numStars, mNumStars);
            mRating = styleAttributesArray.getInteger(R.styleable.RatingView_rating, mRating);
            styleAttributesArray.recycle();
        }
        else
        {
            mNumStars = 5;
            mRating = 0;
        }

        allowRating = true;
        addStars();
        updateCheckedStars(mNumStars);
    }

    private void addStars()
    {
        if (mNumStars != 0){
            for (int i=0;i<mNumStars;++i)
            {
                CheckBox ratingView = getNewRatingView();
                addView(ratingView);
            }
        }
    }

    private CheckBox getNewRatingView()
    {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CheckBox checkBox = (CheckBox)inflater.inflate(R.layout.rating_star_item, this, false);
        checkBox.setId(getChildCount());
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        return checkBox;
    }

    private void updateCheckedStars(int stars)
    {

        if (stars > mNumStars)
            return;

        ratingIsChanging = true;
        mRating = stars;

        //Tắt toàn bộ sao từ cuối cho đến số sao được chọn.
        for (int i=mNumStars - 1;i>=stars;--i)
        {
            ((CheckBox)getChildAt(i)).setChecked(false);
        }

        //Bật toàn bộ sao từ đầu cho đến số sao được chọn.
        for (int i=0;i<stars;++i)
        {
            ((CheckBox)getChildAt(i)).setChecked(true);
        }

        ratingIsChanging = false;
    }

    public interface OnRateListener{
        void onRated(int rating, int totalStar);
    }

    //Các phương thức để đối tượng có thể thao tác được từ bên ngoài.
    public void setRating(int rating)
    {
        updateCheckedStars(rating);
    }

    public int getRating()
    {
        return mRating;
    }

    public void setStars(int stars)
    {
        removeAllViews();
        init(null, null);
    }

    public int getStars()
    {
        return mNumStars;
    }

}
