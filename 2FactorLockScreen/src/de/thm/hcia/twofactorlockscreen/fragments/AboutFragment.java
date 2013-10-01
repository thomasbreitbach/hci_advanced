package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class AboutFragment extends SherlockFragment implements OnClickListener {

	private static Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.about_fragment, null); 

		StringBuffer versionName = new StringBuffer().append(mContext.getString(R.string.app_version));
		versionName.append(" ").append(MainActivity.getAppVersion());
				
		TextView tvAppVersion = (TextView) v.findViewById(R.id.tv_app_version);
		tvAppVersion.setText(versionName.toString());
		
		ImageButton github = (ImageButton) v.findViewById(R.id.githubLogo);
		github.setOnClickListener(this);
		
		TextView githubtxt = (TextView) v.findViewById(R.id.githubtxt);
		githubtxt.setOnClickListener(this);
		
		return v;
	}
	
	public void onClick(View v){
		if (v.getId() == R.id.githubLogo || v.getId() == R.id.githubtxt){
			// Open Browser
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://github.com/thomasbreitbach/hci_advanced/"));
			startActivity(browserIntent);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
}
