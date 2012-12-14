package com.sana.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Episode implements Serializable {
	private String epNum;
	private String seasonNum;
	private String prodNum;
	private String link;
	private String title;
	private String airDate;
	private String screencap;
	
	public Episode() {
		this.epNum = "";
		this.seasonNum = "";
		this.prodNum = "";
		this.link = "";
		this.title = "";
		this.airDate = "";
		this.screencap = "";
	}

	public void setEpNum(String epNum) {
		this.epNum = epNum;
	}

	public String getEpNum() {
		return epNum;
	}

	public void setSeasonNum(String seasonNum) {
		this.seasonNum = seasonNum;
	}

	public String getSeasonNum() {
		return seasonNum;
	}

	public void setProdNum(String prodNum) {
		this.prodNum = prodNum;
	}

	public String getProdNum() {
		return prodNum;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}

	public String getAirDate() {
		return airDate;
	}
	
	public String getNiceDate() {
		SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNew = new SimpleDateFormat("EEEE, MM/dd");
		Date date;
		try {
			date = sdfSource.parse(this.airDate);
			return sdfNew.format(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return this.airDate;
		}

	}

	public void setScreencap(String screencap) {
		this.screencap = screencap;
	}

	public String getScreencap() {
		return screencap;
	}

	public Episode copy() {
		Episode copy = new Episode();
		copy.setTitle(title);
		copy.setLink(link);
		copy.setEpNum(epNum);
		copy.setAirDate(airDate);
		copy.setProdNum(prodNum);
		copy.setSeasonNum(seasonNum);
		copy.setScreencap(screencap);
		return copy;
	}
	
	public String toString() {
		return this.seasonNum + " " + this.title;
	}
}
