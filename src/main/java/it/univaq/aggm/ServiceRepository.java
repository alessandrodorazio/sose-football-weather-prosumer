package it.univaq.aggm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

@Path("cities")
@Produces("text/xml")
@WebService(endpointInterface="it.univaq.aggm.ServiceRepositoryInterface")
public class ServiceRepository implements ServiceRepositoryInterface {

	@GET
	@Path("/get")
	public ArrayList<MatchWithWeather> matchesWithWeather() throws IOException, ParserConfigurationException, SAXException {
		ArrayList<MatchWithWeather> result = new ArrayList<MatchWithWeather>();
		ArrayList<Match> matches = MatchService.getTodayMatches();
		for(int i=0; i<matches.size(); i++) {
			MatchWithWeather mw = new MatchWithWeather();
			mw.setMatch(matches.get(i));
			mw.setLocation(matches.get(i).getCoordinates());
			mw.setWeather(WeatherService.getData(mw.getLocation())); //call weather service
			result.add(mw);
		}
		return result;
	}

}
