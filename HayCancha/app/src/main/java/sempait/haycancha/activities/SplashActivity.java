package sempait.haycancha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import sempait.haycancha.ConfigurationClass;
import sempait.haycancha.R;
import sempait.haycancha.base.BaseActivity;
import sempait.haycancha.services.LocationManager;

/**
 * Created by martin on 19/10/15.
 */
public class SplashActivity extends BaseActivity {

    public static final int SPLASH_TIME_DELAY = 2000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        context = getApplicationContext();

        LocationManager.getInstance(this);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (ConfigurationClass.getUserCod(getApplicationContext()) != 0) {

                    continueToMain();

                } else {

                    continueLogin();
                }

            }
        }, SPLASH_TIME_DELAY);

    }

    private void continueLogin() {

        Intent i = new Intent(context, LoginActivity.class);

        startActivity(i);

        this.finish();
    }

    private void continueToMain() {
        Intent i = new Intent(context, MainActivity.class);

        startActivity(i);

        this.finish();
    }
}
