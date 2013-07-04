package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class MainFragment extends SherlockFragment {

	private static Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		
		View v = inflater.inflate(R.layout.main_fragment, null); 
		
		Button b = (Button) v.findViewById(R.id.btn_start_assistent);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "Button clicked", Toast.LENGTH_SHORT).show();
			}
		});
		
		//get app version from AndroidManifest
		StringBuffer versionName = new StringBuffer().append(mContext.getString(R.string.app_version));
		versionName.append(" ").append(MainActivity.getAppVersion());

		TextView appVersion = (TextView) v.findViewById(R.id.tv_app_version);
		appVersion.setText(versionName.toString());
			
		return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
}
