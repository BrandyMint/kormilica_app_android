package com.brandymint.kormilica.db;

import java.util.ArrayList;
import com.brandymint.kormilica.data.AbstractData;
import com.brandymint.kormilica.data.Category;
import com.brandymint.kormilica.data.Order;
import com.brandymint.kormilica.data.Product;
import com.brandymint.kormilica.data.Vendor;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	public DBHelper(Context context) {
      super(context, "kormilicaDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.e(TAG, "--- onCreate database ---");
      
    	Log.e(TAG, "--- onCreate table categories ---");
    	String par = "create table categories ("
                + "idNative integer primary key autoincrement,";
      	for(int i = 0; i < Category.PARAMS.length; i ++) {
      		if(i == Category.PARAMS.length -1) 
      			par += (Category.PARAMS[i] +" text );");
      		else
      			par += (Category.PARAMS[i] +" text,");
      	}
  		db.execSQL( par);

    	Log.e(TAG, "--- onCreate table products ---");
    	par = "create table products ("
              + "idNative integer primary key autoincrement,";
    	for(int i = 0; i < Product.PARAMS.length; i ++) {
      		if(i == Product.PARAMS.length -1) 
      			par += (Product.PARAMS[i] +" text );");
      		else
      			par += (Product.PARAMS[i] +" text,");
    	}
		db.execSQL( par);

    	Log.e(TAG, "--- onCreate table vendors ---");
    	par = "create table vendors ("
              + "idNative integer primary key autoincrement,";
    	for(int i = 0; i < Vendor.PARAMS.length; i ++) {
      		if(i == Vendor.PARAMS.length -1) 
      			par += (Vendor.PARAMS[i] +" text );");
      		else
      			par += (Vendor.PARAMS[i] +" text,");
    	}
		db.execSQL( par);

    
/*		db.execSQL("create table order ("
				+ "id integer primary key autoincrement," 
		        + "productId text");
		        */
    }

    public void updateTable(String tableName, ArrayList<AbstractData> list, String [] params) {
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+ tableName);
		for(AbstractData item : list) {
			if(item instanceof Product)
			Log.e(TAG, "Write table: "+((Product)item).toString());
			for(int i = 0; i < params.length; i ++) {
				if(item.getData(params[i]) != null) {
					
					cv.put(params[i], item.getData(params[i]));
				}
			}
			db.insert(tableName, null, cv);
		}
    	db.close();
		close();
    }
    
    public void updateCategoryTable(ArrayList<AbstractData> list) {
    	updateTable("categories", list, Category.PARAMS);
    }
    
    public void updateProductTable(ArrayList<AbstractData> list) {
    	updateTable("products", list, Product.PARAMS);
    }

    public void updateVendorTable(ArrayList<AbstractData> list) {
    	updateTable("vendors", list, Vendor.PARAMS);
    }
    
    public ArrayList<Category> readCategoryTable() {
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<Category> list = new ArrayList<Category>();
		Log.e(TAG, "--- Rows in table categories: ---");
		Cursor cursor = db.query("categories", null, null, null, null, null, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	Category cat = new Category();
	        	for(int i = 0; i < Category.PARAMS.length; i ++)
	        		cat.putData(Category.PARAMS[i], cursor.getString(cursor.getColumnIndex(Category.PARAMS[i])));
	        	list.add(cat);
	        } while (cursor.moveToNext());
	    }
		cursor.close();	
		db.close();
		return list;
    }

    public ArrayList<Product> readProductTable() {
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<Product> list = new ArrayList<Product>();
		Log.e(TAG, "--- Rows in table products: ---");
		Cursor cursor = db.query("products", null, null, null, null, null, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	Product cat = new Product();
	        	for(int i = 0; i < Product.PARAMS.length; i ++)
	        		cat.putData(Product.PARAMS[i], cursor.getString(cursor.getColumnIndex(Product.PARAMS[i])));
				list.add(cat);
	        } while (cursor.moveToNext());
	    }
		cursor.close();	
		db.close();
		return list;
    }
    
    public ArrayList<Vendor> readVendorTable() {
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<Vendor> list = new ArrayList<Vendor>();
		Log.e(TAG, "--- Rows in table vendor: ---");
		Cursor cursor = db.query("vendors", null, null, null, null, null, null);
	    if (cursor.moveToFirst()) {
	    	do {
	    		Vendor cat = new Vendor();
	    		for(int i = 0; i < Vendor.PARAMS.length; i ++)
	    			cat.putData(Vendor.PARAMS[i], cursor.getString(cursor.getColumnIndex(Vendor.PARAMS[i])));
	    		list.add(cat);
	        } while (cursor.moveToNext());
    	}
		cursor.close();	
		db.close();
		return list;
    }
   
    public Order readOrderTable() {
		SQLiteDatabase db = getWritableDatabase();
		Order order = null;
		Log.e(TAG, "--- Rows in table order: ---");
		Cursor cursor = db.query("order", null, null, null, null, null, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	order = new Order(cursor.getString(cursor.getColumnIndex("productId")));
	        } while (cursor.moveToNext());
	    }
		cursor.close();	
		db.close();
		return order;
    }
    
    public void writeOrder(Order order) {
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from order");
		cv.put("productId", order.getProductsAsString());
    	db.insert("order", null, cv);
    	db.close();
		close();
    }

    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {   }
}
