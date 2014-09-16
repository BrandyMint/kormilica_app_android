package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.ProductData;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListPageFragment extends CommonFragment {
	
	private static final String TAG = "CommonActivity";
    private MyAdapter mAdapter;
    private ViewPager mPager;
    private CommonFragment []  screens;
	protected ProgressDialog progressDialog; 

	public ListPageFragment() {	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());
        screens = new CommonFragment[mAdapter.getCount()];
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.common, container, false);
        mPager = (ViewPager) view.findViewById(R.id.fragment);
        mPager.setAdapter(mAdapter);
    	mPager.setCurrentItem(0);  
    	return view;
    }
    
    public void changePage(int num) {
    	mPager.setCurrentItem(num);    	
    }
    
    public void nextPage() {
    	if(mPager.getCurrentItem() + 1 < mAdapter.getCount()) {
    		mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    	}
    }

    public void prewPage() {
    	if(mPager.getCurrentItem() - 1 >= 0) {
    		mPager.setCurrentItem(mPager.getCurrentItem() - 1);
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
	public void updateFragment() {
    	for(int i = 0; i < mAdapter.getCount(); i ++)
        	if(screens[i] != null)
            	screens[i].updateFragment();
	}
}