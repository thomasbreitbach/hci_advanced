package de.thomasbreitbach.hci.lockscreentest;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	// This is your preferred flag
	private static final int REQ_CODE_CREATE_PATTERN = 1;
	private static final int REQ_CODE_COMPARE_PATTERN = 2;
	private static final String TAG = "LockScreenTest.MainActivity";
	private static Context mContext;
	private static char[] savedPattern = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;
		
		/*
		 * CREATE Button
		 */
		Button btnCreatePattern = (Button) findViewById(R.id.btnCreatePattern);
		btnCreatePattern.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null, mContext, LockPatternActivity.class);
				startActivityForResult(intent, REQ_CODE_CREATE_PATTERN);
			}
		});
		
		/*
		 * COMPARE Button
		 */
		Button btnComparePattern = (Button) findViewById(R.id.btnComparePattern);
		btnComparePattern.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(savedPattern==null){
					Toast.makeText(mContext, R.string.first_create_pattern, Toast.LENGTH_LONG).show();
				}else{
					Intent intent = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN, null, mContext, LockPatternActivity.class);
					intent.putExtra(LockPatternActivity.EXTRA_PATTERN, savedPattern);
					startActivityForResult(intent, REQ_CODE_COMPARE_PATTERN);
				}		
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(requestCode) {
	    case REQ_CODE_CREATE_PATTERN:
	        if(resultCode == RESULT_OK){
	            savedPattern = data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);
	            Log.d(TAG, savedPattern.toString());
	            Toast.makeText(mContext, R.string.pattern_set, Toast.LENGTH_LONG).show();
	        }
	        break;
	    case REQ_CODE_COMPARE_PATTERN:
	    	/*
	         * NOTE that there are 3 possible result codes!!!
	         */
	        switch(resultCode) {
	        case RESULT_OK:
	            // The user passed
	        	Toast.makeText(mContext, R.string.unlocked, Toast.LENGTH_LONG).show();
	            break;
	        case RESULT_CANCELED:
	            // The user cancelled the task
	            break;
	        case LockPatternActivity.RESULT_FAILED:
	            // The user failed to enter the pattern
	            break;
	        }

	        /*
	         * In any case, there's always a key EXTRA_RETRY_COUNT, which holds
	         * the number of tries that the user did.
	         */
	        int retryCount = data.getIntExtra(LockPatternActivity.EXTRA_RETRY_COUNT, 0);

	        break;
	    }
	}
}
