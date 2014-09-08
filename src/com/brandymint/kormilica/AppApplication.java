package com.brandymint.kormilica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.brandymint.kormilica.data.Category;
import com.brandymint.kormilica.data.CategoryList;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.Vendor;
import com.brandymint.kormilica.db.DBHelper;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.utils.BitmapCache;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.ContextThemeWrapper;

public class AppApplication extends Application {
	
	private static final String TAG = "AppApplication";
	public static final int MAX_FRAGMENT_CACHE_SIZE = 10;

	private static AppApplication instance;
	private LinkedList<CommonFragment> fragmentCache;
	private ArrayList<Category> categoryList;
	private ArrayList<CategoryList> productList;
	private HashMap<String, Product> productMap;
	private Order order;

	private Vendor vendor;
	private BitmapCache bitmapCache;
	private DBHelper dbHelper;
	
	public static AppApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	    instance = this;
	    fragmentCache = new LinkedList<CommonFragment>();
	    bitmapCache = new BitmapCache();
	    dbHelper = new DBHelper(this);
	}
	
	public void showMessage(final Activity activity, String header, String message, String positiveLabel, String negativeLabel, final Intent positiveIntent, final boolean isExitWithNegativeButton)
    {
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, 0));
		builder.setMessage(message);
		builder.setTitle(header);
		builder.setCancelable(false);
		if(positiveLabel != null) {
			builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					if(positiveIntent != null)
						activity.startActivity(positiveIntent);
					dialog.cancel();
				}
			});
		}
		if(negativeLabel != null) {
			builder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					if(isExitWithNegativeButton)
						activity.finish();
				}
			});
		}
		AlertDialog alert = builder.create();
		alert.show();
    }

	public void fillAppData() {
		setCategoryList(dbHelper.readCategoryTable());
		ArrayList<Product> tempList = dbHelper.readProductTable();
		productList = new ArrayList<CategoryList>();
		for(Category category : getCategoryList()) {
			CategoryList catList = new CategoryList(category.getName());
			for(Product prod: tempList) {
				if(category.getId().equals(prod.getCategoryId())) {
					catList.addItem(prod);
				}
			}
			if(catList.getList().size() > 0)
				productList.add(catList);
		}
//		setOrder(dbHelper.readOrderTable());
	}
	
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

	public BitmapCache getBitmapCache() {
		return bitmapCache;
	}

	public LinkedList<CommonFragment> getFragmentCache() {
		return fragmentCache;
	}

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		this.dbHelper = dbHelper;
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

	public ArrayList<CategoryList> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<CategoryList> productList) {
		this.productList = productList;
		productMap = new HashMap<String, Product>();
		for(CategoryList list: productList) {
			for(Product prod: list.getList()) {
				productMap.put(prod.getId(), prod);
			}
		}
	}

	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public HashMap<String, Product> getProductMap() {
		return productMap;
	}

	public void setProductMap(HashMap<String, Product> productMap) {
		this.productMap = productMap;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}