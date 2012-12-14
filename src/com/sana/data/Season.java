package com.sana.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Season implements Serializable {
	private String no;
	private List<Episode> episodes;

	public Season() {
		this.no = "";
		this.episodes = new ArrayList<Episode>();
	}
	
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * @return the episodes
	 */
	public List<Episode> getEpisodes() {
		return episodes;
	}
	/**
	 * @param episodes the episodes to set
	 */
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = new ArrayList<Episode>();
		for (Episode e : episodes)
			this.episodes.add(e);
	}
	
	public Season copy() {
		Season copy = new Season();
		copy.setNo(no);
		copy.setEpisodes(this.episodes);
		return copy;
	}

	public Episode getLastEpisode() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(cal.getTime());
		Episode last = null;
		if (this.episodes.isEmpty()) { return null; }
			for (Episode e : this.episodes)
				if (e.getAirDate().compareTo(today) < 0)
					last = e;
		return last;
	}
	
	public Episode getNextEpisode() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(cal.getTime());
		Episode next = null;
		if (this.episodes.isEmpty()) { return null; }
		for (Episode e : this.episodes)
			if (e.getAirDate().compareTo(today) >= 0) {
				next = e;
				break;
			}
		return next;
	}
	
	public String getDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date();
		Date end = new Date();
		if (this.episodes.isEmpty()) { return ""; }
		try {
			start = sdf.parse(episodes.get(0).getAirDate());
			end = sdf.parse(episodes.get(episodes.size()-1).getAirDate());
		} catch (ParseException e) {
		}
		SimpleDateFormat newD = new SimpleDateFormat("yyyy");
		String startS = newD.format(start);
		String endS = newD.format(end);
		if (startS.compareTo("1930") < 0) return "";
		else if (startS.equals(endS)) return "(" + startS + ")";
		else return "(" + startS + " - " + endS + ")";
	}
	
	public String toString() {
		return "Season " + this.no + " " + getDates();
	}
}
