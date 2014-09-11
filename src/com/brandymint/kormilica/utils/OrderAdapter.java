package com.brandymint.kormilica.utils;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.fragments.DetailsFragment;

public class OrderAdapter extends BaseAdapter {
		private ArrayList<Product> listData;
		private LayoutInflater inflater;
		private CommonActivity activity;

		public OrderAdapter(CommonActivity activity, ArrayList<Product> listData) {
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.listData = listData;
			this.activity = activity;
			Product temp = new Product();
			temp.setId("temp");
			this.listData.add(temp);
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
			View view;
			final Product item = (Product) getItem(position);
			if(position == listData.size() - 1) {
				view = inflater.inflate(R.layout.last_item_order, parent, false);
				TextView price = (TextView) view.findViewById(R.id.summ);
				TextView ifely = (TextView) view.findViewById(R.id.delivery);
				price.setText(Integer.parseInt(AppApplication.getInstance().getVendor().getDeliveryPriceCents())/100 + " " + AppApplication.getInstance().getVendor().getDeliveryPriceCurrency());
				ifely.setText(AppApplication.getInstance().getVendor().getMobileDelivery());
				
			} else {
				view = inflater.inflate(R.layout.order_list_item, parent, false);
				LinearLayout layout = (LinearLayout) view.findViewById(R.id.item);
				TextView name = (TextView) view.findViewById(R.id.name);
				TextView price = (TextView) view.findViewById(R.id.price);
//				TextView count = (TextView)view.findViewById(R.id.count);
				final TextView summ = (TextView)view.findViewById(R.id.summ);
				FrameLayout button = (FrameLayout)view.findViewById(R.id.button);
				final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
			    String[] spinnerItems = activity.getResources().getStringArray(R.array.product_count);
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, spinnerItems); 
		    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    	spinner.setAdapter(adapter);
				if(AppApplication.getInstance().getOrder() != null)
					spinner.setSelection(AppApplication.getInstance().getOrder().getCountOfProduct(item.getId()));
		    	final long currtime = System.currentTimeMillis();
		    	spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onNothingSelected(AdapterView<?> arg0) {}
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						if(System.currentTimeMillis() - currtime < 1000)
							return;
						if(arg2 == 0) {
							AppApplication.getInstance().getOrder().removeProduct(item);
							item.setSelected(false);
							listData.remove(item);
						} else {
							AppApplication.getInstance().getOrder().changeCount(item, arg2);
							try{
								int sum = Integer.parseInt(item.getPriceCents()) * AppApplication.getInstance().getOrder().getCountOfProduct(item.getId());
								summ.setText(sum/100+" "+item.getPriceCurrency());
							} catch(Exception ex){}
						}
						activity.updateView();
						activity.updateCurrentFragment();
					}
				});

				name.setText(item.getTitle());
				price.setText(Integer.parseInt(item.getPriceCents())/100 +"  " +item.getPriceCurrency()+""+activity.getString(R.string._pcs));
//				count.setText(AppApplication.getInstance().getOrder().getCountOfProduct(item.getId())+""+activity.getString(R.string.pcs));
				try{
					int sum = Integer.parseInt(item.getPriceCents()) * AppApplication.getInstance().getOrder().getCountOfProduct(item.getId());
					summ.setText(sum/100+" "+item.getPriceCurrency());
				} catch(Exception ex){}
				layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!item.getId().equals("temp"))
						activity.addFragment(new DetailsFragment(activity, item));
				}
			});
/*				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						activity.showPicker(item);
					}
				});
*/			}
			return view;
		}
}



	
