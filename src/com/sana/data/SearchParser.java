package com.sana.data;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.sana.tvtracker.ShowView;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;
import android.widget.Toast;

public class SearchParser extends SearchFeedParser {
	String str;
	
	public SearchParser(String feedUrl){
		super(feedUrl);
	}
	
	public List<String> parse() {
		final List<String> results = new ArrayList<String>();
		RootElement root = new RootElement(RESULTS);
		
		/* Parse show info */
		Element show = root.getChild(SHOW);
		show.getChild(SHOWID).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				results.add(body);
			}
		});	
		try {
			if (this.getInputStream() != null) {
				Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			}
			else {
				results.clear();
			}
		} catch (Exception e) {
		}
		
		return results;
	}
}