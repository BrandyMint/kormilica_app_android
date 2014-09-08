package com.brandymint.kormilica.data;

import java.util.ArrayList;

public class CategoryList {
	
	private String title;
	private ArrayList<Product> list;
	
	public CategoryList() {
		list = new ArrayList<Product>();
	}

	public CategoryList(String title) {
		list = new ArrayList<Product>();
		this.title = title;
	}

	public CategoryList(String title, ArrayList<Product> list) {
		this.list = list;
		this.title = title;
	}
	
	public void addItem(Product cat) {
		list.add(cat);
	}
	
	public ArrayList<Product> getList() {
		return list;
	}

	public String toString() {
		StringBuffer stb = new StringBuffer();
		stb.append("{ ");
		stb.append("title: "+title);
		for(int i = 0; i < list.size(); i ++)
			stb.append("item : "+i+" - "+list.get(i).toString()+"\n");
		stb.append(" }");
		return stb.toString();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}