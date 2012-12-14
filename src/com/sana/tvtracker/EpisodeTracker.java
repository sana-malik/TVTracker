package com.sana.tvtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


import com.sana.data.FavoriteShows;
import com.sana.data.Show;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.os.Bundle;

public class EpisodeTracker extends ListActivity {
	
	EpisodeListAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent);

        List<Show> shows = FavoriteShows.instance().getRunning();
        
      
        if (shows != null) {
        	adapter = new EpisodeListAdapter(
        			this, R.layout.episode_item, shows);
        	setListAdapter(adapter);
        }
    }
    
    protected void onResume() {
    	super.onResume();
    	if (adapter != null) {
    		adapter.notifyDataSetChanged();
    	}
    }
    
    /** 
	Launches ShowView when a course is selected from the
	this activity.

	@param l ListView in which item that was clicked resides
 	@param v parent view holding l
	@param position position of clicked item in l
	@param id
	*/
	@Override
	protected void onListItemClick(
		ListView l, 
		View v, 
		int position, 
		long id) 
	{
        
        Intent i = new Intent(EpisodeTracker.this,
        		   			  ShowView.class);

        Bundle extras = new Bundle();
        extras.putSerializable("show",(Show)l.getItemAtPosition(position));
        i.putExtras(extras);
		
		startActivity(i);
	}
}