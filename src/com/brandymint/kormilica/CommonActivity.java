package com.brandymint.kormilica;

import java.util.LinkedList;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.ListPageFragment;
import com.brandymint.kormilica.fragments.OrderFragment;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.NetworkConnection;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonActivity extends FragmentActivity implements LoadListener{
	
	private static final String TAG = "CommonActivity";
    private CommonActivity activity;
	protected ProgressDialog progressDialog;
	private ActionBar actionBar;
	private AppApplication app;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
		setContentView(R.layout.activity);
        app = AppApplication.getInstance();
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xff999999));
        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null)
                title.setTextColor(Color.BLACK);
        }
		if(!app.isOnline()) {
			app.showMessage(this, getString(R.string.warning), getString(R.string.error_netw_connection), getString(R.string.action_settings), getString(R.string.exit), new Intent(android.provider.Settings.ACTION_SETTINGS), true);
			return;
		}
		(new NetworkConnection(this, this)).execute();
    }
    
	public void updateView() {
		TextView summOrder = (TextView) findViewById(R.id.summ_order);
		TextView prepareOrder = (TextView) findViewById(R.id.prepare_order);
		TextView prepareOrderFull = (TextView) findViewById(R.id.prepare_order_full);
		TextView minimalOrder = (TextView) findViewById(R.id.minimal_summ_order);
		LinearLayout blueLayout = (LinearLayout) findViewById(R.id.blue_layout);
		LinearLayout fullBlueLayout = (LinearLayout) findViewById(R.id.full_blue_layout);
		FrameLayout grayLayout = (FrameLayout) findViewById(R.id.gray_layout);

		if(app.getFragmentCache().size() > 1 && OrderFragment.class.isInstance(app.getFragmentCache().get(0))) {
			setTitle(R.string.your_order);
			fullBlueLayout.setVisibility(View.VISIBLE);
			blueLayout.setVisibility(View.INVISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
		} else {
			setTitle(R.string.app_name);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			if(app.getOrder() == null) {
				blueLayout.setVisibility(View.INVISIBLE);
				grayLayout.setVisibility(View.VISIBLE);
				if(app.getVendor() != null)
					minimalOrder.setText(getString(R.string.minimal_summ)+" "+app.getVendor().getMinimalPriceCents()+"  "+app.getVendor().getMinimalPriceCurrency());
			} else {
				blueLayout.setVisibility(View.VISIBLE);
				grayLayout.setVisibility(View.INVISIBLE);
				summOrder.setText(""+app.getOrder().getSummOrder()+"  "+app.getVendor().getMinimalPriceCurrency());
			}
		}
		prepareOrderFull.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				addFragment(new OrderFragment(activity));
			}
		});
		prepareOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addFragment(new OrderFragment(activity));
			}
		});
	}
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		menu = openAboutMenu(menu);
		return super.onCreateOptionsMenu(menu);
    }

	public Menu openAboutMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about_menu, menu);
        return menu;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.about:
					showAbout();
				break;
			case android.R.id.home:
					finish();
				break;
		}
		return true;
	}    
	
    private void showAbout() {
		LayoutInflater factory = LayoutInflater.from(this);
        final View v = factory.inflate(R.layout.about, null);
        final TextView text = (TextView)v.findViewById(R.id.version);
        try{
        	text.setText(getString(R.string.version)+": "+getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        }catch(Exception ex){}

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setInverseBackgroundForced(false);
		builder.setView(v);
		builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
   		AlertDialog alert = builder.create();
		alert.show();
	}

    
	public void resetFragmentCache() {
		app.setFragmentCache(new LinkedList<CommonFragment>());
		addFragment(new ListPageFragment(this));
	}

	public void addFragment(CommonFragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.activity_container, fragment).commitAllowingStateLoss();
		app.getFragmentCache().add(0, fragment);
		app.checkFragmentCacheSize();
		setUpActionBarButtons();
		app.getFragmentCache().get(0).updateFragment();
		updateView();
	}
	
	public void removeFragment(int ind) {
		getSupportFragmentManager().beginTransaction().detach(AppApplication.getInstance().getFragmentCache().get(ind)).commit();
		app.getFragmentCache().remove(ind);
		app.checkFragmentCacheSize();
		setUpActionBarButtons();
		app.getFragmentCache().get(0).updateFragment();
		updateView();
	}
	
	private void setUpActionBarButtons() {
		if(app.getFragmentCache().size() > 1) {
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
	}

	public void finish() {
		if(app.getFragmentCache().size() > 1)
			removeFragment(0);
		else
			super.finish();
	}

	@Override
	public void onLoadComplite() {
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