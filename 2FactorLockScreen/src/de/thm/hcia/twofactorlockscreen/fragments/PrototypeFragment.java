package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.AssistentMainActivity;
import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.PrototypeStartActivity;
import de.thm.hcia.twofactorlockscreen.PrototypeTestActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class PrototypeFragment extends SherlockFragment {

	private View v;
	private static Context mContext;
	private Button btnStartProto;
	private TextView tvProtoExplanation;
	private Button btnStartAssistent;
	private LinearLayout quickStart;
	private Button btnGotoManualInput;
	private TextView tvInstallSpeechExpl;
	private TextView tvInstallPatternExpl;
	MainActivity mMainActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		mMainActivity = (MainActivity) getActivity();
		
		v = inflater.inflate(R.layout.prototype_fragment, null); 

		quickStart = (LinearLayout) v.findViewById(R.id.layout_quick_start);	
		tvProtoExplanation = (TextView) v.findViewById(R.id.tv_prototype_explanation);
		tvInstallSpeechExpl = (TextView) v.findViewById(R.id.tv_install_speech_explanation);
		tvInstallPatternExpl = (TextView) v.findViewById(R.id.tv_install_pattern_explanation);
		btnStartProto = (Button) v.findViewById(R.id.btn_startProto);
		btnStartAssistent = (Button) v.findViewById(R.id.btn_start_assistent);
		btnGotoManualInput = (Button) v.findViewById(R.id.btn_goto_manuel_input);
		
		setUpOnClickListeners();

		return v;
	}
	
	public void onStart(){
		super.onStart();
		/*
		 * Sprache und Pattern bereits eigerichet?
		 * Disable bzw Enable jeweilige Views
		 */	
		mMainActivity.checkInstallation();
		if(!mMainActivity.isPatternInstalled() && !mMainActivity.isSpeechInstalled()){
			//Nothing installed set prototype start invisible
			quickStart.setVisibility(View.VISIBLE);
		}else if(!mMainActivity.isPatternInstalled() && mMainActivity.isSpeechInstalled()){
			//Pattern not installed
			btnGotoManualInput.setVisibility(View.VISIBLE);
			tvInstallPatternExpl.setVisibility(View.VISIBLE);
		}else if(!mMainActivity.isSpeechInstalled() && mMainActivity.isPatternInstalled()){
			//Speech not installed
			btnGotoManualInput.setVisibility(View.VISIBLE);
			tvInstallSpeechExpl.setVisibility(View.VISIBLE);
		}else{
			//Everything installed --> show start prototype
			tvProtoExplanation.setVisibility(View.VISIBLE);
			btnStartProto.setVisibility(View.VISIBLE);
		}
	}
	
	private void setUpOnClickListeners() {
		btnStartProto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 * Start assistent
				 */
				Intent intent = new Intent();
	            intent.setClass(mContext, PrototypeTestActivity.class);
	            startActivity(intent);
			}
		});
		
		btnStartAssistent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMainActivity.switchContent(new AssistentFragment());
			}
		});
		
		btnGotoManualInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ManualInputFragment mInput  = new ManualInputFragment();
				MainActivity activity = (MainActivity) getActivity();
				activity.switchContent(mInput);
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
