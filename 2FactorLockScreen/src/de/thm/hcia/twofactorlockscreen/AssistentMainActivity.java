package de.thm.hcia.twofactorlockscreen;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AssistentMainActivity extends SherlockActivity {

	private static Button mBtnCancel;
	private static Button mBtnNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		setContentView(R.layout.assistent_main_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mBtnCancel = (Button) findViewById(R.id.btn_cancel);
		mBtnNext = (Button) findViewById(R.id.btn_next);
		
		setOnClickListeners();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * sets all required onClickListeners
	 */
	private void setOnClickListeners() {
		mBtnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
}
