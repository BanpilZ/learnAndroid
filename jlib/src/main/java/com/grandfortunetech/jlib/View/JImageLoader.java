package com.grandfortunetech.jlib.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by jeff.hsu on 2016/9/5.
 */
public class JImageLoader {
    private static JImageLoader mJImageLoader;
    private Context mContext;
    private ImageLoader mImageLoader;
    private Boolean mIsInit = false;

    public static JImageLoader getInstance(Context context)
    {
        if(mJImageLoader == null) {
            mJImageLoader = new JImageLoader();
            mJImageLoader.mContext= context;
            mJImageLoader.initImageLoader(context);
            mJImageLoader.mImageLoader = ImageLoader.getInstance();
        }
        return mJImageLoader;
    }

    private void initImageLoader(Context context)
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void displayImage(ImageView _imageView, String _url, DisplayImageOptions _options)
    {
        mImageLoader.displayImage(_url, _imageView, _options);
    }

    public void displayImage(ImageView _imageView,String _url,  int _imgDefaultRes)
    {
        mImageLoader.displayImage(_url, _imageView, getDefaultOptions(_imgDefaultRes));
    }

    public DisplayImageOptions getDefaultOptions(int _imgDefaultRes)
    {
        return new DisplayImageOptions.Builder().showImageOnLoading(_imgDefaultRes).
                showImageForEmptyUri(_imgDefaultRes)
                .showImageOnFail(_imgDefaultRes)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
}
