package it.univaq.aggm;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MatchService {
	
	private static Team getTeamFromElement(Element element) {
		int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
		String name = element.getElementsByTagName("name").item(0).getTextContent();
		return new Team(id, name);
	}
	
	public static Match getMatchByTeamNames(String localTeamName, String visitorTeamName) throws IOException, SAXException, ParserConfigurationException {
		Document doc = getDocument();
		NodeList list = doc.getElementsByTagName("Match"); // get matches from the document
		for (int temp = 0; temp < list.getLength(); temp++) {
			System.out.println("Analyzing " + temp + "th match of " + list.getLength());
			Node node = list.item(temp);
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
				 Element element = (Element) node;
				 Element localTeam = (Element) element.getElementsByTagName("localTeam").item(0);
				 Team local = getTeamFromElement(localTeam);
				 Element visitorTeam = (Element) element.getElementsByTagName("visitorTeam").item(0);
				 Team visitor = getTeamFromElement(visitorTeam);
				 System.out.println("Local team: " + local.getName() + " - Query: " + localTeamName);
				 System.out.println("Visitor team: " + visitor.getName() + " - Query: " + visitorTeamName);
				 if(local.getName().equals(localTeamName) && visitor.getName().equals(visitorTeamName)) {
					 System.out.println("prova");
					Match match = new Match();
					 match.setLocalTeam(local);
					 match.setVisitorTeam(visitor);
					 String coordinates = element.getElementsByTagName("coordinates").item(0).getTextContent();
					 System.out.println("Coords from element " + coordinates);
					 match.setCoordinates(coordinates);
					 return match;
				 }
			 } else {
				 System.out.println("Node problem");
			 }
		}
		return null;
	}
	
	public static ArrayList<Match> getTodayMatches() throws IOException, SAXException, ParserConfigurationException {
		ArrayList<Match> result = new ArrayList<Match>();
		Document doc = getDocument();
		 NodeList list = doc.getElementsByTagName("Match");
		 for (int temp = 0; temp < list.getLength(); temp++) {
			 Node node = list.item(temp);
			 if (node.getNodeType() == Node.ELEMENT_NODE) {
				 Element element = (Element) node;
				 Element localTeam = (Element) element.getElementsByTagName("localTeam").item(0);
				 Team local = getTeamFromElement(localTeam);
				 Element visitorTeam = (Element) element.getElementsByTagName("visitorTeam").item(0);
				 Team visitor = getTeamFromElement(visitorTeam);
				 String coordinates = element.getElementsByTagName("coordinates").item(0).getTextContent();
				 String localScore = element.getElementsByTagName("localScore").item(0).getTextContent();
				 String visitorScore = element.getElementsByTagName("visitorScore").item(0).getTextContent();
				 int localScoreInt = Integer.parseInt(localScore);
				 int visitorScoreInt = Integer.parseInt(visitorScore);
				 result.add(new Match(local, localScoreInt, visitor, visitorScoreInt, coordinates ));
			 }
		 }
		return result;
	}
	
	private static Document getDocument() throws IOException, SAXException, ParserConfigurationException {
		OkHttpClient client = new OkHttpClient();
		String url = "http://localhost:8081/matches";
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		String data = response.body().string();
		System.out.println(data.toString());
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(data)));
		doc.getDocumentElement().normalize();
		return doc;
	}
}
