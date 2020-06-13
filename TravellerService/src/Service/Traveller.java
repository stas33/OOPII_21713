package Service;

import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.util.Map.Entry;
import java.lang.Throwable.*;

import org.apache.http.client.ClientProtocolException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


public class Traveller implements Comparable<Traveller>{


    // create Traveller constructor
    public Traveller(String att1, int att2, double att3, double att4) {
        super();
        Att1 = att1;
        Att2 = att2;
        Att3 = att3;
        Att4 = att4;
    }

    // fields and variables
    private static String Att1;
    private static int Att2;
    private static double Att3;
    private static double Att4;
    private static String PassengersAttribute;


    // setters and getters

    public static String getAtt1() {
        return Att1;
    }


    public static void setAtt1(String att1) {
        Att1 = att1;
    }


    public static int getAtt2() {
        return Att2;
    }


    public static void setAtt2(int att2) {
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


    // similarity method
    // takes two objects as parameters
    // returns the integer similarity that shows how many same values have object1 and object2
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

    // CompareCities method
    // Takes as parameter an map, a string and the two objects
    // Returns the string only if is found in the arraylist
    public void CompareCities(HashMap<String, City> h1, String maxcity, Object ob1, Object ob2) {

        if ((h1.containsKey(maxcity)) && (Similarity(ob1, ob2) == 5))  {
            System.out.println("City found:" +maxcity);
        } else {
            System.out.println("No city matches with the list's cities");
        }

    }


    // Overloaded CompareCities method (same method type, different number of parameters)
    // Takes as parameter a boolean weather, a map and a string
    // If it is raining in the city, boolean weather  becomes true and the city is excluded
    public  void CompareCities(Boolean weather, ArrayList<String> list, String maxcity) {
        if ((maxcity.contains("rain")) && (list.contains("rain"))) {

            System.out.println("It is raining in the city so is excuded, rain =" +weather);
        } else {
            weather=false;
            System.out.println("No rain found in the city");
        }
    }


    // Free ticket method
    // Takes as parameter a map and two objects
    // If similarity number is 5 (max) and the second object is contained in the list, then the traveller can get free ticket
    // getAtt1 = name , getAtt2 = age
    public void FreeTicket(HashMap<String, Traveller> h, Object k, Object n) {
        int i;
        for (i=0; i<h.size(); i++) {
            if((Similarity(k, n) == 5) && (h.containsKey(n))) {
                h.get(i);
                System.out.println("The eligible traveller for a free ticket is:" + getAtt1() + getAtt2());
            }
            System.out.println("Traveller is not eligible for a free ticket");
        }
    }


    public static HashMap<String, Traveller> sortByValue(HashMap<String, Traveller> hm) {
        // Create a list from elements of HashMap
        List<Entry<String, Traveller>> list = new LinkedList<>(hm.entrySet());

        // Sort the list //
        Collections.sort(list, new Comparator<Entry<String, Traveller>>() {
            public int compare(Entry<String, Traveller> o1, Entry<String, Traveller> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Traveller> temp = new LinkedHashMap<>();
        for (Entry<String, Traveller> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    @Override
    public int compareTo(Traveller t) {
        return Att2 - getAtt2();
    }


}
