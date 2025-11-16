package com.pp_lab_05.advanced;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MapImplTest {

    @Test
    void addUpdateRemoveAndSize() {
        MapImpl<Integer, String> map = new MapImpl<>();
        map.add(1, "A");
        map.add(2, "B");
        map.add(3, "C");

        // size and keys
        assertEquals(3, map.size());
        assertEquals(Arrays.asList(1, 2, 3), new LinkedList<>(map.keys()));

        // update key 3 -> "N"
        map.add(3, "N");
        // removal returns the current value for key 3
        assertEquals("N", map.remove(3));
        assertEquals(2, map.size());

        // remove another key and check returned value
        assertEquals("B", map.remove(2));
        assertEquals(1, map.size());
    }

    @Test
    void printOutputsValuesInInsertionOrder() {
        MapImpl<Integer, String> map = new MapImpl<>();
        map.add(1, "A");
        map.add(2, "B");
        map.add(3, "C");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream orig = System.out;
        System.setOut(new PrintStream(out));
        try {
            map.print();
        } finally {
            System.setOut(orig);
        }

        String[] lines = out.toString().trim().split("\\R");
        assertArrayEquals(new String[] {"A", "B", "C"}, lines);
    }
}