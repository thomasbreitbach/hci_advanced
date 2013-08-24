package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.R;

public class EvaluationFragment extends SherlockFragment {

	private static Context mContext;
	private static String url = "http://goo.gl/UqwY7W";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.evaluation_fragment, null); 
		WebView wv = (WebView) v.findViewById(R.id.webView1);
		
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(url);
		
		return v;
	}
}
