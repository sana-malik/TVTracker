package com.sana.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.text.Html;

public class Show implements Serializable {
	private String name;
	private String totalseasons;
	private String showid;
	private String showlink;
	private String started;
	private String ended;
	private String origin_country;
	private String status;
	private String classification;
	private List<String> genres;
	private String runtime;
	private String network;
	private String airtime;
	private String airday;
	private String timezone;
	private List<String> akas;
	private List<Season> episodelist;
	
	public Show() {
		this.genres = new ArrayList<String>();
		this.akas = new ArrayList<String>();
		this.episodelist = new ArrayList<Season>();
		this.name = "";
		this.totalseasons = "";
		this.showid = "";
		this.showlink = "";
		this.started = "";
		this.ended = "";
		this.origin_country = "";
		this.status = "";
		this.classification = "";
		this.runtime = "";
		this.network = "";
		this.airtime = "";
		this.airday = "";
		this.timezone = "";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * @return the totalseasons
	 */
	public String getTotalseasons() {
		return totalseasons;
	}

	/**
	 * @param totalseasons the totalseasons to set
	 */
	public void setTotalseasons(String totalseasons) {
		this.totalseasons = totalseasons;
	}

	/**
	 * @return the showid
	 */
	public String getShowid() {
		return showid;
	}

	/**
	 * @param showid the showid to set
	 */
	public void setShowid(String showid) {
		this.showid = showid;
	}

	/**
	 * @return the showlink
	 */
	public String getShowlink() {
		return showlink;
	}

	/**
	 * @param showlink the showlink to set
	 */
	public void setShowlink(String showlink) {
		this.showlink = showlink;
	}

	/**
	 * @return the started
	 */
	public String getStarted() {
		return started;
	}

	/**
	 * @param started the started to set
	 */
	public void setStarted(String started) {
		this.started = started;
	}

	/**
	 * @return the ended
	 */
	public String getEnded() {
		return ended;
	}

	/**
	 * @param ended the ended to set
	 */
	public void setEnded(String ended) {
		this.ended = ended;
	}

	/**
	 * @return the origin_country
	 */
	public String getOrigin_country() {
		return origin_country;
	}

	/**
	 * @param originCountry the origin_country to set
	 */
	public void setOrigin_country(String originCountry) {
		origin_country = originCountry;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the genres
	 */
	public List<String> getGenres() {
		return genres;
	}

	public String getGenreString() {
		String genreStr = "";
		for (String g : this.genres) {
			genreStr = genreStr + g + ", "; 
		}
		if (genreStr.equals("")) return "";
		return genreStr.substring(0, genreStr.length()-2);
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = new ArrayList<String>();
		for (String s : genres) {
			this.genres.add(s);
		}
	}

	/**
	 * @return the runtime
	 */
	public String getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	/**
	 * @return the network
	 */
	public String getNetwork() {
		return network;
	}

	/**
	 * @param network the network to set
	 */
	public void setNetwork(String network) {
		this.network = network;
	}

	/**
	 * @return the airtime
	 */
	public String getAirtime() {
		return airtime;
	}

	/**
	 * @param airtime the airtime to set
	 */
	public void setAirtime(String airtime) {
		this.airtime = airtime;
	}

	/**
	 * @return the airday
	 */
	public String getAirday() {
		return airday;
	}

	/**
	 * @param airday the airday to set
	 */
	public void setAirday(String airday) {
		this.airday = airday;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getNiceTime() {
		SimpleDateFormat sdfSource = new SimpleDateFormat("H:m");
		SimpleDateFormat sdfNew = new SimpleDateFormat("h:mm a");
		Date date;
		if (this.airtime.equals("")) { return ""; }
		try {
			date = sdfSource.parse(this.airtime);
			return sdfNew.format(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return this.airtime;
		}

	}
	
	public String getAirInfo() {
		if (!this.getNextEpisode().equals(""))
			return this.airday + " at " + this.getNiceTime() + " on " + this.network;
		else
			return this.status;
	}
	
	/**
	 * @return the akas
	 */
	public List<String> getAkas() {
		return akas;
	}

	/**
	 * @param akas the akas to set
	 */
	public void setAkas(List<String> akas) {
		this.akas = new ArrayList<String>();
		for (String s : akas) {
			this.akas.add(s);
		}
	}

	/**
	 * @return the episodelist
	 */
	public List<Season> getEpisodelist() {
		return episodelist;
	}

	/**
	 * @param episodelist the episodelist to set
	 */
	public void setEpisodelist(List<Season> episodelist) {
		this.episodelist = new ArrayList<Season>();
		for (Season s : episodelist)
			this.episodelist.add(s.copy());
	}
	
	public Season getCurrentSeason() {
		Season currentSeason = null;
		if (this.episodelist.isEmpty()) return null;
		for (Season s : this.episodelist) {
			if (s.getNo().equals(this.totalseasons)) {
				currentSeason = s;
				break;
			}
		}
		return currentSeason;
	}
	
	public String getLastEpisode() {
		Season currentSeason = this.getCurrentSeason();
		String last = "";
		
		if (currentSeason != null) {
			Episode ep = currentSeason.getLastEpisode();
			
			if (ep != null) 
				last = this.getTotalseasons() + "x" + ep.getSeasonNum() 
						+ " - " +  ep.getTitle() + " ("
					    + ep.getNiceDate() + ")";
		}
		return last;
	}
	
	public String getLastEpisodeHi() {
		Season currentSeason = this.getCurrentSeason();
		String last = "";
		
		if (currentSeason != null) {
			Episode ep = currentSeason.getLastEpisode();
			
			if (ep != null) 
				last = this.getTotalseasons() + "x" + ep.getSeasonNum() 
						+ " - " +  ep.getTitle() + " ("
					    + ep.getAirDate() + ")";
		}
		return last;
	}
	
	public String getNextEpisode() {
		Season currentSeason = this.getCurrentSeason();
		String next = "";
		
		if (currentSeason != null) {
			Episode ep = currentSeason.getNextEpisode();
				if (ep != null)
				next = this.getTotalseasons() + "x" + ep.getSeasonNum()
					    + " - " +  ep.getTitle() + " ("
					    + ep.getNiceDate() + ")";
		}
		return next;
	}
	
	public Show copy() {
		Show copy = new Show();
		copy.name = this.getName();
		copy.totalseasons = this.getTotalseasons();
		copy.showid = this.getShowid();
		copy.showlink = this.getShowlink();
		copy.started = this.getStarted();
		copy.ended = this.getEnded();
		copy.origin_country = this.getOrigin_country();
		copy.status = this.getStatus();
		copy.classification = this.getClassification();
		copy.setGenres(this.getGenres());
		copy.runtime = this.getRuntime();
		copy.network = this.getNetwork();
		copy.airtime = this.getAirtime();
		copy.airday = this.getAirday();
		copy.timezone = this.getTimezone();
		copy.setAkas(this.getAkas());
		copy.setEpisodelist(this.getEpisodelist());
		return copy;
	}
}
