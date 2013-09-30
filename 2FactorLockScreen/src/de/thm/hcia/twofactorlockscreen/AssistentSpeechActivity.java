package de.thm.hcia.twofactorlockscreen;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;
import de.thm.hcia.twofactorlockscreen.network.NetInfo;

public class AssistentSpeechActivity extends SherlockActivity implements OnClickListener {
	private static final String TAG = "AssistentSpeechActivity";

	private SpeechRecognizer 	sr;
	private Button 				bttnNext, bttnAbord;
	private ImageButton 		iBttnRecord;
	private ProgressBar			mDbBar;
	private boolean				mIsRecording = false;
	private boolean				isSetResult = false;	
	private Context 			mContext;
	private TextView 			txtResult;
	private Intent 				recordingIntent;
	private int 				mChoicePosition;
	private SharedPreferenceIO	sIo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_speech_input_activity);
		setTitle(R.string.main_assistent_headline);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		sr = SpeechRecognizer.createSpeechRecognizer(this);
		sr.setRecognitionListener(new RecordListener());

		mContext = this;
		sIo = new SharedPreferenceIO(mContext);
		
		bttnNext = (Button) findViewById(R.id.bttnSpeechNext);
		bttnAbord = (Button) findViewById(R.id.bttnSpeechAbort);
		iBttnRecord = (ImageButton) findViewById(R.id.iBttnRecord);

		iBttnRecord.setOnClickListener(this);
		bttnNext.setOnClickListener(this);
		bttnAbord.setOnClickListener(this);
		
		mDbBar = (ProgressBar) findViewById(R.id.dbProgressBar);
		mDbBar.setMax(10);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(View v){
		if (v.getId() == R.id.iBttnRecord){
			if(!mIsRecording){
				if(NetInfo.inetAvailable(mContext)){
					//inet available -> start speech recognition
					mIsRecording = true;
					iBttnRecord.setBackgroundColor(Color.RED);
					recordingIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					recordingIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);	
					recordingIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(2000));
					recordingIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
					recordingIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
			
					sr.startListening(recordingIntent);
				}else{
					//no inet available -> required!
					Toast.makeText(mContext, R.string.no_inet_connection, Toast.LENGTH_LONG).show();
				}			
			}else{
				//cancel recording intent
				sr.stopListening();
			}
		}
		if (v.getId() == R.id.bttnSpeechAbort) {
			//cancel recognition
			iBttnRecord.setBackgroundColor(Color.GRAY);
			this.finish();
		}
		if (v.getId() == R.id.bttnSpeechNext) {
			if(isSetResult)
			{
				Intent intent = new Intent();
	            intent.setClass(this.getApplicationContext(), AssistentFinishActivity.class);
	            startActivity(intent);
	            finish();
			}else{
				Toast.makeText(mContext, R.string.nothing_recorded, Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Inner Class RescordListener
	 *
	 * Computes the returned values of google speech recognition
	 */
	class RecordListener implements RecognitionListener {
		public void onReadyForSpeech(Bundle params) {
			Log.d(TAG, "onReadyForSpeech");
		}

		public void onBeginningOfSpeech() {
			Log.d(TAG, "onBeginningOfSpeech");
		}

		/**
		 * update value of progress bar
		 */
		public void onRmsChanged(float rmsdB) {
			//set progress bar
			int progress = (int) rmsdB;
			mDbBar.setProgress(progress);		
		}

		public void onBufferReceived(byte[] buffer) {
			Log.d(TAG, "onBufferReceived");
		}

		public void onError(int error) {
			Log.d(TAG, "error " + error);
			sr.startListening(recordingIntent);
		}

		public void onEndOfSpeech() {
			Log.d(TAG, "onEndofSpeech");
		}

		/**
		 * compute speech recognition recognition
		 */
		public void onResults(Bundle results) {
			iBttnRecord.setBackgroundColor(Color.argb(255, 0, 200, 0));
			mIsRecording = false;
			
			/* Filtern der Ergebnisse auch für später zum Vergleichen */
			final ArrayList<SpeechResult> speechResults = new ArrayList<SpeechResult>();
			ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			for (int i = 0; i < data.size(); i++) {
				speechResults.add(new SpeechResult(data.get(i)));
			}
			
			final ResultAdapter rAdapter = new ResultAdapter(mContext, speechResults);
    		
    		/*
    		 * CONSTRUCT ALERT DIALOG
    		 * to display matching speech results
    		 */
    		new  AlertDialog.Builder(mContext)
    		.setTitle(R.string.assistent_speech_select_result)
    		.setNegativeButton(R.string.main_assistent_abort, new DialogInterface.OnClickListener()
    		{
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    			}
    		})
    		.setPositiveButton(R.string.main_assistent_next, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {					
					Log.i(TAG, "ChiocePosition: " + mChoicePosition);
					SpeechResult spResult = speechResults.get(mChoicePosition);
					String choice = spResult.getResult();
					
					sIo.putString(SharedPreferenceIO.PREF_SPEECH_RESULT, choice);
					isSetResult = true;
					
					String message = mContext.getResources().getString(R.string.your_choice, choice);
					Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				}    			
    		})
    		.setSingleChoiceItems(rAdapter, -1, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which) {
					mChoicePosition = which;
					rAdapter.setCheckedPosition(which);
				}
    		}).show();
			//alert dialog end
		}

		public void onPartialResults(Bundle partialResults) {
			Log.d(TAG, "onPartialResults");
		}

		public void onEvent(int eventType, Bundle params) {
			Log.d(TAG, "onEvent " + eventType);
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		sr.destroy();
	}
	

}
