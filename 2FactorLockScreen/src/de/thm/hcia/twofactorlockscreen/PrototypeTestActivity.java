package de.thm.hcia.twofactorlockscreen;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;

public class PrototypeTestActivity extends SherlockActivity
{	
	private static final String TAG = "PrototypeTestActivity";
	private TextView			test;
	private String 				theAwnser;
	private SharedPreferenceIO 	sIo;
	private SpeechRecognizer 	sr;
	private boolean				mIsRecording = false;
	private Context 			mContext;
	private Intent 				recordingIntent;
	
	
	private boolean				isSpeechCorrect = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		setContentView(R.layout.prototype_test_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
		mContext = this;
		sr = SpeechRecognizer.createSpeechRecognizer(this);
		sr.setRecognitionListener(new RecordListener());
		
		
		sIo = new SharedPreferenceIO(getBaseContext());
		theAwnser = sIo.getSpeech();
		
		test = (TextView) findViewById(R.id.test);
		test.setText("Spracheingabe: "+theAwnser);
		
		recordingIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recordingIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);	
		recordingIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, Long.valueOf(2000));
		recordingIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
		recordingIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

		sr.startListening(recordingIntent);
		
	}
	
	private boolean checkSpeechInput(ArrayList<String> theList)
	{
		boolean isTrue = false;
		for(int i = 0; i < theList.size(); i++)
		{
			if(theList.get(i).equals(theAwnser))
			{
				Log.d(TAG, theList.get(i));
				isTrue = true;
			}
		}
		
		return isTrue;
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
		public void onRmsChanged(float rmsdB) {}
		public void onBufferReceived(byte[] buffer) {Log.d(TAG, "onBufferReceived");}
		public void onError(int error) 				{Log.d(TAG, "error " + error);}
		public void onEndOfSpeech() 				{Log.d(TAG, "onEndofSpeech");}

		/**
		 * compute speech recognition recognition
		 */
		public void onResults(Bundle results) 
		{
			mIsRecording = false;
			
			/* Filtern der Ergebnisse auch für später zum Vergleichen */
			ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			isSpeechCorrect = checkSpeechInput(data);
		}

		public void onPartialResults(Bundle partialResults) {
			Log.d(TAG, "onPartialResults");
		}

		public void onEvent(int eventType, Bundle params) {
			Log.d(TAG, "onEvent " + eventType);
		}
	}
}
