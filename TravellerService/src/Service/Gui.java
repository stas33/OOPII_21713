package Service;
//import javax.swing.*;

//import java.awt.*;
//import java.awt.event.*;
//import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;



import javax.swing.*;

class gui extends JFrame { //implements  ActionListener, ItemListener

    static JFrame f, f1, f2, f3;        // create some fields

    static JRadioButton b, b1, b2;

    static JButton but, but1, but2, but3, but4, but5, but6, but7;

    static JPanel p, p1, p2, p3;

    static JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;

    static JTextField t, t1, t2, t3, t4, t5, t6, t7, t8;


    public static void startingMenu(HashMap<String,Traveller> h1, HashMap<String,City> h2) {

        f = new JFrame("Starting Page");    // frame name

        p = new JPanel();   // create panel

        f.setContentPane(p);

        JMenuBar menuBar = new JMenuBar();      // create menubar

        JMenu menu = new JMenu("Load");     // create load column bar

        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(new AbstractAction("Load Travellers") {    // create load trav column and add action if Load travellers clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.readCollectionFromFile(Main.outputFilePath,h1);    // if clicked read the map from the file
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        JMenuItem menuItem2 = new JMenuItem(new AbstractAction("Load Cities") {     // create load cities column and add action if clicked
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.retrieveDataFromDB(h2);        // if clicked retrieve map from database
            }
        });

        JMenuItem menuItem3 = new JMenuItem(" Exit");

        menu.add(menuItem1);        // add these fields
        menu.add(menuItem2);
        menu.add(menuItem3);

        JMenu menu2 = new JMenu("Help");        // create help column
        menuBar.add(menu2);
        JMenuItem menuItem4 = new JMenuItem(" Help");       // create some fileds in help column
        JMenuItem menuItem5 = new JMenuItem(" About");
        menu2.add(menuItem4);           // add fileds
        menu2.add(menuItem5);


        f.setJMenuBar(menuBar);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closing the frame, it also close the application.

        final WindowListener listener = new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {        // create a window listener
                ((JFrame) e.getWindow()).setJMenuBar(menuBar);
            }
        };

        f.setContentPane(p);
        f.setBounds(400,400,300,300);	//Moves and resizes this component. The new location of the top-left corner is specified by x and y, and the new size is specified by width and height.
        //f.setLocation(50, 100);
        f.setSize(400, 200);
        f.isActive();
        f.setVisible(true);     // show window

    }



    public static void travellerFieldsPage() {      // create page for the traveller fields

        f1 = new JFrame("Edit Traveller");      // frame name
        p1 = new JPanel();

        f1.setBounds(100, 100, 594, 476);
        f1.getContentPane().setLayout(null);

        p1.setBounds(79, 115, 270, 120);
        f1.getContentPane().add(p1);
        p1.setLayout(null);


        l1 = new JLabel("Choose traveller type:"); // create 3 buttons and select one
        l1.setBounds(10, 11, 75, 31);
        p1.add(l1);

        ButtonGroup bg = new ButtonGroup();


        b = new JRadioButton("Traveller");
        //b.addItemListener(g);
        b.addItemListener(new ItemListener() {      // create button and an itemlistener
            @Override
            public void itemStateChanged(ItemEvent e) {     // change state of radio button if user selects this radioButton
                if (e.getSource() == b) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        l1.setText("Traveller selected");
                    }
                }
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        // add actionevent and if button clicked update setPassengersAttribute
                String s = e.getActionCommand();
                if (s.equals("Traveller")) {
                    b.setSelected(true);            // change state to selected true if button clicked
                    Traveller.setPassengersAttribute("traveller");
                }
            }
        });



        b1 = new JRadioButton("Business");
        b1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {     // the same process
                if (e.getSource() == b1) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        l1.setText("Business selected");
                    }
                }
            }
        });
        b1.addActionListener(new ActionListener() {     // the same process
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("Business")) {
                    b1.setSelected(true);
                    Traveller.setPassengersAttribute("business");
                }
            }
        });



        b2 = new JRadioButton("Tourist");
        b2.addItemListener(new ItemListener() {     // the same process
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == b2) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        l1.setText("Tourist selected");
                    }
                }
            }
        });
        b2.addActionListener(new ActionListener() {     // the same process
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("Tourist")) {
                    b2.setSelected(true);
                    Traveller.setPassengersAttribute("tourist");
                }
            }
        });



        p1.add(b);          // add the buttons to the appropriate panels
        p1.add(b1);
        p1.add(b2);


        l2 = new JLabel("Type 4 attributes");       // create label for the attribute fields
        l2.setBounds(10, 11, 75, 31);
        p1.add(l2);

        but = new JButton("OK");            // create ok button
        but.addActionListener(new ActionListener() {    // add an actionListener
            @Override
            public void actionPerformed(ActionEvent e) {    // add an action when ok button pressed
                String s = e.getActionCommand();
                if (s.equals("OK")) {
                    l2.setText(but.getText());
                    but.setText(" ");
                }
            }
        });



        l3 = new JLabel("Name:");        // ask user for 4 attributes
        l3.setBounds(10, 11, 75, 31);
        p1.add(l3);
        t = new JTextField();   // once these attributes have been typed, pass them in the appropriate setAtt fields of traveller
        t.setToolTipText("Type Name");
        p1.add(t);
        Traveller.setAtt1(t.getText());


        l4 = new JLabel("Age:");
        l4.setBounds(10, 11, 75, 31);
        p1.add(l4);
        t1 = new JTextField();
        t1.setToolTipText("Type Age");
        //t1.setText(" ");
        p1.add(t1);
        try {
            Traveller.setAtt2(Integer.parseInt(t1.getText()));
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }                                           // same processes for all these

        l5 = new JLabel("Lat:");
        l5.setBounds(10, 11, 75, 31);
        p1.add(l5);
        t2 = new JTextField();
        t2.setToolTipText("Type Lat");
        p1.add(t2);                             // add textfields to the panel
        try {
            Traveller.setAtt3(Double.parseDouble(t2.getText()));
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }

        l6 = new JLabel("Lon");
        l6.setBounds(10, 11, 75, 31);
        p1.add(l6);                         // add labels to the panel
        t3 = new JTextField();
        t3.setToolTipText("Type Lon");
        p1.add(t3);
        try {
            Traveller.setAtt4(Double.parseDouble(t2.getText()));
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }

                // add labels, textfields and button to the panel


        p1.add(but);                //add button to the panel
        //p1.add(but1);


        f1.setSize(500, 500);
        f1.setVisible(true);

    }



    public static void cityFieldsPage() {       // create page for choosing city's criteria

        f2 = new JFrame("Editing City"); // create frame name
        p2 = new JPanel();

        f2.setBounds(100, 100, 594, 476); // set bounds and layout
        f2.getContentPane().setLayout(null);

        p2.setBounds(79, 115, 270, 120);
        f2.getContentPane().add(p2);
        p2.setLayout(null);

        l7 = new JLabel("Type 3 criteria for the city");     // create label for criteria ans setBounds
        l7.setBounds(10, 11, 75, 31);
        p2.add(l7);

        l8 = new JLabel("Criterion 1");      // ask user to type 4 criteria
        l8.setBounds(10, 11, 75, 31);
        p2.add(l8);
        t4 = new JTextField();// Criterion 4, Criterion 5 will be given by wikipedia,openweather map
        t4.setToolTipText("Criterion 1");
        p2.add(t4);
        City.setCriterion1(t4.getText());       // when criteria typed then pass them to the appropriate setCriterion fields of city



        l9 = new JLabel("Criterion 2");
        l9.setBounds(10, 11, 75, 31);
        p2.add(l9);
        t5 = new JTextField();
        t5.setToolTipText("Criterion 2");
        p2.add(t5);
        City.setCriterion2(t5.getText());       // same process



        l10 = new JLabel("Criterion 3");
        l10.setBounds(10, 11, 75, 31);
        p2.add(l10);
        t6 = new JTextField();
        t6.setToolTipText("Criterion 3");
        p2.add(t6);
        City.setCriterion3(t6.getText());       // same process



        l11 = new JLabel("Criterion 4");
        l11.setBounds(20, 21, 85, 41);
        p2.add(l11);
        t7 = new JTextField(String.valueOf((City.getCriterion4())));        // same process, convert double to string
        p2.add(t7);

        l12 = new JLabel("Criterion 5");
        l12.setBounds(20, 21, 85, 41);
        p2.add(l12);
        t8 = new JTextField(String.valueOf(City.getCriterion5()));         // same process, convert double to string
        p2.add(t8);


        but2 = new JButton("OK");
        //but2.addActionListener(t);
        p2.add(but2);
        but2.addActionListener(new ActionListener() {           // add actionlistener when ok pressed, the same as above
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                if (s.equals("OK")) {
                    l2.setText(but.getText());
                    but.setText(" ");
                }
            }
        });

                // once user has typed, actionPerformed function passes them in City fields
                // add labels, textfields and buttons to the panel

        f2.setSize(500, 500);
        f2.setVisible(true);

    }



    public static void resultsPage(String city, String country, String appid,HashMap<String, Traveller> h1, HashMap<String, City> h2) throws Exception {    // create page for the results

        f3 = new JFrame("Wikipedia-Openweather map results page");      // create frame name
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));


        p3 = new JPanel();              // create panel

        p3.setBounds(79, 115, 270, 120);        // setBounds and layout
        f3.getContentPane().add(p3);
        p3.setLayout(null);

        JLabel l12 = new JLabel("Cities found from RetrieveWikipedia:");    // create labels and buttons
        l12.setBounds(10, 11, 75, 31);
        p3.add(l12);                                        // add label to panel

        but4 = new JButton("Show Cities");

        but4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){     // once user has clicked 'Show Cities' button, show results from Wikipedia
                try {
                    but4.setText(City.RetrieveWikipedia(city));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        p3.add(but4);                           // add button to panel

        JLabel l13 = new JLabel("Weather found from OpenWeather map:");
        l13.setBounds(10, 11, 75, 31);
        p3.add(l13);

        but5 = new JButton("Show Weather");

        but5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){     // once  user has clicked 'Show Weather' button, results from Openweather are shown
                try {
                    but5.setText(City.RetrieveOpenWeatherMap(city,country,appid));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        p3.add(but5);           // add button to panel



        but6 = new JButton("Save and close");
        but6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){     // if user has clicked "Save and Close" button, the maps are stored in the file an in database

                Main.addDataToDB(h2);
                Main.writeCollectionToFile(Main.outputFilePath,h1);
            }
        });
        p3.add(but6);


        f3.setSize(600, 600);
        f3.setVisible(true);

    }

}

