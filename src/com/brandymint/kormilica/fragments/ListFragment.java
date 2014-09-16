package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.CategoryList;
import com.brandymint.kormilica.utils.ProductListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends CommonFragment {
	
	private ProductListAdapter adapter;
	private ListView listView;
	private CategoryList catList;

	public ListFragment() {}
	
	public ListFragment(CategoryList catList) {
		this.catList = catList;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		adapter = new ProductListAdapter(activity, catList.getList());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		listView = (ListView) view.findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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