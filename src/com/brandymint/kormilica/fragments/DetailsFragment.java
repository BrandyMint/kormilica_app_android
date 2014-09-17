package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.PhotoActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.utils.BitmapCache;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailsFragment extends CommonFragment {
	
	private Product product;
	private View view;

	public DetailsFragment() {	}
	
	public DetailsFragment(Product product) {
		this.product = product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.details_fragment, container, false);
		updateFragment();
		return view;
	}
	
	@Override
	public void updateFragment() {
		if(view == null)
			return;
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView price = (TextView) view.findViewById(R.id.price);
		ImageView imView = (ImageView)view.findViewById(R.id.image);
		final TextView blueButton = (TextView)view.findViewById(R.id.blue_button);
		final LinearLayout greenButton = (LinearLayout)view.findViewById(R.id.green_button);
// TODO		final TextView description = (TextView)view.findViewById(R.id.description);
		
		final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
	    String[] spinnerItems = getResources().getStringArray(R.array.product_count);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, spinnerItems); 
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
		if(AppApplication.getInstance().getOrder() != null)
			spinner.setSelection(AppApplication.getInstance().getOrder().getCountOfProduct(product.getId()));
    	final long currtime = System.currentTimeMillis();
    	spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onNothingSelected(AdapterView<?> arg0) {}
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(System.currentTimeMillis() - currtime < 1000)
					return;
				if(arg2 == 0) {
					AppApplication.getInstance().getOrder().removeProduct(product);
					product.setSelected(false);
				} else {
					AppApplication.getInstance().getOrder().changeCount(product, arg2);
				}
				updateFragment();
				eventListener.event(CommonActivity.EVENT_UPDATE_ACTIVITY, null);
			}
		});

		view.findViewById(R.id.common_layout).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {}
        });
		
		imView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), PhotoActivity.class);
            	intent.putExtra("PHOTO_URL", product.getImageUrl());
            	getActivity().startActivity(intent);
            }
        });
        
		name.setText(product.getTitle());
		if(product.isSelected()) {
			greenButton.setVisibility(View.VISIBLE);
			blueButton.setVisibility(View.INVISIBLE);
		} else {
			greenButton.setVisibility(View.INVISIBLE);
			blueButton.setVisibility(View.VISIBLE);
		}
// TODO		description.setText(product.get);
		price.setText(Integer.parseInt(product.getPriceCents())/100 +"  " +product.getPriceCurrency() + getString(R.string._pcs));
		if(product.getImageUrl() != null) {
			imView.setTag(product.getImageUrl());
			BitmapCache.getInstance().loadImage(imView, true);
		}

		greenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {			}
		});
		blueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(AppApplication.getInstance().getOrder() == null)
					AppApplication.getInstance().setOrder(new Order());
				AppApplication.getInstance().getOrder().addProduct(product);
				greenButton.setVisibility(View.VISIBLE);
				blueButton.setVisibility(View.INVISIBLE);
				product.setSelected(true);
				eventListener.event(CommonActivity.EVENT_UPDATE_ACTIVITY, null);
				updateFragment();
			}
		});
	}
}