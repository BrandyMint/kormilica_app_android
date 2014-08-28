package com.brandymint.kormilica.data;

import org.json.JSONObject;
import android.util.Log;

public class Vendor {
	
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
	
	private String key; 
	private String name;
	private String phone;
	private String updatedAt; 
	private String city;
	private String mobileLogoUrl;
	private String mobileTitle;
	private String mobileSubject;
	private String mobileDescription;
	private String mobileDelivery; 
	private String mobileEmptyCartAlert; 
	private String mobileMinimalAlert; 
	private String minimalPriceCents; 
	private String minimalPriceCurrency;
	private String deliveryPriceCents; 
	private String deliveryPriceCurrency;
	private String currency; 
	private boolean demo; 
	
	public Vendor() {}
	
	public Vendor(JSONObject jObject) {
		try {
			key = jObject.getString(KEY_KEY); 
			name = jObject.getString(KEY_NAME);
			phone = jObject.getString(KEY_PHONE);
			updatedAt = jObject.getString(KEY_UPDATED); 
			city = jObject.getString(KEY_CITY);
			mobileLogoUrl = jObject.getString(KEY_MOBILE_LOGO);
			mobileTitle = jObject.getString(KEY_MOBILE_TITLE);
			mobileSubject = jObject.getString(KEY_MOBILE_SUBJECT);
			mobileDescription = jObject.getString(KEY_MOBILE_DESCRIPTION);
			mobileDelivery = jObject.getString(KEY_MOBILE_DELIVERY); 
			mobileEmptyCartAlert = jObject.getString(KEY_MOBILE_EMPTY_CART_ALERT); 
			mobileMinimalAlert = jObject.getString(KEY_MOBILE_MINIMAL_ALERT); 
			currency = jObject.getString(KEY_CURRENCY); 
			JSONObject obj1 = jObject.getJSONObject(KEY_MINIMAL_PRICE);
			minimalPriceCents = obj1.getString(KEY_CENTS); 
			minimalPriceCurrency = obj1.getString(KEY_CURRENCY);
			obj1 = jObject.getJSONObject(KEY_DELIVERY_PRICE);
			deliveryPriceCents = obj1.getString(KEY_CENTS); 
			deliveryPriceCurrency = obj1.getString(KEY_CURRENCY);
			demo = jObject.getBoolean(KEY_DEMO);
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse vendor object. "+ex);
		}
	}

	
	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ key: "+ key +"; "); 
		stb.append("name: "+ name +"; ");
		stb.append("phone: "+ phone +"; ");
		stb.append("updatedAt: "+ updatedAt +"; ");
		stb.append("city: "+ city +"; ");
		stb.append("mobileLogoUrl: "+ mobileLogoUrl +"; ");
		stb.append("mobileTitle: "+ mobileTitle +"; ");
		stb.append("mobileSubject: "+ mobileSubject +"; ");
		stb.append("mobileDescription: "+ mobileDescription +"; ");
		stb.append("mobileDelivery: "+ mobileDelivery +"; ");
		stb.append("mobileEmptyCartAlert: "+ mobileEmptyCartAlert +"; "); 
		stb.append("mobileMinimalAlert: "+ mobileMinimalAlert +"; ");
		stb.append("minimalPriceCents: "+ minimalPriceCents +"; ");
		stb.append("minimalPriceCurrency: "+ minimalPriceCurrency +"; ");
		stb.append("deliveryPriceCents: "+ deliveryPriceCents +"; ");
		stb.append("deliveryPriceCurrency: "+ deliveryPriceCurrency +"; ");
		stb.append("currency: "+ currency +"; ");
		stb.append("demo: "+ demo +" }");
		
		return stb.toString().replaceAll("\n", " ");
	}
	
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMobileLogoUrl() {
		return mobileLogoUrl;
	}

	public void setMobileLogoUrl(String mobileLogoUrl) {
		this.mobileLogoUrl = mobileLogoUrl;
	}

	public String getMobileTitle() {
		return mobileTitle;
	}

	public void setMobileTitle(String mobileTitle) {
		this.mobileTitle = mobileTitle;
	}

	public String getMobileSubject() {
		return mobileSubject;
	}

	public void setMobileSubject(String mobileSubject) {
		this.mobileSubject = mobileSubject;
	}

	public String getMobileDescription() {
		return mobileDescription;
	}

	public void setMobileDescription(String mobileDescription) {
		this.mobileDescription = mobileDescription;
	}

	public String getMobileDelivery() {
		return mobileDelivery;
	}

	public void setMobileDelivery(String mobileDelivery) {
		this.mobileDelivery = mobileDelivery;
	}

	public String getMobileEmptyCartAlert() {
		return mobileEmptyCartAlert;
	}

	public void setMobileEmptyCartAlert(String mobileEmptyCartAlert) {
		this.mobileEmptyCartAlert = mobileEmptyCartAlert;
	}

	public String getMobileMinimalAlert() {
		return mobileMinimalAlert;
	}

	public void setMobileMinimalAlert(String mobileMinimalAlert) {
		this.mobileMinimalAlert = mobileMinimalAlert;
	}

	public String getMinimalPriceCents() {
		return minimalPriceCents;
	}

	public void setMinimalPriceCents(String minimalPriceCents) {
		this.minimalPriceCents = minimalPriceCents;
	}

	public String getMinimalPriceCurrency() {
		return minimalPriceCurrency;
	}

	public void setMinimalPriceCurrency(String minimalPriceCurrency) {
		this.minimalPriceCurrency = minimalPriceCurrency;
	}

	public String getDeliveryPriceCents() {
		return deliveryPriceCents;
	}

	public void setDeliveryPriceCents(String deliveryPriceCents) {
		this.deliveryPriceCents = deliveryPriceCents;
	}

	public String getDeliveryPriceCurrency() {
		return deliveryPriceCurrency;
	}

	public void setDeliveryPriceCurrency(String deliveryPriceCurrency) {
		this.deliveryPriceCurrency = deliveryPriceCurrency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isDemo() {
		return demo;
	}

	public void setDemo(boolean demo) {
		this.demo = demo;
	}
}