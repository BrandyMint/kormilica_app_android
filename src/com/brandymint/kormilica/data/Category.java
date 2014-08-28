package com.brandymint.kormilica.data;

import org.json.JSONObject;
import android.util.Log;

public class Category {
	
	private static final String KEY_ID		 = "id";
	private static final String KEY_NAME	 = "name";
	private static final String KEY_UPDATED	 = "updated_at";
	private static final String KEY_POSITION = "position";

	private String id; 
	private String name; 
	private String updatedAt; 
	private int position; 
	private boolean deleted = false; 

	public Category() {}
	
	public Category(JSONObject jObject) {
		try {
			id = jObject.getString(KEY_ID); 
			name = jObject.getString(KEY_NAME);
			updatedAt = jObject.getString(KEY_UPDATED); 
			position = jObject.getInt(KEY_POSITION);
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse vendor object. "+ex);
		}
	}

	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ id: "+ id +"; "); 
		stb.append("name: "+ name +"; ");
		stb.append("updatedAt: "+ updatedAt +"; ");
		stb.append("position: "+ position +"; ");
		stb.append("deleted: "+ deleted +" }");
		return stb.toString().replaceAll("\n", " ");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}