package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.R;

public class PrototypeFragment extends SherlockFragment {

	private static Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.prototype_fragment, null); 

		
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
