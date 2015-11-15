package sempait.haycancha.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by martin on 19/03/15.
 */
public class BaseInnerActivity extends BaseActivity {


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        super.onCreate(savedInstanceState);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                backWithAnimation();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
