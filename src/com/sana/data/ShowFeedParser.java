package com.sana.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class ShowFeedParser {
    /* Names of XML Tags */
	// Root
	static final  String SHOW = "Show";
 
	// Show
	static final  String NAME = "name";
    static final  String TOTALSEASONS = "totalseasons";
    static final  String SHOWID = "showid";
    static final  String SHOWLINK = "showlink";
    static final  String STARTED = "started";
    static final  String ENDED = "ended";
    static final  String ORIGIN_COUNTRY = "origin_country";
    static final  String STATUS = "status";
    static final  String CLASSIFICATION = "classification";
    static final  String RUNTIME = "runtime";
    static final  String NETWORK = "network";
    static final  String AIRTIME = "airtime";
    static final  String AIRDAY = "airday";
    static final  String TIMEZONE = "timezone";
    static final  String GENRES = "genres";
    static final  String EPISODELIST = "Episodelist";
    static final  String AKAS = "akas";
    
    // Genres
    static final  String GENRE = "genre";
    
    // Akas
    static final String AKA = "aka";
    
    // Episodelist
	static final String SEASON = "Season";
	
	// Season
    static final  String EPISODE = "episode";
    
    // Episode
    static final  String EPNUM = "epnum";
    static final  String SEASONNUM = "seasonnum";
    static final  String PRODNUM = "prodnum";
    static final  String AIRDATE = "airdate";
    static final  String LINK = "link";
    static final  String TITLE = "title";
    static final  String SCREENCAP = "screencap";
    
	URL feedUrl;
	
	public ShowFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            System.out.println("ShowFeedParser line 57: " + e);
            this.feedUrl = null;
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println("ShowFeedParser line 66: " + e);
            return null;
        }
    }
}