package luubieunghi.lbn.booklib.UI.ReadBook;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.UI.BookPageManagement.PageManagementActivity;
import luubieunghi.lbn.booklib.R;

public class BookReadingFirstPageFrm extends Fragment implements FirstPageFrmContract.FirstPageFrmView {

    private BookReadingDefineFrm defineFrm;
    private BookReadingTranslateFrm translateFrm;
    private BookReadingHighlightNoteFrm highlightNoteFrm;
    protected View view;
    private ImageView clickTextViewBackGround;
    private TextView readTextView;
    private CustomActionMode callback;
    private Animation anim_ZoomOut;
    private Boolean hasReadTextViewTouchedOnBookMarkArea;
    private ImageView bookmarkImage;
    private ObjectAnimator anim_Bookmark_Up;
    private ObjectAnimator anim_Bookmark_Down;
    private boolean hasBookmark = false;
    private FirstPageFrmPresenter firstPageFrmPresenter;

    public BookReadingFirstPageFrm() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_page_frm, container, false);
        firstPageFrmPresenter = new FirstPageFrmPresenter(this.getContext());
        firstPageFrmPresenter.setFragmentManager(getFragmentManager());
        generalSetUp();
        setUpViews();

        if (readTextView == null) {
            Log.e("Frm_1:", "On create Root View NULL!");
        } else
            Log.e("Frm_1:", "On create Root View NOT NULL!");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpEvents();
    }


    @Override
    public void setUpViews() {
        defineFrm = new BookReadingDefineFrm();
        highlightNoteFrm = new BookReadingHighlightNoteFrm(getContext());
        translateFrm = new BookReadingTranslateFrm(getContext());

        readTextView = view.findViewById(R.id.frm_1_read_textview);
        clickTextViewBackGround = view.findViewById(R.id.frm_1_imgview);
        anim_ZoomOut = AnimationUtils.loadAnimation(this.getContext(), R.anim.text_view_zoom_out);
        hasReadTextViewTouchedOnBookMarkArea = false;
        bookmarkImage = view.findViewById(R.id.bookmark_image);
        anim_Bookmark_Down = ObjectAnimator.ofFloat(bookmarkImage, "translationY", -200, 0);
        anim_Bookmark_Down.setDuration(500);
        anim_Bookmark_Down.setRepeatCount(0);
        anim_Bookmark_Down.setInterpolator(new AccelerateDecelerateInterpolator());
        anim_Bookmark_Down.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //postFirstAnimation(): This function is called after the animation ends
                postUpAnimation();
            }
        });

        callback = new CustomActionMode(this.getContext());
        callback.setFragmentManager(getFragmentManager());
        callback.setDefineFrm(defineFrm);
        callback.setHighlightNoteFrm(highlightNoteFrm);
        callback.setTranslateFrm(translateFrm);
        readTextView.setCustomSelectionActionModeCallback(callback);

    }

    @Override
    public void setUpEvents() {
        onTextViewClicked();
    }

    @Override
    public void generalSetUp() {
        //general set up
    }

    @SuppressLint({"TouchableViewAccessibility", "ClickableViewAccessibility"})
    private void onTextViewClicked() {

        readTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getContext(), "Touched!", Toast.LENGTH_SHORT).show();
                if (readTextView.hasSelection()) {
                    return false;
                }

                //Xu ly vung cho phep book mark
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x >= (int) (5.0 / 6 * readTextView.getWidth()) && y <= (int) ((1.0 / 6) * readTextView.getHeight())) {

                    hasReadTextViewTouchedOnBookMarkArea = true;
                    firstPageFrmPresenter.showBookmark(anim_Bookmark_Down);

                    return false;
                }
                hasReadTextViewTouchedOnBookMarkArea = false;
                return false;
            }
        });

        readTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean allowTransActivity = false;
                firstPageFrmPresenter.onTextViewClicked(readTextView, defineFrm, highlightNoteFrm, translateFrm, hasReadTextViewTouchedOnBookMarkArea, allowTransActivity, anim_ZoomOut, clickTextViewBackGround);
//                if (!defineFrm.isVisible() && !highlightNoteFrm.isVisible() && !translateFrm.isVisible()) {
//                    allowTransActivity = true;
//                }
//
//                //dong tat ca cac fragment
//                firstPageFrmPresenter.removeFragments(highlightNoteFrm);
//                firstPageFrmPresenter.removeFragments(translateFrm);
//                firstPageFrmPresenter.removeFragments(defineFrm);
//
//                //neu tat cac fragment deu dong thi cho phep chuyen activity
//                if (allowTransActivity && !hasReadTextViewTouchedOnBookMarkArea) {
//                    //zoom out +  change activity in 0,5 second
//                    readTextView.startAnimation(anim_ZoomOut);
//                    clickTextViewBackGround.startAnimation(anim_ZoomOut);
//                    readTextView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            readTextView.setVisibility(View.GONE);
//                            Intent intent = new Intent(getContext(), PageManagementActivity.class);
//                           getContext().startActivity(intent);
//                            Bundle bundle = ActivityOptions.makeCustomAnimation(getContext(), R.anim.anim_nothing, R.anim.anim_nothing).toBundle();
//                            startActivity(intent, bundle);
//                        }
//                    }, 500);
//                }
//            }
            }
        });
    }

    @Override
    public String toString() {
        String str_return = null;
        if (view == null) {
            str_return = "Frm_1: Root View NULL!";
        } else
            str_return = "Frm_1: Root View is created!";
        Log.e("Frm_1:", str_return);
        return str_return;
    }


    private void postUpAnimation() {
        if (!hasBookmark) {
            anim_Bookmark_Up = ObjectAnimator.ofFloat(bookmarkImage, "translationY", 0, -200);
            anim_Bookmark_Up.setDuration(500);
            anim_Bookmark_Up.setRepeatCount(0);
            anim_Bookmark_Up.start();
        } else {
            anim_Bookmark_Up = ObjectAnimator.ofFloat(bookmarkImage, "translationY", 0, -200);
            anim_Bookmark_Up.setDuration(500);
            anim_Bookmark_Up.setRepeatCount(0);
            anim_Bookmark_Up.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    bookmarkImage.setVisibility(View.INVISIBLE);
                }
            });
            anim_Bookmark_Up.start();
        }
    }
}
