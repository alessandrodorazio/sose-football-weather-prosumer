package it.univaq.aggm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WeatherService {
	private static String getElementValue(String tagName, String xmlString) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
            Element rootElement = document.getDocumentElement();

            NodeList list = rootElement.getElementsByTagName(tagName);

            if (list != null && list.getLength() > 0) {
                NodeList subList = list.item(0).getChildNodes();

                if (subList != null && subList.getLength() > 0) {
                    return subList.item(0).getNodeValue();
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
	
	public static Weather getData(String cityName) throws IOException, ParserConfigurationException, SAXException {
		OkHttpClient client = new OkHttpClient();
		String url = "http://localhost:8080/cities/getWeather/" + cityName;
		Request request = new Request.Builder().url(url).get().build();
		Response response = client.newCall(request).execute();
		String data = response.body().string();
		Weather weather = new Weather();
		weather.setName(getElementValue("name", data));
		weather.setTemperature(getElementValue("temperature", data));
		return weather;
	}
}
