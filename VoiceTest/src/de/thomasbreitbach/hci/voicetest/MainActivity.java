package de.thomasbreitbach.hci.voicetest;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends Activity {
	
	private static final String TAG = "voicetest.MainActivity";
	private static final boolean DEBUG = true;
	private static final int REQUEST_CODE = 1234;
	
	private ArrayList<String> matches;
	private SpeechListAdapter adapter;
	private ListView wordsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
		btnSpeak.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});
		
		wordsList = (ListView) findViewById(R.id.lvWordList);
		
		// Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            btnSpeak.setEnabled(false);
            btnSpeak.setText("Recognizer not present");
            Toast.makeText(this, R.string.no_speech, Toast.LENGTH_SHORT).show();
        }else{
        	matches = new ArrayList<String>();
        	adapter = new SpeechListAdapter(this, matches);
            wordsList.setAdapter(adapter);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);    
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.voice_promt);
        
        startActivityForResult(intent, REQUEST_CODE);
    }

    
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {  	
            // Populate the wordsList with the String values the recognition engine thought it heard
            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(DEBUG) Log.d(TAG, matches.toString());       
            adapter.setMatches(matches);
            wordsList.invalidateViews(); //rebuild ListView
        }
        
        if(requestCode == REQUEST_CODE && resultCode == RecognizerIntent.RESULT_NO_MATCH){
        	Toast.makeText(this, R.string.no_match, Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
