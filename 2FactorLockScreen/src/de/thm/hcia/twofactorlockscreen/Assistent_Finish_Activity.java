package de.thm.hcia.twofactorlockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Assistent_Finish_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent__finish_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assistent__finish_, menu);
		return true;
	}

}
