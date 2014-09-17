package com.brandymint.kormilica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.brandymint.kormilica.utils.BitmapCache;

public class PhotoActivity extends Activity {

	private String photoUrl;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.photo_activity);
		Intent intent = getIntent();
		if(intent.hasExtra("PHOTO_URL"))
			photoUrl = intent.getStringExtra("PHOTO_URL");
		ImageView photo = (ImageView) findViewById(R.id.photo);
		if(photo != null) {
				photo.setTag(photoUrl);
				BitmapCache.getInstance().loadImage(photo, false);
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case android.R.id.home:
				finish();
				break;
		}
		return true;
	}    
	
}