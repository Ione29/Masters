package com.pp_lab_05.beginner_templates;

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public void add(T val) {
        root = insertRec(root, val);
    }

    private Node<T> insertRec(Node<T> root, T val) {
        if(root == null) {
            root = new Node<>(val);
            return root;
        }
        
        if(searchFor(val) == true)
            return root;

        if(val.compareTo(root.val) < 0)
            root.left = insertRec(root.left, val);
        else
            root.right = insertRec(root.right, val);

        return root;
    }

    public boolean searchFor(T val){
        return searchForRec(root, val);
    }

    public boolean searchForRec(Node<T> root, T val){
        if(root == null)
            return false;

        if(val.compareTo(root.val) == 0)
            return true;
        if(val.compareTo(root.val) < 0)
            return searchForRec(root.left, val);
        return searchForRec(root.right, val);
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node<T> curr) {
        if(curr != null){
            inOrderRec(curr.left);
            System.out.print(curr.val + " ");
            inOrderRec(curr.right);
        }
    }

    private static class Node<T> {
        T val;
        Node<T> left, right;

        public Node(T val) {
            this.val = val;
        }
    }
}
