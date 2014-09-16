package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.utils.EventListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonFragment extends Fragment{
	
	protected ProgressDialog progressDialog;
	protected EventListener eventListener;
	
	public CommonFragment() {	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
	    	eventListener = (EventListener) activity;
	    } catch (ClassCastException e) {
	    	throw new ClassCastException("CommonFrgment: Error convert activity to EventListener");
	    }
	}
	
	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	public abstract void updateFragment();
}