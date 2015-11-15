package sempait.haycancha;

import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by martin on 14/11/15.
 */
public class Utils {

    public static DisplayImageOptions getImageLoaderOptionRouded() {

        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(false)
                .cacheInMemory(false).cacheOnDisk(true).considerExifParams(false).showImageOnLoading(R.drawable.logo).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new RoundedBitmapDisplayer(1000)).handler(new Handler()).build();

        return options;
    }
}

