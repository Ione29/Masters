package com.pp_lab_05.advanced;

public interface Stackable<T> {
    public void push(T person);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public void print();
    public int size();
}
