package sempait.haycancha.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;


import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.base.BaseFragment;
import sempait.haycancha.base.BaseInnerActivity;
import sempait.haycancha.fragment.LoginFragment;

/**
 * Created by martin on 21/10/15.
 */
public class LoginActivity extends BaseInnerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.newInstance()).commit();
        setTitle(getString(R.string.login_title));


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setCustomView(R.layout.custom_nav);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(null);
        getSupportActionBar().setHomeButtonEnabled(false);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));


    }
}
