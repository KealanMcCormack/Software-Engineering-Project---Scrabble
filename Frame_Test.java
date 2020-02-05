package com.company;
public class Frame_Test {
    //Can't test refill until bag exists
    public static void main(String[] args) {
        Frame a = new Frame();
        char temp;

        System.out.println(a.empty() + " " + a.hasLetters());
        System.out.println(a.displayFrame());


            a.refill();



        System.out.println(a.displayFrame());
        System.out.println(a.empty() + " " + a.hasLetters());
        temp = a.playLetter();
        System.out.println(a.displayFrame());


    }
}
