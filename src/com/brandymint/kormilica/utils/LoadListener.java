package com.brandymint.kormilica.utils;

import java.util.ArrayList;
import com.brandymint.kormilica.data.Product;


public interface LoadListener {
	public void onLoadComplite(ArrayList<Product> list);
}