package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.utils.OrderAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OrderFragment extends CommonFragment {
	
	private OrderAdapter adapter;
	private ListView listView;

	public OrderFragment() {	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		cleanProdOrderList();
		adapter = new OrderAdapter(getActivity(), AppApplication.getInstance().getOrder().getProductList());
	}
	
	public void cleanProdOrderList() {
		for(Product prod: AppApplication.getInstance().getOrder().getProductList())
			if(prod.getId().equals("temp"))
				AppApplication.getInstance().getOrder().getProductList().remove(prod);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		listView = (ListView) view.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(!adapter.getItem(arg2).getId().equals("temp"))
					eventListener.event(CommonActivity.EVENT_START_DETAILS_FRAGMENT, adapter.getItem(arg2));
			}
        });
		eventListener.event(CommonActivity.EVENT_UPDATE_ACTIVITY, null);
		return view;
	}

	@Override
	public void updateFragment() {
		adapter.notifyDataSetChanged();
	}
}