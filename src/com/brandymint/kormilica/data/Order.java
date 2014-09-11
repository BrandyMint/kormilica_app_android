package com.brandymint.kormilica.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;


public class Order {
	
	private ArrayList<Product> listProducts = new ArrayList<Product>();
	private HashMap<String, String> mapCounts = new HashMap<String, String>();
	private int summ;
	
	public Order() {	}

	public Order(String products) {
		//String myString = "[ { \"1\":33 }, { \"2\":30 }, { \"3\":15 }, { \"4\":23 }, { \"9\":17 }, { \"U\":2 }, { \"V\":22 }, { \"W\":1 }, { \"X\":35 }, { \"Y\":6 }, { \"Z\":19 } ]";
		//HashMap<String, Integer> map = convertToHashMap(myString);
	}
	
	public String getProductsAsString() {
		return null;
	}
	
	public void addProduct(Product product) {
		Log.e("ORDER", "Add product to order:  "+ product);
		listProducts.add(product);
		mapCounts.put(product.getId(), ""+1);
		calculateSumm();
	}

	public void removeProduct(Product product) {
		Log.e("ORDER", "Add product to order:  "+ product);
		listProducts.remove(product);
		mapCounts.remove(product.getId());
		calculateSumm();
	}
	
	public void changeCount(Product product, int count) {
		mapCounts.put(product.getId(), ""+count);
		calculateSumm();
	}
	
	public int getCountOfProduct(String id) {
		try{
			return Integer.parseInt(mapCounts.get(id));
		} catch(Exception ex) {
			return 0;
		}
	}
	
	public ArrayList<Product> getProductList() {
		return listProducts;
	}
	
	private void calculateSumm() {
		summ = 0;
		Log.e("ORDER", "listProducts1 -- "+listProducts.size());
		for(Product prod: listProducts) {
			if(!prod.getId().equals("temp"))
				summ += (Integer.parseInt(prod.getPriceCents()) * Integer.parseInt(mapCounts.get(prod.getId())));
		}
		Log.e("ORDER", "listProducts2  -- "+listProducts.size());
	}
	
	public int getSummOrder() {
		return summ;
	}
}
