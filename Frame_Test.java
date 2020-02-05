package com.company;
public class Frame_Test {
    //Can't test refill until bag exists
    public static void main(String[] args) {
        Frame a = new Frame();
        char temp;
        System.out.println(a.empty() + " " + a.nonEmpty());
        System.out.println(a.displayFrame());

        a.setPlayer('a');
        a.setPlayer('b');
        a.setPlayer('c');
        a.setPlayer('d');
        a.setPlayer('e');
        a.setPlayer('f');
        a.setPlayer('g');


        System.out.println(a.displayFrame());
        System.out.println(a.empty() + " " + a.nonEmpty());
        temp = a.play();
        System.out.println(a.displayFrame());
    }
}
