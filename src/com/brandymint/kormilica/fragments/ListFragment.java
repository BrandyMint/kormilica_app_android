package com.brandymint.kormilica.fragments;

import java.util.ArrayList;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.utils.ProductListAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends CommonFragment {
	
	private ProductListAdapter adapter;
	private ArrayList<Product> list;

	public ListFragment() {
		super();
	}
	
	public ListFragment(CommonActivity activity, ArrayList<Product> list) {
		super(activity);
		this.list = list;
		adapter = new ProductListAdapter(activity, this.list);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list_fragment, container, false);

		ListView listView = (ListView) v.findViewById(R.id.list);
		listView.setAdapter(adapter);
   		Log.e("ListFragment", "listView  -  "+listView);
   		Log.e("ListFragment", "adapter  -  "+adapter);
   		Log.e("ListFragment", "list  -  "+list);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				startIntent(list.get(arg2).getOriginalUrl());
			}
        });
		return v;
	}
}