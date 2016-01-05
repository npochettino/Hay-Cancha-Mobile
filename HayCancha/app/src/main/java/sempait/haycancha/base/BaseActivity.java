package sempait.haycancha.base;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sempait.haycancha.R;
import sempait.haycancha.fragment.TurnsFilterFragment;

/**
 * Created by martin on 19/10/15.
 */
public class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    public static final String ANIM_TYPE_KEY = "ANIM_TYPE_KEY";
    private AnimType mAnimType;

    @Override
    protected void onCreate(Bundle onsaveinstance) {
        super.onCreate(onsaveinstance);


        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setCustomView(R.layout.main_action_bar);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }

        mAnimType = AnimType.NONE;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.get(ANIM_TYPE_KEY) != null)
                mAnimType = (AnimType) bundle.get(ANIM_TYPE_KEY);
        }

        setUpActivityInAnimation();


    }


    public void setSectionTitle(String title) {

        ((TextView) mActionBar.getCustomView().findViewById(R.id.action_bar_title_centered)).setText(title);
    }

    public void setBackgroundTransparect() {
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void setBackgroundPink() {
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bright_pink)));
    }


    public void showLoadingView() {
        RelativeLayout loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        ImageView loadingView_item = (ImageView) findViewById(R.id.loadingView_item);
        if (loadingView != null)
            loadingView.setVisibility(View.VISIBLE);

        loadingView_item.setBackgroundResource(R.drawable.loading);

        AnimationDrawable frameAnimation = (AnimationDrawable) loadingView_item.getBackground();

        frameAnimation.start();
    }

    public void replaceInnerFragment(android.support.v4.app.Fragment fragment, Boolean addToBackStack) {

        this.replaceInnerFragment(fragment, AnimType.NONE, addToBackStack);
    }

    public void replaceInnerFragment(android.support.v4.app.Fragment fragment, AnimType animType, Boolean addToBackStack) {

        mAnimType = animType;
        replaceInnerFragment(fragment, addToBackStack, animType, true);
    }

    public void replaceFragmentSection(Fragment fragment, String tag) {

        getSupportFragmentManager().popBackStack(null, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.container, fragment, tag);
        if (false)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceInnerFragmentWhitFLip(Fragment fragment, Boolean addToBackstack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.flip_in, R.anim.flip_out, R.anim.flip_in, R.anim.flip_out);
        transaction.replace(R.id.container, fragment);

        if (addToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceInnerFragment(Fragment fragment, Boolean addToBackstack, AnimType animType, Boolean override) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (animType == null)
            animType = AnimType.NONE;

        switch (animType) {

            case NONE:
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case FLIP:

                this.overridePendingTransition(R.anim.fast_flip_in, R.anim.fast_flip_out);
                break;

            case BOTTOM_TO_TOP:

                transaction.setCustomAnimations(R.anim.fast_push_bottom_in, 0, 0, R.anim.fast_push_bottom_out);
                break;

            case TOP_TO_BOTTOM:
                transaction.setCustomAnimations(R.anim.fast_push_top_in, 0, 0, R.anim.fast_push_top_out);
                break;

            case LEFT_RIGH:
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case RIGHT_LEFT:
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            default:
                break;
        }

        if (override)
            transaction.replace(R.id.container, fragment);
        else
            transaction.add(R.id.container, fragment);

        if (addToBackstack)
            transaction.addToBackStack(null);


    }


    public void openActivity(Intent intent, AnimType animType) {

        if (animType != null)
            intent.putExtra(BaseActivity.ANIM_TYPE_KEY, animType);


        startActivity(intent);
    }

    private void setUpActivityInAnimation() {

        switch (mAnimType) {

            case NONE:
                break;

            case FLIP:
                this.overridePendingTransition(R.anim.fast_flip_in, R.anim.fast_flip_out);
                break;

            case BOTTOM_TO_TOP:

            case RIGHT_LEFT:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case LEFT_RIGH:
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            default:
                break;
        }
    }

    private void setUpActivityOutAnimation() {

        if (mAnimType == null)
            mAnimType = AnimType.NONE;

        switch (mAnimType) {

            case NONE:
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;

            case FLIP:

                this.overridePendingTransition(R.anim.fast_flip_in, R.anim.fast_flip_out);
                break;

            case BOTTOM_TO_TOP:

                this.overridePendingTransition(R.anim.fast_push_bottom_in, R.anim.fast_push_bottom_out);
                break;


            case RIGHT_LEFT:
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case LEFT_RIGH:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;


            default:
                break;
        }
    }

//    public void replaceInnerFragmentFullFilter(Fragment fragment, Boolean addToBackstack, Boolean override) {
//
////        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.setCustomAnimations(R.anim.fast_push_bottom_in, 0, 0, R.anim.fast_push_bottom_out);
////
////        if (override)
////            transaction.replace(R.id.container_filter, fragment);
////        else
////            transaction.add(R.id.container_filter, fragment);
////
////        if (addToBackstack)
////            transaction.addToBackStack(null);
////        transaction.commit();
//    }

    protected void backWithAnimation() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
            this.overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            TurnsFilterFragment myFragment = (TurnsFilterFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.fields));

            if (!(myFragment != null && myFragment.isVisible())) {
                replaceFragmentSection(new TurnsFilterFragment(), getString(R.string.fields));
            } else
                super.onBackPressed();

        } else
            super.onBackPressed();
    }

    public void dismissLoadingView() {

        final RelativeLayout loadingView = (RelativeLayout) findViewById(R.id.loadingView);
        ImageView loadingView_item = (ImageView) findViewById(R.id.loadingView_item);

        if (loadingView_item != null) {
            loadingView_item.setBackgroundResource(R.drawable.loading);

            AnimationDrawable frameAnimation = (AnimationDrawable) loadingView_item.getBackground();

            frameAnimation.stop();
        }

        if (loadingView != null) {
            if (loadingView.getVisibility() != View.GONE) {
                Animation fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                fadeInAnimation.setStartOffset(200);
                fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        loadingView.setVisibility(View.GONE);

                    }
                });

                loadingView.startAnimation(fadeInAnimation);
            }
        }
    }


    public enum AnimType {
        NONE,
        BOTTOM_TO_TOP,
        TOP_TO_BOTTOM,
        LEFT_RIGH,
        RIGHT_LEFT,
        FLIP
    }

}
