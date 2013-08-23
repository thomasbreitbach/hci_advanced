package de.thm.hcia.twofactorlockscreen.io;

import group.pals.android.lib.ui.lockpattern.prefs.SecurityPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SharedPreferenceIO{
	
	SharedPreferences.Editor 			mPrefEditor; 
	private static SlidingMenu 			mMenu;
	private static SharedPreferences 	mSettings;
	private Context						mCont;
	
	public SharedPreferenceIO(Context mContext)
	{
		mCont 		= mContext;
		mSettings 	= mCont.getSharedPreferences("AppPrefs", mCont.MODE_PRIVATE);
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
	public Boolean putString(String key, String value)
	{	    
		mPrefEditor.putString(key, value);
        if(mPrefEditor.commit())
        {
      	  return true;
        }else{
      	  return false;
        }
	}
	
	public Boolean butBoolean(String key, Boolean value)
	{	    
		mPrefEditor.putBoolean(key, value);
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
	  
	public String getString(String key)
	{         
		return  mSettings.getString(key, null);
	}
	
	public boolean getBoolean(String key)
	{
		return mSettings.getBoolean(key, false);
	
	}
	public char[] getPatter()
	{
		return SecurityPrefs.getPattern(mCont);
	}
}
