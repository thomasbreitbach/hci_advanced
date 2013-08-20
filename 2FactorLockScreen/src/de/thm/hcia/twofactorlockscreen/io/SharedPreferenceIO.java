package de.thm.hcia.twofactorlockscreen.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import de.thm.hcia.twofactorlockscreen.R;

public class SharedPreferenceIO extends SlidingFragmentActivity{

	SharedPreferences.Editor 			mPrefEditor; 
	private static SlidingMenu 			mMenu;
	private static SharedPreferences 	mSettings;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assistent_speech_input_acrtivity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		mSettings 	= getSharedPreferences("AppPrefs", MODE_PRIVATE);
		mPrefEditor = mSettings.edit();
	}
	//----------------------------------------------------------------
	//	Spezial Funktionen
	//----------------------------------------------------------------
	public Set<String> replaceToLowercase(Set<String> strings)
	{
	    String[] stringsArray = strings.toArray(new String[0]);
	    for (int i=0; i<stringsArray.length; ++i)
	    {
	       stringsArray[i] = stringsArray[i].toLowerCase();
	    }
	    strings.clear();
	    strings.addAll(Arrays.asList(stringsArray));
	      
	    return strings;
	}
	  
	//----------------------------------------------------------------
	//	Funktionen zum Laden und Speichern
	//----------------------------------------------------------------
	  
	public Boolean saveToSharedPreferences(String key, ArrayList<String> aList)
	{
		Set<String> stList = new HashSet<String>();
		  
        stList.addAll(aList);		    
        mPrefEditor.putStringSet(key, replaceToLowercase(stList));
        if(mPrefEditor.commit())
        {
      	  return true;
        }else{
      	  return false;
        }
	}
	public Boolean saveToSharedPreferences(String key, String value)
	{	    
		mPrefEditor.putString(key, value);
        if(mPrefEditor.commit())
        {
      	  return true;
        }else{
      	  return false;
        }
	}
	  
	public ArrayList<String> loadArrayFromSharedPreferences(String key)
	{
		Set<String> aSList 		= new HashSet<String>();
        aSList 					= mSettings.getStringSet(key, null);
        ArrayList<String> aList 	= new ArrayList<String>();
        aList.addAll(aSList);
        
		  return  aList;
	}
	  
	public String loadStringFromSharedPreferences(String key)
	{         
		return  mSettings.getString(key, null);
	}
}
