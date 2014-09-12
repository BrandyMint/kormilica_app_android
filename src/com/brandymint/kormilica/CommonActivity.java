package com.brandymint.kormilica;

import com.brandymint.kormilica.data.CategoryList;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.fragments.AddressFragment;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.DetailsFragment;
import com.brandymint.kormilica.fragments.ListPageFragment;
import com.brandymint.kormilica.fragments.OrderFragment;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.GetDataTask;
import com.brandymint.kormilica.utils.PrepareData;
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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class CommonActivity extends FragmentActivity implements LoadListener {
	
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
        app.setActivity(this);
        actionBar = getActionBar();
		if(!app.isOnline()) {
			app.showMessage(this, getString(R.string.warning), getString(R.string.error_netw_connection), getString(R.string.action_settings), getString(R.string.exit), new Intent(android.provider.Settings.ACTION_SETTINGS), true);
			return;
		}
		String firstStart = AppApplication.getInstance().loadPreference(AppApplication.IS_FIRST_START);
		if(firstStart == null || Boolean.parseBoolean(firstStart))
			(new GetDataTask(this, this, true)).execute();
		else
			(new PrepareData(this, this)).execute();
			
    }
    
	public void updateView() {
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

		
		if(app.getFragmentCache().size() > 1 && DetailsFragment.class.isInstance(app.getFragmentCache().get(0))) {
			itogoLayout.setVisibility(View.INVISIBLE);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			blueLayout.setVisibility(View.INVISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
		  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1,1);
		  	itogoLayout.setLayoutParams(parms);
		  	bottomLayout.setLayoutParams(parms);
		} else if(app.getFragmentCache().size() > 1 && AddressFragment.class.isInstance(app.getFragmentCache().get(0))) {
			setTitle(R.string.your_order);
			itogoLayout.setVisibility(View.INVISIBLE);
			fullBlueLayout.setVisibility(View.INVISIBLE);
			blueLayout.setVisibility(View.INVISIBLE);
			grayLayout.setVisibility(View.INVISIBLE);
		  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1,1);
		  	itogoLayout.setLayoutParams(parms);
		  	bottomLayout.setLayoutParams(parms);
		} else if(app.getFragmentCache().size() > 1 && OrderFragment.class.isInstance(app.getFragmentCache().get(0))) {
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
//				((OrderFragment)app.getFragmentCache().get(0)).cleanProdOrderList();
				addFragment(new AddressFragment(activity));
			}
		});
		blueLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(app.getOrder().getSummOrder()/100 < Integer.parseInt(app.getVendor().getMinimalPriceCents())/100)
					app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
				else
					addFragment(new OrderFragment(activity));
			}
		});
		grayLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
			}
		});
	}
    
	public void updateCurrentFragment() {
		app.getFragmentCache().get(0).updateFragment();
	}
	
	public void showPicker(final Product product) {
		LayoutInflater factory = LayoutInflater.from(activity);
        final View v = factory.inflate(R.layout.picker_dialog, null);
        final NumberPicker np = (NumberPicker) v.findViewById(R.id.picker);
        np.setMaxValue(10);
        np.setMinValue(0);
        np.setValue(AppApplication.getInstance().getOrder().getCountOfProduct(product.getId()));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setInverseBackgroundForced(false);
		builder.setTitle(R.string.select_count);
		builder.setView(v);
		Log.e("ShowPicker", "product: "+product);
		Log.e("ShowPicker", "order: "+AppApplication.getInstance().getOrder());
		
		builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Log.e("ShowPicker tap", "product: "+product);
				Log.e("ShowPicker tap", "order: "+AppApplication.getInstance().getOrder());

				if(np.getValue() == 0) {
					AppApplication.getInstance().getOrder().removeProduct(product);
					product.setSelected(false);
				} else {
					AppApplication.getInstance().getOrder().changeCount(product, np.getValue());
				}
				app.getFragmentCache().get(0).updateFragment();
				activity.updateView();
				dialog.cancel();
			}
		});
		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
   		AlertDialog alert = builder.create();
		alert.show();
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
//					showAbout();
					startActivity(new Intent(activity, AboutActivity.class));
				break;
			case android.R.id.home:
					finish();
				break;
			case R.id.update:
				(new GetDataTask(this, this, false)).execute();
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
		app.setOrder(new Order());
		for(CategoryList cat: app.getProductList())
			for(Product prod: cat.getList())
				prod.setSelected(false);
		while(app.getFragmentCache().size() > 1) {
			removeFragment(0);
		}
		System.gc();
	}
	
	public void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
	}

	public void finish() {
		if(app.getFragmentCache().size() > 0)
			removeFragment(0);
		if(app.getFragmentCache() == null || app.getFragmentCache().size() == 0)
			super.finish();
	}

	@Override
	public void onLoadComplite(String string) {
		if(app.getFragmentCache().size() > 0) {
			Log.e(TAG, "AFTER UPDATE 1");
			updateView();
			Log.e(TAG, "AFTER UPDATE 2");
			setUpActionBarButtons();
			Log.e(TAG, "AFTER UPDATE 3");
			for(CommonFragment fr: app.getFragmentCache()) {
				Log.e(TAG, "AFTER UPDATE 4  "+fr);
				fr.updateDataAndFragment();
			}
			Log.e(TAG, "AFTER UPDATE 5");
		} else 
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