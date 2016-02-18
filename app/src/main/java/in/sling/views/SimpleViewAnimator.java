package in.sling.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import in.sling.R;

/**
 * Created by abhishek on 18/02/16 at 2:28 PM.
 */
public class SimpleViewAnimator extends LinearLayout {
    private Animation inAnimation;
    private Animation outAnimation;

    public SimpleViewAnimator(Context context) {
        super(context);
        initializeAnimation(context);
    }

    public SimpleViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeAnimation(context);
    }

    public SimpleViewAnimator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAnimation(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SimpleViewAnimator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeAnimation(context);
    }

    private void initializeAnimation(Context context) {
        this.inAnimation = AnimationUtils.loadAnimation(context, R.anim.right_to_left);
        this.outAnimation = AnimationUtils.loadAnimation(context, R.anim.left_to_right);
    }

    @Override
    public void setVisibility(int visibility) {
        if (getVisibility() != visibility) {
            if (visibility == VISIBLE) {
                if (inAnimation != null) startAnimation(inAnimation);
            } else if ((visibility == INVISIBLE) || (visibility == GONE)) {
                if (outAnimation != null) startAnimation(outAnimation);
            }
        }

        super.setVisibility(visibility);
    }
}