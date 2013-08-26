package de.thm.hcia.twofactorlockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class AssistentFinishActivity extends SherlockActivity {

	private Button bttnAbort, bttnTesten;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent__finish_activity);
		
		bttnAbort = (Button) findViewById(R.id.bttnAbort);
		bttnTesten = (Button) findViewById(R.id.bttnTesten);
		
		bttnAbort.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODOs!!!!
				Intent intent = new Intent();
	            intent.setClass(getApplicationContext(), MainActivity.class);
	            intent.putExtra("fragment", MainActivity.MAIN_FRAGMENT);
	            startActivity(intent);
				finish();
			}
		});
	}
}
