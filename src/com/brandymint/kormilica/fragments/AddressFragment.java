package com.brandymint.kormilica.fragments;

import org.json.JSONException;
import org.json.JSONObject;
import com.brandymint.kormilica.AppApplication;
import com.brandymint.kormilica.CommonActivity;
import com.brandymint.kormilica.R;
import com.brandymint.kormilica.utils.LoadListener;
import com.brandymint.kormilica.utils.SendOrderTask;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressFragment extends CommonFragment implements LoadListener{
	
	private String errorString, phoneString, cityString, addressString, trueMessage;
	private View view;
	private EditText phone, address;
	private TextView lockButton;
	private boolean orderComplite;
	private boolean wrongOrder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.address_fragment, container, false);
		updateFragment();
		return view;
	}
	
	@Override
	public void updateFragment() {
		if(view == null)
			return;
		LinearLayout wrongLayout = (LinearLayout) view.findViewById(R.id.wrong_layout);
		LinearLayout trueLayout = (LinearLayout) view.findViewById(R.id.true_layout);
		if(orderComplite) {
			trueLayout.setVisibility(View.VISIBLE);
			wrongLayout.setVisibility(View.INVISIBLE);
			TextView phone = (TextView) view.findViewById(R.id.phone_text);
			TextView city = (TextView) view.findViewById(R.id.city_text);
			TextView address = (TextView) view.findViewById(R.id.address_text);
			TextView call = (TextView) view.findViewById(R.id.call);
			TextView message = (TextView) view.findViewById(R.id.message);
			TextView returned = (TextView) view.findViewById(R.id.returned);
			TextView phoneNumber = (TextView) view.findViewById(R.id.phone_number);
			
			phoneNumber.setText(AppApplication.getInstance().getVendor().getPhone());
			phone.setText(phoneString);
			city.setText(cityString);
			address.setText(addressString);
			message.setText(trueMessage);
			call.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String number = "tel:" + AppApplication.getInstance().getVendor().getPhone().replaceAll("()-", "").trim();
			        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number)); 
			        startActivity(callIntent);
				}
			});

			returned.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					eventListener.event(CommonActivity.EVENT_RESET_SCREENS, null);
				}
			});
		} else {
			trueLayout.setVisibility(View.INVISIBLE);
			wrongLayout.setVisibility(View.VISIBLE);
			phone = (EditText) view.findViewById(R.id.phone);
			phone.requestFocus();
			InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
			TextView city = (TextView) view.findViewById(R.id.city);
			address = (EditText) view.findViewById(R.id.address);
			TextView button = (TextView) view.findViewById(R.id.button);
			lockButton = (TextView) view.findViewById(R.id.lock_button);
			FrameLayout errorLayout = (FrameLayout) view.findViewById(R.id.error_layout);
			TextView errorText = (TextView) view.findViewById(R.id.error);
			city.setText(AppApplication.getInstance().getVendor().getCity());
			lockButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {		}
			});
	    	phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	    	    @Override
	    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	    	    	if (actionId == EditorInfo.IME_ACTION_NEXT) {
            			checkButtonState();
	            		address.requestFocus();
	        			InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	        			imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	            		return true;
	    	        }
	    	        return false;
	    	    }
	    	});

	    	address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	    	    @Override
	    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	    	    	Log.e("ADDRESS", "event - "+event);
	    	    	Log.e("ADDRESS", "actionId - "+actionId);

	    	    	if (event.getKeyCode() == event.KEYCODE_ENTER) {
            			checkButtonState();
	                	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	            		imm.hideSoftInputFromWindow(phone.getWindowToken(), 0);
		                return true;
	    	        }
	    	        return false;
	    	    }
	    	});

			
			if(wrongOrder) {
			  	LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			  	errorLayout.setLayoutParams(parms);
			  	errorText.setText(errorString);
			} else {
				LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(1,1);
				errorLayout.setLayoutParams(parms);
			}
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(phone.getText().toString() == null || phone.getText().toString().length() == 0) {
						AppApplication.getInstance().showMessage(getActivity(), getActivity().getString(R.string.error), getActivity().getString(R.string.empty_phone), getActivity().getString(R.string.ok), null, null, false);
						return;
					}
					if(address.getText().toString() == null || address.getText().toString().length() == 0) {
						AppApplication.getInstance().showMessage(getActivity(), getActivity().getString(R.string.error), getActivity().getString(R.string.empty_address), getActivity().getString(R.string.ok), null, null, false);
						return;
					}
					addressString = address.getText().toString();
					cityString = AppApplication.getInstance().getVendor().getCity();
					phoneString = phone.getText().toString();
					String [] params = {phoneString, cityString, addressString};
					((CommonActivity)getActivity()).startProgressDialog();
					new SendOrderTask((CommonActivity)getActivity(), AddressFragment.this, AppApplication.getInstance().getOrder()).execute(params);
				}
			});
		}
		eventListener.event(CommonActivity.EVENT_UPDATE_ACTIVITY, null);
		checkButtonState();
	}

	private void checkButtonState() {
		lockButton.setVisibility(View.VISIBLE);
		if((phone.getText().toString() == null || phone.getText().toString().length() == 0) 
				&& (address.getText().toString() == null || address.getText().toString().length() == 0)) 
			lockButton.setText(R.string.button_empty_phone_address);
		else if(phone.getText().toString() == null || phone.getText().toString().length() == 0) 
			lockButton.setText(R.string.button_empty_phone);
		else if(address.getText().toString() == null || address.getText().toString().length() == 0) 
			lockButton.setText(R.string.button_empty_address);
		else
			lockButton.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onLoadComplite(String string) {
		try {
			JSONObject json = new JSONObject(string);
			if(json.has("error")) {
				orderComplite = true;
				wrongOrder = false;
				errorString = json.getString("error"); 
			} else {
				orderComplite = true;
				wrongOrder = false;
				JSONObject obj = json.getJSONObject("message");
				trueMessage = obj.getString("subject")+". "+obj.getString("text");
			}
			updateFragment();
		} catch (JSONException e) {
			Log.e("AddressFragment", "Error parse json: "+e);
		}
		((CommonActivity)getActivity()).stopProgressDialog();
	}
}