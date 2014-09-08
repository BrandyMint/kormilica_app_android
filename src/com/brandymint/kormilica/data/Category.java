package com.brandymint.kormilica.data;

import java.util.HashMap;
import org.json.JSONObject;
import android.util.Log;

public class Category extends AbstractData {
	
	private static final String KEY_ID		 = "id";
	private static final String KEY_NAME	 = "name";
	private static final String KEY_UPDATED	 = "updated_at";
	private static final String KEY_POSITION = "position";
	private static final String KEY_DELETED	 = "deleted";
	public static final String [] PARAMS = {KEY_ID, KEY_NAME, KEY_UPDATED, KEY_POSITION, KEY_DELETED};
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public Category() {}
	
	public Category(JSONObject jObject) {
		try {
			map.put(KEY_ID, jObject.getString(KEY_ID)); 
			map.put(KEY_NAME, jObject.getString(KEY_NAME));
			map.put(KEY_UPDATED, jObject.getString(KEY_UPDATED)); 
			map.put(KEY_POSITION, ""+jObject.getInt(KEY_POSITION));
			map.put(KEY_POSITION, ""+jObject.getInt(KEY_POSITION));
			map.put(KEY_DELETED, ""+false);
		} catch(Exception ex) {
			Log.e("VENDOR", "Error parse vendor object. "+ex);
		}
	}
	
	@Override
	public void putData(String key, String value) {
		map.put(key, value);
	}

	@Override
	public String getData(String key) {
		return map.get(key);
	}

	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ ");
		for(int i = 0; i < PARAMS.length; i ++)
			stb.append(PARAMS[i]+": "+map.get(PARAMS[i])+"; ");
		stb.append(" }");
		return stb.toString().replaceAll("\n", " ");
	}

	public String getId() {
		return map.get(KEY_ID);
	}

	public void setId(String id) {
		map.put(KEY_ID, id);
	}

	public String getName() {
		return map.get(KEY_NAME);
	}

	public void setName(String name) {
		map.put(KEY_NAME, name);
	}

	public String getUpdatedAt() {
		return map.get(KEY_UPDATED);
	}

	public void setUpdatedAt(String updatedAt) {
		map.put(KEY_UPDATED, updatedAt);
	}

	public int getPosition() {
		return Integer.parseInt(map.get(KEY_POSITION));
	}

	public void setPosition(int position) {
		map.put(KEY_POSITION, ""+position);
	}

	public boolean isDeleted() {
		return Boolean.parseBoolean(map.get(KEY_DELETED));
	}

	public void setDeleted(boolean deleted) {
		map.put(KEY_DELETED, ""+deleted);
	}
}