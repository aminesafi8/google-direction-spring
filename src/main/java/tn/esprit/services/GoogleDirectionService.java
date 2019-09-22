package tn.esprit.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tn.esprit.models.Polyline;

@Service
public class GoogleDirectionService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${google.direction.api.key}")
	private String key;
	private static final String BASE_URL_GOOGLE_DIRECTION = "https://maps.googleapis.com/maps/api/directions/json?";

	public List<Polyline> getPolyAndDistance(Double fromLat, Double fromLong, Double toLat, Double toLong) {
		String link = BASE_URL_GOOGLE_DIRECTION + "origin=" + fromLat + "," + fromLong + "&destination=" + toLat + ","
				+ toLong + "&key=" + key;

		String jsonString = restTemplate.getForEntity(link, String.class).getBody();
		JSONObject json = new JSONObject(jsonString);

		JSONArray arrayAsString = new JSONArray(json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs")
				.getJSONObject(0).getJSONArray("steps").toString());

		List<Polyline> polylines = new ArrayList<Polyline>();

		for (int i = 0; i < arrayAsString.length(); i++) {

			String polylinePoints = arrayAsString.getJSONObject(i).getJSONObject("polyline").get("points").toString();
			String distanceAsString = arrayAsString.getJSONObject(i).getJSONObject("distance").get("text").toString();
			int distanceAsInt = arrayAsString.getJSONObject(i).getJSONObject("distance").getInt("value");

			if (!distanceAsString.contains("km"))  {
				float distance = ((float) distanceAsInt) / 1000;
				String distanceWithTwoDecimals = String.format("%2.02f", distance);
				polylines.add(new Polyline(polylinePoints, distanceWithTwoDecimals + " km"));
			} else
				polylines.add(new Polyline(polylinePoints, distanceAsString));

		}

		return polylines;

	}
	
	public Map<String,String> calculateHaversineDistance(Double fromLat, Double fromLong, Double toLat, Double toLong){
		int R = 6371; // Radius of the earth
		Double latDistance = toRad(toLat - fromLat);
		Double lonDistance = toRad(toLong - fromLong);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(toRad(fromLat)) * Math.cos(toRad(toLat))
						* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double haversineDistance = R * c;

		Map<String, String> jsonObject = new HashMap<String, String>();

		jsonObject.put("fromLat", fromLat.toString());
		jsonObject.put("fromLong", fromLong.toString());
		jsonObject.put("toLat", toLat.toString());
		jsonObject.put("toLong", toLong.toString());
		jsonObject.put("haversineDistance", haversineDistance + " km");
		
		return jsonObject;
		
	}

	public Double toRad(Double value) {
		return value * Math.PI / 180;
	}

}
