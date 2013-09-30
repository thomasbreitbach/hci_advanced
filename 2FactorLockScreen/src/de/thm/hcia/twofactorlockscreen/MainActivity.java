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
import de.thm.hcia.twofactorlockscreen.fragments.AssistentFragment;
import de.thm.hcia.twofactorlockscreen.fragments.EvaluationFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MainFragment;
import de.thm.hcia.twofactorlockscreen.fragments.ManualInputFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MenuFragment;
import de.thm.hcia.twofactorlockscreen.fragments.PrototypeFragment;
import de.thm.hcia.twofactorlockscreen.fragments.SettingsFragment;
import de.thm.hcia.twofactorlockscreen.io.SharedPreferenceIO;
import de.thm.hcia.twofactorlockscreen.network.NetInfo;
import de.thm.hcia.twofactorlockscreen.security.LPEncrypter;

public class MainActivity extends SlidingFragmentActivity {

	private static final String 		TAG 						= "MainActivity";
	
	public static final int 			REQ_CODE_CREATE_PATTERN 	= 1;
	public static final int 			REQ_CODE_COMPARE_PATTERN 	= 2;
	public static final int 			REQ_CODE_CREATE_VOICE 		= 3; 
	public static final int			MAIN_FRAGMENT				= 10;
	public static final int			ASSISTENT_FRAGMENT			= 11;
	public static final int			MANUAL_INPUT_FRAGMENT		= 12;
	public static final int			PROTOTYPE_FRAGMENT			= 13;
	public static final int			EVALUATION_FRAGMENT			= 14;
	public static final int			SETTINGS_FRAGMENT			= 15;
	public static final int			ABOUT_FRAGMENT				= 16;	
	
	public static CheckBox 			mDontShowAgain;	
	
	private static boolean			speechInstalled = false;
	private static boolean			patternInstalled = false;
	
	SharedPreferences.Editor 			mPrefEditor; 
	private static SlidingMenu 		mMenu;
	private static Fragment 			mContent;
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
//		SecurityPrefs.setEncrypterClass(mContext, LPEncrypter.class); <-- TODO!
		SecurityPrefs.setAutoSavePattern(mContext, true);
		
		sPiO = new SharedPreferenceIO(mContext);
		
		//check if pattern and speech are installed
		checkInstallation();
		
		//get version number
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
        if (savedInstanceState == null && sPiO.getBoolean(SharedPreferenceIO.PREF_START_INFO_READ) == false){
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
						sPiO.putBoolean(SharedPreferenceIO.PREF_START_INFO_READ, true);
					}					
				}
			})
			.show();     	
        }      	
	}

	@Override
	public void onNewIntent(Intent intent){
		//check for intent extras
		if(intent!=null){
			Bundle extras = intent.getExtras();
			
			if(extras != null){
				int fragInt = extras.getInt("fragment", MAIN_FRAGMENT);
				 if(fragInt != 0){
					 Fragment fragment = null;
					 
					 switch(fragInt){
					 case MAIN_FRAGMENT:
						 fragment = new MainFragment();
						 break;
					 case ASSISTENT_FRAGMENT:
						 fragment = new AssistentFragment();
						 break;
					 case MANUAL_INPUT_FRAGMENT:
						 fragment = new ManualInputFragment();
						 break;
					 case PROTOTYPE_FRAGMENT:
						 fragment = new PrototypeFragment();
						 break;
					 case EVALUATION_FRAGMENT:
						 fragment = new EvaluationFragment();
						 break;
					 case SETTINGS_FRAGMENT:
						 fragment = new SettingsFragment();
						 break;
					 case ABOUT_FRAGMENT:
						 fragment = new AboutFragment();
						 break;
					 }
					 
					 switchContent(fragment);
				 }
			}
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
	 * @param fragment the target fragment
	 */
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commitAllowingStateLoss();
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
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(TAG, "onActivityResult");
		switch(requestCode){
		case MainActivity.REQ_CODE_CREATE_PATTERN:
	        if(resultCode == RESULT_OK){            
	            /*
	    		 * Pattern wird über die Bibliothek automatisch gespeichert
	    		 * 
	    		 */
	            Toast.makeText(mContext, R.string.pattern_recorded, Toast.LENGTH_LONG).show(); 
	        }else{
	        	//TODO
	        	//TOAST Fehlerausgabe
	        }
	        break;			
	    case REQ_CODE_COMPARE_PATTERN:
	        switch(resultCode){
	        case RESULT_OK:
	           Log.e("RESULT", String.valueOf(resultCode));
	           Intent i = new Intent(mContext, PrototypeTestFinish.class);
	           startActivity(i);
	            break;
	        case RESULT_CANCELED:
	        	Log.e("RESULT", String.valueOf(resultCode));
	            break;
	        case LockPatternActivity.RESULT_FAILED:
	        	Log.e("RESULT", String.valueOf(resultCode));
	            break;
	        }	
	        Log.e("RESULT", String.valueOf(resultCode)); 
	        /*
    		 * Speichern der Sprache in "prefs"
    		 */
//		    case REQ_CODE_CREATE_VOICE:
//		    	Log.d(TAG, "REQ_CODE_CREATE_VOICE");
//		    	if (resultCode == RESULT_OK)
//		        { 
//		            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
////		            saveToSharedPreferences("savedRecord", matches);
//		            
//		            if(mPrefEditor.commit())
//		            {		   
//		            	Toast.makeText(mContext, R.string.speech_recorded, Toast.LENGTH_SHORT).show();
////		            	Log.d("TEST", loadArrayFromSharedPreferences("savedRecord").toString());
//		            }else{
//		            	Toast.makeText(mContext, R.string.writing_prefs_error_01, Toast.LENGTH_SHORT).show();
//		            }     
//		            
//		        }
		        
//		        if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
//		        	Toast.makeText(this, R.string.voice_no_match, Toast.LENGTH_LONG).show();
//		        }
//		    	break;
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
	    return true;
	  }
	  
	  
	  public boolean isSpeechInstalled(){
		  return speechInstalled;
	  }
	  
	  public boolean isPatternInstalled(){
		  return patternInstalled;
	  }
	  
	  public void checkInstallation(){
		  
		if(sPiO.getPattern() != null){
			
			patternInstalled = true;
		}
		if(!sPiO.getSpeech().equals("")) speechInstalled = true;
	  }
	  
	  public static void setSpeechInstalled(boolean b)
	  {
		  speechInstalled = b;
	  }
	  public static void setPatternInstalled(boolean b)
	  {
		  patternInstalled = b;
	  }
}
 