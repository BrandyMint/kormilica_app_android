package com.brandymint.kormilica;

import com.astuetz.PagerSlidingTabStrip;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.AbstractData;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.ProductData;
import com.brandymint.kormilica.fragments.CommonFragment;
import com.brandymint.kormilica.fragments.ListFragment;
import com.brandymint.kormilica.fragments.OrderFragment;
import com.brandymint.kormilica.utils.EventListener;
import com.brandymint.kormilica.utils.GetDataTask;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.PrepareData;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListPageActivity extends BaseActivity implements LoadListener, EventListener {
	
	private static final String TAG = "CommonActivity";
    private MyAdapter adapter;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private CommonFragment []  screens;
	protected ProgressDialog progressDialog; 

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.common);
    	actionBar.setDisplayHomeAsUpEnabled(false);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.pagerTitle);
		pager = (ViewPager) findViewById(R.id.fragment);
		String firstStart = AppApplication.getInstance().loadPreference(AppApplication.IS_FIRST_START);
		startProgressDialog();
		if(firstStart == null || Boolean.parseBoolean(firstStart))
			(new GetDataTask(this)).execute();
		else
			(new PrepareData(this, this)).execute();
    }
	
    public void changePage(int num) {
    	pager.setCurrentItem(num);    	
    }
    
    public void nextPage() {
    	if(pager.getCurrentItem() + 1 < adapter.getCount()) {
    		pager.setCurrentItem(pager.getCurrentItem() + 1);
    	}
    }

    public void prewPage() {
    	if(pager.getCurrentItem() - 1 >= 0) {
    		pager.setCurrentItem(pager.getCurrentItem() - 1);
    	}
    }

    public class MyAdapter extends FragmentPagerAdapter {
        
    	public MyAdapter(FragmentManager fm) {
            super(fm);
        }
    	@Override
        public int getCount() {
            return ProductData.getInstance().getProductList().size();
        }
 
        @Override
        public Fragment getItem(int position) {
        	if(screens[position] == null)
        		screens[position] = new ListFragment(ProductData.getInstance().getProductList().get(position)); 
        	return screens[position];
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
        	return ProductData.getInstance().getProductList().get(position).getTitle();
        }        

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			Log.e(TAG, "destroyItem   "+arg1);
		}

		@Override
		public void finishUpdate(View arg0) {
			Log.e(TAG, "finishUpdate   "+arg0);
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.e(TAG, "instantiateItem   "+arg0+"    "+arg1);
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
    }
    
	@Override
	public void onResume(){
		super.onResume();
		updateView();
	}

	@Override
	public void onLoadComplite(String string) {
		adapter = new MyAdapter(getSupportFragmentManager());
		screens = new CommonFragment[adapter.getCount()];
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
    	pager.setCurrentItem(0);  
		stopProgressDialog();
	}
    
	@Override
	public void updateView() {
		if(adapter == null)
			return;
		setTitle(app.getVendor().getName());
		TextView summOrder = (TextView) findViewById(R.id.summ_order);
		TextView minimalOrder = (TextView) findViewById(R.id.minimal_summ_order);
		LinearLayout blueLayout = (LinearLayout) findViewById(R.id.blue_layout);
		FrameLayout grayLayout = (FrameLayout) findViewById(R.id.gray_layout);
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
		
    	for(int i = 0; i < adapter.getCount(); i ++)
        	if(screens[i] != null)
            	screens[i].updateFragment();
	
    	blueLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(app.getOrder().getSummOrder()/100 < Integer.parseInt(app.getVendor().getMinimalPriceCents())/100)
					app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
				else {
					Intent intent = new Intent(activity, CommonActivity.class);
					intent.putExtra("INTENT_ID", CommonActivity.ID_FRAGMENT_ORDER);
					startActivity(intent);
					
				}
			}
		});
		grayLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				app.showMessage(activity, getString(R.string.warning), app.getVendor().getMobileMinimalAlert(), getString(R.string.ok), null, null, false);
			}
		});
		adapter.notifyDataSetChanged();
	}

	@Override
	public void event(int event, AbstractData data) {
		switch(event) {
			case EVENT_UPDATE_ACTIVITY:
					updateView();
				break;
			case EVENT_START_DETAILS_FRAGMENT:
					Intent intent = new Intent(this, CommonActivity.class);
					intent.putExtra("INTENT_ID", CommonActivity.ID_FRAGMENT_DETAILS);
					intent.putExtra("PRODUCT_ID", ((Product)data).getId());
					startActivity(intent);
				break;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.about:
					startActivity(new Intent(activity, AboutActivity.class));
				break;
			case android.R.id.home:
            		finish();
				break;
		}
		return true;
	}    
	
}