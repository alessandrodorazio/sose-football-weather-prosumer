package it.univaq.aggm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@Path("football-weather")
@Produces("text/xml")
@WebService(endpointInterface="it.univaq.aggm.ServiceRepositoryInterface")
public class ServiceRepository implements ServiceRepositoryInterface {

	@GET @Path("/weather-by-match")
	public Weather getWeatherByMatch(@QueryParam("localTeamName") String localTeamName, @QueryParam("visitorTeamName") String visitorTeamName) throws IOException, ParserConfigurationException, SAXException {
		Match match = MatchService.getMatchByTeamNames(localTeamName, visitorTeamName);
		if(match!=null) {
			return WeatherService.getData(match.getCoordinates());
		}
		return new Weather();
	}
	
	@GET @Path("/matches-with-weather")
	public ArrayList<MatchWithWeather> matchesWithWeather() throws IOException, ParserConfigurationException, SAXException {
		System.out.println("Football weather prosumer 1");
		ArrayList<MatchWithWeather> result = new ArrayList<MatchWithWeather>();
		ArrayList<Match> matches = MatchService.getTodayMatches(); //get matches of the day
		for(int i=0; i<matches.size(); i++) {
			Match match = matches.get(i);
			String location = matches.get(i).getCoordinates();
			Weather weather = WeatherService.getData(location); //call weather service
			result.add(new MatchWithWeather(match, location, weather));
		}
		return result;
	}

}
