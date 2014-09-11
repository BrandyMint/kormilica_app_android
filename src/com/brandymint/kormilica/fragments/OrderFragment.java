package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.utils.OrderAdapter;
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
		this.activity = activity;
		cleanProdOrderList();
		adapter = new OrderAdapter(activity, AppApplication.getInstance().getOrder().getProductList());
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

	@Override
	public void updateDataAndFragment() {
		adapter = new OrderAdapter(activity, AppApplication.getInstance().getOrder().getProductList());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(!adapter.getItem(arg2).getId().equals("temp"))
					activity.addFragment(new DetailsFragment(activity, adapter.getItem(arg2)));
			}
        });
	}
}