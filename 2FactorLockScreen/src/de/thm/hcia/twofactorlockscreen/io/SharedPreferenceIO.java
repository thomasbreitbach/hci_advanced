package de.thm.hcia.twofactorlockscreen.io;

import group.pals.android.lib.ui.lockpattern.prefs.SecurityPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.thm.hcia.twofactorlockscreen.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for IO operations of shared preferences: mode is private!
 */
public class SharedPreferenceIO{
	
	SharedPreferences.Editor 			mPrefEditor; 
	private static SharedPreferences 	mSettings;
	private Context						mCont;
	
	//PREF CONSTANTS
	public static final String PREF_SPEECH_RESULT 		= "speechResult";
	public static final String PREF_START_INFO_READ 		= "informationRead";
	public static final String PREF_LOGINS_FAILED 		= "loginsFailed";
	public static final String PREF_LOGINS_SUCCESSFUL 	= "loginsSuccessful";
	
	/**
	 * constructor
	 */
	public SharedPreferenceIO(Context mContext)
	{
		mCont 		= mContext;
		mSettings 	= mCont.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
		mPrefEditor = mSettings.edit();
	}

	/**
	 * set a string in s prefs
	 * @param key the name
	 * @param value the new value
	 * @return true if everything is ok, false if an error occurred
	 */
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
	
	/**
	 * retrieve value of given key
	 */
	public String getString(String key)
	{         
		return mSettings.getString(key, "");
	}
	
	/**
	 * set a boolean value in s prefs
	 * @param key the name
	 * @param value true or false
	 * @return true if everything is ok, false if an error occurred
	 */
	public Boolean putBoolean(String key, Boolean value)
	{	    
		mPrefEditor.putBoolean(key, value);
        if(mPrefEditor.commit())
        {
      	  return true;
        }else{
      	  return false;
        }
	}
	
	/**
	 * Retrieve boolean value
	 */
	public Boolean getBoolean(String key){
		return mSettings.getBoolean(key, false);
	}

	/**
	 * Gets the patterns
	 * @return the pattern. Default is null
	 */
	public char[] getPattern()
	{
		return SecurityPrefs.getPattern(mCont);
	}
	
	/**
	 * Set the patterns to null
	 */
	public void setPatternToNull()
	{
		SecurityPrefs.setPattern(mCont, null);
	}
	
	/**
	 * Methode zum laden der abgespeicherten Spracheingabe
	 * @return the speech input as String or null
	 */
	public String getSpeech(){
		return getString(PREF_SPEECH_RESULT);
	}
	
	/**
	 * Increments value of PREF_LOGINS_FAILED
	 */
	public boolean incrementLoginsFailed(){
		int fails = mSettings.getInt(PREF_LOGINS_FAILED, 0);
		mPrefEditor.putInt(PREF_LOGINS_FAILED, fails++);
		return mPrefEditor.commit();
	}
	
	/**
	 * Increments value of PREF_LOGINS_SUCCESSFUL
	 */
	public boolean incrementLoginsSuccessful(){
		int success = mSettings.getInt(PREF_LOGINS_SUCCESSFUL, 0);
		mPrefEditor.putInt(PREF_LOGINS_SUCCESSFUL, success++);
		return mPrefEditor.commit();
	}
	
	/**
	 * replace strings to lower case
	 */
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
	
	/**
	 * remove setting from preferences
	 * @return true or false, for the result
	 */
	public boolean remove()
	{
		mPrefEditor.clear();
		mPrefEditor.commit();
		
		setPatternToNull();
		
		MainActivity.setPatternInstalled(false);
		MainActivity.setSpeechInstalled(false);
		
		return true;
	}
	
	
	
	
//	public ArrayList<String> loadArrayFromSharedPreferences(String key)
//	{
//		Set<String> aSList 		= new HashSet<String>();
//        aSList 					= mSettings.getStringSet(key, null);
//        ArrayList<String> aList 	= new ArrayList<String>();
//        aList.addAll(aSList);
//        
//		  return  aList;
//	}
	
	//----------------------------------------------------------------
	//	Funktionen zum Laden und Speichern
	//----------------------------------------------------------------
	  
//	public Boolean saveToSharedPreferences(String key, ArrayList<String> aList)
//	{
//		Set<String> stList = new HashSet<String>();
//		  
//        stList.addAll(aList);		    
//        mPrefEditor.putStringSet(key, replaceToLowercase(stList));
//        if(mPrefEditor.commit())
//        {
//      	  return true;
//        }else{
//      	  return false;
//        }
//	}
}
