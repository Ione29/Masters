package com.pp_lab_08;

import javax.swing.JTextArea;

public class Dispatcher extends Thread{
    private SharedRes shared;
    private JTextArea notificationsArea;

    public Dispatcher(String name, SharedRes vShared){
        super(name);
        this.shared = vShared;
        this.notificationsArea = null;
    }

    public Dispatcher(String name, SharedRes vShared, JTextArea vNotificationsArea){
        super(name);
        this.shared = vShared;
        this.notificationsArea = vNotificationsArea;
    }

    @Override
    public void run(){
        while(true){
            Sensor.SensorEvent event = shared.consume();
            System.out.println(event.toString());

            if(notificationsArea != null){
                notificationsArea.append(event.toString() + "\n");
                notificationsArea.setCaretPosition(notificationsArea.getDocument().getLength());
            }
            try{
                sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
}