package de.thm.hcia.twofactorlockscreen.fragments;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class ManualInputFragment extends SherlockFragment {

	private static Context mContext;
	private static Button mBtnPattern;
	private static Button mBtnSpeech;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.manual_input_fragment, null); 

		mBtnPattern = (Button) v.findViewById(R.id.btn_start_pattern_input);	
		mBtnSpeech = (Button) v.findViewById(R.id.btn_start_speech_input);
		
		setupOnClickListeners();
		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * sets required click listeners
	 */
	private void setupOnClickListeners() {
		mBtnPattern.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN, null, mContext, LockPatternActivity.class);
				getActivity().startActivityForResult(intent, MainActivity.REQ_CODE_CREATE_PATTERN);
			}
		});
		
		mBtnSpeech.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);    
		        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.voice_prompt);
		        
		        getActivity().startActivityForResult(intent, MainActivity.REQ_CODE_CREATE_VOICE);
			}
		});
	}
	
}
