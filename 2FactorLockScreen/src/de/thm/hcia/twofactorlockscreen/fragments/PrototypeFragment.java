package de.thm.hcia.twofactorlockscreen.fragments;

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
import de.thm.hcia.twofactorlockscreen.PrototypeStart;
import de.thm.hcia.twofactorlockscreen.R;

public class PrototypeFragment extends SherlockFragment {

	private static Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.prototype_fragment, null); 

		Button btnStartProto = (Button) v.findViewById(R.id.btn_startProto);
		btnStartProto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*
				 * Start the assistent
				 */
				Intent intent = new Intent();
	            intent.setClass(getActivity().getBaseContext(), PrototypeStart.class);
	            startActivity(intent);
			}
		});
		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
