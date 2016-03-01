package com.Cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**加载网络图片缓存类
 * Created by 九龙 on 2015/11/16.
 */
public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String,Bitmap> cache;
    private  int Max=8*1024*1024;
    public BitmapCache(){
       cache=new LruCache<String,Bitmap>(Max){
           @Override
           protected int sizeOf(String key, Bitmap value) {
               return value.getRowBytes()*value.getHeight();
           }
       };
    }
    @Override
    public Bitmap getBitmap(String s) {
        return cache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        cache.put(s,bitmap);
    }
}
