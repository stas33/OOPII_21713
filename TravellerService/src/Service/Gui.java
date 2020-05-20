package Service;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class gui extends JFrame implements ActionListener, ItemListener {

    static JFrame f, f1, f2, f3;

    static JRadioButton b, b1, b2;

    static JButton but, but1, but2, but3, but4, but5, but6, but7;

    static JPanel p, p1, p2, p3;

    static JLabel l1, l2;

    static JTextField t, t1, t2, t3, t4, t5, t6, t7, t8;

    // ***** Main.java is not ready yet for calling the below methods ****** //

    public static void startingMenu() {
        f = new JFrame("Starting Page");    // create frame

        p = new JPanel();

        f.setContentPane(p);

        JMenuBar menuBar = new JMenuBar();      // create menubar

        JMenu menu = new JMenu("Load");     // create load column bar

        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Load Travellers");    // create some fileds in load column
        JMenuItem menuItem2 = new JMenuItem(" Load Cities");
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

        //f.setBounds(120,120,300,300);	//Moves and resizes this component. The new location of the top-left corner is specified by x and y, and the new size is specified by width and height.
        f.setLocation(50, 100);
        f.setSize(400, 200);
        f.isActive();
        f.setVisible(true);

    }


    public static void travellerFieldsPage() {      // create page for the traveller fields
        // Scanner s = new Scanner(System.in);
        gui g =  new gui();

        f1 = new JFrame("Edit Traveller");

        p1 = new JPanel();

        l1 = new JLabel("Choose traveller type:");      // create 3 buttons and select one

        b =  new JRadioButton("Traveller");

        b1 = new JRadioButton("Business");

        b2 = new JRadioButton("Tourist");

        ButtonGroup bg = new ButtonGroup();

        b.addItemListener(g);
        b1.addItemListener(g);
        b2.addItemListener(g);

        bg.add(b);
        bg.add(b1);
        bg.add(b2);
        b.setSelected(true);            // show selected if button clicked
        b1.setSelected(true);
        b2.setSelected(true);

        p1.add(l1);
        p1.add(b);
        p1.add(b1);
        p1.add(b2);



        l2 = new JLabel("Type 4 attributes");       // create label for the attribute fields

        but = new JButton("OK");            // create two buttons ok and cancel

        but1 = new JButton("Cancel");

        gui te = new gui();
        but.addActionListener(te);
        but1.addActionListener(te);


        JLabel l3 = new JLabel("Name:");        // ask user for 4 attributes
        t = new JTextField(" ");                    // once these attributes have been typed, actionPerformed function passes them in the traveller fields
        JLabel l4 = new JLabel("Age:");
        t1 = new JTextField(" ");
        JLabel l5 = new JLabel("Lat:");
        t2 = new JTextField(" ");
        JLabel l6 = new JLabel("Lon");
        t3 = new JTextField(" ");

        p1.add(l2);                 // add labels, textfields and button to the panel
        p1.add(l3);
        p1.add(t);
        p1.add(l4);
        p1.add(t1);
        p1.add(l5);
        p1.add(t2);
        p1.add(l6);
        p1.add(t3);
        p1.add(but);
        p1.add(but1);

        f1.add(p1);

        f1.setSize(400, 400);
        f1.setVisible(true);

    }


    public static void cityFieldsPage() {       // create page for choosing city's criteria

        f2 = new JFrame("Editing City");        // create frame

        JLabel l7 = new JLabel("Type 3 criteria for the city");     // create label

        but2 = new JButton("OK");

        but3 = new JButton("Cancel");
        gui t = new gui();

        but2.addActionListener(t);
        but3.addActionListener(t);

        JLabel l8 = new JLabel("Criterion 1");      // ask user to type 4 criteria
        t4 = new JTextField(" ");                       // Criterion 4, Criterion 5 will be given by wikipedia,openweather map
                                                        // once user has typed, actionPerformed function passes them in City fields
        JLabel l9 = new JLabel("Criterion 2");
        t5 = new JTextField(" ");

        JLabel l10 = new JLabel("Criterion 3");
        t6 = new JTextField(" ");

        JLabel l11 = new JLabel("Criterion 4");
        t7 = new JTextField(String.valueOf((City.getCriterion4())));

        JLabel l12 = new JLabel("Criterion 5");
        t8 = new JTextField(String.valueOf(City.getCriterion5()));


        p2.add(l7);             // add labels, textfields and buttons to the panel
        p2.add(l8);
        p2.add(t4);
        p2.add(l9);
        p2.add(t5);
        p2.add(l10);
        p2.add(t6);
        p2.add(l11);
        p2.add(t7);
        p2.add(l12);
        p2.add(t8);
        p2.add(but2);
        p2.add(but3);

        f2.add(p2);

        f2.setSize(400, 400);
        f2.setVisible(true);
        
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == b) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                l1.setText("Traveller selected");
            }
        }
        if (e.getSource() == b1) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                l1.setText("Business selected");
            }
        }
        if (e.getSource() == b2) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                l1.setText("Tourist selected");
            }
        }
    }


    @Override
    public  void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("OK")) {
            l2.setText(but.getText());
            but.setText(" ");
        }
        if (s.equals("Cancel")) {
            l2.setText(but1.getText());
            but1.setText(" ");
        }
        if (s.equals("Traveller")) {
            Traveller.setPassengersAttribute("traveller");
        }
        if (s.equals("Business")) {
            Traveller.setPassengersAttribute("business");
        }
        if (s.equals("Tourist")) {
            Traveller.setPassengersAttribute("tourist");
        }
        Traveller.setAtt1(t.getText());
        Traveller.setAtt2(Integer.parseInt(t1.getText()));
        Traveller.setAtt3(Double.parseDouble(t2.getText()));
        Traveller.setAtt4(Double.parseDouble(t3.getText()));

        City.setCriterion1(t4.getText());
        City.setCriterion2(t5.getText());
        City.setCriterion3(t6.getText());
    }


    public static void resultsPage(String city, String country, String appid) throws Exception {    // create page for the results

        f3 = new JFrame("Wikipedia-Openweather map results page");      // create frame
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f3.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        p3 = new JPanel();

        JLabel l12 = new JLabel("Cities found from RetrieveWikipedia:");    // create labels and buttons

        but4 = new JButton("Show Cities");

        JLabel l13 = new JLabel("Weather found from OpenWeather map:");

        but5 = new JButton("Show Weather");

        but6 = new JButton("Save and close");

        but7 = new JButton("Cancel");
        gui obj = new gui();

        but4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){     // once user has clicked 'Show Cities' button, results from Wikipedia are shown
                try {
                    but4.setText(City.RetrieveWikipedia(city));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        but5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){     // once  user haw clicked 'Show Weather' button, results from Openweather are shown
                try {
                    but5.setText(City.RetrieveOpenWeatherMap(city,country,appid));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        but6.addActionListener(obj);
        but7.addActionListener(obj);


        p3.add(l12);                // add labels and button to panel
        p3.add(but4);

        p3.add(l13);
        p3.add(but5);

        p3.add(but6);
        p3.add(but7);

        f3.add(p3);
        f3.setSize(400, 400);
        f3.setVisible(true);

    }

}