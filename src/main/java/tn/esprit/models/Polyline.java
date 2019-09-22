package tn.esprit.models;

public class Polyline {

	private String poly;
	private String distance;

	public Polyline() {
		// TODO Auto-generated constructor stub
	}

	public Polyline(String poly, String distance) {
		super();
		this.poly = poly;
		this.distance = distance;
	}

	public String getPoly() {
		return poly;
	}

	public void setPoly(String poly) {
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
