package com.sana.tvtracker;

import java.util.ArrayList;
import java.util.List;


import com.sana.data.FavoriteShows;
import com.sana.data.Season;
import com.sana.data.Show;
import com.sana.data.ShowParser;

import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Bundle;

public class Hiatus extends ListActivity {
	HiatusListAdapter adapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hiatus);

        List<Show> shows = FavoriteShows.instance().getHiatus();
        	
        if (shows != null) {
        	adapter = new HiatusListAdapter(this, R.layout.hiatus_item, shows);
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
	Launches ShowViewer when a course is selected from the
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
        
        Intent i = new Intent(Hiatus.this,
        		   			  ShowView.class);

        Bundle extras = new Bundle();
        extras.putSerializable("show",(Show)l.getItemAtPosition(position));
        i.putExtras(extras);
		
		startActivity(i);
	}
}