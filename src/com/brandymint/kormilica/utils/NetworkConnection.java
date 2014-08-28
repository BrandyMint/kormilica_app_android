package com.brandymint.kormilica.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Category;
import com.brandymint.kormilica.data.NewsDataItem;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.Vendor;

import android.os.AsyncTask;
import android.util.Log;


public class NetworkConnection extends AsyncTask<String, String, String> {

	private static final String TAG				 = "NetworkConnection";  
	private static final String BASE_URL		 = "http://api.kormilica.info/";
	private static final String API_VERSION		 = "v1";
	private static final String VENDOR_KEY		 = "467abe2e7d33e6455fe905e879fd36be";
	
	private static final String KEY_VENDOR		 = "vendor";
	private static final String KEY_CATEGORIES	 = "categories";
	private static final String KEY_PRODUCTS	 = "products";

	
	private CommonActivity activity;
	private LoadListener loadListener;
	private String resultString;
	private ArrayList<Product> list;

	public NetworkConnection(CommonActivity activity, LoadListener loadListener) {
		this.activity = activity;
		this.loadListener = loadListener; 
	}
	
	private ArrayList<Product> getData(String[] arg) {
		Log.d(TAG, "getData start");
		try
		{
			String url = BASE_URL + API_VERSION + "/bundles.json";
			HttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet(url);
		    httpget.setHeader("X-Vendor-Key", VENDOR_KEY);	
		    httpget.setHeader("Accept","application/xml; charset=utf-8");
		    httpget.setHeader("Content-Type","application/xml; charset=utf-8");	
		    
		    HttpResponse response = httpclient.execute(httpget);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
    		StringBuffer stb = new StringBuffer();
    		String str;
    		while((str = reader.readLine()) != null) {
    			stb.append(str);
    			stb.append("\n");
    		}
    		reader.close();
    		String answer = stb.toString();
    		Log.d(TAG, "answer: "+answer);
    		JSONObject commObject = new JSONObject(answer);
    		Vendor v = new Vendor(commObject.getJSONObject(KEY_VENDOR));
    		Log.d(TAG, "vendor: "+v.toString());

    		JSONArray arr = commObject.getJSONArray(KEY_CATEGORIES);
    		for(int i = 0; i < arr.length(); i ++) {
    			Category cat = new Category((JSONObject)arr.get(i));
        		Log.d(TAG, "category "+i+"  = "+cat.toString());
    		}
    		
    		list = new ArrayList<Product>();
    		arr = commObject.getJSONArray(KEY_PRODUCTS);
    		for(int i = 0; i < arr.length(); i ++) {
    			Product prod = new Product((JSONObject)arr.get(i));
        		Log.d(TAG, "product "+i+"  = "+prod.toString());
        		list.add(prod);
    		}
    		return list;
		} catch (Exception ex) {
			Log.e(TAG, "getNewsData error 1  "+ex);
			return null;
		}
	}
	
	@Override
	protected void onPreExecute() {
		if(activity != null)
			activity.startProgressDialog();
	}

	@Override
	protected String doInBackground(String... arg) {
		int resultCode = -1; 
		list = getData(arg);
		if(list == null)
			return activity.getString(R.string.wrong_data);
		Log.d(TAG, "doInBackground result:  "+resultCode);
		return resultString;
	}

	@Override
    protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Log.d(TAG, "onPostExecute:  "+result);
		activity.stopProgressDialog();
		if(result != null)
			AppApplication.getInstance().showMessage(activity, activity.getString(R.string.error), result, activity.getString(R.string.ok), null, null, false);
		else	
			loadListener.onLoadComplite(list);
	}
}