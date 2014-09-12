package com.brandymint.kormilica.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;

import android.os.AsyncTask;
import android.util.Log;


public class SendOrderTask extends AsyncTask<String, String, String> {

	private static final String TAG				 = "SendOrderTask";  
	private static final String BASE_URL		 = "http://api.kormilica.info/";
	private static final String API_VERSION		 = "v1";
	private static final String VENDOR_KEY		 = "467abe2e7d33e6455fe905e879fd36be";
//	private static final String VENDOR_KEY		 = "45751f0d53a336ffb5fb91447c165fc9";

	private CommonActivity activity;
	private LoadListener loadListener;
	private String resultString;
	private Order order;

	public SendOrderTask(CommonActivity activity, LoadListener loadListener, Order order) {
		this.activity = activity;
		this.loadListener = loadListener;
		this.order = order;
	}
	
	private String sendOrder(String[] arg) {
		Log.d(TAG, "getData start");
		try
		{	
    		StringBuffer stb = new StringBuffer();
			String url = BASE_URL + API_VERSION + "/orders.json";
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url);
		    httppost.setHeader("X-Vendor-Key", VENDOR_KEY);	
		    httppost.setHeader("Accept","application/json; charset=utf-8");
		    httppost.setHeader("Content-Type","application/json; charset=utf-8");	
		  	int summ = order.getSummOrder() + Integer.parseInt(AppApplication.getInstance().getVendor().getDeliveryPriceCents());
		  	
		  	JSONArray arr = new JSONArray();
		  	for(Product product: order.getProductList()) {
				if(!product.getId().equals("temp")) {
				  	JSONObject obj = new JSONObject();
					Log.e(TAG, "tovar :  "+product.getTitle());
				  	obj.put("product_id", Long.parseLong(product.getId()));
				  	obj.put("count", order.getCountOfProduct(product.getId()));
				  	obj.put("price", Integer.parseInt(product.getPriceCents()));
				  	arr.put(obj);
				}
		  	}
		  	JSONObject user = new JSONObject();
			user.put("phone", arg[0]);
			user.put("address", arg[2]);
			user.put("comment", "Test order");

		  	JSONStringer json = new JSONStringer().object() 
					.key("user").value(user)
					.key("items").value(arr)
					.key("total_price").value(summ)
					.endObject();
			Log.d(TAG, "JSON:  "+json);
			StringEntity entity = new StringEntity(json.toString());
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
			httppost.setEntity(entity); 

			
			HttpResponse response = httpclient.execute(httppost);
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
	    	String str;
	    	while((str = reader.readLine()) != null) {
	    		stb.append(str);
	    		stb.append("\n");
	    	}
	    	reader.close();

	    	String answer = stb.toString();
			Log.e(TAG, "answer:  "+answer);
    		return answer;
		} catch (Exception ex) {
			Log.e(TAG, "getData error: "+ex);
			return "getData error: "+ex;
		}
	}
	
	@Override
	protected void onPreExecute() {
		if(activity != null)
			activity.startProgressDialog();
	}

	@Override
	protected String doInBackground(String... arg) {
		String res = sendOrder(arg);
		if(res == null)
			return activity.getString(R.string.wrong_data);
		return res;
	}

	@Override
    protected void onPostExecute(String result) {
		super.onPostExecute(result);
		activity.stopProgressDialog();
		if(result == null)
			AppApplication.getInstance().showMessage(activity, activity.getString(R.string.error), result, activity.getString(R.string.ok), null, null, false);
		else
			if(loadListener != null)
				loadListener.onLoadComplite(result);
	}
}