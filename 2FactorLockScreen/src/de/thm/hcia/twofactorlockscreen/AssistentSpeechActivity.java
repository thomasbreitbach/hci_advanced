package de.thm.hcia.twofactorlockscreen;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.actionbarsherlock.app.SherlockActivity;

public class AssistentSpeechActivity extends SherlockActivity implements OnClickListener 
{
	private SpeechRecognizer 	sr;
	private Button 				bttnNext, bttnAbord;
	private ImageButton			iBttnRecord;
	private Context 			mContext;	   
	private static final String	TAG = "SpeechTAG_ERRORs";
	private TextView 			txtResult;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_speech_input_acrtivity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		sr = SpeechRecognizer.createSpeechRecognizer(this);       
        sr.setRecognitionListener(new RecordListener());    
		
		mContext = this;
		
		bttnNext	= (Button) findViewById(R.id.bttnSpeechNext);
		bttnAbord 	= (Button) findViewById(R.id.bttnSpeechAbort);
		iBttnRecord = (ImageButton) findViewById(R.id.iBttnRecord);
		
		iBttnRecord.setOnClickListener(this);
		bttnNext.setOnClickListener(this);
		bttnAbord.setOnClickListener(this);
	}
	   public void onClick(View v) 
	   {
		   if (v.getId() == R.id.iBttnRecord) 
           {
               Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
               intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
               intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

               intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5); 
               sr.startListening(intent);
               Log.i("111111","11111111");
           }
		   if (v.getId() == R.id.bttnSpeechAbort) 
           {
               this.finish();
           }
	   }

	   class RecordListener implements RecognitionListener          
	   {
	            public void onReadyForSpeech(Bundle params)	{	Log.d(TAG, "onReadyForSpeech");		}
	            public void onBeginningOfSpeech()			{	Log.d(TAG, "onBeginningOfSpeech");	}
	            public void onRmsChanged(float rmsdB)		{	Log.d(TAG, "onRmsChanged");			}
	            public void onBufferReceived(byte[] buffer)	{	Log.d(TAG, "onBufferReceived");		}
	            public void onError(int error)				{	Log.d(TAG,  "error " +  error);		}
	            
	            
	            public void onEndOfSpeech()
	            {	
	            	Log.d(TAG, "onEndofSpeech");
	            }
	            
	            public void onResults(Bundle results)                   
	            {
	            	/*Filtern der Ergebnisse auch f�r sp�ter zum Vergleichen */
					String str = new String();
					Log.d(TAG, "onResults " + results);
					ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
					for (int i = 0; i < data.size(); i++)
					{
						Log.d(TAG, "result " + data.get(i));
						str += data.get(i);
					}
					txtResult = (TextView) findViewById(R.id.txtResults);
					txtResult.setText(str);
					
					Toast.makeText(mContext, "results: "+String.valueOf(data.size()), Toast.LENGTH_LONG);        
	            }
	            public void onPartialResults(Bundle partialResults)
	            {
	                     Log.d(TAG, "onPartialResults");
	            }
	            public void onEvent(int eventType, Bundle params)
	            {
	                     Log.d(TAG, "onEvent " + eventType);
	            }
	   }
	   
}
