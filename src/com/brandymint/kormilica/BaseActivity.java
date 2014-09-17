package com.brandymint.kormilica;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public abstract class BaseActivity extends FragmentActivity {

	private static final String TAG = "CommonActivity";
	public static final int EVENT_UPDATE_ACTIVITY			= 0;
	public static final int EVENT_RESET_SCREENS				= 1;
	public static final int EVENT_START_DETAILS_FRAGMENT	= 2;
	
	protected BaseActivity activity;
	protected ProgressDialog progressDialog;
	protected ActionBar actionBar;
	protected AppApplication app;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        app = AppApplication.getInstance();
        actionBar = getActionBar();
		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
	        @Override
	        public void onBackStackChanged() {
	        	if(getSupportFragmentManager().getBackStackEntryCount() == 0)
	            	finish();
	        	else
	        		updateView();
	        }
	    });
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about_menu, menu);
		return super.onCreateOptionsMenu(menu);
    }

	public void startProgressDialog() {
        try {
        	if(progressDialog == null)
        		progressDialog = new ProgressDialog(this);
    		progressDialog.setTitle(getString(R.string.please_wait));
    		progressDialog.setIndeterminate(true); 
    		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    		progressDialog.setCancelable(false);
    		progressDialog.show();
        } catch(Exception ex) {
        	Log.e(TAG, "Error start Progress dialog:  "+ex);
        }
	}
	
	public void stopProgressDialog() {
        try {
        	if(progressDialog != null)
        		progressDialog.dismiss();
        } catch(Exception ex) {
        	Log.e(TAG, "Error stop Progress dialog:  "+ex);
        }
	}
	
	
	public void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
    
    public abstract void updateView();
	
}