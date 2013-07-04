package de.thm.hcia.twofactorlockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AssistentMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_main_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assistent_main, menu);
		return true;
	}

}
