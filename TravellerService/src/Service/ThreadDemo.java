package Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import weather.OpenWeatherMap;
import wikipedia.MediaWiki;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;

public class ThreadDemo implements Runnable {

    public static Thread t, t1;
    public static String threadName, threadName1, city, country, appid;

    ThreadDemo(String name, String name1,String city,String country, String appid) {
        threadName = name;
        threadName1 = name1;
        System.out.println("Creating " + threadName);
        System.out.println("Creating " + threadName1);
    }


    @Override
    public void run() {
        System.out.println("Running " +  threadName );
        /*try {
            RetrieveOpenWeatherMap(city,country,appid);
        } catch (IOException e) {
            e.printStackTrace();
        } */
        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");


        System.out.println("Running " +  threadName1 );
        /*try {
            RetrieveWikipedia(city);
        } catch (Exception e) {
            e.printStackTrace();
        } */

        try {
            for(int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName1 + ", " + i);
                // Let the thread sleep for a while.
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName1 + " interrupted.");
        }
        System.out.println("Thread " +  threadName1 + " exiting.");

    }


    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }

        System.out.println("Starting " +  threadName1 );
        if (t1 == null) {
            t1 = new Thread (this, threadName1);
            t1.start ();
        }

    }


    public static String RetrieveOpenWeatherMap(String city, String country, String appid) throws JsonParseException, JsonMappingException, IOException {

        //System.out.println("Creating " + threadName);
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q="+city+","+country+"&APPID="+appid+"").build());
        ObjectMapper mapper = new ObjectMapper();
        String json= service.accept(MediaType.APPLICATION_JSON).get(String.class);
        OpenWeatherMap weather_obj = mapper.readValue(json,OpenWeatherMap.class);
        System.out.println(city+" temperature: " + (weather_obj.getMain()).getTemp());
        City.setCriterion4(weather_obj.getCoord().getLat());
        City.setCriterion5(weather_obj.getCoord().getLon());
        System.out.println(city+" lat: " + City.getCriterion4()+" lon: " + City.getCriterion5());
        String cr4 = Double.toString(City.getCriterion4());
        String cr5 = Double.toString(City.getCriterion5());
        return cr4+"|"+cr5;
    }


    public static String RetrieveWikipedia(String city) throws  IOException, Exception {
        String article;

        //System.out.println("Creating " + threadName1);
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


}
