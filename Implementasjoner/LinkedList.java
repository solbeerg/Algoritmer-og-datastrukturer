/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkedList;

import javafx.scene.Node;

/**
 *
 * @author eivind
 */
public class LinkedList<T> {

    public T value;
    public LinkedList<T> next;
    public LinkedList<T> prev;



    public static void main(String[] args) {

        LinkedList<String> head = new LinkedList<>();

        head.value = "B";

        head.next = new LinkedList<>();
        head.next.value = "R";



        head.next.next = new LinkedList<>();
        head.next.next.value = "A";


        LinkedList<String> current = head;
        while (current!= null) {
            System.out.println(current.value);
            current = current.next;


        }

    }

}
