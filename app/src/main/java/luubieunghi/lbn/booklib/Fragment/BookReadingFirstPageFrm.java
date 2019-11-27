package luubieunghi.lbn.booklib.Fragment;

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
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import luubieunghi.lbn.booklib.Activity.PageManagementActivity;
import luubieunghi.lbn.booklib.CustomActionMode;
import luubieunghi.lbn.booklib.R;

public class BookReadingFirstPageFrm extends Fragment {

    private BookReadingDefineFrm defineFrm;
    private BookReadingTranslateFrm translateFrm;
    private BookReadingHighlightNoteFrm highlightNoteFrm;
    protected View view;
    private ImageView clickTextViewBackGround;
    private TextView readTextView;
    private CustomActionMode callback;
    private Animation anim_ZoomOut;
    private boolean hasReadTextViewTouchedOnBookMarkArea;
    private ImageView bookmarkImage;
    private ObjectAnimator anim_Bookmark_Up;
    private ObjectAnimator anim_Bookmark_Down;
    private boolean hasBookmark = false;

    //
    public BookReadingFirstPageFrm() {
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first_page_frm, container, false);

        //initiate value
        initiateValue();

        if (readTextView == null) {
            Log.e("Frm_1:", "On create Root View NULL!");
        } else
            Log.e("Frm_1:", "On create Root View NOT NULL!");
        return view;
    }

    private void initiateValue() {
        defineFrm = new BookReadingDefineFrm();
        highlightNoteFrm = new BookReadingHighlightNoteFrm();
        translateFrm = new BookReadingTranslateFrm();
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

    @Override
    public void onResume() {
        super.onResume();
//handle to show action mode
        onTextLongTouched();
//handle to show page management activity
        onTextViewClicked();
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

    private void onTextLongTouched() {
        callback = new CustomActionMode(this.getContext()) {
            private List<Integer> validResIdList = new ArrayList<>();

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                List<Integer> toRemoveResId = new ArrayList<>();
                for (int i = 0; i < menu.size(); i++) {

                    if (menu.getItem(i).getItemId() != (validResIdList.get(0))
                            && menu.getItem(i).getItemId() != (validResIdList.get(1))
                            && menu.getItem(i).getItemId() != (validResIdList.get(2))
                            && menu.getItem(i).getItemId() != (validResIdList.get(3))
                            && menu.getItem(i).getItemId() != (validResIdList.get(4))
                            && menu.getItem(i).getItemId() != (validResIdList.get(5))
                            && menu.getItem(i).getItemId() != (validResIdList.get(6))
                            && menu.getItem(i).getItemId() != (validResIdList.get(7))
                            && menu.getItem(i).getItemId() != (validResIdList.get(8))
                    ) {

                        toRemoveResId.add(menu.getItem(i).getItemId());
                    }
                }

                for (Integer resId : toRemoveResId) {
                    menu.removeItem(resId);
                }

                return true;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                super.onCreateActionMode(mode, menu);

                //show define frm
                FragmentTransaction transaction;
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.first_page_layout, defineFrm);
                transaction.addToBackStack(null);
                transaction.commit();

                //delete default item
                for (int i = 0; i < menu.size(); i++) {

                    validResIdList.add(menu.getItem(i).getItemId());
                }


                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.custom_one) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_layout, highlightNoteFrm);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.custom_two) {
                    Toast.makeText(view.getContext(), "This(these) word(s) has(have) copied to clipboard!", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.custom_three) {
                    FragmentTransaction transaction;
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.first_page_layout, translateFrm);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                //kill define frm when destroy action mode
                if (!defineFrm.isRemoving()) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(defineFrm);
                    transaction.commit();
                }

            }
        };
        readTextView.setCustomSelectionActionModeCallback(callback);

    }

    @SuppressLint({"TouchableViewAccessibility", "ClickableViewAccessibility"})
    protected void onTextViewClicked() {

        readTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getContext(), "Touched!", Toast.LENGTH_SHORT).show();

                // readTextView vua duoc select la return luon, cho phep select, click tiep
                if (readTextView.hasSelection()) {
                    return false;
                }

                //Xu ly vung cho phep book mark
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x >= (int) (5.0 / 6 * readTextView.getWidth()) && y <= (int) ((1.0 / 6) * readTextView.getHeight())) {

                    hasReadTextViewTouchedOnBookMarkArea = true; // danh dau la trong vung bookmark
                    //animtiotn bookmark
                    anim_Bookmark_Down.start();

                    return false;
                }
                hasReadTextViewTouchedOnBookMarkArea = false;
                return false;
            }
        });

        //Onclick => Change Activity or remove Frm
        readTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show();

                //dong tat fragment [+ bookmark] va khong cho phep chuyen activity neu van con fragment dang mo
                boolean allowTransActivity = false;

                //Neu con fragment dang mo, khong cho chuyen activity, sau do dong tat ca activity
                if (!defineFrm.isVisible() && !highlightNoteFrm.isVisible() && !translateFrm.isVisible()) {
                    allowTransActivity = true;
                }

                //dong tat ca cac fragment
                if (!defineFrm.isRemoving()) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(defineFrm);
                    transaction.commit();
                }
                if (!highlightNoteFrm.isRemoving()) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(highlightNoteFrm);
                    transaction.commit();
                }
                if (!translateFrm.isRemoving()) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(translateFrm);
                    transaction.commit();
                }

                //neu tat cac fragment deu dong thi cho phep chuyen activity
                if (allowTransActivity && !hasReadTextViewTouchedOnBookMarkArea) {
                    //zoom out +  change activity in 0,5 second
                    readTextView.startAnimation(anim_ZoomOut);
                    clickTextViewBackGround.startAnimation(anim_ZoomOut);
                    readTextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            readTextView.setVisibility(View.GONE);
                            Intent intent = new Intent(getContext(), PageManagementActivity.class);
                            startActivity(intent);
                            Bundle bundle = ActivityOptions.makeCustomAnimation(getContext(), R.anim.anim_nothing, R.anim.anim_nothing).toBundle();
                            getContext().startActivity(intent, bundle);
                        }
                    }, 500);
                }
            }
        });


    }


}
