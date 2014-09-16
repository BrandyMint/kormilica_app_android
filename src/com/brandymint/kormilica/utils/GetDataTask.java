package com.brandymint.kormilica.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.AbstractData;
import com.brandymint.kormilica.data.Category;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.Vendor;
import com.brandymint.kormilica.db.DBHelper;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class GetDataTask extends AsyncTask<String, String, String> {

	private static final String TAG				 = "GetDataTask";  
	private static final String BASE_URL		 = "http://api.kormilica.info/";
	private static final String API_VERSION		 = "v1";
	private static final String VENDOR_KEY		 = "467abe2e7d33e6455fe905e879fd36be";
	private static final String KEY_VENDOR		 = "vendor";
	private static final String KEY_CATEGORIES	 = "categories";
	private static final String KEY_PRODUCTS	 = "products";

	private Context context;
	private LoadListener loadListener;
	private ArrayList<AbstractData> productList;
	private ArrayList<AbstractData> categoryList;
	private ArrayList<AbstractData> vendorList;
	private String firstStart;

	public GetDataTask() {
		firstStart = AppApplication.getInstance().loadPreference(AppApplication.IS_FIRST_START);
	}

	public GetDataTask(Context context) {
		firstStart = AppApplication.getInstance().loadPreference(AppApplication.IS_FIRST_START);
		this.context = context;
		loadListener = (LoadListener)context;
	}
	
	private String getData(String[] arg) {
		Log.e(TAG, "getData start");
		HttpURLConnection connection = null;
		try {	
    		StringBuffer stb = new StringBuffer();
			if(context != null && (firstStart == null || Boolean.parseBoolean(firstStart))) {
				Log.e(TAG, "getData start 1");
			    InputStream json = context.getAssets().open("default.json");
			    BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
			    String str;
			    while ((str=in.readLine()) != null) {
			      stb.append(str);
			    }
			    in.close();				
			} else {
				Log.e(TAG, "getData start 2");
				String url = BASE_URL + API_VERSION + "/bundles.json";
				connection = (HttpURLConnection) (new URL(url)).openConnection();
	            connection.setRequestProperty("X-Vendor-Key", VENDOR_KEY);
	            connection.setRequestProperty("Accept","application/json; charset=utf-8");
	            connection.setRequestProperty("Content-Type","application/json; charset=utf-8");
	    		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String str;
	    		while((str = reader.readLine()) != null) {
	    			stb.append(str);
	    			stb.append("\n");
	    		}
	    		reader.close();
			}
    		String answer = stb.toString().replaceAll("RUB", "р.");
    		Log.e(TAG, "answer: "+answer);
    		categoryList = new ArrayList<AbstractData>();
    		vendorList = new ArrayList<AbstractData>();
    		productList = new ArrayList<AbstractData>();

    		JSONObject commObject = new JSONObject(answer);
    		Vendor v = new Vendor(commObject.getJSONObject(KEY_VENDOR));
    		vendorList.add(v);
    		Log.d(TAG, "vendor: "+v.toString());

    		JSONArray arr = commObject.getJSONArray(KEY_CATEGORIES);
    		for(int i = 0; i < arr.length(); i ++) {
    			Category cat = new Category((JSONObject)arr.get(i));
    			categoryList.add(cat);
        		Log.d(TAG, "category "+i+"  = "+cat.toString());
    		}
    		
    		arr = commObject.getJSONArray(KEY_PRODUCTS);
    		for(int i = 0; i < arr.length(); i ++) {
    			Product prod = new Product((JSONObject)arr.get(i));
        		Log.d(TAG, "product "+i+"  = "+prod.toString());
        		productList.add(prod);
    		}
    		return null;
		} catch (Exception ex) {
			Log.e(TAG, "getData error: "+ex);
			return "getData error: "+ex;
		} finally {
			if(connection != null)
				connection.disconnect();
	    }

	}
	
	@Override
	protected String doInBackground(String... arg) {
		String err = getData(arg);
		if(err != null && context != null)
			return context.getString(R.string.wrong_data);
		DBHelper dbHelper = new DBHelper(AppApplication.getInstance());
		dbHelper.updateCategoryTable(categoryList);
		dbHelper.updateProductTable(productList);
		dbHelper.updateVendorTable(vendorList);
		AppApplication.getInstance().fillAppData();
//		dbHelper.close();
//		dbHelper = null;
		return null;
	}

	@Override
    protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.d(TAG, "onPostExecute:  "+result);
		if(loadListener != null)
			loadListener.onLoadComplite(null);
		if(result != null)
			if(context != null)
				Toast.makeText(context, context.getString(R.string.error)+": "+result, Toast.LENGTH_LONG).show();
		else {	
			if(firstStart != null && !Boolean.parseBoolean(firstStart)) 
				if(context != null)
					Toast.makeText(context, context.getString(R.string.updated_successfull), Toast.LENGTH_LONG).show();
			AppApplication.getInstance().savePreference(AppApplication.IS_FIRST_START, ""+false);
		}
	}
}