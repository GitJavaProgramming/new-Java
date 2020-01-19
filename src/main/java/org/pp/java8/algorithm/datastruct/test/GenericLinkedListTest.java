package org.pp.java8.algorithm.datastruct.test;

import org.pp.java8.algorithm.datastruct.linear.GenericLinkedList;

public class GenericLinkedListTest {
    public static void main(String[] args) {
        GenericLinkedList<Integer> linkedList = new GenericLinkedList();
//        LinkedList<Integer> linkedList = new LinkedList();
        linkedList.addFirst(1);
        linkedList.addFirst(2);
        linkedList.addLast(3);
        linkedList.addLast(4);
        linkedList.addFirst(5); // first
        System.out.println(linkedList);
        System.out.println("getFirst = " + linkedList.getFirst());
        System.out.println("getLast = " + linkedList.getLast());
    }
}
