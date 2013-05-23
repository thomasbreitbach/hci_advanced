package de.thomasbreitbach.hci.voicetest;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpeechListAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> mMatches;
	
	
	public SpeechListAdapter(Context context, ArrayList<String> matches){
		this.mContext = context;
		this.mMatches = matches;
	}
	
	public void setMatches(ArrayList<String> matches){
		this.mMatches = matches;
	}
	
	@Override
	public int getCount() {
		return mMatches.size();
	}

	@Override
	public Object getItem(int position) {
		return mMatches.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView tv = (TextView) v.findViewById(R.id.text1);
		
		if(tv!=null){
			tv.setText(mMatches.get(position).toString());
		}
		
		return v;
	}

}
