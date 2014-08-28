package com.brandymint.kormilica.data;

import org.json.JSONObject;
import android.util.Log;

public class Product {
	
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

	private String id; 
	private String categoryId; 
	private String title; 
	private String updatedAt; 
	private int position; 
	private String priceCents;
	private String priceCurrency;
	private String imageUrl;
	private boolean deleted = false; 

	public Product() {}
	
	public Product(JSONObject jObject) {
		try {
			id = jObject.getString(KEY_ID); 
			categoryId = jObject.getString(KEY_CATEGORY_ID);
			title = jObject.getString(KEY_TITLE);
			updatedAt = jObject.getString(KEY_UPDATED); 
			position = jObject.getInt(KEY_POSITION);
			JSONObject obj1 = jObject.getJSONObject(KEY_PRICE);
			priceCents = obj1.getString(KEY_CENTS); 
			priceCurrency = obj1.getString(KEY_CURRENCY);
			obj1 = jObject.getJSONObject(KEY_IMAGE);
			imageUrl = obj1.getString(KEY_IMAGE_URL);
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse vendor object. "+ex);
		}
	}

	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ id: "+ id +"; "); 
		stb.append("categoryId: "+ categoryId +"; ");
		stb.append("title: "+ title +"; ");
		stb.append("updatedAt: "+ updatedAt +"; ");
		stb.append("position: "+ position +"; ");
		stb.append("priceCents: "+ priceCents +"; ");
		stb.append("priceCurrency: "+ priceCurrency +"; ");
		stb.append("imageUrl: "+ imageUrl +"; ");
		stb.append("deleted: "+ deleted +" }");
		return stb.toString().replaceAll("\n", " ");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getPriceCents() {
		return priceCents;
	}

	public void setPriceCents(String priceCents) {
		this.priceCents = priceCents;
	}

	public String getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}