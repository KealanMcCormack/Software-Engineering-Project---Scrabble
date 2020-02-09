


import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Kealan
 * The frame class uses an ArrayList (playerTiles) to hold the values associated
 * with the players frame and contains methods that allow the use and manipulation of this data
 * Requires the ScrabbleBag class to function
 */
public class Frame {

    ScrabbleBag pool = new ScrabbleBag();

    //Data structure storing player tiles
    private ArrayList<Character> playerTiles = new ArrayList<>();

    public Frame(){
        refill();
    }

    /**
     * The method empty returns a boolean value based on whether the Arraylist is empty
     * True - empty
     * False - contains letters
     * @return true/false value
     *
     */
    public boolean empty() {//Checks if the arrayList has any contents
        return playerTiles.isEmpty();
    }

    /**
     * The method hasLetters checks whether there are any letters in the Arraylist
     * If the Arraylist contains letters the Arraylist is returned as a String
     * Otherwise No letters is returned
     * @return String of letters in frame
     */
    public String hasLetters() {//Checks if there are any letters in the frame and returns a string of them
        /**
         * @return returns string of letters in frame
         */
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

    /**
     * The method displayFrame returns a String of the
     * letters contained in the Arraylist
     * @return letters in frame
     */
    public String displayFrame() {//returns a string of the characters in the frame
        /**
         * @return string of characters in the frame
         */
        String temp = "";

        for(char a:playerTiles) {
            temp = temp + " " + a ;
        }

        return temp;
    }

    /**
     * The method playLetter takes an integer value for the index (will remove 1 from the value) of the tile being played
     * and removes it from the ArrayList. It then returns the char value that has been removed
     * @param index amount of characters you want removed
     * @return removed character
     */
    public char playLetter(int index) {//removes characters from the frame
        index--;
        char removedChar;

        if(index > -1 && index < playerTiles.size()) {//checks if input is valid
            removedChar = playerTiles.get(index);
            playerTiles.remove(index);
            return removedChar;
        } else{//throw error if they select an index that is invalid
            throw new IllegalArgumentException("Invalid index, please check remaining tiles");
        }

    }

    /**
     * The method refill checks how many tiles have been played
     * and refills the ArrayList using the TileToPLayer method in the
     * class scrabbleBag
     */
    public void refill() {//Adds tiles back into the frame from the pool

        for(int count = (7 - playerTiles.size());count > 0;count--) {
            playerTiles.add(pool.TileToPlayer());
        }
    }

    /**
     * The method setPlayerTiles takes a char that should be
     * added to the ArrayList and adds it
     * @param a character added to frame
     */
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
        System.out.println("Please write the index of the tile you wish to swap");
		  if(in.hasNext()) {
		    int intake = in.nextInt();
		    if(playerTiles.size() < intake) {

                pool.TileToBag(playerTiles.get(intake));
                playerTiles.remove(intake);

            }
		  }
	  }

     for(int count = 0;count < temp;count++){
         playerTiles.add(pool.TileToPlayer());
     }

	  in.close();
  }


}
