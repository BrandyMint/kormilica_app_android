package com.brandymint.kormilica;

import java.util.ArrayList;
import java.util.LinkedList;

import com.brandymint.kormilica.data.NewsDataItem;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.utils.BitmapCache;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.ContextThemeWrapper;

public class AppApplication extends Application 
{
	private static final String TAG = "AppApplication";
	public static final int MAX_FRAGMENT_CACHE_SIZE = 10;

	private static AppApplication instance;
	private LinkedList<CommonFragment> fragmentCache;
	private ArrayList<NewsDataItem> topNews;
	private ArrayList<Product> productList;
	private BitmapCache bitmapCache;
	
	public static AppApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	    instance = this;
	    fragmentCache = new LinkedList<CommonFragment>();
	    bitmapCache = new BitmapCache();
	}
	
	public void showMessage(final Activity activity, String header, String message, String positiveLabel, String negativeLabel, final Intent positiveIntent, final boolean isExitWithNegativeButton)
    {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, 0));
		builder.setMessage(message);
		builder.setTitle(header);
		builder.setCancelable(false);
		if(positiveLabel != null) {
			builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int id) 
				{
					if(positiveIntent != null)
						activity.startActivity(positiveIntent);
					dialog.cancel();
				}
			});
		}
		if(negativeLabel != null) {
			builder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int id) 
				{
					dialog.cancel();
					if(isExitWithNegativeButton)
						activity.finish();
				}
			});
		}
		AlertDialog alert = builder.create();
		alert.show();
    }

	
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

	public ArrayList<NewsDataItem> getTopNews() {
		return topNews;
	}

	public void setTopNews(ArrayList<NewsDataItem> topNews) {
		this.topNews = topNews;
	}

	public BitmapCache getBitmapCache() {
		return bitmapCache;
	}

	public LinkedList<CommonFragment> getFragmentCache() {
		return fragmentCache;
	}

	public void setFragmentCache(LinkedList<CommonFragment> fragmentCache) {
		this.fragmentCache = fragmentCache;
	}
	
	public void checkFragmentCacheSize() {
       	if(fragmentCache.size() > MAX_FRAGMENT_CACHE_SIZE){
       		fragmentCache.remove(fragmentCache.size() - 1);
       		System.gc();
       	}
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
	
	
}