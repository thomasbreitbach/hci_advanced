package de.thm.hcia.twofactorlockscreen;

import group.pals.android.lib.ui.lockpattern.LockPatternActivity;
import group.pals.android.lib.ui.lockpattern.prefs.SecurityPrefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import de.thm.hcia.twofactorlockscreen.fragments.AboutFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MainFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MenuFragment;
import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;
import de.thm.hcia.twofactorlockscreen.network.NetInfo;
import de.thm.hcia.twofactorlockscreen.security.LPEncrypter;

public class MainActivity extends SlidingFragmentActivity {

	private static final String 		TAG 						= "MainActivity";
	
	public static final int 			REQ_CODE_CREATE_PATTERN 	= 1;
	public static final int 			REQ_CODE_COMPARE_PATTERN 	= 2;
	public static final int 			REQ_CODE_CREATE_VOICE 		= 3; 
	public static CheckBox 			mDontShowAgain;
	
	
	private static boolean			speechInstalled = false;
	private static boolean			patternInstalled = false;
	
	SharedPreferences.Editor 			mPrefEditor; 
	private static SlidingMenu 		mMenu;
	private static Fragment 			mContent;
	private static SharedPreferences 	mSettings;
	private static Context 			mContext;
	private static String 				mAppVersion;
	private static char[] 			savedPattern = null;
	private ArrayList<String> 			matches;
	
	/*
	 * Variablen f�r Zur�ck, Men�-Button
	 */
	private boolean						isSlideMenue	= false;
	private boolean						isHomescreen	= true;
	
	private SharedPreferenceIO 			sPiO;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setTitle(R.string.app_name);		
		setContentView(R.layout.responsive_content_frame);
		
		//Zum Speichern des Pattern �ber die AutoSave-Funktion der Lib.
		SecurityPrefs.setEncrypterClass(mContext, LPEncrypter.class);
		SecurityPrefs.setAutoSavePattern(mContext, true);
		
		sPiO = new SharedPreferenceIO(mContext);
		
		//check for inet connection
		if(NetInfo.inetAvailable(mContext)){
			Toast.makeText(mContext, "inet ist da", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(mContext, "inet ist nicht da", Toast.LENGTH_SHORT).show();
		}
		
		//check if pattern and speech are installed
		if(sPiO.getPatter() != null) patternInstalled = true;
		if(!sPiO.getSpeech().equals("")) speechInstalled = true;
		
		
		/*AUSLAGERN -->!!!<--
			/*
			 * Vorbereiten für das lokale Speichern von Einstellungen
			 *
		*/
			mSettings 	= getSharedPreferences("AppPrefs", MODE_PRIVATE);
			mPrefEditor = mSettings.edit();
		
		
		
		/*
		 * Holen der Versionsnummer über die PackageInfo
		 */
		try {
			mAppVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			mAppVersion = "1.0";
		}
		
		/*
		 *	Einstellungen für das Slide-Framework
		 */
		//check if the content frame contains the menu frame
		if(findViewById(R.id.menu_frame) == null){
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
			// show home as up so we can toggle
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}else{
			/*
			 *  add a dummy view
			 *  disable SlidingMenu
			 */
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
		
		// set the Above View Fragment
		if (savedInstanceState != null){
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}		
		if (mContent == null){
			mContent = new MainFragment();	
		}			
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View Fragment
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();
        
		//set sliding menu
        prepareSlidingMenu();  
        
        //Show explanation dialog (one time)   
        if (savedInstanceState == null && mSettings.getBoolean("informationRead", false) == false){
        	AlertDialog.Builder adb 	= new AlertDialog.Builder(this);
        	
			LayoutInflater adbInflater 	= LayoutInflater.from(this);
			View checkBoxLayout 		= adbInflater.inflate(R.layout.checkbox, null);
			mDontShowAgain 				= (CheckBox) checkBoxLayout.findViewById(R.id.never_again);
        	
			adb.setView(checkBoxLayout);
        	adb.setTitle(R.string.app_explanation_head);
			adb.setMessage(R.string.app_explanation_txt);
			adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {					
					if(mDontShowAgain.isChecked()){					
			        	mPrefEditor.putBoolean("informationRead", true);
			            mPrefEditor.commit();
					}					
				}
			})
			.show();     	
        }      	
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, item.toString());
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		case R.id.action_toggle_sliding_menu:
			toggle();
			break;
		case R.id.action_about:
			switchContent(new AboutFragment());
			break;
		case R.id.action_code_on_github:
			//Open Browser
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thomasbreitbach/hci_advanced/"));
			startActivity(browserIntent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * sets some menu preferences
	 */
	private void prepareSlidingMenu() {
		mMenu = getSlidingMenu();
		mMenu.setMode(SlidingMenu.LEFT);
		mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mMenu.setShadowWidthRes(R.dimen.shadow_width);
		mMenu.setShadowDrawable(R.drawable.shadow);
		mMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mMenu.setFadeDegree(0.35f);
	}

	/**
	 * switches the fragment
	 * @param fragment
	 */
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	//HIER AM ARBEITEN
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Log.i(TAG, "onActivityResult");
		switch(requestCode) 
		{
			/*
			 * Speichern der Pattern in "prefs"
			 */
		    case REQ_CODE_CREATE_PATTERN:
		        if(resultCode == RESULT_OK){
		            savedPattern = data.getCharArrayExtra(LockPatternActivity.EXTRA_PATTERN);
		            /*
		    		 * Speichern der Pattern in "prefs"
		    		 */
		            saveToSharedPreferences("savedPattern", savedPattern.toString());
		            if(mPrefEditor.commit()){
		            	Toast.makeText(mContext, R.string.pattern_recorded, Toast.LENGTH_SHORT).show();
		            	Log.d("TEST", loadStringFromSharedPreferences("savedPattern").toString());
		            }else{
		            	Toast.makeText(mContext, R.string.writing_prefs_error_01, Toast.LENGTH_SHORT).show();
		            }      
		        }
		        break;
		        
	        /*
    		 * Speichern der Sprache in "prefs"
    		 */
		    case REQ_CODE_CREATE_VOICE:
		    	Log.d(TAG, "REQ_CODE_CREATE_VOICE");
		    	if (resultCode == RESULT_OK)
		        { 
		            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//		            saveToSharedPreferences("savedRecord", matches);
		            
		            if(mPrefEditor.commit())
		            {		   
		            	Toast.makeText(mContext, R.string.speech_recorded, Toast.LENGTH_SHORT).show();
//		            	Log.d("TEST", loadArrayFromSharedPreferences("savedRecord").toString());
		            }else{
		            	Toast.makeText(mContext, R.string.writing_prefs_error_01, Toast.LENGTH_SHORT).show();
		            }     
		            
		        }
		        
		        if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
		        	Toast.makeText(this, R.string.voice_no_match, Toast.LENGTH_LONG).show();
		        }
		    	break;
		}
	} 
	
	public static String getAppVersion(){
		return mAppVersion;
	}
	
	//----------------------------------------------------------------
	// Die Funktion die den Button abfragt
	//----------------------------------------------------------------
	  @Override
	    public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_MENU)) 
	    {
	    	toggle();
	    }
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(!mMenu.isMenuShowing())
	    	{
	    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
	    		alertDialogBuilder.setTitle(R.string.dialog_title);
	    		alertDialogBuilder
					.setMessage(R.string.dialog_message)
					.setCancelable(false)
					.setPositiveButton("Beenden",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							MainActivity.this.finish();
						}
					  })
					.setNegativeButton("Abbrechen",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
	    	}else{
	    		toggle();
	    	}
	    }
	//----------------------------------------------------------------
	    return true;
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
	  
//	  public Boolean saveToSharedPreferences(String key, ArrayList<String> aList)
//	  {
//		  Set<String> stList = new HashSet<String>();
//		  
//          stList.addAll(aList);		    
//          mPrefEditor.putStringSet(key, replaceToLowercase(stList));
//          if(mPrefEditor.commit())
//          {
//        	  return true;
//          }else{
//        	  return false;
//          }
//	  }
	  
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
	  
//	  public ArrayList<String> loadArrayFromSharedPreferences(String key)
//	  {
//		  Set<String> aSList 		= new HashSet<String>();
//          aSList 					= mSettings.getStringSet(key, null);
//          ArrayList<String> aList 	= new ArrayList<String>();
//          aList.addAll(aSList);
//          
//		  return  aList;
//	  }
	  
	  public String loadStringFromSharedPreferences(String key)
	  {         
		  return  mSettings.getString(key, null);
	  }
	  
	  public static boolean isSpeechInstalled(){
		  return speechInstalled;
	  }
	  
	  public static boolean isPatternInstalled(){
		  return patternInstalled;
	  }
}
 