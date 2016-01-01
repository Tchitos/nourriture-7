package com.android.nurriture.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;


import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

public class SyncImageLoader {

	private Object lock = new Object();
	
	private boolean mAllowLoad = true;
	
	private boolean firstLoad = true;
	
	private int mStartLoadLimit = 0;
	
	private int mStopLoadLimit = 0;
	
	final Handler handler = new Handler();
	
	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();   
	
	public interface OnImageLoadListener {
		public void onImageLoad(Integer t, Drawable drawable);
		public void onError(Integer t);
	}
	
	public void setLoadLimit(int startLoadLimit,int stopLoadLimit){
		if(startLoadLimit > stopLoadLimit){
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}
	
	public void restore(){
		mAllowLoad = true;
		firstLoad = true;
	}
		
	public void lock(){
		mAllowLoad = false;
		firstLoad = false;
	}
	
	public void unlock(){
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void loadImage(Integer t, String imageUrl,
			OnImageLoadListener listener) {
		final OnImageLoadListener mListener = listener;
		final String mImageUrl = imageUrl;
		final Integer mt = t;
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				if(!mAllowLoad){
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(mAllowLoad && firstLoad){
					loadImage(mImageUrl, mt, mListener);
				}
				
				if(mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit){
					loadImage(mImageUrl, mt, mListener);
				}
			}

		}).start();
	}
	
	private void loadImage(final String mImageUrl,final Integer mt,final OnImageLoadListener mListener){
		
		if (imageCache.containsKey(mImageUrl)) {  
            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);
            final Drawable d = softReference.get();  
            if (d != null) {  
            	handler.post(new Runnable() {
    				@Override
    				public void run() {
    					if(mAllowLoad){
    						mListener.onImageLoad(mt, d);
    					}
    				}
    			});
                return;  
            }  
        }  
		try {
			final Drawable d = loadImageFromUrl(mImageUrl);
			if(d != null){
                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if(mAllowLoad){
						mListener.onImageLoad(mt, d);
					}
				}
			});
		} catch (IOException e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					mListener.onError(mt);
				}
			});
			e.printStackTrace();
		}
	}

	public static Drawable loadImageFromUrl(String url) throws IOException {
		//从服务器端下载图片
		/*if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
			return loadImageFromUrl(url);
		}else{*/
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
		Log.v("drawable:", d.isVisible()+"");
			return d;
		/*}*/
		
	}
}
