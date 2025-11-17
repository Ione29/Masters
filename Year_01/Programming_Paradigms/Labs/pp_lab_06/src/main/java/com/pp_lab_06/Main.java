package com.pp_lab_06;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    // 1) remove odd elements, square remaining
    public List<Integer> exercise1(List<Integer> input) {
        return input.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    // 2) sum of squares
    public int exercise2(List<Integer> input) {
        return input.stream()
                .map(n -> n * n)
                .reduce(0, Integer::sum);
    }

    // 3a) map length -> count (returns Map<Integer, Long>)
    public Map<Integer, Long> exercise3a(List<String> input) {
        return input.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
    }

    // 3b) alternative: Map<Integer, Integer>
    public Map<Integer, Integer> exercise3b(List<String> input) {
        return input.stream()
                .collect(Collectors.groupingBy(String::length, Collectors.summingInt(s -> 1)));
    }

    // 4) flatten list of lists of strings and return distinct words
    public List<String> exercise4(List<List<String>> input) {
        return input.stream()
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    // 5) students from given group -> comma-separated names
    public String exercise5(List<Student> students, String group) {
        return students.stream()
                .filter(s -> Objects.equals(s.group, group))
                .map(s -> s.name)
                .collect(Collectors.joining(","));
    }

    // 6) generate large list, compute sum of squares sequential vs parallel and measure time
    // returns a map with times (ns) and sums
    public Map<String, Long> exercise6(int size) {
        List<Integer> bigList = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());

        long startSeq = System.nanoTime();
        long sumSeq = bigList.stream().mapToLong(n -> 1L * n * n).sum();
        long timeSeq = System.nanoTime() - startSeq;

        long startPar = System.nanoTime();
        long sumPar = bigList.parallelStream().mapToLong(n -> 1L * n * n).sum();
        long timePar = System.nanoTime() - startPar;

        Map<String, Long> result = new HashMap<>();
        result.put("sequentialTimeNs", timeSeq);
        result.put("parallelTimeNs", timePar);
        result.put("sequentialSum", sumSeq);
        result.put("parallelSum", sumPar);
        result.put("size", (long) size); 
        return result;
    }

    // 7) students enrolled in any course with creditPoints > 5
    public List<Student> exercise7(List<Student> students) {
        return students.stream()
                .filter(s -> s.courses != null && s.courses.stream().anyMatch(c -> c.creditPoints > 5))
                .collect(Collectors.toList());
    }

    // 8) students whose total credit points > 30
    public List<Student> exercise8(List<Student> students) {
        return students.stream()
                .filter(s -> s.courses != null && s.courses.stream().mapToInt(c -> c.creditPoints).sum() > 30)
                .collect(Collectors.toList());
    }

    // 9) courses -> map courseID -> list of studentIDs
    public Map<Integer, List<Integer>> exercise9(List<Course> courses) {
        return courses.stream()
                .collect(Collectors.toMap(
                        c -> c.courseID,
                        c -> c.students == null ? Collections.emptyList() :
                                c.students.stream().map(st -> st.studentID).collect(Collectors.toList())
                ));
    }

    // 10) first 5 students in group, sorted by name
    public List<Student> exercise10(List<Student> students, String group) {
        return students.stream()
                .filter(s -> Objects.equals(s.group, group))
                .sorted(Comparator.comparing(s -> s.name))
                .limit(5)
                .collect(Collectors.toList());
    }

    // 11) map group -> list of courses students from that group are enrolled in (duplicates removed)
    public Map<String, List<Course>> exercise11(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(s -> s.group)) // Map<String, List<Student>>
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .flatMap(s -> s.courses == null ? java.util.stream.Stream.empty() : s.courses.stream())
                                .collect(Collectors.collectingAndThen(
                                        Collectors.toMap(c -> c.courseID, c -> c, (a, b) -> a),
                                        m -> new ArrayList<>(m.values())
                                ))
                ));
    }

    public static void main(String[] args) {
        Main impl = new Main();

        System.out.println("\nExercise 1:");
        System.out.println(impl.exercise1(Arrays.asList(1,2,3,4))); // [4,16]

        System.out.println("\nExercise 2:");
        System.out.println(impl.exercise2(Arrays.asList(1,2,3))); // 14

        System.out.println("\nExercise 3a / 3b:");
        List<String> words = Arrays.asList("a","bb","cc","ddd");
        System.out.println("3a: " + impl.exercise3a(words));
        System.out.println("3b: " + impl.exercise3b(words));

        System.out.println("\nExercise 4:");
        List<List<String>> lists = Arrays.asList(Arrays.asList("a","b"), Arrays.asList("b","c"));
        System.out.println(impl.exercise4(lists));

        System.out.println("\nExercise 5:");
        Student sa = new Student(1, "Mircea", "1231");
        Student sb = new Student(2, "Vlad", "1231");
        Student sc = new Student(3, "Marian", "1231");
        System.out.println(impl.exercise5(Arrays.asList(sa,sb,sc), "1231")); // Alice,Ann

        System.out.println("\nExercise 6:");
        Map<String, Long> perf = impl.exercise6(100_000);
        System.out.println(perf);

        // prepare courses and students for 7..11
        Course c1 = new Course(1, 6, 1, "Special Mathematics");
        Course c2 = new Course(2, 4, 1, "Digital Signals Processing");
        Course c3 = new Course(3, 20, 1, "Neural Networks");
        Course c4 = new Course(4, 12, 1, "Object Oriented Programming");

        Student s1 = new Student(1, "Alex", "1241");
        Student s2 = new Student(2, "Diana", "1241");
        Student s3 = new Student(3, "Tibi", "1242");
        Student s4 = new Student(4, "Daniel", "1241");
        Student s5 = new Student(5, "Adi", "1242");
        Student s6 = new Student(6, "Eduard", "1242");

        s1.courses.addAll(Arrays.asList(c1, c2)); // total 10
        s2.courses.addAll(Arrays.asList(c2)); // 4
        s3.courses.addAll(Arrays.asList(c3)); // 20
        s4.courses.addAll(Arrays.asList(c3, c4)); // 32
        s5.courses.addAll(Arrays.asList(c1)); // 6

        c1.students.addAll(Arrays.asList(s1, s5));
        c2.students.addAll(Arrays.asList(s1, s2));
        c3.students.addAll(Arrays.asList(s3, s4));
        c4.students.addAll(Arrays.asList(s4));

        List<Student> all = Arrays.asList(s1,s2,s3,s4,s5,s6);

        System.out.println("\nExercise 7:");
        List<Student> ex7 = impl.exercise7(all);
        System.out.println("students with any course >5 credits: " + ids(ex7));

        System.out.println("\nExercise 8:");
        List<Student> ex8 = impl.exercise8(all);
        System.out.println("students with total >30 credits: " + ids(ex8));

        System.out.println("\nExercise 9:");
        Map<Integer, List<Integer>> ex9 = impl.exercise9(Arrays.asList(c1,c2,c3,c4));
        System.out.println(ex9);

        System.out.println("\nExercise 10:");
        List<Student> ex10 = impl.exercise10(all, "1241");
        System.out.println("first 5 in G1 sorted by name: " + ids(ex10));

        System.out.println("\nExercise 11");
        Map<String, List<Course>> ex11 = impl.exercise11(all);
        ex11.forEach((g, cs) -> System.out.println(g + " -> " + courseIds(cs)));
    }

    private static List<Integer> ids(List<Student> students) {
        List<Integer> out = new ArrayList<>();
        for (Student s : students) out.add(s.studentID);
        return out;
    }

    private static List<Integer> courseIds(List<Course> courses) {
        List<Integer> out = new ArrayList<>();
        for (Course c : courses) out.add(c.courseID);
        return out;
    }
}