package de.thm.hcia.twofactorlockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PrototypeStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prototype_start);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.prototype_start, menu);
		return true;
	}

}
