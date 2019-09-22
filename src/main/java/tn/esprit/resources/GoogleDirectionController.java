package tn.esprit.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tn.esprit.models.Polyline;
import tn.esprit.services.GoogleDirectionService;

@RestController
@RequestMapping("direction")
public class GoogleDirectionController {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	GoogleDirectionService googleDirectionService;

	@RequestMapping(path = "getGoogleDirections", method = RequestMethod.GET)
	public ResponseEntity<List<Polyline>> getPolyAndDistance(@RequestParam(name = "fromLat") Double fromLat,
			@RequestParam(name = "fromLong") Double fromLong, @RequestParam(name = "toLat") Double toLat,
			@RequestParam(name = "toLong") Double toLong) {

		List<Polyline> polylines = googleDirectionService.getPolyAndDistance(fromLat, fromLong, toLat, toLong);

		return new ResponseEntity<List<Polyline>>(polylines, HttpStatus.OK);
	}

	@RequestMapping(path = "getDistance", method = RequestMethod.GET)
	public ResponseEntity<?> getHaversineDistance(@RequestParam(name = "fromLat") Double fromLat,
			@RequestParam(name = "fromLong") Double fromLong, @RequestParam(name = "toLat") Double toLat,
			@RequestParam(name = "toLong") Double toLong) {

		return new ResponseEntity<>(googleDirectionService.calculateHaversineDistance(fromLat, fromLong, toLat, toLong),
				HttpStatus.OK);
	}

}
