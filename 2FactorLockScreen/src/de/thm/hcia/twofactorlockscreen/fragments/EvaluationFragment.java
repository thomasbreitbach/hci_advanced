package de.thm.hcia.twofactorlockscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;

import de.thm.hcia.twofactorlockscreen.R;

public class EvaluationFragment extends SherlockFragment {

	private static Context mContext;
	private static String url = "http://goo.gl/UqwY7W";
	private ProgressBar mProgressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.evaluation_fragment, null); 
		WebView wv = (WebView) v.findViewById(R.id.webView1);
		mProgressBar = (ProgressBar) v.findViewById(R.id.loadingURL);
		
		wv.getSettings().setJavaScriptEnabled(true);
		// By default, redirects cause jump from WebView to default
	    // system browser. Overriding url loading allows the WebView  
	    // to load the redirect into this screen.
	    wv.setWebViewClient(new WebViewClient() {
	    	
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            view.loadUrl(url);
	            return false;
	        }
	        
	        @Override
	        public void onPageFinished(WebView view, String url) {
	        	super.onPageFinished(view, url);
	        	mProgressBar.setVisibility(View.GONE);
	        }
	    });
		wv.loadUrl(url);
		
		return v;
	}
}
