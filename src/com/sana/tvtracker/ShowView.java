package com.sana.tvtracker;

import java.util.List;

import com.sana.data.Episode;
import com.sana.data.FavoriteShows;
import com.sana.data.Season;
import com.sana.data.Show;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ShowView extends Activity {
	// The show that this is displaying
	
	ExpandableListAdapter mAdapter;
	Show s;
	Boolean isTracked = false; 
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
		// Retrieve course object and associated semester.
		s = (Show)getIntent().getExtras().getSerializable("show");
        setContentView(R.layout.show);
        
        // Show details
        TextView showName = (TextView)findViewById(R.id.show_name);
        showName.setText(s.getName());
        
        TableLayout details = (TableLayout)findViewById(R.id.info_table);
        TextView classification = (TextView)details.findViewById(R.id.classi);
       	classification.setText(s.getClassification());	

       	TextView genres = (TextView)details.findViewById(R.id.genres);
       	genres.setText(s.getGenreString());
       	
       	TextView airs = (TextView)details.findViewById(R.id.airs);
       	airs.setText(s.getAirInfo());
       	
       	TextView runtime = (TextView)details.findViewById(R.id.runtime);
       	runtime.setText(s.getRuntime() + " min");
       	
       	
       	// Episodes
       	TableLayout eps  = (TableLayout)findViewById(R.id.ep_table);
        TextView last = (TextView)eps.findViewById(R.id.lastEp);
       	last.setText(s.getLastEpisode());	

       	TextView next = (TextView)eps.findViewById(R.id.nextEp);
       	next.setText(s.getNextEpisode());	

       	ExpandableListView seas = (ExpandableListView)findViewById(R.id.season_list);
       	mAdapter = new SeasonListAdapter();
        seas.setAdapter(mAdapter);
        seas.setOnChildClickListener(new OnChildClickListener(){
    		public boolean onChildClick(
    				ExpandableListView parent, View v, 
    				int s, int e, long id) {
    			Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(((SeasonListAdapter)mAdapter).getChild(s, e).getLink()));
    	    	startActivity(browse);
    			return true;
    		}
        });
    }
    
    public void launchURL(View v) {
    	Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(s.getShowlink()));
    	startActivity(browse);
    }
    
    public class SeasonListAdapter extends BaseExpandableListAdapter {
    	private List<Season> seasons = s.getEpisodelist();
    	
		public Episode getChild(int s, int e) {
			return seasons.get(s).getEpisodes().get(e);
		}

		public long getChildId(int s, int e) {
			return e;
		}

		public View getChildView(int s, int e, boolean isLastChild, View convertView,
				ViewGroup parent) {
		//	LinearLayout layout = new LinearLayout(ShowView.this);
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 
					ViewGroup.LayoutParams.WRAP_CONTENT);
			
			//layout.setLayoutParams(lp);
			TextView ep = new TextView(ShowView.this);
			ep.setText(seasons.get(s).getEpisodes().get(e).toString());
			ep.setTextSize(17);
			ep.setLayoutParams(lp);
			ep.setPadding(40, 4, 0, 4);
			//layout.addView(ep);
			
			/*
			TextView date = new TextView(ShowView.this);
			date.setText("Air date: " + seasons.get(s).getEpisodes().get(e).getNiceDate());
			date.setLayoutParams(lp);
			date.setPadding(40, 0, 0, 0);
			layout.addView(date);*/
			return ep;
		}

		public int getChildrenCount(int s) {
			return seasons.get(s).getEpisodes().size();
		}

		public Season getGroup(int s) {
			return seasons.get(s);
		}

		public int getGroupCount() {
			return seasons.size();
		}

		public long getGroupId(int s) {
			return s;
		}

		public View getGroupView(int s, boolean isExpanded, View convertView,
				ViewGroup parent) {
			TextView se = new TextView(ShowView.this);
			se.setText(seasons.get(s).toString());
			se.setPadding(38, 5, 0, 5);
			se.setTextSize(17);
			return se;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int s, int e) {
			return true;
		}
		
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		List<Show> favShows = FavoriteShows.instance().getAll();

		if (!favShows.isEmpty()) {
			for (Show sho : favShows) {
				if (sho.getShowid().equals(this.s.getShowid())) { 
					isTracked = true;
				}
			}
		}
		
		menu.add(0,0,0,"Remove show").setIcon(R.drawable.ic_menu_delete).setVisible(isTracked);
		menu.add(0,1,0,"Add show").setIcon(R.drawable.ic_menu_add).setVisible(!isTracked);
		menu.add(0,2,1,"View on TVRage").setIcon(R.drawable.ic_menu_browse);
		

		return true;
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
		menu.getItem(0).setVisible(isTracked);
		menu.getItem(1).setVisible(!isTracked);
		return true;
	}
    
	/** 
	Launches corresponding activity that was selected from the menu 
	options.

	@param item menu item that was clicked
	@return true if menu action was successful, false otherwise
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
			case 0: // Remove show
				if (FavoriteShows.instance().deleteShow(s.getShowid())) Toast.makeText(getApplicationContext(), 
						"Show removed!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(getApplicationContext(), 
						"Unable to remove show. Try again :(", Toast.LENGTH_SHORT).show();
				isTracked = false;
				return true;
			case 1: // Add show
				if (FavoriteShows.instance().addShow(s)) Toast.makeText(getApplicationContext(), 
						"Show added!", Toast.LENGTH_SHORT).show();
				else Toast.makeText(getApplicationContext(), 
						"Unable to add show. Try again :(", Toast.LENGTH_SHORT).show();
				isTracked = true;
				return true;
			case 2: // Launch TVRage
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s.getShowlink())));
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}

}
