package com.pp_lab_05.beginner_templates;

import java.util.NoSuchElementException;

public class MyLinkedList<T>
{
    private Node<T> first;
    private int size = 0;

    public void add(T val)
    {
        if(first == null)
        {
            first = new Node<>(val);
            this.size++;
        }

        else
        {
            Node<T> curr = first;
            while(curr.next != null)
                curr = curr.next;
            curr.next = new Node<>(val);
            this.size++;
        }
    }

    public void add(T val, int index)
    {
        if(index < 0)
        {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if(first == null && index == 0)
            first = new Node<>(val);
        else
        {
            if(index == 0)
            {
                Node<T> next = first;
                first = new Node<>(val);
                first.next = next;
                this.size++;
                return;
            }
            Node<T> curr = first;
            int i;
            for(i = 0; i < index - 1 && curr.next != null; i++)
                curr = curr.next;

            if(i < index - 1)
                throw new IndexOutOfBoundsException("Index out of range");


            Node<T> next = curr.next;
            curr.next = new Node<>(val);
            curr.next.next = next;
            this.size++;
        }
    }

    public T get(int index)
    {
        if(first == null)
            throw new NoSuchElementException("List empty");
        if(index < 0)
            throw new IndexOutOfBoundsException("Index out of range");

        Node<T> curr = first;
        int i;
        for(i = 0; i < index && curr.next != null; i++)
            curr = curr.next;

        if(i < index)
            throw new IndexOutOfBoundsException("Index out of range");

        return curr.value;
    }

    public T remove()
    {
        if(first == null)
            throw new NoSuchElementException("List empty");

        this.size--;
        T value = first.value;
        first = first.next;
        return value;

    }

    public T remove(int index)
    {
        if(first == null)
        {
            System.out.println("List empty");
            throw new NoSuchElementException();
        }
        if(index < 0)
            throw new IndexOutOfBoundsException("Index out of range");
        if(index == 0)
            return remove();

        Node<T> curr = first;
        int i;
        for(i = 0; i < index - 1 && curr.next != null; i++)
            curr = curr.next;

        if(curr.next == null)
            throw new IndexOutOfBoundsException("Index out of range");

        T value = curr.next.value;
        curr.next = curr.next.next;
        this.size--;
        return value;
    }

    public int size()
    {
        return this.size;
    }

    public int indexOf(T value)
    {
        if(first == null)
            throw new NoSuchElementException("List empty");
        Node<T> curr = first;
        int i = 0;

        while(curr != null)
        {
            if(curr.value.equals(value))
                return i;
            i++;
            curr = curr.next;
        }
        return -1;
    }

    private static class Node<T> {
        Node<T> next;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

}
