package sempait.haycancha.activities;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.parse.GcmBroadcastReceiver;
import com.parse.Parse;
import com.parse.ParseBroadcastReceiver;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;

import org.json.JSONException;
import org.json.JSONObject;

import sempait.haycancha.R;


public class PushNotificationReceiver extends ParsePushBroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    Notification myNotification;


    @Override
    public final void onReceive(Context context, Intent intent) {


        String action = intent.getAction();
        JSONObject pushData = null;


        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));


            Log.w("Push Notification", "Received message");
            String deepLink = json.getString("deep_link");
            String message = json.getString("message");
            String title = json.getString("title");

            Log.w("Push Notification", "deep_link:" + deepLink);
            createNotification(context, deepLink, title, message);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context, String deepLink, String title, String message) {

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent = new Intent(context, DeepLinkActivity.class);
        intent.putExtra("deeplink", deepLink);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification noti = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();


        notificationManager.notify(0, noti);

    }

}
