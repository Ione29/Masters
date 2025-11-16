package com.pp_lab_05.advanced;

import java.util.LinkedList;

public interface Map<K, V> {
    public void add(K key, V value);
    public V remove(K key);
    public int size();
    public boolean isEmpty();
    public LinkedList<K> keys();
    public void print();
}
