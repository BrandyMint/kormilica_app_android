package com.brandymint.kormilica.utils;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Product;

public class ProductListAdapter extends BaseAdapter {
		private ArrayList<Product> listData;
		private LayoutInflater inflater;

		public ProductListAdapter(CommonActivity activity, ArrayList<Product> listData) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.listData = listData;
		}

		public void setListData(ArrayList<Product> listData) {
			this.listData = listData;
		}

		@Override
		public int getCount() {
			if(listData != null)
				return listData.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.list_item, parent, false);
			Product item = (Product) getItem(position);

			TextView name = (TextView) view.findViewById(R.id.name);
			TextView price = (TextView) view.findViewById(R.id.price);
			ImageView imView = (ImageView)view.findViewById(R.id.image);
			
			name.setText(item.getTitle());
			price.setText(item.getPriceCents() + item.getPriceCurrency());
			if(item.getImageUrl() != null) {
				imView.setTag(item.getImageUrl());
				AppApplication.getInstance().getBitmapCache().loadImage(imView, true);
			}
			return view;
		}
}



	
