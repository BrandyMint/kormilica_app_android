package com.brandymint.kormilica.data;

import java.util.HashMap;

import org.json.JSONObject;
import android.util.Log;

public class Vendor extends AbstractData {
	
	private static final String KEY_KEY						 = "key";
	private static final String KEY_NAME					 = "name";
	private static final String KEY_PHONE					 = "phone";
	private static final String KEY_UPDATED					 = "updated_at";
	private static final String KEY_CITY					 = "city";
	private static final String KEY_MOBILE_LOGO				 = "mobile_logo_url";
	private static final String KEY_MOBILE_TITLE			 = "mobile_title";
	private static final String KEY_MOBILE_SUBJECT			 = "mobile_subject";
	private static final String KEY_MOBILE_DESCRIPTION		 = "mobile_description";
	private static final String KEY_MOBILE_FOOTER			 = "mobile_footer";
	private static final String KEY_MOBILE_DELIVERY			 = "mobile_delivery";
	private static final String KEY_MOBILE_EMPTY_CART_ALERT	 = "mobile_empty_cart_alert";
	private static final String KEY_MOBILE_MINIMAL_ALERT	 = "mobile_minimal_alert";
	private static final String KEY_MINIMAL_PRICE			 = "minimal_price";
	private static final String KEY_DELIVERY_PRICE			 = "delivery_price";
	private static final String KEY_CENTS					 = "cents";
	private static final String KEY_CURRENCY				 = "currency";
	private static final String KEY_DEMO					 = "is_demo";

	public static final String [] PARAMS = {KEY_KEY, KEY_NAME, KEY_PHONE, KEY_UPDATED, KEY_CITY, KEY_MOBILE_LOGO, KEY_MOBILE_TITLE, KEY_MOBILE_SUBJECT, KEY_MOBILE_DESCRIPTION, KEY_MOBILE_DELIVERY, KEY_MOBILE_FOOTER, KEY_MOBILE_EMPTY_CART_ALERT, KEY_MOBILE_MINIMAL_ALERT, KEY_CURRENCY, KEY_MINIMAL_PRICE+KEY_CENTS, KEY_MINIMAL_PRICE+KEY_CURRENCY, KEY_DELIVERY_PRICE+KEY_CENTS, KEY_DELIVERY_PRICE+KEY_CURRENCY, KEY_DEMO };
	private HashMap<String, String> map = new HashMap<String, String>();
		
	public Vendor() {}
	
	public Vendor(JSONObject jObject) {
		try {
			map.put(KEY_KEY, jObject.getString(KEY_KEY)); 
			map.put(KEY_NAME, jObject.getString(KEY_NAME));
			map.put(KEY_PHONE, jObject.getString(KEY_PHONE));
			map.put(KEY_UPDATED, jObject.getString(KEY_UPDATED)); 
			map.put(KEY_CITY, jObject.getString(KEY_CITY));
			map.put(KEY_MOBILE_LOGO, jObject.getString(KEY_MOBILE_LOGO));
			map.put(KEY_MOBILE_TITLE, jObject.getString(KEY_MOBILE_TITLE));
			map.put(KEY_MOBILE_SUBJECT, jObject.getString(KEY_MOBILE_SUBJECT));
			map.put(KEY_MOBILE_DESCRIPTION, jObject.getString(KEY_MOBILE_DESCRIPTION));
			map.put(KEY_MOBILE_DELIVERY, jObject.getString(KEY_MOBILE_DELIVERY));
			map.put(KEY_MOBILE_FOOTER, jObject.getString(KEY_MOBILE_FOOTER));
			map.put(KEY_MOBILE_EMPTY_CART_ALERT, jObject.getString(KEY_MOBILE_EMPTY_CART_ALERT)); 
			map.put(KEY_MOBILE_MINIMAL_ALERT, jObject.getString(KEY_MOBILE_MINIMAL_ALERT)); 
			map.put(KEY_CURRENCY, jObject.getString(KEY_CURRENCY)); 
			JSONObject obj1 = jObject.getJSONObject(KEY_MINIMAL_PRICE);
			map.put(KEY_MINIMAL_PRICE+KEY_CENTS, obj1.getString(KEY_CENTS)); 
			map.put(KEY_MINIMAL_PRICE+KEY_CURRENCY, obj1.getString(KEY_CURRENCY));
			obj1 = jObject.getJSONObject(KEY_DELIVERY_PRICE);
			map.put(KEY_DELIVERY_PRICE+KEY_CENTS, obj1.getString(KEY_CENTS)); 
			map.put(KEY_DELIVERY_PRICE+KEY_CURRENCY, obj1.getString(KEY_CURRENCY));
			map.put(KEY_DEMO, ""+jObject.getBoolean(KEY_DEMO));
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse vendor object. "+ex);
		}
	}

	
	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ ");
		for(int i = 0; i < PARAMS.length; i ++)
			stb.append(PARAMS[i]+": "+map.get(PARAMS[i])+"; ");
		stb.append(" }");
		return stb.toString().replaceAll("\n", " ");
	}
	
	
	
	public String getKey() {
		return map.get(KEY_KEY);
	}

	public void setKey(String key) {
		map.put(KEY_KEY, key);
	}

	public String getName() {
		return map.get(KEY_NAME);
	}

	public void setName(String name) {
		map.put(KEY_NAME, name);
	}

	public String getPhone() {
		return map.get(KEY_PHONE);
	}

	public void setPhone(String phone) {
		map.put(KEY_PHONE, phone);
	}

	public String getUpdatedAt() {
		return map.get(KEY_UPDATED);
	}

	public void setUpdatedAt(String updatedAt) {
		map.put(KEY_UPDATED, updatedAt);
	}

	public String getCity() {
		return map.get(KEY_CITY);
	}

	public void setCity(String city) {
		map.put(KEY_CITY, city);
	}

	public String getMobileLogoUrl() {
		return map.get(KEY_MOBILE_LOGO);
	}

	public void setMobileLogoUrl(String mobileLogoUrl) {
		map.put(KEY_MOBILE_LOGO, mobileLogoUrl);
	}

	public String getMobileTitle() {
		return map.get(KEY_MOBILE_TITLE);
	}

	public void setMobileTitle(String mobileTitle) {
		map.put(KEY_MOBILE_TITLE, mobileTitle);
	}

	public String getMobileSubject() {
		return map.get(KEY_MOBILE_SUBJECT);
	}

	public void setMobileSubject(String mobileSubject) {
		map.put(KEY_MOBILE_SUBJECT, mobileSubject);
	}

	public String getMobileDescription() {
		return map.get(KEY_MOBILE_DESCRIPTION);
	}

	public void setMobileDescription(String mobileDescription) {
		map.put(KEY_MOBILE_DESCRIPTION, mobileDescription);
	}

	public String getMobileDelivery() {
		return map.get(KEY_MOBILE_DELIVERY);
	}

	public void setMobileDelivery(String mobileDelivery) {
		map.put(KEY_MOBILE_DELIVERY, mobileDelivery);
	}

	public String getMobileEmptyCartAlert() {
		return map.get(KEY_MOBILE_EMPTY_CART_ALERT);
	}

	public void setMobileEmptyCartAlert(String mobileEmptyCartAlert) {
		map.put(KEY_MOBILE_EMPTY_CART_ALERT, mobileEmptyCartAlert);
	}

	public String getMobileMinimalAlert() {
		return map.get(KEY_MOBILE_MINIMAL_ALERT);
	}

	public void setMobileMinimalAlert(String mobileMinimalAlert) {
		map.put(KEY_MOBILE_MINIMAL_ALERT, mobileMinimalAlert);
	}

	public String getMinimalPriceCents() {
		return map.get(KEY_MINIMAL_PRICE+KEY_CENTS);
	}

	public void setMinimalPriceCents(String minimalPriceCents) {
		map.put(KEY_MINIMAL_PRICE+KEY_CENTS, minimalPriceCents);
	}

	public String getMinimalPriceCurrency() {
		return map.get(KEY_MINIMAL_PRICE+KEY_CURRENCY);
	}

	public void setMinimalPriceCurrency(String minimalPriceCurrency) {
		map.put(KEY_MINIMAL_PRICE+KEY_CURRENCY, minimalPriceCurrency);
	}

	public String getDeliveryPriceCents() {
		return map.get(KEY_DELIVERY_PRICE+KEY_CENTS);
	}

	public void setDeliveryPriceCents(String deliveryPriceCents) {
		map.put(KEY_DELIVERY_PRICE+KEY_CENTS, deliveryPriceCents);
	}

	public String getDeliveryPriceCurrency() {
		return map.get(KEY_DELIVERY_PRICE+KEY_CURRENCY);
	}

	public void setDeliveryPriceCurrency(String deliveryPriceCurrency) {
		map.put(KEY_DELIVERY_PRICE+KEY_CURRENCY, deliveryPriceCurrency);
	}

	public String getCurrency() {
		return map.get(KEY_CURRENCY);
	}

	public void setCurrency(String currency) {
		map.put(KEY_CURRENCY, currency);
	}

	public boolean isDemo() {
		return Boolean.parseBoolean(map.get(KEY_DEMO));
	}

	public void setDemo(boolean demo) {
		map.put(KEY_DEMO, ""+demo);
	}

	public String getMobileFooter() {
		return map.get(KEY_MOBILE_FOOTER);
	}

	public void setMobileFooter(String mobileFooter) {
		map.put(KEY_MOBILE_FOOTER, mobileFooter);
	}
	
	@Override
	public void putData(String key, String value) {
		map.put(key, value);
	}

	@Override
	public String getData(String key) {
		return map.get(key);
	}

}