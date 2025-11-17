package com.pp_lab_07.ex_01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.Runnable;

public class TimeRunnable implements Runnable {
    private final String id;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    public TimeRunnable(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(id + " -> " + LocalDateTime.now().format(FMT));
                Thread.sleep(10_000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}