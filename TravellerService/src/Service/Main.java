package Service;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.*;

import static Service.City.*;
import static Service.Traveller.*;

// ****** Main class is not complete yet and needs some changes. Check Gui class ****** //

public class Main {

    final static String outputFilePath = "/Users/anastasisgkikas/TravellerService/src/Service/hmaptraveller.dat";
    static Connection db_con_obj = null;
    static PreparedStatement db_prep_obj = null;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // TODO Auto-generated method stub

        // connecting to database
        makeJDBCConnection();


        // create two collections (hashmaps) with a key and a value
        // trmap has a String (visited city/inputcity) as a key  and a traveller object as a value
        // citymap has a String (visited city/inputcity) as a key and a city object as a value
        HashMap<String, Traveller> trmap = new HashMap<>();
        HashMap<String, City> citymap = new HashMap<>();


        // retrieving file data
        if(outputFilePath.length() == 0) {
            System.out.println("The file is empty");
        } else {
            try {
                readCollectionFromFile(outputFilePath,trmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // retrieving database data
        retrieveDataFromDB(citymap);

        // gui starting menu
        gui.startingMenu(trmap,citymap);


        String appid = "489be831b9526618635ad0ff71612c38";
        // create 3 traveller objects with 4 fields (name, age, current lat, current lon)
        Traveller t = new Business("Nikos", 30, 0.0, 0.1);
        Traveller t1 = new Traveller("Jim", 25, 0.2, 0.3);
        Traveller t2 = new Traveller("John", 30, 0.4, 0.5);
        // create 2 Business and 2 Tourist object in order to call override methods
        Business b = new Business("George", 50, 1.0, 1.1);
        Business b1 = new Business("Kostas", 18, 0.3, 0.6);
        Tourist tr = new Tourist("Panos", 60, 2.0, 2.1);
        Tourist tr1 = new Tourist("Mike", 40, 0.8, 0.9);

        // create city objects with 5 fields (5 criteria);
        City c = new City("bars", "bike", "museums", 0.0d, 0.0d);
        City c1 = new City("cafe", "theater", "sun", 0.51, 0.53);
        // Optional: create 1 arraylist of String cities and 1 arraylist of traveller objects
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Object> travellers = new ArrayList<>();

        // add objects and strings in the two hashmaps
        trmap.put("Athens",t);
        trmap.put("Berlin", t1);
        trmap.put("Rome", t2);
        trmap.put("Madrid", b);
        trmap.put("London", b1);
        trmap.put("Milan", tr);
        trmap.put("Istanbul", tr1);
        citymap.put("Zurich", c);
        citymap.put("Moscow", c1);

        Scanner s = new Scanner(System.in);

            // call gui for the fields of city
            gui.cityFieldsPage();
            // call gui for the fields of traveller
            gui.travellerFieldsPage();

            // set paggenger's attribute(t/b/tr) by calling  checkpassengersattribute method
            // checking passengers attribute and reading object fields
            setPassengersAttribute(checkPassengersAttribute(s));
        while(true) {
            Boolean weather = null;
            // Depending on the passenger's attribute, execute the appropriate if statement
            System.out.println("Type city's name you want to find, i.e Athens, Madrid etc.");
            String inputcity = s.next();
            System.out.println("Type country's first two letters. i.e gr, sp, it etc.");
            String inputcountry = s.next();
            ThreadDemo th = new ThreadDemo("Thread-1", "Thread-2", inputcity, inputcountry, appid);
            th.start();
            try {
                // case checkPassengersAttribute(s) returns traveller
                if (getPassengersAttribute().equals("traveller")) {
                    // adds inputcity and traveller objects in the trmap
                    trmap.put(inputcity, t);
                    // gui resultspage of inputcity and inputcountry
                    gui.resultsPage(inputcity, inputcountry, appid, trmap, citymap);

                    // adds inputcity and city object in the citymap
                    citymap.put(inputcity, c);
                    // creates a candidate city and add it in the map if it does not already exist
                    City.CheckCityInCollection(inputcity, citymap);
                    // checking the similarity between city and traveller objects
                    t.Similarity(c, t);
                    // checking if this traveller is eligible for a free ticket
                    t.FreeTicket(trmap, c, t);
                    // Check if inputcity exists in citymap
                    t.CompareCities(citymap, City.RetrieveWikipedia(inputcity), c, t);
                    // Check if it's raining in inputcity and if so, exclude inputcity
                    t.CompareCities(weather, cities, inputcity);
                }
                // case checkPassengersAttribute(s) returns business
                if (getPassengersAttribute().equals("business")) {
                    // add inputcity and Business object to trmap
                    trmap.put(inputcity, b);
                    // gui resultspage of inputcity and inputcountry
                    gui.resultsPage(inputcity, inputcountry, appid, trmap, citymap);

                    // adds inputcity and city object in the citymap
                    citymap.put(inputcity, c);
                    // creates a candidate city and add it in the map if it does not already exist
                    City.CheckCityInCollection(inputcity, citymap);
                    // checking the similarity between city and business objects
                    b.Similarity(c, b);
                    // checking if business object is eligible for a free ticket
                    b.FreeTicket(trmap, c, b);
                    // Check if inputcity exists in citymap
                    b.CompareCities(citymap, City.RetrieveWikipedia(inputcity), c, b);
                    // Check if it's raining in inputcity and if so exclude inputcity
                    b.CompareCities(weather, cities, inputcity);
                }
                // case checkPassengersAttribute(s) returns tourist
                if (getPassengersAttribute().equals("tourist")) {
                    // add inputcity and tourist objects in trmap
                    trmap.put(inputcity, tr);
                    // gui resultspage of inputcity and inputcountry
                    gui.resultsPage(inputcity, inputcountry, appid, trmap, citymap);

                    // add City objects to citymap
                    citymap.put(inputcity, c);
                    // creates a candidate city and add it in the map if it does not already exist
                    City.CheckCityInCollection(inputcity, citymap);
                    // checking for the similarity between city and tourist objects
                    tr.Similarity(c, tr);
                    // checking if tourist is eligible for a free ticket
                    tr.FreeTicket(trmap, c, tr);
                    // Check if inputcity exists in citymap
                    tr.CompareCities(citymap, City.RetrieveWikipedia(inputcity), c, tr);
                    // Check if it's raining in inputcity and if so exclude inputcity
                    tr.CompareCities(weather, cities, inputcity);
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Incorrect fields, type again");
                break;
            }
            break;
        }

        // sorts the traveller map by value
        sortByValue(trmap);
        // writes the trmap into the txt file
        writeCollectionToFile(outputFilePath, trmap);
        // adds data to db and deletes data
        addDataToDB(citymap);


        deleteDataFromDB();


        System.out.println("PROGRAM TERMINATED SUCCESSFULLY!");


    }


    public static String checkPassengersAttribute(Scanner s) {

        //try(Scanner s = new Scanner(System.in)) {
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
                    System.out.println("Type your current latitude");
                    Traveller.setAtt3(s.nextDouble());
                    System.out.println("Type your current longtitude");
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
                    System.out.println("Type your current latitude");
                    Business.setAtt3(s.nextDouble());
                    System.out.println("Type your current longtitude");
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
                    System.out.println("Type your current latitude");
                    Tourist.setAtt3(s.nextDouble());
                    System.out.println("Type your current longtitude");
                    Tourist.setAtt4(s.nextDouble());
                    break;
                } else {
                    System.out.println("Invalid field. Type a correct attribute");
                }

            }
            return att;
    }


    public static void writeCollectionToFile(String Filename, HashMap<String, Traveller> h1)  {

        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(Filename));

            for(Map.Entry<String, Traveller> entry : h1.entrySet()) {
                bf.write(entry.getKey() + ":" + entry.getValue());

                bf.newLine();
            }
            System.out.println("Data added to the file successfully");
            bf.flush();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf.close();
            } catch(NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static void readCollectionFromFile(String Filename, HashMap<String, Traveller> h1) throws FileNotFoundException, IOException, ClassNotFoundException {


        String line;
        BufferedReader reader = new BufferedReader(new FileReader(Filename));

        while ((line = reader.readLine()) != null) {
            for (Map.Entry<String, Traveller> entry : h1.entrySet()) {
                System.out.println("Key = " + entry.getKey() + " - Value = " + entry.getValue());
            }
        }
        reader.close();

    }


    private static void makeJDBCConnection() {

        try {
            // We check that the DB Driver is available in our project.
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.	 //We connect to a DBMS.
            //Using the DriverManager, we can have many connections to different DBMS
            db_con_obj = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "15septemvriou");
            if (db_con_obj != null) {
                System.out.println("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("MySQL Connection Failed!");
            e.printStackTrace();
        }

    }


    public static void retrieveDataFromDB(HashMap<String, City> h2) {

        ResultSet rs;
        try {   // A simple MySQL Select Query
            String getQueryStatement = "SELECT * FROM dbtest;";

            // We make a statement to the connected DBMS. We pass to the statement a query.
            db_prep_obj = db_con_obj.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            rs = db_prep_obj.executeQuery();

            if (!rs.next()) {
                System.out.println("Resultset is empty");
            } else {

                // Let's iterate through the java ResultSet
                while (rs.next()) {
                    String cname = rs.getString("CITYNAME");
                    String criterion1 = rs.getString("CRIT1");
                    String criterion2 = rs.getString("CRIT2");
                    String criterion3 = rs.getString("CRIT3");
                    double criterion4 = rs.getDouble("CRIT4");
                    double criterion5 = rs.getDouble("CRIT5");
                    City c = new City(criterion1, criterion2, criterion3, criterion4, criterion5);
                    h2.put(cname, c);
                    // Simply Print the results
                    System.out.println(cname + "  " + getCriterion1() + "  " + getCriterion2() + "  " + getCriterion3() + "  " + getCriterion4() + "  " + City.getCriterion5());
                    System.out.println("Data retrieved successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void addDataToDB(HashMap<String, City> h2) {
        String cname;
        String criterion1;
        String criterion2;
        String criterion3;
        String criterion4;
        String criterion5;
        Scanner s = new Scanner(System.in);
        System.out.println("Time to save your city criteria");
        System.out.println("Type again Cityname");
        cname = s.next();
        System.out.println("Type again criterion1");
        criterion1 = s.next();
        System.out.println("Type again criterion2");
        criterion2 = s.next();
        System.out.println("Type again criterion3");
        criterion3 = s.next();
        System.out.println("Type again criterion4");
        criterion4 = s.next();
        System.out.println("Type again criterion5");
        criterion5 = s.next();
        System.out.println("Storing data into database table ...");

        try {
            String sql = "INSERT INTO dbtest (CITYNAME, CRIT1, CRIT2, CRIT3, CRIT4, CRIT5) VALUES (" + h2.get(cname) + "," + h2.get(criterion1) + "," + h2.get(criterion2) + "," + h2.get(criterion3) + "," + h2.get(criterion4) + "," + h2.get(criterion5) + ");";

            db_prep_obj = db_con_obj.prepareStatement(sql);

            // execute insert SQL statement Executes the SQL statement in this PreparedStatement object, which must be an SQL Data Manipulation Language (DML) statement
            //either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
            int numRowChanged = db_prep_obj.executeUpdate();
            System.out.println("Rows "+numRowChanged+" changed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        s.close();
    }

    private static void deleteDataFromDB() {
        String columnname;
        String choice;
        Scanner s = new Scanner(System.in);
        System.out.println("Type the name of a column: CRIT1, CRIT2, CRIT3, CITYNAME");
        columnname = s.next();
        System.out.println("Type a criterion of the column you chose that you want to delete");
        choice = s.next();
        if ((columnname.equals("CRIT1")) || (columnname.equals("CRIT2")) || (columnname.equals("CRIT3")) || (columnname.equals("CITYNAME"))) {

            try {
                String deleteQueryStatement = "DELETE FROM dbtest WHERE " + columnname + " = " + choice + ";";
                db_prep_obj = db_con_obj.prepareStatement(deleteQueryStatement);

                // execute insert SQL statement
                int numRowChanged = db_prep_obj.executeUpdate();

                if( numRowChanged > 0 ) {
                    System.out.println("Rows " + numRowChanged + " changed.");
                } else {
                    System.out.println("Error on deleting row");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Column name does not exist in this database");
        }
        s.close();
    }

}
