package de.thm.hcia.twofactorlockscreen.fragments;

import android.R.bool;
import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.R;
import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;

public class SettingsFragment extends SherlockFragment implements OnClickListener{

	SharedPreferences.Editor		mPrefEditor; 
	private static 	Context 		mContext;
	private 		Button			btnPatternActivation, btnSpeechActivation, bttnInfoDialog; 
	private			boolean			isPatternActive, isSpeechActive, isInfoDialog;
	private 		SharedPreferenceIO sIo;

	private static SharedPreferences 	mSettings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.settings_fragment, null); 

		sIo = new SharedPreferenceIO(mContext);
			
		/*btnPatternActivation 	= (Button) v.findViewById(R.id.musterActivationBtn);
		btnSpeechActivation 	= (Button) v.findViewById(R.id.SpeechActivationBtn);*/
		bttnInfoDialog 			= (Button) v.findViewById(R.id.bttn_infoDialog);
		
		/*btnPatternActivation.setBackgroundResource(R.drawable.sa_off);
		btnSpeechActivation.setBackgroundResource(R.drawable.sa_off);*/
		bttnInfoDialog.setBackgroundResource(R.drawable.sa_off);
		
		isPatternActive 		= false;
		isSpeechActive 			= false;
		isInfoDialog			= false;
		
		setupOnClickListeners();
		
		
		//mSettings 	= mContext.getSharedPreferences("AppPrefs", mContext.MODE_PRIVATE);
		//mPrefEditor = mSettings.edit();
		
		
		if(sIo.getBoolean("informationRead"))
		{
			isInfoDialog			= false;
			bttnInfoDialog.setBackgroundResource(R.drawable.sa_off);
		}else{
			isInfoDialog			= true;
			bttnInfoDialog.setBackgroundResource(R.drawable.sa_on);
		}
		
		return v;
	}
	
	private void setupOnClickListeners() {
		
		/*btnPatternActivation.setOnClickListener(new OnClickListener() {
			
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
		});*/

		bttnInfoDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infoDialogActivation();
				
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

	/* private void speechActivation() {
		if(!isSpeechActive){
			
			isSpeechActive = true;
			btnSpeechActivation.setBackgroundResource(R.drawable.sa_on);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_speech_on);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		else{
			isSpeechActive = false;
			btnSpeechActivation.setBackgroundResource(R.drawable.sa_off);
		
			Toast.makeText(mContext,  R.string.tst_speech_off , Toast.LENGTH_SHORT).show();
		}
		
	}

	private void patternActivation() {
		if(!isPatternActive){
			
			isPatternActive = true;
			btnPatternActivation.setBackgroundResource(R.drawable.sa_on);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_muster_on);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		else{
			isPatternActive = false;
			btnPatternActivation.setBackgroundResource(R.drawable.sa_off);
			
			
			CharSequence toastText =(CharSequence) getString(R.string.tst_muster_off);
			Toast.makeText(mContext,  toastText , Toast.LENGTH_SHORT).show();
		}
		
	}
	*/
	private void infoDialogActivation() 
	{
		boolean isReaded = false;
		if(!isInfoDialog){
			
			isInfoDialog = true;
			bttnInfoDialog.setBackgroundResource(R.drawable.sa_on);
			isReaded = false;
			
			Toast.makeText(mContext,  "InfoDialog-ON" , Toast.LENGTH_SHORT).show();
		}
		else{
			isInfoDialog = false;
			bttnInfoDialog.setBackgroundResource(R.drawable.sa_off);
			
			Toast.makeText(mContext,  "InfoDialog-OFF" , Toast.LENGTH_SHORT).show();
			isReaded = true;
		}
		sIo.butBoolean("informationRead", isReaded);		
	}
	
	public Boolean getSpeechStatus(){
		return isSpeechActive;
	}
	
	public Boolean getMusterStatus(){
		return isPatternActive;
	}
	
	/*public void setSpeechStatus(Boolean s){
		isSpeechActive = s;
		speechActivation();
	}
	
	public void setMusterStatus(Boolean m){
		isPatternActive = m;
		patternActivation();
	}*/

	
}
