package com.pp_lab_07.ex_04;

import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCountMain {
    public static void main(String[] args) throws Exception {
        List<Path> txtFiles = findTextFiles();
        if (txtFiles.isEmpty()) {
            System.out.println("No .txt files found in project tree. Put files under project root (or subfolders).");
            return;
        }

        System.out.println("Found " + txtFiles.size() + " text files.");

        long start = System.currentTimeMillis();
        Map<String, Integer> single = singleThreadCount(txtFiles);
        long singleTime = System.currentTimeMillis() - start;
        System.out.println("Single-thread: unique words=" + single.size() + ", time=" + singleTime + "ms");

        start = System.currentTimeMillis();
        Map<String, Integer> parallel = parallelCount(txtFiles, Runtime.getRuntime().availableProcessors());
        long parTime = System.currentTimeMillis() - start;
        System.out.println("Parallel: unique words=" + parallel.size() + ", time=" + parTime + "ms");

        // show top 20 words from parallel result
        System.out.println("\nTop 20 words:");
        parallel.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(20)
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
    }
    private static List<Path> findTextFiles() throws Exception {
        try (Stream<Path> s = Files.walk(Paths.get("."))) {
            return s.filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".txt"))
                    .collect(Collectors.toList());
        }
    }

    // single-threaded aggregation
    private static Map<String, Integer> singleThreadCount(List<Path> files) {
        Map<String, Integer> agg = new HashMap<>();
        for (Path p : files) {
            try {
                Files.lines(p)
                     .map(line -> line.split("[^A-Za-z]+"))
                     .forEach(tokens -> {
                         for (String t : tokens) {
                             if (t == null || t.isEmpty()) continue;
                             String w = t.toLowerCase();
                             agg.merge(w, 1, Integer::sum);
                         }
                     });
            } catch (Exception e) {
                System.err.println("Read error " + p + ": " + e.getMessage());
            }
        }
        return agg;
    }

    // parallel: split files into `workers` chunks, use worker threads, then merge results
    private static Map<String, Integer> parallelCount(List<Path> files, int workers) throws Exception {
        if (workers <= 0) workers = 1;
        int n = files.size();
        List<List<Path>> parts = new ArrayList<>();
        for (int i = 0; i < workers; i++) parts.add(new ArrayList<>());
        for (int i = 0; i < n; i++) parts.get(i % workers).add(files.get(i));

        ExecutorService ex = Executors.newFixedThreadPool(Math.min(workers, n));
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        for (List<Path> part : parts) {
            if (part.isEmpty()) continue;
            futures.add(ex.submit(new WordCountWorker(part)));
        }

        Map<String, Integer> agg = new HashMap<>();
        for (Future<Map<String, Integer>> f : futures) {
            Map<String, Integer> local = f.get();
            // merge
            local.forEach((k, v) -> agg.merge(k, v, Integer::sum));
        }

        ex.shutdown();
        return agg;
    }
}