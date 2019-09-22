package tn.esprit.models;

public class MapPayload {

	private Polyline poly;
	private String distance;

	public MapPayload() {
		// TODO Auto-generated constructor stub
	}

	public MapPayload(Polyline poly, String distance) {
		super();
		this.poly = poly;
		this.distance = distance;
	}

	public Polyline getPoly() {
		return poly;
	}

	public void setPoly(Polyline poly) {
		this.poly = poly;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Polyline [poly=" + poly + ", distance=" + distance + "]";
	}

}
