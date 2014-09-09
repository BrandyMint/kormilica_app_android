package com.brandymint.kormilica.fragments;

import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AddressFragment extends CommonFragment {
	
	private CommonActivity activity;

	public AddressFragment() {
		super();
	}
	
	public AddressFragment(CommonActivity activity) {
		super(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.address_fragment, container, false);
		final EditText phone = (EditText) view.findViewById(R.id.phone);
		final EditText city = (EditText) view.findViewById(R.id.city);
		final EditText address = (EditText) view.findViewById(R.id.address);
		TextView button = (TextView) view.findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(phone.getText().toString() == null || phone.getText().toString().length() == 0) {
					AppApplication.getInstance().showMessage(activity, activity.getString(R.string.error), activity.getString(R.string.empty_phone), activity.getString(R.string.ok), null, null, false);
					return;
				}
				
				if(city.getText().toString() == null || city.getText().toString().length() == 0) {
					AppApplication.getInstance().showMessage(activity, activity.getString(R.string.error), activity.getString(R.string.empty_city), activity.getString(R.string.ok), null, null, false);
					return;
				}
				if(address.getText().toString() == null || address.getText().toString().length() == 0) {
					AppApplication.getInstance().showMessage(activity, activity.getString(R.string.error), activity.getString(R.string.empty_address), activity.getString(R.string.ok), null, null, false);
					return;
				}
			}
		});
		activity.updateView();
		return view;
	}

	
	@Override
	public void updateFragment() {
	}
}