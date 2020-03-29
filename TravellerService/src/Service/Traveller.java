package Service;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.lang.reflect.Array;

import org.apache.http.client.ClientProtocolException;

import weather.OpenWeatherMap;
import wikipedia.MediaWiki;



public class Traveller {
	
	
	// create Traveller constructor //
	public Traveller(String att1, String att2, double att3, double att4) {
		super();
		Att1 = att1;
		Att2 = att2;
		Att3 = att3;
		Att4 = att4;
	}
	
	// fields and variables //
	private static String Att1;
	private static String Att2;
	private static double Att3;
	private static double Att4;
	private static String PassengersAttribute;
	static String article="";
	
		
	public static void main(String[] args) throws ClientProtocolException, IOException, Exception {
		// TODO Auto-generated method stub
		
		String appid = "489be831b9526618635ad0ff71612c38";
		// create 2 traveller objects with 4 fields each (name, age, current lat, current lon) //
		Traveller t = new Business("Nikos", "30", 0.0, 0.1); 
		Traveller t1 = new Tourist("John", "40", 0.8, 0.9);
		// create 1 Business and 1 Tourist object in order to call override methods //
		Business b = new Business("George", "50", 1.0, 1.1);
		Tourist tr = new Tourist("Panos", "60", 2.0, 2.1);
		// create 2 city objects with 5 fields each (5 criteria);
		City c = new City("bars", "bike", "museums", 0.0d, 0.0d); 
		City c1 = new City("cafes", "cinema", "mountain", 0.0d, 0.0d);
		// create 1 arraylist with cities and 1 arraylist travellers //	
		ArrayList<String> cities = new ArrayList<String>(); 
		ArrayList<Object> travellers = new ArrayList<Object>();
		// add objects and strings in the two arraylists //
		travellers.add(t);
		travellers.add(t1);
		travellers.add(b);
		travellers.add(tr);
		cities.add("Berlin");
		cities.add("Rome");
		cities.add("Athens");
		cities.add("Madrid");
		cities.add("London");
		

		try (Scanner s = new Scanner(System.in)) {
			// Ask user for name, age //
			System.out.print("Type your name:");
			setAtt1(s.next());
			System.out.println("Type your age:");
			setAtt2(s.next());
			
			// Traveller's current lat and lot (Att3,Att4) typed from user //
			// City's lat and lon (Criterion4,Criterion5) calculated from the RetrieveOpenWeatherMap method //
			System.out.println("Type your current latitude and longtitude");
			setAtt3(s.nextDouble());
			setAtt4(s.nextDouble());
			
			System.out.println("Type first city's criterion");
			c.setCriterion1(s.next());
			System.out.println("Type second city's criterion");
			c.setCriterion2(s.next());
			System.out.println("Type third city's criterion");
			c.setCriterion3(s.next());
			
			// Criterion4,Criterion5 (lat,lon) will be given by OpenweatherMap //
			
			
			System.out.println("Type passenger's attribute: traveller, business or tourist"); 
				setPassengersAttribute(s.next());
				Boolean weather=null;
				// Depending on the passenger's attribute, execute the appropriate if statement //
				// After the appropriate if statement is executed then break from the while loop //
				while (true) {
					System.out.println("Type city's name you want to find, i.e Athens, Madrid etc.");
					String inputcity; 
					inputcity = s.next(); 
					cities.add(inputcity); 
					System.out.println("Type country's first two letters. i.e gr, sp, it etc.");
					String inputcountry;
					inputcountry = s.next();
					try {
						if(Traveller.getPassengersAttribute() == "traveller") {
							// Return the article for inputcity from RetrieveWikipedia //
							article=City.RetrieveWikipedia(inputcity);
							// Call OpenweatherMap and check city's temperature, lat and lon //
							City.RetrieveOpenWeatherMap(inputcity, inputcountry, appid);
							// checking for the similarity between city and traveller objects //
							t.Similarity(c, t);
							// checking if this traveller is eligible for a free ticket //
							t.FreeTicket(travellers, c, t);
							// Check if inputcity exists in arraylist cities //
							CompareCities(cities,article, c, t);
							// Check if it's raining in inputcity and if so exclude inputcity //
							CompareCities(weather,cities,inputcity);
							break;
						}
						if (Traveller.getPassengersAttribute() == "business") {
							// Return the article for inputcity from RetrieveWikipedia //
							article=City.RetrieveWikipedia(inputcity);
							// Call OpenweatherMap and check city's temperature, lat and lon //
							City.RetrieveOpenWeatherMap(inputcity, inputcountry, appid);
							// checking for the similarity between city and business objects //
							b.Similarity(c, b);
							// checking if business object is eligible for a free ticket //
							b.FreeTicket(travellers, c, b);
							// Check if inputcity exists in arraylist cities //
							CompareCities(cities,article, c, t);
							// Check if it's raining in inputcity and if so exclude inputcity //
							CompareCities(weather,cities,inputcity);
							break;
						} 
						if (Traveller.getPassengersAttribute() == "tourist") {
							// Return the article for inputcity from RetrieveWikipedia //
							article=City.RetrieveWikipedia(inputcity);
							// Call OpenweatherMap and check city's temperature, lat and lon //
							City.RetrieveOpenWeatherMap(inputcity, inputcountry, appid);
							// checking for the similarity between city and tourist objects // 
							tr.Similarity(c, tr);
							// checking if tourist is eligible for a free ticket //
							tr.FreeTicket(travellers, c, tr);
							// Check if inputcity exists in arraylist cities //
							CompareCities(cities,article, c, t);
							// Check if it's raining in inputcity and if so exclude inputcity //
							CompareCities(weather,cities,inputcity);
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("Incorrect fields, type again"); 
						inputcity=s.next();
						continue;
					}
				}		
		
		}
		
	}

	// setters and getters //
	
	public static String getAtt1() {
		return Att1;
	}


	public static void setAtt1(String att1) {
		Att1 = att1;
	}


	public static String getAtt2() {
		return Att2;
	}


	public static void setAtt2(String att2) {
		Att2 = att2;
	}

	public static double getAtt3() {
		return Att3;
	}

	public static void setAtt3(double att3) {
		Att3 = att3;
	}
	

	public static double getAtt4() {
		return Att4;
	}


	public static void setAtt4(double att4) {
		Att4 = att4;
	}

	
	public static String getPassengersAttribute() {
		return PassengersAttribute;
	}

	
	public static void setPassengersAttribute(String passengersAttribute) {
		PassengersAttribute = passengersAttribute;
	}

	
	// similarity method //
	// takes two objects as parameters //
	// returns the integer similarity that shows how many same values have object1 and object2 //
	public int Similarity(Object obj1, Object obj2) {
		 obj1=((String) obj1).toLowerCase();
		 int index = ((String) obj1).indexOf((String) obj2);
		 int similarity = 0;
		 while (index != -1) {
			 similarity++;
	    	 obj1 = ((String) obj1).substring(index + 1);
	    	 index = ((String) obj1).indexOf((String) obj2);
		 }
		 if (similarity>0) {
			 System.out.println("Similar values found:" +similarity);
			 return similarity;
		 } else {
			 System.out.println("No similar values between City and Traveller's criteria");
			 return 0;
		 }
	  } 
	 
	// CompareCities method //
	// Takes as parameter an arraylist of strings, a string and the two objects //
	// Create instance of class in order to call the non static method Similarity // 
	// Returns the string only if is found in the arraylist //
	public static void CompareCities(ArrayList<String> arraylist, String maxcity, Object ob1, Object ob2) {
		Traveller instance = new Traveller(Att1, Att2, Att3, Att4);
		if ((arraylist.contains(maxcity)) && (instance.Similarity(ob1, ob2) == 5))  {
			System.out.println("City found:" +maxcity);
		} else {
			System.out.println("No city matches with the list's cities");
		}
		
	}
	
	
	// Overloaded CompareCities method (same method type, different number of parameters) //
	// Takes as parameter a boolean weather, an arraylist of strings and a string //
	// If it is raining in the city, boolean weather  becomes true and the city is excluded //
	public static void CompareCities(Boolean weather, ArrayList<String> list, String maxcity) {
		if ((maxcity.contains("rain")) && (list.contains("rain"))) {
			weather=true;
			System.out.println("It is raining in the city so is excuded, rain =" +weather);
		} else {
			weather=false;
			System.out.println("No rain found in the city");
		}
	}
	
	
	// Free ticket method //
	// Takes as parameter an arraylist of object and two objects //
	// If similarity number is 5 (max) and the second object is contained in the list, then the traveller can get free ticket //
	// getAtt1 = name , getAtt2 = age //
	public void FreeTicket(ArrayList<Object> travellers, Object k, Object n) {
		int i;
		for (i=0; i<travellers.size(); i++) {
			if((Similarity(k, n) == 5) && (travellers.contains(n))) {
				travellers.get(i);
				System.out.println("The eligible traveller for a free ticket is:" +Traveller.getAtt1() +Traveller.getAtt2());
			} 
		}
	}
	
	
}


