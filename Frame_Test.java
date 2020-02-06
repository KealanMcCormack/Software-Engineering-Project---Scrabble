package com.company;
public class Frame_Test {
    //Can't test refill until bag exists
    public static void main(String[] args) {
        Frame a = new Frame();
        char temp;

        System.out.println(a.empty() + " " + a.hasLetters());//checks that functions work on an empty frame
        System.out.println(a.displayFrame());

        a.refill();//tests refill correctly adds 7 letters
        System.out.println(a.displayFrame());
        System.out.println(a.empty() + " " + a.hasLetters());//checks that functions work on a filled frame

        temp = a.playLetter(1);//Tests removal of tile and addition of tile
        a.setPlayerTiles('A');
        System.out.println(a.displayFrame());

        a.playLetter(3);
        System.out.println(a.displayFrame());
        a.playLetter(4);
        System.out.println(a.displayFrame());
        a.playLetter(1);
        System.out.println(a.displayFrame());
        a.playLetter(2);
        System.out.println(a.displayFrame());//Further tests on removal and refill
        a.refill();
        System.out.println(a.displayFrame());


    }
}
