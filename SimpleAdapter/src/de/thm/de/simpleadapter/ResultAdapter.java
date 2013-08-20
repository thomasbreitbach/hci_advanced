package de.thm.de.simpleadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ResultAdapter extends ArrayAdapter<Ergebnisse>{

	private 	String 		resAwnser	= null;
    private		Context 	context; 
    private		int 		layoutResourceId;    
    private		Ergebnisse 	data[]		= null;
    
    private RadioButton mSelectedRB;
    private int mSelectedPosition 		= -1;
    private int rbPosition				= -1;
    
    public ResultAdapter(Context context, int layoutResourceId, Ergebnisse[] data) 
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId 	= layoutResourceId;
        this.context 			= context;
        this.data 				= data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	this.rbPosition 		= position;
        View 			row		= convertView;
        ResultsetHolder holder 	= null;        
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row 					= inflater.inflate(layoutResourceId, parent, false);
            
            holder 					= new ResultsetHolder();
            holder.txtTitle 		= (TextView)row.findViewById(R.id.txtTitle);
            holder.bttn 			= (Button)row.findViewById(R.id.bttnAwnser);
            holder.rb				= (RadioButton)row.findViewById(R.id.rb);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ResultsetHolder)row.getTag();
        }
        
        Ergebnisse result = data[position];
        
        
        holder.txtTitle.setText(result.title);
        holder.bttn.setId(result.buttonID);
        holder.bttn.setText("<- ist Richtig!");
        
        holder.bttn.setOnClickListener(new OnClickListener() 
        {	
			@Override
			public void onClick(View v) 
			{
				Button b = (Button)v;
				b.setBackgroundColor(Color.rgb(0,  200, 0));
				resAwnser = data[b.getId()-1].title;
			}
		});
        
        holder.rb.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View v) {

                if((rbPosition != mSelectedPosition) && mSelectedRB != null)
                {
                    mSelectedRB.setChecked(false);	
                }

                mSelectedPosition = rbPosition;
                mSelectedRB = (RadioButton)v;
            }
        });


        if(mSelectedPosition != position){
            holder.rb.setChecked(false);
        }else{
            holder.rb.setChecked(true);
            if(mSelectedRB != null && holder.rb != mSelectedRB){
                mSelectedRB = holder.rb;
            }
        }
        
        return row;
    }
    
    public String getAwnser()
    {
    	return resAwnser;
    }
    
    static class ResultsetHolder
    {
        TextView txtTitle;
        Button bttn;
        
        RadioButton rb;
    }
}