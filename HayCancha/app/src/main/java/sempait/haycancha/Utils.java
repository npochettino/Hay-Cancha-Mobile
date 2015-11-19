package sempait.haycancha;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created by martin on 14/11/15.
 */
public class Utils {

    public static DisplayImageOptions getImageLoaderOptionRouded() {

        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(false)
                .cacheInMemory(false).cacheOnDisk(true).considerExifParams(false).showImageOnLoading(R.drawable.logo).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new RoundedBitmapDisplayer(1000)).handler(new Handler()).build();

        return options;
    }

    public static String getUUID(Context context) {

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static ParseFile getAvatarParseFile(Intent data, Context context) {

        String path = "";
        Uri selectedImageUri = data.getData();

        if (Build.VERSION.SDK_INT < 19) {
            path = getRealPathFromURI(data.getData(), context);
        } else {
            if (selectedImageUri.getLastPathSegment().split(":").length > 1) {
                String id = selectedImageUri.getLastPathSegment().split(":")[1];
                final String[] imageColumns = { MediaStore.Images.Media.DATA };
                final String imageOrderBy = null;
                Uri uri = getUri();
                Cursor imageCursor = context.getContentResolver()
                        .query(uri, imageColumns, MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);
                if (imageCursor.moveToFirst()) {
                    path = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
            }
        }
        if (!path.equalsIgnoreCase("")) {
            Bitmap avatar = BitmapFactory.decodeFile(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            avatar.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return new ParseFile(stream.toByteArray());
        } else {
            return null;
        }
    }

    public static String getRealPathFromURI(Uri contentUri, Context context) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Uri getUri() {
        String state = Environment.getExternalStorageState();
        if (!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

}

