package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.NumberPicker;

public class DetailsFragment extends CommonFragment {
	
	private Product product;
	private View view;

	public DetailsFragment() {
		super();
	}
	
	public DetailsFragment(CommonActivity activity, Product product) {
		super(activity);
		this.product = product;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.details_fragment, container, false);
		updateFragment();
		return view;
	}
	
	public void showPicker() {
		LayoutInflater factory = LayoutInflater.from(activity);
        final View v = factory.inflate(R.layout.picker_dialog, null);
        final NumberPicker np = (NumberPicker) v.findViewById(R.id.picker);
        np.setMaxValue(100);
        np.setMinValue(1);
        np.setValue(AppApplication.getInstance().getOrder().getCountOfProduct(product.getId()));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setInverseBackgroundForced(false);
		builder.setTitle(R.string.select_count);
		builder.setView(v);
		builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				AppApplication.getInstance().getOrder().changeCount(product, np.getValue());
				updateFragment();
				activity.updateView();
				dialog.cancel();
			}
		});
		builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
   		AlertDialog alert = builder.create();
		alert.show();
    }
	

	@Override
	public void updateFragment() {	
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView price = (TextView) view.findViewById(R.id.price);
		ImageView imView = (ImageView)view.findViewById(R.id.image);
		final TextView blueButton = (TextView)view.findViewById(R.id.blue_button);
		final TextView greenButton = (TextView)view.findViewById(R.id.green_button);
		final TextView description = (TextView)view.findViewById(R.id.description);
		name.setText(product.getTitle());
		if(product.isSelected()) {
			greenButton.setVisibility(View.VISIBLE);
			blueButton.setVisibility(View.INVISIBLE);
		} else {
			greenButton.setVisibility(View.INVISIBLE);
			blueButton.setVisibility(View.VISIBLE);
		}
//		description.setText(product.get);
		price.setText(product.getPriceCents() +"  " +product.getPriceCurrency() + getString(R.string._pcs));
		if(AppApplication.getInstance().getOrder() != null)
			greenButton.setText(activity.getString(R.string.in_order)+"   "+AppApplication.getInstance().getOrder().getCountOfProduct(product.getId()) + getString(R.string.pcs));
		if(product.getImageUrl() != null) {
			imView.setTag(product.getImageUrl());
//			AppApplication.getInstance().getBitmapCache().loadImage(imView, true);
		}
		greenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showPicker();
			}
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
				activity.updateView();
				updateFragment();
			}
		});
	}
}