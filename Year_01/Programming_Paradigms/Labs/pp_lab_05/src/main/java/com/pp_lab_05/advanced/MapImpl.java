package com.pp_lab_05.advanced;

import java.util.LinkedList;

public class MapImpl<K, V> implements Map<K, V> {
    LinkedList<K> keys = new LinkedList<>();
    LinkedList<V> values = new LinkedList<>();

    @Override
    public void add(K key, V value){
        if(this.keys.size() == 0){
            this.keys.add(key);
            this.values.add(value);
        }
        else{
            int findKeyIndex = this.keys.indexOf(key);
            if(findKeyIndex == -1){
                this.keys.add(key);
                this.values.add(value);
            }
            else {
                this.values.remove(findKeyIndex);
                this.values.add(findKeyIndex, value);
            }
        }
    }

    @Override
    public V remove(K key){
        int index = this.keys.indexOf(key);
        V value = this.values.get(index);
        this.keys.remove(index);
        this.values.remove(index);
        return value;
    }

    @Override
    public int size(){
        return this.keys.size();
    }

    @Override
    public boolean isEmpty(){
        return this.keys.size() > 0 && this.values.size() > 0;
    }

    @Override
    public LinkedList<K> keys(){
        return this.keys;
    }

    @Override 
    public void print(){
        for(int i = 0; i < this.values.size(); i++)
            System.out.println(this.values.get(i));
    }
}