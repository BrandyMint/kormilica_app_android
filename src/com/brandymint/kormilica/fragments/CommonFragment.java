package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.CommonActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonFragment extends Fragment{
	
	protected CommonActivity activity;
	protected String url;
	
	public CommonFragment() {
	}

	public CommonFragment(CommonActivity activity) {
		this.activity = activity;
	}
	
	public CommonActivity getCommonActivity() {
		return activity;
	}
	
	@Override
	public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
	
	public abstract void updateFragment();
	
	public abstract void updateDataAndFragment();
}