import java.util.ArrayList;

/**
 * Gerard Colman - 18327576
 * Lukasz Filanowski - 18414616
 * Kealan McCormack - 18312236
 * @author Kealan
 * The frame class uses an ArrayList (playerTiles) to hold the values associated
 * with the players frame and contains methods that allow the use and manipulation of this data
 * Requires the ScrabbleBag class to function
 */
public class Frame {


    //Data structure storing player tiles
    private ArrayList<Character> playerTiles = new ArrayList<>();

    public Frame(ScrabbleBag pool){
        refill(pool);
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
     * @return string of characters in the frame
     */
    public String displayFrame() {//returns a string of the characters in the frame
        String temp = "";

        for(char a:playerTiles) {
            temp = temp + " " + a ;
        }

        return temp;
    }

    /**
     * The method playLetter takes a Character value for the of the tile being played
     * and removes it from the ArrayList. It then returns true if the operation was successful
     * @param character to be removed
     * @return boolean true if operation successful
     */
    public boolean playLetter(char character) {//removes characters from the frame
        if(playerTiles.contains(character)) {//checks if input is valid
            for(int count = 0;count < playerTiles.size();count++){//Finds the character
                if(playerTiles.get(count) == character){
                    playerTiles.remove(count);
                    return true;
                }
            }
        }else{
            return false;
        }
      return false;
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
    public void refill(ScrabbleBag pool) {//Adds tiles back into the frame from the pool

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


 public void swap(char[] swapping, ScrabbleBag pool) {  //Allows the player to exchange tiles,  will require more work
     int count = 0;

     while(swapping[count] != ' ' && count < 7){
         System.out.println(swapping[count]);
         playerTiles.remove(playerTiles.indexOf(swapping[count]));
          pool.TileToBag(swapping[count]);
          count++;
     }
  }
	
public boolean getLetter(char check){
        return playerTiles.contains(check);
    }

public char getLetterIndex(int index){
        return playerTiles.get(index - 1);
    }

    public ArrayList<Character> getPlayerTiles() {
        return playerTiles;
    }
}
