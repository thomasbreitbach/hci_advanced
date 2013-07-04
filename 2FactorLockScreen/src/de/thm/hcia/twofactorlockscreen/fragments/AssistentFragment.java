package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.AssistentMainActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class AssistentFragment extends SherlockFragment {

	private static Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.assistent_fragment, null); 
		
		Button btnStartAssistent = (Button) v.findViewById(R.id.btn_start_assistent);
		btnStartAssistent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ToDo
			}
		});
		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
}
