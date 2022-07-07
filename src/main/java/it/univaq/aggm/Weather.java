package it.univaq.aggm;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Weather")
public class Weather {
	private String name;
	private String temperature;
	private String description;
	
	public Weather() {
		
	}
	
	public Weather(String name, String temperature, String description) {
		this.name = name;
		this.temperature = temperature;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
