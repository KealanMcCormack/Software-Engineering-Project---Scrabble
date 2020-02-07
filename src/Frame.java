package com.company;

import javax.management.InvalidAttributeValueException;
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

    public char playLetter(int index) {//removes characters from the frame

        char removedChar;

        if(index > -1 && index < playerTiles.size()) {//checks if input is valid
            removedChar = playerTiles.get(index);
            playerTiles.remove(index);
            return removedChar;
        } else{
            return ' ';//if input is invalid simply returns an empty character
        }

    }

    public void refill() {//Adds tiles back into the frame from the pool

        for(int count = (7 - playerTiles.size());count > 0;count--) {
            playerTiles.add(pool.TileToPlayer());
        }
    }

    public void setPlayerTiles(char a) {//Adds a letter to the ArrayList
        this.playerTiles.add(a);
    }//Allows addition of letters into frame

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
