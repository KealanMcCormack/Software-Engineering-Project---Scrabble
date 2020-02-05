package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Frame {

    ScrabbleBag pool = new ScrabbleBag();

    //Data structure storing player tiles
    private ArrayList<Character> playerTiles = new ArrayList<>();

    public boolean empty() {//Checks if the arrayList has any contents
        return playerTiles.isEmpty();
    }

    public String hasLetters() {//Checks if there are any letters in the frame and returns a string of them
        if(playerTiles.isEmpty()) {
            return "No letters";
        }else {
            String temp = "";
            for(char a:playerTiles) {
                temp = temp + a + " ";
            }
            return temp;//Return the character of tiles still in the frame
        }
    }

    public String displayFrame() {//returns a string of the characters in the frame

        String temp = "";

        for(char a:playerTiles) {
            temp = temp + " " + a ;
        }

        return temp;
    }

    public char playLetter() {//removes characters from the frame
        int a = -1;

        System.out.println("PLease input a valid index");
        System.out.println("To exit please enter -1");
        Scanner in = new Scanner(System.in);

        if(in.hasNext()) {
            a = in.nextInt() - 1;//Will use 1-7 for playerTiles input
        }

        in.close();//closes scanner

        if(a == -2){//Allows an escape if the player decides to change their play
            return ' ';
        }

        char playerInput;
        if(a > -1 && a < playerTiles.size()) {//checks if input is valid
            playerInput = playerTiles.get(a);
            playerTiles.remove(a);
            return playerInput;
        }else {//If input is invalid asks for another entry
            System.out.println("Error");
            System.out.println("PLease input a valid index");
            playerInput = playLetter();
        }

        return  playerInput;

    }

    public void refill() {//Adds tiles back into the frame from the pool
        for(int count = 0;count < (7 - playerTiles.size());count++) {
            playerTiles.add(pool.TileToPlayer());
        }
    }

    public void setPlayerTiles(char a) {//Adds a letter to the ArrayList
        this.playerTiles.add(a);
    }

//Not needed for assignment 1
 public void swap() {        //Allows the player to exchange tiles one at a time, will require more work

      int temp;              //Needs protection against exploitation
	  ArrayList<Integer> a = new ArrayList<Integer>();
	  Scanner in = new Scanner(System.in);
	  System.out.println("How many tiles would you like to exchange?");
	  temp = in.nextInt();

	  for(int count = 0;count < temp;count++) {

		  if(in.hasNext()) {
		    int intake = in.nextInt();
		    if(playerTiles.size() < intake) {

		        playerTiles.add(pool.TileToPlayer());
                pool.TileToBag(playerTiles.get(intake));
                playerTiles.remove(intake);

            }
		  }

	  }
	  in.close();

  }


}
