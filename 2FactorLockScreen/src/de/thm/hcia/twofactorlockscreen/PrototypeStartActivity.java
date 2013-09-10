package de.thm.hcia.twofactorlockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class PrototypeStartActivity extends SherlockActivity {
	private Button nextTest;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		setContentView(R.layout.prototype_start_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		nextTest = (Button) findViewById(R.id.bttnPtest);
		nextTest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(getBaseContext(), PrototypeTestActivity.class);
	            startActivity(intent);
				
			}
		});
		
	}
}
