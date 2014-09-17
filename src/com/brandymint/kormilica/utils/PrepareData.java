package com.brandymint.kormilica.utils;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.BaseActivity;

import android.os.AsyncTask;


public class PrepareData extends AsyncTask<String, String, String> {

	private LoadListener loadListener;
	private BaseActivity activity;


	public PrepareData(BaseActivity activity, LoadListener loadListener) {
		this.loadListener = loadListener; 
		this.activity = activity;
	}
	
	@Override
	protected void onPreExecute() {
		if(activity != null)
			activity.startProgressDialog();
	}
	
	@Override
	protected String doInBackground(String... arg) {
		AppApplication.getInstance().fillAppData();
		AppApplication.getInstance().savePreference(AppApplication.IS_FIRST_START, ""+false);
		return null;
	}

	@Override
    protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(activity != null)
			activity.stopProgressDialog();
		loadListener.onLoadComplite(null);
	}
}