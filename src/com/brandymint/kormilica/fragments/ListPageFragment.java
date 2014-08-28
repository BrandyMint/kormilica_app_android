package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
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
    public static final int COUNT_OF_PAGE   = 3;

    private MyAdapter mAdapter;
    private ViewPager mPager;
    private CommonActivity activity;
//    private CommonPageFragment []  screens;
	protected ProgressDialog progressDialog; 
	private AppApplication app;


	public ListPageFragment(CommonActivity activity) {
		this.activity = activity;
        mAdapter = new MyAdapter(activity.getSupportFragmentManager());
        app = AppApplication.getInstance();
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
            return COUNT_OF_PAGE;
        }
 
        @Override
        public Fragment getItem(int position) {
			return new ListFragment(activity, app.getProductList());
        	
/*
            switch (position) {
             	case STATE_TOP_PAGE:
             			screens[STATE_TOP_PAGE] = new TopNewsFragment(activity);
            		break;
             	case STATE_WORLDNEWS_PAGE:
             			screens[STATE_WORLDNEWS_PAGE] =  new WorldNewsFragment(activity);
            		break;
             	case STATE_BUSSINESNEWS_PAGE:
             			screens[STATE_BUSSINESNEWS_PAGE] =  new BussinesNewsFragment(activity);
            		break;
             	case STATE_NATIONNEWS_PAGE:
             			screens[STATE_NATIONNEWS_PAGE] =  new NationNewsFragment(activity);
             		break;
             	case STATE_POLITICNEWS_PAGE:
             			screens[STATE_POLITICNEWS_PAGE] =  new PoliticNewsFragment(activity);
            		break;
            }
            return screens[position];

        	
        	Log.e(TAG, "position  -  "+position+" =?  "+STATE_BUSSINESNEWS_PAGE);
            switch (position) {
         		case STATE_TOP_PAGE:
         			return new TopNewsFragment(activity);
         		case STATE_WORLDNEWS_PAGE:
         			return new WorldNewsFragment(activity);
         		case STATE_BUSSINESNEWS_PAGE:
         			return new BussinesNewsFragment(activity);
         		case STATE_NATIONNEWS_PAGE:
         			return new NationNewsFragment(activity);
         		case STATE_POLITICNEWS_PAGE:
         			return new PoliticNewsFragment(activity);
         		default:
         			return null;
            }
            */
//            return null;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
/*            switch (position) {
         		case STATE_TOP_PAGE:
         			return getString(R.string.top);
         		case STATE_WORLDNEWS_PAGE:
         			return getString(R.string.world);
         		case STATE_BUSSINESNEWS_PAGE:
         			return getString(R.string.bussines);
         		case STATE_NATIONNEWS_PAGE:
         			return getString(R.string.nation);
         		case STATE_POLITICNEWS_PAGE:
         			return getString(R.string.politics);
         		default:
         			return "";
            }
            */
        	return "Fig cho!!!";
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
}