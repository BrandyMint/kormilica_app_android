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
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;

public class OrderAdapter extends BaseAdapter {
		private ArrayList<Product> listData;
		private LayoutInflater inflater;
		private CommonActivity activity;

		public OrderAdapter(CommonActivity activity, ArrayList<Product> listData) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.listData = listData;
			this.activity = activity;
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
		public Product getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.list_item, parent, false);
			final Product item = (Product) getItem(position);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView price = (TextView) view.findViewById(R.id.price);
			ImageView imView = (ImageView)view.findViewById(R.id.image);
			final TextView blueButton = (TextView)view.findViewById(R.id.blue_button);
			final TextView greenButton = (TextView)view.findViewById(R.id.green_button);
			name.setText(item.getTitle());
			if(item.isSelected()) {
				greenButton.setVisibility(View.VISIBLE);
				blueButton.setVisibility(View.INVISIBLE);
			} else {
				greenButton.setVisibility(View.INVISIBLE);
				blueButton.setVisibility(View.VISIBLE);
			}
			price.setText(item.getPriceCents() +"  " +item.getPriceCurrency());
			if(item.getImageUrl() != null) {
				imView.setTag(item.getImageUrl());
//				AppApplication.getInstance().getBitmapCache().loadImage(imView, true);
			}
			greenButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
			blueButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(AppApplication.getInstance().getOrder() == null)
						AppApplication.getInstance().setOrder(new Order());
					AppApplication.getInstance().getOrder().addProduct(item);
					greenButton.setVisibility(View.VISIBLE);
					blueButton.setVisibility(View.INVISIBLE);
					item.setSelected(true);
					activity.updateView();
				}
			});
			return view;
		}
}



	
