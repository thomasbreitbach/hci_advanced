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
	private 		Button			bttnInfoDialog, bttnDeleteConfig; 
	private			boolean			isInfoDialog, isDeleteConfig;
	private 		SharedPreferenceIO sIo;

	private static SharedPreferences 	mSettings;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.settings_fragment, null); 

		sIo = new SharedPreferenceIO(mContext);
			
		bttnInfoDialog 			= (Button) v.findViewById(R.id.bttnDialog);
		bttnDeleteConfig		= (Button) v.findViewById(R.id.bttnAppDelete);
		
		bttnInfoDialog.setBackgroundResource(R.drawable.sa_off);
		

		isInfoDialog			= false;
		isDeleteConfig			= false;
		
		setupOnClickListeners();
		
		
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
	
	private void setupOnClickListeners() 
	{

		bttnInfoDialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infoDialogActivation();
				
			}
		});
		
		bttnDeleteConfig.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				isDeleteConfig = true;
				
				sIo.setPatternToNull();				
				if(sIo.remove())
				{
					Toast.makeText(mContext, "Einstellungen wurden zurückgesetzt!", Toast.LENGTH_SHORT);
				}				
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
		sIo.putBoolean("informationRead", isReaded);		
	}
}
