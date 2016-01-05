package sempait.haycancha.activities;

import android.content.Intent;
import android.os.Bundle;

import sempait.haycancha.base.BaseActivity;


public class DeepLinkActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle onsaveinstance) {
        super.onCreate(onsaveinstance);

        String data;

        Bundle bundle = getIntent().getExtras();

        data = getIntent().getExtras().getString("deeplink");
        Intent i = null;

        if (data != null && !data.isEmpty()) {

            if (data.equalsIgnoreCase("home"))
                i = new Intent(getApplicationContext(), SplashActivity.class);
            else if (data.equalsIgnoreCase("invitation")) {
                i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("section", data);

            } else if (data.equalsIgnoreCase("d")) {
                i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("section", data);
            }


        }
        if (i != null) {
            openActivity(i, AnimType.NONE);
        }
        finish();


    }
}
