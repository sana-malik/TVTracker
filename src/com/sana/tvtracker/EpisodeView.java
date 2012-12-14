package com.sana.tvtracker;

import com.sana.data.Episode;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;

public class EpisodeView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	// Retrieve show
		Episode ep = (Episode)getIntent().getExtras()
			.getSerializable("episode");
		
		TextView title = new TextView(this);
		title.setText(ep.getTitle());
		setContentView(title);

    }
}