package com.brandymint.kormilica.utils;

import com.brandymint.kormilica.data.AbstractData;

public interface EventListener {
	public void event(int event, AbstractData data);
}