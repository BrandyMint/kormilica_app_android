package com.brandymint.kormilica;

import com.brandymint.kormilica.utils.GetDataTask;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) findViewById(R.id.title);
        TextView text = (TextView) findViewById(R.id.text);
        TextView dop = (TextView) findViewById(R.id.dop);
        TextView update = (TextView) findViewById(R.id.update);
        title.setText(AppApplication.getInstance().getVendor().getMobileSubject());
        text.setText(Html.fromHtml(AppApplication.getInstance().getVendor().getMobileDescription()));
        String dopString = "vendor key - "+ AppApplication.getInstance().getVendor().getKey()+"\n"
        		+"minimal price - "+AppApplication.getInstance().getVendor().getMinimalPriceCents()+"\n"
        		+"delivery price - "+AppApplication.getInstance().getVendor().getDeliveryPriceCents()+"\n";
        dop.setText(dopString);
        update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(new GetDataTask()).execute();
				finish();
			}
		});
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
