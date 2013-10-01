package de.thm.hcia.twofactorlockscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import de.thm.hcia.twofactorlockscreen.fragments.EvaluationFragment;
import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;

public class PrototypeTestFinish extends SherlockActivity {

	private final String TAG = "PrototypeTestFinish";
	private Context mContext;
	
	//UI
	private Button mEval;
	private Button mAbort;
	private TextView mSuccessful;
	private TextView mFail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prototype_test_finish);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mContext = this;
		
		SharedPreferenceIO spIO = new SharedPreferenceIO(mContext);
		
		mEval = (Button) findViewById(R.id.bttnEval);
		mAbort = (Button) findViewById(R.id.bttnAbort);
		mSuccessful = (TextView) findViewById(R.id.tv_logins_successfull_count);
		mFail = (TextView) findViewById(R.id.tv_logins_failed_count);
		
		//set counters
		mSuccessful.setText(String.valueOf(spIO.getLoginsSuccessful()));	
		mFail.setText(String.valueOf(spIO.getLoginsFailed()));
		
		setUpOnClickListeners();
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
	 * defines onclicklisteners
	 */
	private void setUpOnClickListeners() {
		mEval.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.EVALUATION_FRAGMENT);
	            startActivity(intent);
				finish();
			}
		});
		
		mAbort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.MAIN_FRAGMENT);
	            startActivity(intent);
				finish();
			}
		});
	}

}
