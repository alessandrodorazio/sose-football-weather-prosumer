package it.univaq.aggm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MatchWithWeather")
public class MatchWithWeather {
	private Match match;
	private String location;
	private Weather weather;
	
	public MatchWithWeather() {
		
	}
	
	public MatchWithWeather(Match match, String location, Weather weather) {
		this.match = match;
		this.location = location;
		this.weather = weather;
	}
	
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	
	
}
