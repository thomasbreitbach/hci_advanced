package de.thm.hcia.twofactorlockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class AssistentFinishActivity extends SherlockActivity {

	private Button bttnAbort, bttnTesten;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_finish_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		bttnAbort = (Button) findViewById(R.id.bttnAbort);
		bttnTesten = (Button) findViewById(R.id.bttnTesten);
		
		bttnAbort.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.MAIN_FRAGMENT);
	            startActivity(intent);
				finish();
			}
		});
		bttnTesten.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.PROTOTYPE_FRAGMENT);
	            startActivity(intent);
				finish();				
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
