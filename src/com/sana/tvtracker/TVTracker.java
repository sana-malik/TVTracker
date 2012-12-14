package com.sana.tvtracker;


import java.io.File;
import java.nio.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sana.data.FavoriteShows;
import com.sana.data.Show;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;
import android.os.Bundle;

public class TVTracker extends TabActivity {
	TabHost tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	if (FavoriteShows.instance().getAll().isEmpty()) {
    		try {
    			FileInputStream fIn = openFileInput("shows.txt");
    			byte[] content = new byte[fIn.available()];
    			fIn.read(content);
    			fIn.close();
    			String showStr = new String(content);
    			String[] shows = showStr.split(" ");
    			for (String s : shows) {
    				FavoriteShows.instance().addShow(s);
    			}
    		} catch (Exception e) {
    			Log.e("FILE I/O", e.getMessage());
    		}
    	}

        setContentView(R.layout.main);

        Resources res = 
        	getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be
        // reused)
        intent = new Intent().setClass(this, EpisodeTracker.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("running").setIndicator("On Now",
                          res.getDrawable(R.drawable.ic_tab_recent))
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, Hiatus.class);
        spec = tabHost
        	.newTabSpec("hiatus").setIndicator("Hiatus",
        	   res.getDrawable(R.drawable.ic_tab_hiatus))
               .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, FindShows.class);
        spec = tabHost.newTabSpec("search").setIndicator("Find Shows",
                          res.getDrawable(R.drawable.ic_tab_search))
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	try {
    		FileOutputStream fOut = openFileOutput("shows.txt", Context.MODE_PRIVATE);
    		String str = "";
    		for (Show s : FavoriteShows.instance().getAll()) {
    			str += s.getShowid() + " ";
    		}
    		fOut.write(str.getBytes());
    		fOut.close();
    	} catch (Exception e) {
    		Log.e("FILE I/O", e.getMessage());
    	}
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", tabHost.getCurrentTabTag());
    }
}