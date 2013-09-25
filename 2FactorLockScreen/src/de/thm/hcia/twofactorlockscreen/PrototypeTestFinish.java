package de.thm.hcia.twofactorlockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

import de.thm.hcia.twofactorlockscreen.fragments.EvaluationFragment;

public class PrototypeTestFinish extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prototype_test_finish);
		
		Button eval = (Button) findViewById(R.id.bttnEval);
		Button abort = (Button) findViewById(R.id.bttnAbort);
		
		eval.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.EVALUATION_FRAGMENT);
	            startActivity(intent);
				finish();
			}
		});
		
		abort.setOnClickListener(new OnClickListener() {
			
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
