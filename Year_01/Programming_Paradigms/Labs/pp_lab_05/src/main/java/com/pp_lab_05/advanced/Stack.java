package com.pp_lab_05.advanced;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class Stack<T> implements Stackable<T>{
    private LinkedList<T> list;
    private int size;

    public Stack(){
        this.list = new LinkedList<T>();
        this.size = 0;
    }

    public void push(T value){
        this.list.add(0, value);
        this.size();
    }

    public T pop(){
        if(this.isEmpty())
            throw new EmptyStackException();
        T value = this.list.get(0);
        this.list.remove(0);
        return value;
    }

    public T peek(){
        return this.list.get(0);
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public void print(){
        for(int i = list.size(); i >= 0; i--)
            System.out.println(list.get(i) + " ");
        System.out.println();
    }

    public int size(){
        return this.size;
    }
}
