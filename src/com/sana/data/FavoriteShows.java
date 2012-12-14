package com.sana.data;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.sana.tvtracker.EpisodeTracker;
import com.sana.tvtracker.Hiatus;
import com.sana.tvtracker.ShowView;

import com.sana.data.FavoriteShows;
import com.sana.data.Show;


/** My first singleton class!!! **/
public class FavoriteShows {
	static private FavoriteShows _instance = null;
	
	/** Fields and such */
	private List<Show> running;
	private List<Show> hiatus;
	
	protected FavoriteShows() {
		running = new ArrayList<Show>();
		hiatus = new ArrayList<Show>();
		
	}
	
	static public FavoriteShows instance() {
		if (null == _instance) {
			_instance = new FavoriteShows();
		}
		return _instance;
	}
	
	public List<Show> getRunning() {
		return running;
	}
	
	public List<Show> getHiatus() {
		return hiatus;
	}
	
	public List<Show> getAll() {
		List<Show> newList = new ArrayList<Show>(running);
		newList.addAll(hiatus);
		return newList;
	}
	
	public boolean addShow(Show s) {
    	if (s != null) {
    		String next = s.getNextEpisode();
    		if (!next.equals("")) {
    			running.add(s);
    		}
    		else {
    			hiatus.add(s);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public boolean addShow(String id) {
		ShowParser parser = new ShowParser("http://www.tvrage.com/feeds/full_show_info.php?sid=" + id);
    	Show s = parser.parse();
    	if (s != null) {
    		String next = s.getNextEpisode();
    		if (!next.equals("")) {
    			running.add(s);
    		}
    		else {
    			hiatus.add(s);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	
	public boolean deleteShow(String id) {
		for (Show s : running) {
			if (s.getShowid().equals(id)) {
				running.remove(s);
				return true;
			}
		}
		for (Show s : hiatus) {
			if (s.getShowid().equals(id)) {
				hiatus.remove(s);
				return true;
			}
		}
		return false;
	}
	
}
