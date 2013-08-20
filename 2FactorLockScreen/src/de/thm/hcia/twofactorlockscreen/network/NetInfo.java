package de.thm.hcia.twofactorlockscreen.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetInfo {
	
	/**
	 * Checks for available network connection
	 * @param context
	 * @return true, if there is an established connection
	 */
	public static boolean inetAvailable(Context context){
		final ConnectivityManager conMgr =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
		    //user is online
			return true;
		} else {
		    //user is offline
			return false;
		} 
	}
}
