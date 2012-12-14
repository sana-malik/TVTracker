package com.sana.tvtracker;

import java.util.ArrayList;
import java.util.List;

import com.sana.data.SearchParser;
import com.sana.data.Show;
import com.sana.data.ShowParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class FindShows extends ListActivity implements Runnable {
	
	/**
	 Dialog displayed to the user while querying the server
	 */
	ProgressDialog waiting;
	
	String str = ""; 
	
	List<Show> shows;
	
	View searchView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        final Button button = (Button) findViewById(R.id.go);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	str = ((EditText)findViewById(R.id.search)).getText().toString().replace(" ", "%20");
            	// Create wait dialog
            	waiting = new ProgressDialog(FindShows.this);
                waiting.setMessage("Searching!");
                waiting.setIndeterminate(true);
                waiting.setCancelable(true);
                waiting.show();
                searchView = v;
                
                Thread thread = new Thread(FindShows.this);
                thread.start();
            }
        });

    }
    
    /**
    Threaded search query. Returns to the main thread via a handler
    with a code identifying whether the search was successful.
    */
	public void run()
	{
        SearchParser parser = new SearchParser("http://www.tvrage.com/feeds/search.php?show=" + str);
        List<String> results = parser.parse();
        shows = new ArrayList<Show>();

        if (!results.isEmpty()){
        	for( String s : results ){
        		ShowParser showParser = new ShowParser("http://www.tvrage.com/feeds/full_show_info.php?sid="+s);
        		Show sh = showParser.parse();
        		if (sh != null) shows.add(sh);
        	}
        	handler.sendEmptyMessage(1);
        }
        else handler.sendEmptyMessage(0);
        
     }
    
	/**
	 Handles the return from the search thread.
	 */
	private Handler handler = new Handler()
	{
		/**
		 Upon return from the search thread, displays the results
		 if the search was successful, or notifies the user if it
		 was unsuccessful. Then dismisses the progress dialog.
		 */
		@Override
       public void handleMessage(Message msg)
       {
			if (msg.what == 1) {
				if (shows.isEmpty()) {
					Toast.makeText(
						FindShows.this,
						"No results found.",
						Toast.LENGTH_SHORT).
							show();
				} else {
					((FindShows)searchView.getContext()).getListView().
					setAdapter(new SearchListAdapter(
    			getApplicationContext(), R.layout.search_item, shows));
					}
				}
			else if (msg.what == 0) {
				Toast.makeText(
					FindShows.this,
					"No results found.",
					Toast.LENGTH_SHORT).
						show();
				}
           waiting.dismiss();
       }
};
	
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
        
        Intent i = new Intent(FindShows.this,
        		   			  ShowView.class);

        Bundle extras = new Bundle();
        extras.putSerializable("show",(Show)l.getItemAtPosition(position));
        i.putExtras(extras);
		
		startActivity(i);
	}

}
