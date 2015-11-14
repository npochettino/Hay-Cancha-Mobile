package sempait.haycancha.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;


public class BaseFragment extends Fragment {

    public Context mContext;
    public BaseActivity mBaseActivity;
    ActionBar mActionBar;

    @Override
    public void onAttach(Activity activity) {

        mContext = activity;
        mBaseActivity = (BaseActivity) activity;
        mActionBar = ((BaseActivity) activity).getSupportActionBar();
        super.onAttach(activity);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public boolean isActive() {
        return !getActivity().isFinishing();
    }

//    public void dismissLoadingView() {
//
//        if (mBaseActivity != null)
//            mBaseActivity.dismissLoadingView();
//    }
//
//    public void showLoadingView() {
//
//        if (mBaseActivity != null)
//            mBaseActivity.showLoadingView();
//    }
//
//    public void onBackPressed() {
//
//        if (mBaseActivity != null)
//            mBaseActivity.onBackPressed();
//    }

}
