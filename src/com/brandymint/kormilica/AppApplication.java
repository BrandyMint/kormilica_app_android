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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;

import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes(
        formKey = "",
        formUri = "http://acra.icfdev.ru/acra-kormilica/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="kormilica",
        formUriBasicAuthPassword="dfg8Kmdybc",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text, 
        additionalDropBoxTags = { "" }, additionalSharedPreferences = { "" }, applicationLogFile = "", applicationLogFileLines = 0, connectionTimeout = 0, 
        customReportContent = { ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT}, 
        deleteOldUnsentReportsOnApplicationStart = false, 
        deleteUnapprovedReportsOnApplicationStart = false, disableSSLCertValidation = false, dropboxCollectionMinutes = 0, 
        excludeMatchingSettingsKeys = { "" }, excludeMatchingSharedPreferencesKeys = { "" }, forceCloseDialogAfterToast = false, 
        googleFormUrlFormat = "", includeDropBoxSystemTags = false, logcatArguments = { "" }, logcatFilterByPid = false, mailTo = "",
        maxNumberOfRequestRetries = 0, resDialogCommentPrompt = 0, resDialogEmailPrompt = 0, resDialogIcon = 0, resDialogNegativeButtonText = 0, 
        resDialogOkToast = 0, resDialogPositiveButtonText = 0, resDialogText = 0, resDialogTitle = 0, resNotifIcon = 0, resNotifText = 0,
        resNotifTickerText = 0, resNotifTitle = 0, sendReportsInDevMode = false, sharedPreferencesMode = 0, sharedPreferencesName = "", 
        socketTimeout = 60000
        )
public class AppApplication extends Application {
	
	private static final String TAG = "AppApplication";
	public static final String PREFERENCE_NAME = "KormilicaApplication";
	public static final String IS_FIRST_START = "IsFirstStart";
	public static final int MAX_FRAGMENT_CACHE_SIZE = 10;

	private static AppApplication instance;
	private LinkedList<CommonFragment> fragmentCache;
	private ArrayList<Category> categoryList;
	private ArrayList<CategoryList> productList;
	private HashMap<String, Product> productMap;
	private Order order;
	private CommonActivity activity;

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
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
		Log.e(TAG, "fillAppData:  read vendor");
		ArrayList<Vendor> list = dbHelper.readVendorTable();
		setVendor(list.get(0));
		Log.e(TAG, "fillAppData:  read vendor:  "+getVendor());

		Log.e(TAG, "fillAppData:  read product");
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
		Log.e(TAG, "fillAppData:  read product compleate");
//		setOrder(dbHelper.readOrderTable());
	}
	
	public String loadPreference(String preferenceName) {
    	SharedPreferences preference = getSharedPreferences(PREFERENCE_NAME, 0);
        String str =preference.getString(preferenceName, null);
		return str;
	}
	
	public void savePreference(String preferenceName, String value) {
        SharedPreferences preference = getSharedPreferences(PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(preferenceName, value);
        editor.commit();
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

	public CommonActivity getActivity() {
		return activity;
	}

	public void setActivity(CommonActivity activity) {
		this.activity = activity;
	}
}