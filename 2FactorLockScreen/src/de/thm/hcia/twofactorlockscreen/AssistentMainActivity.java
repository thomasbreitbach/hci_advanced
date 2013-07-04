package de.thm.hcia.twofactorlockscreen;

import com.actionbarsherlock.view.MenuItem;

import de.thm.hcia.twofactorlockscreen.fragments.AboutFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.widget.Toast;


public class AssistentMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		setContentView(R.layout.assistent_main_activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assistent_main, menu);
		return true;
	}
	
	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_abort:
	        	Toast.makeText(getApplicationContext(), "TEST", 1000);
	            return true;
	        default:
	            return false;
	    }
	}*/
	
}
