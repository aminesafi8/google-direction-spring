package tn.esprit.models;

public class Polyline {
	private Double startLocationLat;
	private Double startLocationLng;
	private Double endLocationLat;
	private Double endLocationLng;

	public Polyline() {
		// TODO Auto-generated constructor stub
	}

	public Polyline(Double startLocationLat, Double startLocationLng, Double endLocationLat, Double endLocationLng) {
		super();
		this.startLocationLat = startLocationLat;
		this.startLocationLng = startLocationLng;
		this.endLocationLat = endLocationLat;
		this.endLocationLng = endLocationLng;
	}

	public Double getStartLocationLat() {
		return startLocationLat;
	}

	public void setStartLocationLat(Double startLocationLat) {
		this.startLocationLat = startLocationLat;
	}

	public Double getStartLocationLng() {
		return startLocationLng;
	}

	public void setStartLocationLng(Double startLocationLng) {
		this.startLocationLng = startLocationLng;
	}

	public Double getEndLocationLat() {
		return endLocationLat;
	}

	public void setEndLocationLat(Double endLocationLat) {
		this.endLocationLat = endLocationLat;
	}

	public Double getEndLocationLng() {
		return endLocationLng;
	}

	public void setEndLocationLng(Double endLocationLng) {
		this.endLocationLng = endLocationLng;
	}

	@Override
	public String toString() {
		return "Polyline [startLocationLat=" + startLocationLat + ", startLocationLng=" + startLocationLng
				+ ", endLocationLat=" + endLocationLat + ", endLocationLng=" + endLocationLng + "]";
	}

}
