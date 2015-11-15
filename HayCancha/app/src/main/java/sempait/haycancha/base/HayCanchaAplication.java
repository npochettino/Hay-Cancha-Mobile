package sempait.haycancha.base;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.parse.Parse;
import com.parse.PushService;

import java.io.File;

import sempait.haycancha.activities.SplashActivity;

/**
 * Created by martin on 14/11/15.
 */
public class HayCanchaAplication extends Application {


    public void onCreate() {

        super.onCreate();

        Parse.initialize(this, "2p3PaBJnkhIVS4j2elSz51g3lXvOk1sszrnveJwj", "ZS8ojWT1G3QS1dLk6ZhA95yalnPuTX2dHmhW7nb8");
        PushService.setDefaultPushCallback(this, SplashActivity.class);
        PushService.subscribe(this, "HayCancha", SplashActivity.class);

        intializeImageLoader();
    }

    private void intializeImageLoader() {

        File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
                .diskCache(new UnlimitedDiscCache(cacheDir)).diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()).imageDownloader(new BaseImageDownloader(getApplicationContext()))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).writeDebugLogs().build();

        ImageLoader.getInstance().init(config);

    }

}
