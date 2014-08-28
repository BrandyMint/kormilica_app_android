package com.brandymint.kormilica;

import java.util.ArrayList;
import java.util.LinkedList;

import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.ListPageFragment;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.NetworkConnection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CommonActivity extends FragmentActivity implements LoadListener{
	
	private static final String TAG = "CommonActivity";
    private CommonActivity activity;
	protected ProgressDialog progressDialog; 
	private AppApplication app;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity);
        app = AppApplication.getInstance();
		if(!app.isOnline()) {
			app.showMessage(this, getString(R.string.warning), getString(R.string.error_netw_connection), getString(R.string.action_settings), getString(R.string.exit), new Intent(android.provider.Settings.ACTION_SETTINGS), true);
			return;
		}
        activity = this;
		(new NetworkConnection(this, this)).execute();
    }
    
	public void reserFragmentCache() {
		app.setFragmentCache(new LinkedList<CommonFragment>());
		addFragment(new ListPageFragment(this));
	}

	public void addFragment(CommonFragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.activity_container, fragment).commitAllowingStateLoss();
//		if(AppApplication.getInstance().getFragmentCache().size() > 0 
//				&& AppApplication.getInstance().getFragmentCache().get(0).getClass() == fragment.getClass() && fragment.title == null) 
//			getFragmentManager().beginTransaction().show(AppApplication.getInstance().getFragmentCache().get(0));
//		else	
		app.getFragmentCache().add(0, fragment);
		checkOrientation();	
	}
	
	public void removeFragment(int ind) {
		getSupportFragmentManager().beginTransaction().detach(AppApplication.getInstance().getFragmentCache().get(ind)).commit();
		app.getFragmentCache().remove(ind);
        checkOrientation();
        System.gc();
	}

	public void checkOrientation() {
//       	if(AppApplication.getInstance().getFragmentCache().get(0) instanceof ListActivityFragment) {
//         	setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//       	} else {
//         	setRequestedOrientation (getDefaultOrientation());
//       	}
		app.checkFragmentCacheSize();
	}
	
	public void finish() {
		if(app.getFragmentCache().size() > 1)
			removeFragment(0);
		else {
//			if(app.getFragmentCache().get(0).finish()) {
//				app.finish();
				super.finish();
//			}
		}
	}


	@Override
	public void onLoadComplite(ArrayList<Product> list) {
		app.setProductList(list);
		addFragment(new ListPageFragment(this));
	}
    

	public void startProgressDialog() {
        try{
        	if(progressDialog == null)
        		progressDialog = new ProgressDialog(this);
    		progressDialog.setTitle(getString(R.string.please_wait));
    		progressDialog.setIndeterminate(true); 
    		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    		progressDialog.setCancelable(false);
    		progressDialog.show();
        }catch(Exception ex) {
        	Log.e(TAG, "Error start Progress dialog:  "+ex);
        }
	}
	
	public void stopProgressDialog() {
        try{
        	if(progressDialog != null)
        		progressDialog.dismiss();
        }catch(Exception ex) {
        	Log.e(TAG, "Error stop Progress dialog:  "+ex);
        }
	}

}