package com.pp_lab_07.ex_01;

public class TimeMain {
    public static void main(String[] args) {
        System.out.println("Starting 3 TimeThread instances (extends Thread)...");
        for (int i = 1; i <= 3; i++) {
            new TimeThread("TimeThread-" + i).start();
        }

        System.out.println("Starting 3 TimeRunnable instances (implements Runnable)...");
        for (int i = 1; i <= 3; i++) {
            new Thread(new TimeRunnable("TimeRunnable-" + i)).start();
        }
    }
}