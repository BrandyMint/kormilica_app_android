package com.brandymint.kormilica.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.*;
import android.util.*;
import android.widget.ImageView;


public class BitmapCache {
	
    private static final String PATH = Environment.getExternalStorageDirectory() + "/" + "appData/Kormilica/";
	private final static int MAX_CACH_SIZE = 40;
	private final static int MAX_DOWNLOADER_COUNT = 10;
	private static final String TAG = "BitmapCache";
    private HashMap<String, Bitmap> cache;
    private String currUrl = null;
    private ArrayList<DownloaderTask> downloader;
    
    
    public BitmapCache() {
    	cache = new HashMap<String, Bitmap>();
    	downloader = new ArrayList<DownloaderTask>();
    }
    
    public void loadImage(ImageView image, boolean isThumbnail) {
       	if(cache.containsKey((String)image.getTag())) {
        	currUrl = (String)image.getTag();
    		Bitmap bm = cache.get((String)image.getTag());
    		if(bm  !=  null && !bm.isRecycled()) {	
    			image.setImageBitmap(bm);
    		}
    	} else {
    		if(downloader.size() > MAX_DOWNLOADER_COUNT) {
    			downloader.get(0).cancel(true);
    			downloader.remove(0);
    		}
			downloader.add(new DownloaderTask(isThumbnail));
			downloader.get(downloader.size()-1).execute(image);
    	}
    }

    private Bitmap loadImage( final String uri) {
		try {
			Bitmap bmp = BitmapFactory.decodeFile(uri);
			if(bmp != null && !bmp.isRecycled()) {
				Log.d(TAG, "ADD TO CACHE:  "+bmp.getWidth()+"  xx  "+bmp.getHeight());
				return bmp;
			}
		}catch(Exception ex) {
			Log.i(TAG, "loadImage from SD-CARD exception  "+ex);
		}
        return null;
	}

	public Bitmap getBitmap(String path) {
    	if(isFilePresent(path))	{
    		try{
    			Bitmap bm = loadImage(path);
    			return bm;
    		}catch(Exception ex){	}
    	}
   		return null;
	}
	
    public void clearCache() {
		if(cache.size() > 0) {
			try{
				cache.remove(currUrl);
			}catch(Exception e) {	
				Log.i(TAG, "Error recycle bitmap durring clear cache: "+e);
			}
		}
    }
    
	public class DownloaderTask extends AsyncTask<ImageView, Void, Bitmap> 	{
		private ImageView imageView = null;
		boolean isThumbnail;
		
		public DownloaderTask(boolean en) {
			isThumbnail = en;
		}
		
		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
		    this.imageView = imageViews[0];
	    	if(cache.size() >= MAX_CACH_SIZE)
	    		clearCache();
	    	
	    	Bitmap bm = null;
	    	if(isFilePresent((String)imageView.getTag())) {
	    		try{
    				bm = loadImage(PATH + getFileName((String)imageView.getTag()));
    				if(isThumbnail) {
    		    		Bitmap bmResized = getResizedBitmap(bm, imageView.getWidth(), imageView.getHeight());
    		    		bm.recycle();
    					cache.put(((String)imageView.getTag()), bmResized);
    					Log.d(TAG, imageView.getWidth()+"   x   "+imageView.getHeight()+"  !!!   "+bmResized.getWidth()+"   x   "+bmResized.getHeight());
    		    		return bmResized;
    		    	} else {
    		    		return bm;
    		    	}
	    		}catch(Exception ex){ 		}
	    	}

    		URL url;
			try {
				String uri = (String)imageView.getTag();
				if(uri.startsWith("http")) {
					url = new URL(uri);
					bm = readBitmapFromNetwork(url, isThumbnail);
				} else {
					bm = loadImage(uri);
				}
				if(isThumbnail) {
		    		Bitmap bmResized = getResizedBitmap(bm, imageView.getWidth(), imageView.getHeight());
		    		bm.recycle();
					cache.put(((String)imageView.getTag()), bmResized);
					Log.d(TAG, imageView.getWidth()+"   x   "+imageView.getHeight()+"  !!!   "+bmResized.getWidth()+"   x   "+bmResized.getHeight());
		    		return bmResized;
		    	} else {
		    		return bm;
		    	}
			} catch (Exception ex) 
			{
    			Log.i(TAG, "Error load Bitmap from network:  "+ex+"    "+(String)imageView.getTag());
			}
		    return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) 
		{
    		if(result != null && !result.isRecycled()) {
		    	imageView.setImageBitmap(result);
    		}
		}
	}
	
	public Bitmap readBitmapFromNetwork( URL url,  boolean isThumbnail ) {
        InputStream is = null;
        BufferedInputStream bis = null;
        Bitmap bmp = null;
        try {
            is = fetch(url.toString());
            bis = new BufferedInputStream(is);
            bmp = BitmapFactory.decodeStream(bis);
            String filename = getFileName(url.toString());
        	File dest = new File(PATH);
            if(!dest.exists()) {
           		dest.mkdirs();
            }            
            if(!isThumbnail)
            	dest = new File(PATH, filename);
            FileOutputStream out = new FileOutputStream(dest);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();            
        } catch (Exception e) {
        	Log.e(TAG, "Error load im 1  "+url.toString());
        } 
        finally {
            try {
                if( is != null )
                    is.close();
                if( bis != null )
                    bis.close();
            } catch (IOException e)   {     }
        }
        return bmp;
    }
	
    private InputStream fetch(String address) throws MalformedURLException,IOException {
        HttpGet httpRequest = new HttpGet(URI.create(address) );
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
        HttpEntity entity = response.getEntity();
        BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
        InputStream instream = bufHttpEntity.getContent();
        return instream;
    }

	
    private String getFileName(String str) {
    	if(str == null)
    		return "";
    	int lastSlash = str.lastIndexOf('/');
        String fileName = "file.bin";
        if(lastSlash >=0) {
                fileName = str.substring(lastSlash + 1);
        }
        if(fileName.equals("")) {
                fileName = "file.bin";
        }
        return fileName;
    }
    
    private boolean isFilePresent(String name) {
    	File dest = new File(PATH + getFileName(name));
        if(dest.exists())
        	return true;
        else
        	return false;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
    	int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return resizedBitmap;
    }
}