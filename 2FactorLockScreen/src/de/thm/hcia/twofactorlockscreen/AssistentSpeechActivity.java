package de.thm.hcia.twofactorlockscreen;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AssistentSpeechActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_speech_input_acrtivity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

}
