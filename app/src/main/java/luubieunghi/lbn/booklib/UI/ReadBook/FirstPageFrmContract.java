package luubieunghi.lbn.booklib.UI.ReadBook;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.drm.DrmStore;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FirstPageFrmContract {

    interface FirstPageFrmView {
        void setUpViews();

        void setUpEvents();

        void generalSetUp();
    }

    interface FirstPageFrmPresenter {

        void showBookmark(ObjectAnimator objectAnimator);

        void removeFragments(Fragment frm);

        void removeBookmark();

        Boolean onTextViewSelected(TextView readTextView, MotionEvent motionEvent, Boolean hasReadTextViewTouchedOnBookMarkArea, ObjectAnimator anim_Bookmark_Down);

        void onTextViewClicked(TextView readTextView, BookReadingDefineFrm defineFrm, BookReadingHighlightNoteFrm highlightNoteFrm,
                               BookReadingTranslateFrm translateFrm, Boolean hasReadTextViewTouchedOnBookMarkArea, Boolean allowTransActivity, Animation anim_ZoomOut, ImageView clickTextViewBackGround);
    }
}
