package de.thm.hcia.twofactorlockscreen.fragments;

import de.thm.hcia.twofactorlockscreen.MainActivity;
import de.thm.hcia.twofactorlockscreen.R;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MenuFragment extends ListFragment {

	private static final String TAG = "MenuFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){		
//		 // create ContextThemeWrapper from the original Activity Context with the custom theme
//	    final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.Theme_Sherlock);
//
//	    // clone the inflater using the ContextThemeWrapper
//	    LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
//
//	    // inflate the layout using the cloned inflater, not default inflater
//	    return localInflater.inflate(R.layout.list, container, false);	
		
	    return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//get menu items and icons
		String[] menuStrings = getResources().getStringArray(R.array.menu);
		String[] menuIcons = getResources().getStringArray(R.array.menu_imgs);
		
		Log.d(TAG, menuIcons.toString());
		
		ManuAdapter adapter = new ManuAdapter(getActivity());
		for (int i = 0; i < menuStrings.length; i++) {

		    int imageResource = getResources().getIdentifier(menuIcons[i], null, 
		    		getActivity().getPackageName());
			adapter.add(new MenuItem(menuStrings[i], imageResource));
		}
				
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new MainFragment();
			break;
		case 1:
			newContent = new AssistentFragment();
			break;
		case 2:
			newContent = new ManualInputFragment();
			break;
		case 3:
			newContent = new PrototypeFragment();
			break;
		case 4:
			
			break;
		case 5:
			newContent = new SettingsFragment();
			break;
		case 6:
			newContent = new AboutFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null) return;
		
		MainActivity activity = (MainActivity) getActivity();
		activity.switchContent(fragment);		
	}
	
	/**
	 * MenuAdapter
	 * @author thomas
	 *
	 */
	public class ManuAdapter extends ArrayAdapter<MenuItem> {

		public ManuAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
	
	/**
	 * MenuItem: hat ein Icon und einen dazugehörigen String
	 * @author thomas
	 *
	 */
	private class MenuItem {
		public String tag;
		public int iconRes;
		
		public MenuItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}
}
