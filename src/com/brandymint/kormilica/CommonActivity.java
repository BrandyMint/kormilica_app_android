package com.brandymint.kormilica;

import com.brandymint.kormilica.data.AbstractData;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.ProductData;
import com.brandymint.kormilica.fragments.AddressFragment;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.DetailsFragment;
import com.brandymint.kormilica.fragments.OrderFragment;
import com.brandymint.kormilica.utils.EventListener;
import com.brandymint.kormilica.utils.LoadListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonActivity extends BaseActivity implements LoadListener, EventListener {
	
	private static final String TAG = "CommonActivity";
	
	public static final int ID_FRAGMENT_DETAILS		= 1;
	public static final int ID_FRAGMENT_ORDER		= 2;
	public static final int ID_FRAGMENT_ADDRESS		= 3;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity);
		Intent intent = getIntent();
		if(intent.hasExtra("INTENT_ID")) {
			final int fragmentId = intent.getExtras().getInt("INTENT_ID");
			switch(fragmentId) {
				case ID_FRAGMENT_DETAILS:
						String productId = intent.getExtras().getString("PRODUCT_ID");
						addFragment(new DetailsFragment(ProductData.getInstance().getProductMap().get(productId)));
					break;
				case ID_FRAGMENT_ORDER:
						addFragment(new OrderFragment());
					break;
				case ID_FRAGMENT_ADDRESS:
						addFragment(new AddressFragment());
					break;
			}
		}
    }
    
	public void updateView() {
		Log.e(TAG, "updateView()");
		TextView summOrder = (TextView) findViewById(R.id.summ_order);
		TextView prepareOrderFull = (TextView) findViewById(R.id.prepare_order_full);
		LinearLayout blueLayout = (LinearLayout) findViewById(R.id.blue_layout);
		LinearLayout fullBlueLayout = (LinearLayout) findViewById(R.id.full_blue_layout);
		TextView itogo_summ = (TextView) findViewById(R.id.itogo_summ);
		FrameLayout bottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
		LinearLayout itogoLayout = (LinearLayout) findViewById(R.id.itogo_layout);
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
    
	public void addFragment(CommonFragment fragment) {
//		if(fragment instanceof ListPageActivity || fragment instanceof ListFragment)
//			getSupportFragmentManager().beginTransaction().add(R.id.activity_container, fragment).addToBackStack("KormilicaSteck").commit();  		
//		else
			replaceFragment(fragment);
		updateView();
	}

	public void replaceFragment(CommonFragment fragment) {
		getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, fragment).addToBackStack("KormilicaSteck").commit();  		
	}
	
	public void removeFragment() {
		getSupportFragmentManager().popBackStack();	
		updateView();
	}
	
	private void resetScreen() {
		while (getSupportFragmentManager().getBackStackEntryCount() > 0)
		    getSupportFragmentManager().popBackStackImmediate();
		super.finish();
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
	
	@Override
	public void onLoadComplite(String string) {
		resetScreen();
		stopProgressDialog();
	}
	

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