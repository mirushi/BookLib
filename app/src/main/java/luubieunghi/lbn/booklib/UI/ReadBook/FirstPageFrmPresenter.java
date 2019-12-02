package luubieunghi.lbn.booklib.UI.ReadBook;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import luubieunghi.lbn.booklib.R;
import luubieunghi.lbn.booklib.UI.BookPageManagement.PageManagementActivity;

public class FirstPageFrmPresenter implements FirstPageFrmContract.FirstPageFrmPresenter {

    Context context;
    FragmentManager fragmentManager;

    FirstPageFrmPresenter(Context context) {
        this.context = context;
    }


    @Override
    public void showBookmark(ObjectAnimator objectAnimator) {
        objectAnimator.start();
    }

    @Override
    public void removeFragments(Fragment frm) {
        if (!frm.isRemoving()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(frm);
            transaction.commit();
        }
    }

    @Override
    public void removeBookmark() {

    }

    @Override
    public Boolean onTextViewSelected(TextView readTextView, MotionEvent motionEvent, Boolean hasReadTextViewTouchedOnBookMarkArea, ObjectAnimator anim_Bookmark_Down ) {
        Toast.makeText(context, "Touched!", Toast.LENGTH_SHORT).show();

        // readTextView vua duoc select la return luon, cho phep select, click tiep
        if (readTextView.hasSelection()) {
            return false;
        }

        //Xu ly vung cho phep book mark
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (x >= (int) (5.0 / 6 * readTextView.getWidth()) && y <= (int) ((1.0 / 6) * readTextView.getHeight())) {

            hasReadTextViewTouchedOnBookMarkArea = true;
            showBookmark(anim_Bookmark_Down);

            return false;
        }
        hasReadTextViewTouchedOnBookMarkArea = false;
        return false;
    }


    @Override
    public void onTextViewClicked(final TextView readTextView, BookReadingDefineFrm defineFrm, BookReadingHighlightNoteFrm highlightNoteFrm,
                                  BookReadingTranslateFrm translateFrm, Boolean hasReadTextViewTouchedOnBookMarkArea, Boolean allowTransActivity, Animation anim_ZoomOut, ImageView clickTextViewBackGround) {
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();

        //dong tat fragment [+ bookmark] va khong cho phep chuyen activity neu van con fragment dang mo
        allowTransActivity = false;

        //Neu con fragment dang mo, khong cho chuyen activity, sau do dong tat ca activity
        if (!defineFrm.isVisible() && !highlightNoteFrm.isVisible() && !translateFrm.isVisible()) {
            allowTransActivity = true;
        }

        //dong tat ca cac fragment
        removeFragments(highlightNoteFrm);
        removeFragments(translateFrm);
        removeFragments(defineFrm);

        //neu tat cac fragment deu dong thi cho phep chuyen activity
        if (allowTransActivity && !hasReadTextViewTouchedOnBookMarkArea) {
            //zoom out +  change activity in 0,5 second
            readTextView.startAnimation(anim_ZoomOut);
            clickTextViewBackGround.startAnimation(anim_ZoomOut);
            readTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    readTextView.setVisibility(View.GONE);
                    Intent intent = new Intent(context, PageManagementActivity.class);
                    context.startActivity(intent);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.anim_nothing, R.anim.anim_nothing).toBundle();
                    context.startActivity(intent, bundle);
                }
            }, 500);
        }
    }

    private FragmentManager getFragmentManager() {
        return this.fragmentManager;
    }

    void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


}
