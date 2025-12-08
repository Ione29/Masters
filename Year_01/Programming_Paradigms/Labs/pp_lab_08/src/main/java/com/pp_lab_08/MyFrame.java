package com.pp_lab_08;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyFrame extends JFrame {
    ExecutorService executor = Executors.newFixedThreadPool(5);
    SharedRes shared = new SharedRes(2);
    
    Sensor s1 = new Sensor("Sensor_1", Sensor.Type.FRONT_DOOR, shared);
    Sensor s2 = new Sensor("Sensor_2", Sensor.Type.BACK_DOOR, shared);
    Sensor s3 = new Sensor("Sensor_3", Sensor.Type.NURSERY, shared);
    Sensor s4 = new Sensor("Sensor_4", Sensor.Type.BEDROOM, shared);

    private JTextArea notificationsArea = new JTextArea(30, 40);
    private JPanel notifications = new JPanel();
    
    Dispatcher dispatcher = new Dispatcher("dispatcher", shared, notificationsArea);
    
    private final int DEFAULT_HEIGHT = 1000;
    private final int DEFAULT_WIDTH = 1000;
    
    private JPanel sensors = new JPanel();
    
    private JPanel frontDoorPanel = new JPanel();
    private JLabel l1 = new JLabel("Front Door");
    private JButton start1 = new JButton("Start");
    private JButton stop1 = new JButton("Stop");
    
    private JPanel backDoorPanel = new JPanel();
    private JLabel l2 = new JLabel("Back Door");
    private JButton start2 = new JButton("Start");
    private JButton stop2 = new JButton("Stop");
    
    private JPanel nurseryPanel = new JPanel();
    private JLabel l3 = new JLabel("Nursery");
    private JButton start3 = new JButton("Start");
    private JButton stop3 = new JButton("Stop");

    private JPanel bedroomPanel = new JPanel();
    private JLabel l4 = new JLabel("Bedroom");
    private JButton start4 = new JButton("Start");
    private JButton stop4 = new JButton("Stop");

    public SensorStartStop sController1 = new SensorStartStop(s1);
    public SensorStartStop sController2 = new SensorStartStop(s2);
    public SensorStartStop sController3 = new SensorStartStop(s3);
    public SensorStartStop sController4 = new SensorStartStop(s4);
    
    public class SensorStartStop implements ActionListener{
        boolean running;
        Sensor sensor;
        
        public SensorStartStop(Sensor vSensor){
            this.running = false;
            this.sensor = vSensor;
        }

        public void actionPerformed(ActionEvent e){
            String command = e.getActionCommand();

            if(command.equals("Start") && !this.running){
                sensor.setRunning(true);
                executor.execute(this.sensor);
                this.running = true;
            }
            else if(command.equals("Stop") && this.running){
                sensor.setRunning(false);
                this.running = false;
            }
        }
    }

    public MyFrame() {
        setTitle("Sensors");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        frontDoorPanel.add(l1);
        frontDoorPanel.add(start1);
        frontDoorPanel.add(stop1);
        start1.addActionListener(sController1);
        stop1.addActionListener(sController1);
        
        l2.setHorizontalAlignment(JLabel.CENTER);
        backDoorPanel.add(l2);
        backDoorPanel.add(start2);
        backDoorPanel.add(stop2);
        start2.addActionListener(sController2);
        stop2.addActionListener(sController2);

        l3.setHorizontalAlignment(JLabel.CENTER);
        nurseryPanel.add(l3);
        nurseryPanel.add(start3);
        nurseryPanel.add(stop3);
        start3.addActionListener(sController3);
        stop3.addActionListener(sController3);

        l4.setHorizontalAlignment(JLabel.CENTER);
        bedroomPanel.add(l4);
        bedroomPanel.add(start4);
        bedroomPanel.add(stop4);
        start4.addActionListener(sController4);
        stop4.addActionListener(sController4);

        sensors.add(frontDoorPanel);
        sensors.add(backDoorPanel);
        sensors.add(bedroomPanel);
        sensors.add(nurseryPanel);
        
        add(sensors);

        notificationsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(notificationsArea);
        notifications.add(scrollPane);

        sensors.setLayout(new GridLayout(4, 1));
        
        add(notifications);
        
        executor.execute(dispatcher);
    }
}