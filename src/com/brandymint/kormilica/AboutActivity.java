package com.brandymint.kormilica;

import com.brandymint.kormilica.utils.GetDataTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        final AppApplication app = AppApplication.getInstance();
        TextView title = (TextView) findViewById(R.id.title);
        TextView text = (TextView) findViewById(R.id.text);
        TextView dop = (TextView) findViewById(R.id.dop);
        TextView update = (TextView) findViewById(R.id.update);
        
        title.setText(app.getVendor().getMobileSubject());
        text.setText(app.getVendor().getMobileDescription());
        String dopString = "vendor key - "+ app.getVendor().getKey()+"\n"
        		+"minimal price - "+app.getVendor().getMinimalPriceCents()+"\n"
        		+"delivery price - "+app.getVendor().getDeliveryPriceCents()+"\n";
        
        dop.setText(dopString);
        
        update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(new GetDataTask(app.getActivity(), app.getActivity(), false)).execute();
			}
		});
	}
}
