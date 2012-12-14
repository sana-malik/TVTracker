package com.sana.tvtracker;

import java.util.List;

import com.sana.data.Episode;
import com.sana.data.Season;
import com.sana.data.Show;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EpisodeListAdapter extends ArrayAdapter<Show> {
	public EpisodeListAdapter(
		Context context,int textViewResourceId,List<Show> items) 
	{
		super(context, textViewResourceId, items);
	}
	
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		if (convertView == null) {
			LayoutInflater vi =
				(LayoutInflater)getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.episode_item, null);
		}
		
		Show show = getItem(position);
		if (show != null) {
			// Show title
			TextView title = (TextView)convertView.findViewById(
				R.id.show_title);
			if (title != null) {
				title.setText(show.getName());
			}

			// Last episode
			TextView last = (TextView)convertView.findViewById(
				R.id.last_ep);
			if (last != null)
				last.setText(Html.fromHtml("<b>Previous:</b> "
							+ show.getLastEpisode()));
			
			
	
			// Next episode
			TextView next = (TextView)convertView.findViewById(
				R.id.next_ep);
			if (next != null)
				next.setText(Html.fromHtml("<b>Next:</b> "
							+ show.getNextEpisode()));
		}
		return convertView;
	}
}
