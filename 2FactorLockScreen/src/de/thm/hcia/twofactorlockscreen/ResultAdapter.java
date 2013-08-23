package de.thm.hcia.twofactorlockscreen;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ResultAdapter extends ArrayAdapter<SpeechResult>{

	private static final String TAG = "ResultAdapter";
    private		Context 	mContext;  
    private		ArrayList<SpeechResult> 	data = null;
    
    private static final int LAYOUT = R.layout.list_item_speech_result;
	private LayoutInflater inflater;
	private int checkedPosition = 0;
    
    public ResultAdapter(Context context, ArrayList<SpeechResult> data) 
    {
        super(context, LAYOUT, data);
        this.mContext 	= context;
        this.inflater = LayoutInflater.from(context);
        this.data 		= data;
    }
    
    public void setCheckedPosition(int checkedPosition)
	{
		this.checkedPosition = checkedPosition;
		this.notifyDataSetChanged();
	}
    
    static class ViewHolder{
        CheckedTextView result;
    }

    @Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null){
			// inflate a new view and setup the view holder for future use
			convertView = inflater.inflate(LAYOUT, null);

			holder = new ViewHolder();
			holder.result = (CheckedTextView) convertView.findViewById(R.id.list_tv_speech_result);
			convertView.setTag(holder);
		}else{
			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();
		}

		SpeechResult sr = getItem(position);
		if (sr == null) {
			Log.e(TAG , "Invalid category for position: " + position);
		}
		holder.result.setText(sr.getResult());
		
		if(position == checkedPosition)
		{
			holder.result.setChecked(true);
		} else {
			holder.result.setChecked(false);
		}

		return convertView;
	}
}