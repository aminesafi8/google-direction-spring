package tn.esprit.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tn.esprit.models.MapPayload;
import tn.esprit.models.Polyline;

@Service
public class GoogleDirectionService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${google.direction.api.key}")
	private String key;
	private static final String BASE_URL_GOOGLE_DIRECTION = "https://maps.googleapis.com/maps/api/directions/json?";

	public MapPayload getPolyAndDistance(Double fromLat, Double fromLong, Double toLat, Double toLong) {
		String link = BASE_URL_GOOGLE_DIRECTION + "origin=" + fromLat + "," + fromLong + "&destination=" + toLat + ","
				+ toLong + "&key=" + key;
		String jsonString = restTemplate.getForEntity(link, String.class).getBody();
		JSONObject json = new JSONObject(jsonString);
		JSONArray arrayAsString = new JSONArray(
				json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").toString());
		Double startLocationLat = Double
				.valueOf(arrayAsString.getJSONObject(0).getJSONObject("start_location").get("lat").toString());
		Double startLocationLng = Double
				.valueOf(arrayAsString.getJSONObject(0).getJSONObject("start_location").get("lng").toString());
		Double endLocationLat = Double
				.valueOf(arrayAsString.getJSONObject(0).getJSONObject("end_location").get("lat").toString());
		Double endLocationLng = Double
				.valueOf(arrayAsString.getJSONObject(0).getJSONObject("end_location").get("lng").toString());
		String distanceAsString = arrayAsString.getJSONObject(0).getJSONObject("distance").get("text").toString();
		MapPayload response = new MapPayload(
				new Polyline(startLocationLat, startLocationLng, endLocationLat, endLocationLng), distanceAsString);
		return response;

	}

	public Map<String, String> calculateHaversineDistance(Double fromLat, Double fromLong, Double toLat,
			Double toLong) {
		int R = 6371; // Radius of the earth
		Double latDistance = toRad(toLat - fromLat);
		Double lonDistance = toRad(toLong - fromLong);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRad(fromLat))
				* Math.cos(toRad(toLat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
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
