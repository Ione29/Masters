package com.pp_lab_07.ex_04;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Callable;
import java.io.IOException;

public class WordCountWorker implements Callable<Map<String, Integer>> {
    private final List<Path> files;

    public WordCountWorker(List<Path> files) {
        this.files = files;
    }

    @Override
    public Map<String, Integer> call() {
        Map<String, Integer> local = new HashMap<>();
        for (Path p : files) {
            try {
                // read all lines and split on non-letters
                Files.lines(p, StandardCharsets.UTF_8)
                     .map(line -> line.split("[^A-Za-z]+"))
                     .forEach(tokens -> {
                         for (String t : tokens) {
                             if (t == null || t.isEmpty()) continue;
                             String w = t.toLowerCase();
                             local.merge(w, 1, Integer::sum);
                         }
                     });
            } catch (IOException e) {
                System.err.println("Failed to read " + p + ": " + e.getMessage());
            }
        }
        return local;
    }
}