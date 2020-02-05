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
        int a = -1;                 //Do we want to be able to play multiple tiles at once or just call
                            //multiple times?
        Scanner in = new Scanner(System.in);

        if(in.hasNext()) {
            a = in.nextInt() - 1;//Will use 1-7 for playerTiles input
        }

        in.close();//closes scanner
        char playerInput;
        if(a > -1 && a < playerTiles.size()) {//checks if input is valid
            playerInput = playerTiles.get(a);
            playerTiles.remove(a);
            return playerInput;
        }else {
            System.out.println("Error, you goober");
            playerInput = playLetter();
        }
        //What to do if the index given doesn't work
        return  playerInput;

    }

    public void refill() {//Adds tiles back into the frame from the pool
        for(int count = 0;count < (7 - playerTiles.size());count++) {
            playerTiles.add(pool.TileToPlayer());
        }
    }

    public void setPlayer(char a) {
        this.playerTiles.add(a);
    }

  public void swap() {
	  int temp;
	  ArrayList<Integer> a = new ArrayList<Integer>();
	  Scanner in = new Scanner(System.in);
	  System.out.println("How many tiles would you like to exchange?");
	  temp = in.nextInt();
	  for(int count = 0;count < temp;count++) {

		  if(in.hasNext()) {
			pool.TileToBag(playerTiles.get(in.nextInt()));
			//playerTiles.remove();
		  }

	  }
	  in.close();

  }
    //Will have two frame classes
    //char TileToPlayer(void)
    //movement of letters possibly by index through terminal
    //Check function for letters in frame - say has letters - public --
    //Check if frame is empty - Say empty frame - public --
    //Function to remove letters from the frame --
    //Refill function --
    //Display frame - public --
    //Make tests
    //Swap function which takes tiles first and sends back chars
    //TileToBag(char v)

}
