package Service;

import static Service.City.getCriterion4;
import static Service.City.getCriterion5;

// inheritance (Business class is child of Traveller class)
public class Business extends Traveller {

	public Business(String att1, int att2, double att3, double att4) {
		super(att1, att2, att3, att4);
		// TODO Auto-generated constructor stub
	}
	
	
	// Calculate distance based on current lat lot and destination's lat lon
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
	
	// Override Similarity method of business class (same return type, same object types)
	// Takes two objects as parameter
	// Pass the calculated distance  in a double variable and makes casting in order to return the result as integer
	// Getcriterion4() and GetCriterion5() have been calculated from the RetrieveOpenWeatherMap method
	@Override
	public int Similarity(Object obj1,Object obj2) {
		double dist;
		dist = Business.distance(getAtt3(), getAtt4(), getCriterion4(), getCriterion5(), "K");
		int sim = (int) dist;
		return sim;
	}

}

