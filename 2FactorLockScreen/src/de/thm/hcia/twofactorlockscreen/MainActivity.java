package de.thm.hcia.twofactorlockscreen;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import de.thm.hcia.twofactorlockscreen.fragments.AboutFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MainFragment;
import de.thm.hcia.twofactorlockscreen.fragments.MenuFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

public class MainActivity extends SlidingFragmentActivity {

	private static Fragment mContent;
	private static SharedPreferences mSettings;
	private static SlidingMenu mMenu;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		mSettings = getSharedPreferences("AppPrefs", MODE_PRIVATE);
		
		setContentView(R.layout.responsive_content_frame);
		
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
        	new AlertDialog.Builder(this)
			.setTitle(R.string.app_explanation_head)
			.setMessage(R.string.app_explanation_txt)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences.Editor prefEditor = mSettings.edit();
		        	prefEditor.putBoolean("informationRead", true);
		            prefEditor.commit();
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
		switch (item.getItemId()) {
		case android.R.id.home:
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
}
 