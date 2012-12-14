package com.sana.tvtracker;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sana.data.Show;

public class SearchListAdapter extends ArrayAdapter<Show> {
	public SearchListAdapter(
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
				convertView = vi.inflate(R.layout.search_item, null);
			}
			
			Show show = getItem(position);
			if (show != null) {
				// Show title
				TextView title = (TextView)convertView.findViewById(
					R.id.result_title);
				if (title != null) {
					title.setText(show.getName());
				}

				// Last episode
				TextView stat = (TextView)convertView.findViewById(
					R.id.res_status);
				if (stat != null)
					stat.setText(show.getStatus());
				
				
		
				// Next episode
				TextView gen = (TextView)convertView.findViewById(
					R.id.res_genre);
				if (gen != null)
					gen.setText(show.getGenreString());
			}
			return convertView;
		}
	}
