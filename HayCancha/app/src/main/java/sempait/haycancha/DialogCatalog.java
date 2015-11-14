package sempait.haycancha;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

import sempait.haycancha.base.BaseActivity;

public class DialogCatalog {

    public static void informativeDialog(String title, String msg, final Context mContext) {

        if (!((BaseActivity) mContext).isFinishing()) {
            new AlertDialog.Builder(mContext).setMessage(msg).setTitle(title).setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create().show();
        }
    }

    public static void serverErrorDialog(final String error, final Context mContext) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                informativeDialog("Server Information", "Oops, looks like there was a problem.  Please try again.\n" + error, mContext);
            }
        });
    }

    public static void localErrorDialog(final String error, final Context mContext) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                informativeDialog("Information", error, mContext);
            }
        });
    }

    public static void logueoIncorrecto(final String error, final Context mContext) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                informativeDialog("Information", "El usuario/contraseña es incorrecto", mContext);
            }
        });
    }


    public static void mensajeError(final String error, final Context mContext) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                informativeDialog("Information", error, mContext);
            }
        });
    }

}
