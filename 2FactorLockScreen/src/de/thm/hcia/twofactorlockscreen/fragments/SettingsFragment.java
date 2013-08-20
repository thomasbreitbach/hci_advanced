package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.R;

public class SettingsFragment extends SherlockFragment implements OnClickListener{

	private static 	Context 		mContext;
	private 		Button			btnPatternActivation, btnSpeechActivation; 
	private			boolean			isPatternActive, isSpeechActive;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.settings_fragment, null); 
		
		btnPatternActivation = (Button) v.findViewById(R.id.musterActivationBtn);
		btnSpeechActivation = (Button) v.findViewById(R.id.SpeechActivationBtn);
		
		
		btnPatternActivation.setBackgroundResource(R.drawable.switch_in_active);
		btnSpeechActivation.setBackgroundResource(R.drawable.switch_in_active);
		
		
		isPatternActive = false;
		isSpeechActive = false;
		
		setupOnClickListeners();
		
		return v;
	}
	
	private void setupOnClickListeners() {
		
		btnPatternActivation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				patternActivation();
				
			}
		});
		
		btnSpeechActivation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				speechActivation();
				
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*	switch(v.getId()){
			case R.id.btn_pattern_activation_switch:
														patternActivation();
														break;
			case R.id.btn_speech_activation_switch: 	speechActivation();
														break;
		
			default: 									break;
		}*/
	}

	private void speechActivation() {
		if(!isSpeechActive){
			
			isSpeechActive = true;
			btnSpeechActivation.setBackgroundResource(R.drawable.switch_active);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_muster_on);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		else{
			isSpeechActive = false;
			btnSpeechActivation.setBackgroundResource(R.drawable.switch_in_active);
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_speech_off);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		
	}

	private void patternActivation() {
		if(!isPatternActive){
			
			isPatternActive = true;
			btnPatternActivation.setBackgroundResource(R.drawable.switch_active);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_muster_on);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		else{
			isPatternActive = false;
			btnPatternActivation.setBackgroundResource(R.drawable.switch_in_active);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_muster_off);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public Boolean getSpeechStatus(){
		return isSpeechActive;
	}
	
	public Boolean getMusterStatus(){
		return isPatternActive;
	}
	
	public void setSpeechStatus(Boolean s){
		isSpeechActive = s;
		speechActivation();
	}
	
	public void setMusterStatus(Boolean m){
		isPatternActive = m;
		patternActivation();
	}

	
}
