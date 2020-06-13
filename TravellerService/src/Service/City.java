package Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

public class City {



    // create city constructor
    public City(String criterion1, String criterion2, String criterion3, double criterion4, double criterion5) {
        super();
        Criterion1 = criterion1;
        Criterion2 = criterion2;
        Criterion3 = criterion3;
        Criterion4 = criterion4;
        Criterion5 = criterion5;
    }

    private static String Criterion1;
    private static String Criterion2;
    private static String Criterion3;
    private static double Criterion4;
    private static double Criterion5;


    // setters and getters

    public static String getCriterion1() {
        return Criterion1;
    }

    public static void setCriterion1(String criterion1) {
        Criterion1 = criterion1;
    }

    public static String getCriterion2() {
        return Criterion2;
    }

    public static void setCriterion2(String criterion2) {
        Criterion2 = criterion2;
    }

    public static String getCriterion3() {
        return Criterion3;
    }

    public static void setCriterion3(String criterion3) {
        Criterion3 = criterion3;
    }

    public static double getCriterion4() {
        return Criterion4;
    }

    public static void setCriterion4(double criterion4) {
        Criterion4 = criterion4;
    }

    public static double getCriterion5() {
        return Criterion5;
    }

    public static void setCriterion5(double criterion5) {
        Criterion5 = criterion5;
    }


    // Retrieve data from Open weather map
    // Takes three strings as parameters
    // Returns city's temperature, latitude(getCriterion4), longitude (getCriterion5)
    public static String RetrieveOpenWeatherMap(String city, String country, String appid) throws JsonParseException, JsonMappingException, IOException {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+"").build());
        ObjectMapper mapper = new ObjectMapper();
        String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
        OpenWeatherMap weather_obj = mapper.readValue(json,OpenWeatherMap.class);
        System.out.println(city+" temperature: " + (weather_obj.getMain()).getTemp());
        setCriterion4(weather_obj.getCoord().getLat());
        setCriterion5(weather_obj.getCoord().getLon());
        System.out.println(city+" lat: " + getCriterion4()+" lon: " + getCriterion5());
        String cr4 = Double.toString(getCriterion4());
        String cr5 = Double.toString(getCriterion5());
        return cr4+"|"+cr5;
    }

    // Retrieve data for city from Wikipedia
    // Takes a string as input
    // Returns the matches city article
    public static String RetrieveWikipedia(String city) throws  IOException, Exception {
        String article;
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles="+city+"&format=json&formatversion=2").build());
        ObjectMapper mapper = new ObjectMapper();
        String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
        if (json.contains("pageid")) {
            MediaWiki mediaWiki_obj =  mapper.readValue(json, MediaWiki.class);
            article= mediaWiki_obj.getQuery().getPages().get(0).getExtract();
            System.out.println(city+" Wikipedia article: "+article);
        } else throw new Exception(city);
        return article;
    }

    // take cityname as parameter and a map
    // create a candiate object and if user input fields do not already exist in map then add them
    public static void CheckCityInCollection(String cityName, HashMap<String, City> map) {
        Scanner s = new Scanner(System.in);
        City cand = new City("", "", "", 0.0d, 0.0d);
        System.out.println("Type Cityname");
        City.setCriterion1(cityName);
        System.out.println("Type Criterion 2");
        City.setCriterion2(s.next());
        System.out.println("Type Criterion 3");
        City.setCriterion3(s.next());
        try {
            System.out.println("Type Criterion 4");
            setCriterion4(s.nextDouble());
            System.out.println("Type Criterion 5");
            setCriterion5(s.nextDouble());
        } catch (InputMismatchException i) {
            i.printStackTrace();
        }
        for (Map.Entry<String, City> entry : map.entrySet()) {
            boolean check = map.containsValue(cand);
            if(!check) {
                map.put(cityName, cand);
                System.out.println("City added successfully");
            } else {
                System.out.println("CityName already exists or not found in the collection");
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this== o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        System.out.println("City - HashCode");
        int hash = 5;
        hash = 31 * hash + Criterion1.hashCode();
        hash = 31 * hash + Criterion2.hashCode();
        hash = 31 * hash + Criterion2.hashCode();
        long cr4long = Double.doubleToLongBits(Criterion4);
        long cr5long = Double.doubleToLongBits(Criterion5);
        hash = 31 * hash + (int) (cr4long ^ (cr4long >>> 32));
        hash = 31 * hash + (int) (cr5long ^ (cr5long >>> 32));
        return hash;
    }


}
