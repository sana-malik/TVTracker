package com.sana.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class SearchFeedParser {
    /* Names of XML Tags */
	// Root
	static final  String RESULTS = "Results";
 
	// Show
	static final  String SHOW = "show";
    static final  String SHOWID = "showid";
    
    URL feedUrl;
	
	public SearchFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            System.out.println("SearchFeedParser line 21: " + e);
            this.feedUrl = null;
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println("SearchFeedParser line 29: " + e);
            return null;
        }
    }
}