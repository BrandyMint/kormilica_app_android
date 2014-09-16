package com.brandymint.kormilica.data;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductData {

	private static ProductData instance;
	private ArrayList<Category> categoryList;
	private ArrayList<CategoryList> productList;
	private HashMap<String, Product> productMap;
	
	public static ProductData getInstance() {
		if(instance == null)
			instance = new ProductData();
		return instance;
	}
	
	private ProductData() {
		instance = this;
		categoryList = new ArrayList<Category>();
		productList = new ArrayList<CategoryList>();
		productMap = new HashMap<String, Product>();
	}
	
	public void resetSelectedProducts() {
		for(CategoryList cat: productList)
			for(Product prod: cat.getList())
				prod.setSelected(false);
	}
	
	public ArrayList<CategoryList> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<CategoryList> productList) {
		this.productList = productList;
		productMap = new HashMap<String, Product>();
		for(CategoryList list: productList) {
			for(Product prod: list.getList()) {
				productMap.put(prod.getId(), prod);
			}
		}
	}

	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public HashMap<String, Product> getProductMap() {
		return productMap;
	}

	public void setProductMap(HashMap<String, Product> productMap) {
		this.productMap = productMap;
	}
}