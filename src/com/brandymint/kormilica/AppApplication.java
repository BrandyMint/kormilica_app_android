package com.brandymint.kormilica;

import java.util.ArrayList;
import com.brandymint.kormilica.data.Category;
import com.brandymint.kormilica.data.CategoryList;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.ProductData;
import com.brandymint.kormilica.data.Vendor;
import com.brandymint.kormilica.db.DBHelper;
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
        resToastText = R.string.crash_toast_text 
        )
public class AppApplication extends Application {
	
	private static final String TAG = "AppApplication";
	public static final String PREFERENCE_NAME = "KormilicaApplication";
	public static final String IS_FIRST_START = "IsFirstStart";

	private Order order;
	private Vendor vendor;
	private static AppApplication instance;
	
	public static AppApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
//		ACRA.init(this);
		super.onCreate();
		instance = this;
	}
	
	public void fillAppData() {
		ProductData prodData = ProductData.getInstance();
		DBHelper dbHelper = new DBHelper(this);
		ArrayList<Vendor> list = dbHelper.readVendorTable();
		setVendor(list.get(0));
		prodData.setCategoryList(dbHelper.readCategoryTable());
		ArrayList<Product> tempList = dbHelper.readProductTable();
		ArrayList<CategoryList> productList = new ArrayList<CategoryList>();
		for(Category category : prodData.getCategoryList()) {
			CategoryList catList = new CategoryList(category.getName());
			for(Product prod: tempList) {
				if(category.getId().equals(prod.getCategoryId())) {
					catList.addItem(prod);
				}
			}
			if(catList.getList().size() > 0)
				productList.add(catList);
		}
		prodData.setProductList(productList);
		dbHelper.close();
		dbHelper = null;
		Log.e(TAG, "fillAppData:  read product compleate");
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