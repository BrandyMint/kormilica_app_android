package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.utils.OrderAdapter;
import com.brandymint.kormilica.utils.ProductListAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OrderFragment extends CommonFragment {
	
	private CommonActivity activity;
	private OrderAdapter adapter;
	private ListView listView;

	public OrderFragment() {
		super();
	}
	
	public OrderFragment(CommonActivity activity) {
		super(activity);
		adapter = new OrderAdapter(activity, AppApplication.getInstance().getOrder().getProductList());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		listView = (ListView) view.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				activity.addFragment(new DetailsFragment(activity, adapter.getItem(arg2)));
			}
        });
		activity.updateView();
		return view;
	}

	@Override
	public void updateFragment() {
		adapter.notifyDataSetChanged();
	}
}