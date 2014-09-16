package com.brandymint.kormilica;

import com.brandymint.kormilica.data.AbstractData;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.ProductData;
import com.brandymint.kormilica.fragments.AddressFragment;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.DetailsFragment;
import com.brandymint.kormilica.fragments.ListPageFragment;
import com.brandymint.kormilica.fragments.OrderFragment;
import com.brandymint.kormilica.utils.EventListener;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.GetDataTask;
import com.brandymint.kormilica.utils.PrepareData;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommonActivity extends FragmentActivity implements LoadListener, EventListener {
	
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
		if(!app.isOnline()) {
			app.showMessage(this, getString(R.string.warning), getString(R.string.error_netw_connection), getString(R.string.action_settings), getString(R.string.exit), new Intent(android.provider.Settings.ACTION_SETTINGS), true);
			return;
		}
		String firstStart = AppApplication.getInstance().loadPreference(AppApplication.IS_FIRST_START);
		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
	        @Override
	        public void onBackStackChanged() {
	        	if(getSupportFragmentManager().getBackStackEntryCount() == 0)
	            	finish();
	        	else
	        		updateView();
	        }
	    });
		if(firstStart == null || Boolean.parseBoolean(firstStart))
			(new GetDataTask(this)).execute();
		else
			(new PrepareData(this, this)).execute();
			
    }
    
	public void updateView() {
		Log.e(TAG, "updateView()");
		TextView summOrder = (TextView) findViewById(R.id.summ_order);
		TextView prepareOrder = (TextView) findViewById(R.id.prepare_order);
		TextView prepareOrderFull = (TextView) findViewById(R.id.prepare_order_full);
		TextView minimalOrder = (TextView) findViewById(R.id.minimal_summ_order);
		LinearLayout blueLayout = (LinearLayout) findViewById(R.id.blue_layout);
		LinearLayout fullBlueLayout = (LinearLayout) findViewById(R.id.full_blue_layout);
		TextView itogo_summ = (TextView) findViewById(R.id.itogo_summ);
		FrameLayout bottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
		FrameLayout itogoLayout = (FrameLayout) findViewById(R.id.itogo_layout);
		FrameLayout grayLayout = (FrameLayout) findViewById(R.id.gray_layout);

		Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_container);
	    if(fragment instanceof DetailsFragment) {
	    	actionBar.setDisplayHomeAsUpEnabled(true);
			itogoLayout.setVisibility(View.INVISIBLE);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			blueLayout.setVisibility(View.VISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
			if(app.getOrder() == null ||  app.getOrder().getProductList().size() <= 0) {
				blueLayout.setVisibility(View.INVISIBLE);
				grayLayout.setVisibility(View.VISIBLE);
			} else {
				blueLayout.setVisibility(View.VISIBLE);
				grayLayout.setVisibility(View.INVISIBLE);
				int summ = app.getOrder().getSummOrder()/100;
				summOrder.setText(""+summ+"  "+app.getVendor().getMinimalPriceCurrency());
			}
		} else if(fragment instanceof  AddressFragment) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			setTitle(R.string.your_order);
			itogoLayout.setVisibility(View.INVISIBLE);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			blueLayout.setVisibility(View.INVISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
		  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1,1);
		  	itogoLayout.setLayoutParams(parms);
		  	bottomLayout.setLayoutParams(parms);
		} else if(fragment instanceof  OrderFragment) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			setTitle(R.string.your_order);
			itogoLayout.setVisibility(View.VISIBLE);
			fullBlueLayout.setVisibility(View.VISIBLE);
			blueLayout.setVisibility(View.INVISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
		  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		  	int summ = AppApplication.getInstance().getOrder().getSummOrder() + Integer.parseInt(AppApplication.getInstance().getVendor().getDeliveryPriceCents());
		  	itogo_summ.setText(getString(R.string.itogo)+": "+summ/100+" "+AppApplication.getInstance().getVendor().getDeliveryPriceCurrency());
		  	itogoLayout.setLayoutParams(parms);
		  	bottomLayout.setLayoutParams(parms);
		} else {
			actionBar.setDisplayHomeAsUpEnabled(false);
			setTitle(app.getVendor().getName());
			itogoLayout.setVisibility(View.INVISIBLE);
		  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1,1);
		  	itogoLayout.setLayoutParams(parms);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			if(app.getOrder() == null ||  app.getOrder().getProductList().size() <= 0) {
				blueLayout.setVisibility(View.INVISIBLE);
				grayLayout.setVisibility(View.VISIBLE);
			} else {
				blueLayout.setVisibility(View.VISIBLE);
				grayLayout.setVisibility(View.INVISIBLE);
				int summ = app.getOrder().getSummOrder()/100;
				summOrder.setText(""+summ+"  "+app.getVendor().getMinimalPriceCurrency());
			}
			if(app.getVendor() != null)
				minimalOrder.setText(app.getVendor().getMobileMinimalAlert());
		  	parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		  	bottomLayout.setLayoutParams(parms);
		}

		prepareOrderFull.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addFragment(new AddressFragment());
			}
		});
		
		blueLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(app.getOrder().getSummOrder()/100 < Integer.parseInt(app.getVendor().getMinimalPriceCents())/100)
					app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
				else
					addFragment(new OrderFragment());
			}
		});
		grayLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
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
					startActivity(new Intent(activity, AboutActivity.class));
				break;
			case android.R.id.home:
	            	if(getSupportFragmentManager().getBackStackEntryCount() == 0)
	            		finish();
	            	else 
	            		removeFragment();
				break;
		}
		return true;
	}    
	
	public void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	public void addFragment(CommonFragment fragment) {
		getSupportFragmentManager().beginTransaction().add(R.id.activity_container, fragment).addToBackStack("KormilicaSteck").commit();  		
		updateView();
	}
	
	public void removeFragment() {
		getSupportFragmentManager().popBackStack();	
		updateView();
	}
	


	@Override
	public void onLoadComplite(String string) {
		resetScreen();
		stopProgressDialog();
	}
	
	private void resetScreen() {
		while (getSupportFragmentManager().getBackStackEntryCount() > 0)
		    getSupportFragmentManager().popBackStackImmediate();
		addFragment(new ListPageFragment());
		ProductData.getInstance().resetSelectedProducts();
		updateView();
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
	
	
	public static final int EVENT_UPDATE_ACTIVITY			= 0;
	public static final int EVENT_RESET_SCREENS				= 1;
	public static final int EVENT_START_DETAILS_FRAGMENT	= 2;

	@Override
	public void event(int event, AbstractData data) {
		switch(event) {
			case EVENT_UPDATE_ACTIVITY:
					updateView();
				break;
			case EVENT_RESET_SCREENS:
					resetScreen();
				break;
			case EVENT_START_DETAILS_FRAGMENT:
					addFragment(new DetailsFragment((Product)data));
				break;
		}
	}
}