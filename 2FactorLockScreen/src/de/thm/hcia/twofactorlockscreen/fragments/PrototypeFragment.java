package de.thm.hcia.twofactorlockscreen.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;



import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.AssistentMainActivity;
import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.PrototypeStartActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class PrototypeFragment extends SherlockFragment {

	private static Context mContext;
	private Button btnStartProto;
	private Button btnStartAssistent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.prototype_fragment, null); 


		btnStartProto = (Button) v.findViewById(R.id.btn_startProto);
		btnStartProto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 * Start assistent
				 */
				Intent intent = new Intent();
	            intent.setClass(mContext, PrototypeStartActivity.class);
	            startActivity(intent);
			}
		});

		btnStartAssistent = (Button) v.findViewById(R.id.btn_start_assistent);
		btnStartAssistent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AssistentFragment assistent  = new AssistentFragment();
				MainActivity activity = (MainActivity) getActivity();
				activity.switchContent(assistent);
			}
		});
		
		
		/*
		 * Sprache und Pattern bereits eigerichet?
		 * Disable bzw Enable jeweilige Views
		 */
		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
