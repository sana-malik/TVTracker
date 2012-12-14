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

public class ShowParser extends ShowFeedParser {
	public ShowParser(String feedUrl){
		super(feedUrl);
	}
	
	public Show parse() {
		final Show show = new Show();
		RootElement root = new RootElement(SHOW);
		
		/* Parse show info */
		root.getChild(NAME).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setName(body);
			}
		});
		root.getChild(TOTALSEASONS).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setTotalseasons(body);
			}
		});	
		
		root.getChild(SHOWID).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setShowid(body);
			}
		});	
		
		root.getChild(SHOWLINK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setShowlink(body);
			}
		});
		
		root.getChild(STARTED).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setStarted(body);
			}
		});
		
		root.getChild(ENDED).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setEnded(body);
			}
		});
		
		root.getChild(ORIGIN_COUNTRY).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setOrigin_country(body);
			}
		});
		
		root.getChild(STATUS).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setStatus(body);
			}
		});
		
		root.getChild(CLASSIFICATION).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setClassification(body);
			}
		});
		
		root.getChild(RUNTIME).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setRuntime(body);
			}
		});
		
		root.getChild(NETWORK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setNetwork(body);
			}
		});
		
		root.getChild(AIRTIME).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setAirtime(body);
			}
		});
		
		root.getChild(AIRDAY).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setAirday(body);
			}
		});
		
		root.getChild(TIMEZONE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				show.setTimezone(body);
			}
		});
		
		// Genres
		final List<String> genres = new ArrayList<String>();
		Element genre = root.getChild(GENRES);
		
		genre.setEndElementListener(new EndElementListener(){
			public void end() {
				show.setGenres(genres);
			}
		});
		genre.getChild(GENRE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				genres.add(body);
			}
		});
		
		// AKAs
		final List<String> akas = new ArrayList<String>();
		Element aka = root.getChild(AKAS);
		
		aka.setEndElementListener(new EndElementListener(){
			public void end() {
				show.setAkas(akas);
			}
		});
		aka.getChild(AKA).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				akas.add(body);
			}
		});
		
		// Episode list
		final List<Season> episodelistL = new ArrayList<Season>();
		final Season currentSeason = new Season();
		final List<Episode> currentEpisodeL = new ArrayList<Episode>();
		final Episode currentEpisode = new Episode();
		
		Element episodelist = root.getChild(EPISODELIST);
		episodelist.setEndElementListener(new EndElementListener(){
			public void end() {
				show.setEpisodelist(episodelistL);
			}
		});
		
		Element season = episodelist.getChild(SEASON);
		
		season.setStartElementListener(new StartElementListener() {
			public void start(Attributes a) {
				currentEpisodeL.clear();
				currentSeason.setNo(a.getValue("no"));
			}
		});
		season.setEndElementListener(new EndElementListener(){
			public void end() {
				currentSeason.setEpisodes(currentEpisodeL);
				episodelistL.add(currentSeason.copy());
			}
		});
		
		Element episode = season.getChild(EPISODE);
		episode.setEndElementListener(new EndElementListener(){
			public void end() {
				currentEpisodeL.add(currentEpisode.copy());
			}
		});
		episode.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setTitle(body);
			}
		});
		episode.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setLink(body);
			}
		});
		episode.getChild(EPNUM).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setEpNum(body);
			}
		});
		episode.getChild(SEASONNUM).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setSeasonNum(body);
			}
		});
		episode.getChild(PRODNUM).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setProdNum(body);
			}
		});
		episode.getChild(AIRDATE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setAirDate(body);
			}
		});
		episode.getChild(SCREENCAP).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentEpisode.setScreencap(body);
			}
		});
		
		try {
			if (this.getInputStream() != null) {
				Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
		return show;
	}
}