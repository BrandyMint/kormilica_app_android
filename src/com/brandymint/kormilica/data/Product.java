package com.brandymint.kormilica.data;

import java.util.HashMap;

import org.json.JSONObject;
import android.util.Log;

public class Product extends AbstractData {
	
	private static final String KEY_ID			 = "id";
	private static final String KEY_CATEGORY_ID	 = "category_id";
	private static final String KEY_TITLE		 = "title";
	private static final String KEY_UPDATED		 = "updated_at";
	private static final String KEY_POSITION	 = "position";
	private static final String KEY_CENTS		 = "cents";
	private static final String KEY_CURRENCY	 = "currency";
	private static final String KEY_PRICE		 = "price";
	private static final String KEY_IMAGE		 = "image";
	private static final String KEY_IMAGE_URL	 = "mobile_url";
	private static final String KEY_DELETED		 = "deleted";

	public static final String [] PARAMS = {KEY_ID, KEY_CATEGORY_ID, KEY_TITLE, KEY_UPDATED, KEY_POSITION, KEY_CENTS, KEY_CURRENCY, KEY_IMAGE_URL, KEY_DELETED};
	private HashMap<String, String> map = new HashMap<String, String>();
	private boolean selected = false;

	public Product() {}
	
	public Product(JSONObject jObject) {
		try {
			map.put(KEY_ID, jObject.getString(KEY_ID)); 
			map.put(KEY_CATEGORY_ID, jObject.getString(KEY_CATEGORY_ID));
			map.put(KEY_TITLE, jObject.getString(KEY_TITLE));
			map.put(KEY_UPDATED, jObject.getString(KEY_UPDATED)); 
			map.put(KEY_POSITION, ""+jObject.getInt(KEY_POSITION));
			JSONObject obj1 = jObject.getJSONObject(KEY_PRICE);
			map.put(KEY_CENTS, obj1.getString(KEY_CENTS)); 
			map.put(KEY_CURRENCY, obj1.getString(KEY_CURRENCY));
			try {
				obj1 = jObject.getJSONObject(KEY_IMAGE);
				map.put(KEY_IMAGE_URL, obj1.getString(KEY_IMAGE_URL));
			} catch(Exception e) {
				map.put(KEY_IMAGE_URL, "");
			}
			map.put(KEY_DELETED, ""+false);
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse product object. "+ex);
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return map.get(KEY_ID);
	}

	public void setId(String id) {
		map.put(KEY_ID, ""+id);
	}

	public String getCategoryId() {
		return map.get(KEY_CATEGORY_ID);
	}

	public void setCategoryId(String categoryId) {
		map.put(KEY_CATEGORY_ID, ""+categoryId);
	}

	public String getTitle() {
		return map.get(KEY_TITLE);
	}

	public void setTitle(String title) {
		map.put(KEY_TITLE, ""+title);
	}

	public String getUpdatedAt() {
		return map.get(KEY_UPDATED);
	}

	public void setUpdatedAt(String updatedAt) {
		map.put(KEY_UPDATED, ""+updatedAt);
	}

	public int getPosition() {
		return Integer.parseInt(map.get(KEY_POSITION));
	}

	public void setPosition(int position) {
		map.put(KEY_POSITION, ""+position);
	}

	public String getPriceCents() {
		return map.get(KEY_CENTS);
	}

	public void setPriceCents(String priceCents) {
		map.put(KEY_CENTS, priceCents);
	}

	public String getPriceCurrency() {
		return map.get(KEY_CURRENCY);
	}

	public void setPriceCurrency(String priceCurrency) {
		map.put(KEY_CURRENCY, priceCurrency);
	}

	public String getImageUrl() {
		return map.get(KEY_IMAGE_URL);
	}

	public void setImageUrl(String imageUrl) {
		map.put(KEY_IMAGE_URL, imageUrl);
	}

	public boolean isDeleted() {
		return Boolean.parseBoolean(map.get(KEY_DELETED));
	}

	public void setDeleted(boolean deleted) {
		map.put(KEY_DELETED, ""+deleted);
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