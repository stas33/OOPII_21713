package Service;

import org.apache.http.client.ClientProtocolException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.Throwable.*;
import Service.*;
import static Service.City.*;
import static Service.Traveller.*;

// ****** Main class is not complete yet and needs some changes. Check Gui class ****** //

public class Main {

    final static String outputFilePath = "/hmaptraveller.txt";
    static Connection db_con_obj = null;
    static PreparedStatement db_prep_obj = null;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

        // connecting to database
        makeJDBCConnection();


        gui.startingMenu();

        // retrieving file and database data
        readCollectionFromFile(outputFilePath);
        retrieveDataFromDB();

        String article;
        String appid = "489be831b9526618635ad0ff71612c38";
        // create traveller object with 4 fields (name, age, current lat, current lon)
        Traveller t = new Business("Nikos", 30, 0.0, 0.1);
        // create 1 Business and 1 Tourist object in order to call override methods
        Business b = new Business("George", 50, 1.0, 1.1);
        Tourist tr = new Tourist("Panos", 60, 2.0, 2.1);
        // create city objects with 5 fields (5 criteria);
        City c = new City("bars", "bike", "museums", 0.0d, 0.0d);
        // create 1 arraylist of String cities and 1 arraylist of traveller objects
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Object> travellers = new ArrayList<>();
        // add objects and strings in the two arraylists
        travellers.add(t);
        travellers.add(b);
        travellers.add(tr);
        cities.add("Berlin");
        cities.add("Rome");
        cities.add("Athens");
        cities.add("Madrid");
        cities.add("London");

        // create two collections (hashmaps) with a key and a value
        // trmap has a String (visited city/inputcity) as a key  and a traveller object as a value
        // citymap has a String (visited city/inputcity) as a key and a city object as a value
        HashMap<String, Traveller> trmap = new HashMap<>();
        HashMap<String, City> citymap = new HashMap<>();

        try (Scanner s = new Scanner(System.in)) {


            gui.cityFieldsPage();

            gui.travellerFieldsPage();

            // set paggenger's attribute(t/b/tr) by calling  checkpassengersattribute method
            // checking passengers attribute and reading object fields
            setPassengersAttribute(checkPassengersAttribute());

            Boolean weather=null;
            String inputcity;
            // Depending on the passenger's attribute, execute the appropriate if statement
            // After the appropriate if statement is executed then break from the while loop
            while (true) {
                System.out.println("Type city's name you want to find, i.e Athens, Madrid etc.");
                inputcity = s.next();
                cities.add(inputcity);
                System.out.println("Type country's first two letters. i.e gr, sp, it etc.");
                String inputcountry = s.next();
                try {
                    if(getPassengersAttribute().equals("traveller")) {
                        // adds inputcity and traveller objects in the trmap
                        trmap.put(inputcity, t);

                        gui.resultsPage(inputcity,inputcountry,appid);

                        // adds inputcity and city object in the citymap
                        citymap.put(inputcity, c);
                        // checking the similarity between city and traveller objects
                        t.Similarity(c, t);
                        // checking if this traveller is eligible for a free ticket
                        t.FreeTicket(travellers, c, t);
                        // Check if inputcity exists in arraylist cities
                        t.CompareCities(cities,City.RetrieveWikipedia(inputcity), c, t);
                        // Check if it's raining in inputcity and if so, exclude inputcity
                        t.CompareCities(weather,cities,inputcity);
                        break;
                    }
                    if (getPassengersAttribute().equals("business")) {
                        // add Business object to trmap
                        trmap.put(inputcity, b);

                        gui.resultsPage(inputcity,inputcountry,appid);

                        // adds inputcity and city object in the citymap
                        citymap.put(inputcity, c);
                        // checking the similarity between city and business objects
                        b.Similarity(c, b);
                        // checking if business object is eligible for a free ticket
                        b.FreeTicket(travellers, c, b);
                        // Check if inputcity exists in arraylist cities
                        b.CompareCities(cities,City.RetrieveWikipedia(inputcity), c, b);
                        // Check if it's raining in inputcity and if so exclude inputcity
                        b.CompareCities(weather,cities,inputcity);
                        break;
                    }
                    if (getPassengersAttribute().equals("tourist")) {
                        // add Traveller objects in travojects list
                        trmap.put(inputcity, tr);

                        gui.resultsPage(inputcity,inputcountry,appid);


                        // add City objects to cityobjects list
                        citymap.put(inputcity, c);
                        // checking for the similarity between city and tourist objects
                        tr.Similarity(c, tr);
                        // checking if tourist is eligible for a free ticket
                        tr.FreeTicket(travellers, c, tr);
                        // Check if inputcity exists in arraylist cities
                        tr.CompareCities(cities,City.RetrieveWikipedia(inputcity), c, tr);
                        // Check if it's raining in inputcity and if so exclude inputcity
                        tr.CompareCities(weather,cities,inputcity);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Incorrect fields, type again");
                    break;
                }
            }
            // creates a candidate city and add it in the map if it does not already exist
            City.CheckCityInCollection(inputcity, citymap);
            // sorts the traveller map by value
            HashMap<String, Traveller> hm1 = sortByValue(trmap);
            // writes the trmap into the txt file
            writeCollectionToFile("hmaptraveller.txt", trmap);
            // adds data to db and deletes data
            addDataToDB();
            deleteDataFromDB();

        }

    }


    public static String checkPassengersAttribute() {

        try(Scanner s = new Scanner(System.in)) {
            String att;
            System.out.println("Type passenger's attribute: traveller, business or tourist");
            while(true) {
                att=s.next();
                if (att.equals("traveller")) {
                    // Ask user for name, age
                    System.out.print("Type your name:");
                    Traveller.setAtt1(s.next());
                    System.out.println("Type your age:");
                    Traveller.setAtt2(s.nextInt());

                    // Traveller's current lat and lot (Att3,Att4) typed from user
                    // City's lat and lon (Criterion4,Criterion5) calculated from the RetrieveOpenWeatherMap method
                    System.out.println("Type your current latitude and longtitude");
                    Traveller.setAtt3(s.nextDouble());
                    Traveller.setAtt4(s.nextDouble());
                    break;
                }
                if (att.equals("business")) {
                    // Ask user for name, age
                    System.out.print("Type your name:");
                    Business.setAtt1(s.next());
                    System.out.println("Type your age:");
                    Business.setAtt2(s.nextInt());

                    // Traveller's current lat and lot (Att3,Att4) typed from user
                    // City's lat and lon (Criterion4,Criterion5) calculated from the RetrieveOpenWeatherMap method
                    System.out.println("Type your current latitude and longtitude");
                    Business.setAtt3(s.nextDouble());
                    Business.setAtt4(s.nextDouble());
                    break;
                }
                if (att.equals("tourist")) {
                    // Ask user for name, age
                    System.out.print("Type your name:");
                    Tourist.setAtt1(s.next());
                    System.out.println("Type your age:");
                    Tourist.setAtt2(s.nextInt());

                    // Traveller's current lat and lot (Att3,Att4) typed from user
                    // City's lat and lon (Criterion4,Criterion5) calculated from the RetrieveOpenWeatherMap method
                    System.out.println("Type your current latitude and longtitude");
                    Tourist.setAtt3(s.nextDouble());
                    Tourist.setAtt4(s.nextDouble());
                    break;
                } else {
                    System.out.println("Invalid field. Type a correct attribute");
                }

            }
            return att;
        }
    }


    public static void writeCollectionToFile(String Filename, HashMap<String, Traveller> h1) throws IOException {

        FileOutputStream out = new FileOutputStream(Filename);
        ObjectOutputStream oout = new ObjectOutputStream(out);

        // write something in the file
        oout.writeObject(h1);
        oout.flush();
        oout.close();
        System.out.println("Data are writen in the file.");
    }


    public static void readCollectionFromFile(String Filename) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream ois;

        ois = new ObjectInputStream(new FileInputStream(Filename));
        // read and print what we wrote before

        HashMap<String, Traveller> h1 = (HashMap<String, Traveller>) ois.readObject();

        Set<?> set = h1.entrySet(); // Get a set of the entries
        Iterator<?> i = set.iterator(); // Get an iterator

        while(i.hasNext()) { // We iterate and display Entries (nodes) one by one.
            @SuppressWarnings("rawtypes")
            Map.Entry me = (Map.Entry)i.next();
            System.out.print("key: "+me.getKey() + ". ");
            System.out.print("Class: "+me.getValue().getClass() + ". ");
            System.out.println("value: "+(Traveller)me.getValue());
        }
        ois.close();
    }


    private static void makeJDBCConnection() {

        try {
            // We check that the DB Driver is available in our project.
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
            //Using the DriverManager, we can have many connections to different DBMS
            db_con_obj = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest","root", "15septemvriou");
            if (db_con_obj != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
            // return;
        }

    }


    public static void retrieveDataFromDB() {
        HashMap<String, City> h2 = new HashMap<>();
        ResultSet rs = null;
        City c;
        try {
            // A simple MySQL Select Query
            String getQueryStatement = "SELECT * FROM dbtest;";

            // We make a statement to the connected DBMS. We pass to the statement a query.
            db_prep_obj = db_con_obj.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            rs = db_prep_obj.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String cname = rs.getString("CITYNAME");
                String criterion1 = rs.getString("CRIT1");
                String criterion2 = rs.getString("CRIT2");
                String criterion3 = rs.getString("CRIT3");
                double criterion4 = rs.getDouble("CRIT4");
                double criterion5 = rs.getDouble("CRIT5");
                c = new City(criterion1, criterion2, criterion3, criterion4, criterion5);
                h2.put(cname, c);
                // Simply Print the results
                System.out.println(cname+"  "+getCriterion1()+"  "+getCriterion2()+"  "+getCriterion3()+"  "+getCriterion4()+"  "+City.getCriterion5());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void addDataToDB() {
        String cname;
        String criterion1;
        String criterion2;
        String criterion3;
        double criterion4;
        double criterion5;
        Scanner s = new Scanner(System.in);
        System.out.println("Type the 6 new criteria you want to add in the db with the following order: 1)cityname  2)crit1  3)crit2  4)crit3  5)crit4  6)crit5");
        cname = s.next();
        criterion1 = s.next();
        criterion2 = s.next();
        criterion3 = s.next();
        criterion4 = s.nextDouble();
        criterion5 = s.nextDouble();

        try {
            String insertQueryStatement = "INSERT  INTO  dbtest  VALUES  (?,?,?,?,?,?)";


            db_prep_obj = db_con_obj.prepareStatement(insertQueryStatement);
            db_prep_obj.setString(1, cname);//.setString
            db_prep_obj.setString(2, criterion1);
            db_prep_obj.setString(3, criterion2);
            db_prep_obj.setString(4, criterion3);
            db_prep_obj.setDouble(5, criterion4);
            db_prep_obj.setDouble(6, criterion5);


            // execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
            int numRowChanged = db_prep_obj.executeUpdate(); //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            System.out.println("Rows "+numRowChanged+" changed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDataFromDB() {
        String columnname;
        String choice;
        Scanner s = new Scanner(System.in);
        System.out.println("Type the name of a column: CRIT1, CRIT2, CRIT3, CNAME");
        columnname = s.next();
        System.out.println("Type a criterion of the column you chose that you want to delete");
        choice = s.next();
        if ((columnname.equals("CRIT1")) || (columnname.equals("CRIT2")) || (columnname.equals("CRIT3")) || (columnname.equals("CNAME"))) {

            try {
                String deleteQueryStatement = "DELETE FROM dbtest WHERE " + columnname + " = " + choice + ";";
                db_prep_obj = db_con_obj.prepareStatement(deleteQueryStatement);

                // execute insert SQL statement
                int numRowChanged = db_prep_obj.executeUpdate();
                System.out.println("Rows " + numRowChanged + " changed.");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Column name does not exist in this database");
        }
    }

}
