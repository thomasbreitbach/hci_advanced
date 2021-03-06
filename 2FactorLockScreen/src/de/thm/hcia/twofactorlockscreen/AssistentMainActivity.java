package de.thm.hcia.twofactorlockscreen;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class AssistentMainActivity extends SherlockActivity {
	
	private static final String TAG = "AssistentMainActivity";
	
	private Context mContext;
	private Button mBtnCancel;
	private Button mBtnNext;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.main_assistent_headline);
		setContentView(R.layout.assistent_main_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mBtnCancel 	= (Button) findViewById(R.id.btn_cancel);
		mBtnNext 	= (Button) findViewById(R.id.btn_next);
				
		mContext = this;
		
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Log.i(TAG, "onActivityResult");
		switch(requestCode) 
		{
			/*
			 * Speichern der Pattern in "prefs"
			 */
		    case MainActivity.REQ_CODE_CREATE_PATTERN:
		        if(resultCode == RESULT_OK){
		            
		            /*
		    		 * Pattern wird über die Bibliothek automatisch gespeichert
		    		 */
		            Toast.makeText(mContext, R.string.pattern_recorded, Toast.LENGTH_LONG).show(); 
		            
		            //start speech input activity
					Intent aIntent = new Intent();
					aIntent.setClass(getBaseContext(), AssistentSpeechActivity.class);
		            startActivity(aIntent);
		            finish();
		        }else{
		        	//TODO
		        	//TOAST Fehlerausgabe
		        }
		        break;
		}
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
		
		mBtnNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
//				Intent aIntent = new Intent();
//				aIntent.setClass(getBaseContext(), AssistentSpeechActivity.class);
//	            startActivity(aIntent);
	            
	            Intent lpIntent = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null, mContext, LockPatternActivity.class);
				startActivityForResult(lpIntent, MainActivity.REQ_CODE_CREATE_PATTERN);
			}
		});

	}
	
	
}
