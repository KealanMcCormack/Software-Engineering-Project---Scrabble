import java.util.Random;

/*
The Array index corresponds to the letter while the content is the amount of tiles in the bag
a = 0
b = 1
c = 3
d = 4
e = 5
f = 6
g = 7
h = 8
i = 9
j = 10
k = 11
l = 12
m = 13
n = 14
o = 15
p = 16
q = 17
r = 18
s = 19
t = 20
u = 21
v = 22
w = 23
x = 24
y = 25
z = 26
blank = 27
 */

/**
 * @author Gerard Colman
 * <h1>Scrabble Bag Class</h1>
 * This class represents the Bag in the scrabble game.
 * It allows the user to draw tiles, reset the bag and convert tiles from ascii codes to characters.
 */
public class ScrabbleBag {
    /**
     * This array represents the Bag.
     */
    int[] Bag = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};
    int[] BagBase = Bag;

    public ScrabbleBag(){
    }

    /**
     * Gives Tiles from bag into player frame
     * @return character to player frame
     */
    public char TileToPlayer(){
        Random rand = new Random();
        int RandomNumber = rand.nextInt(27);
        while(Bag[RandomNumber] == 0){ //Checking if Tiles are empty
            RandomNumber = rand.nextInt(27);
        }
        Bag[RandomNumber]--; //Decreases amount of tiles in the bag
        return ConvertTile(RandomNumber);
    }

    /**
     * Converts tile code to character using ascii
     * @param Tile
     * @return conveted Tile
     */
    private char ConvertTile(int Tile){
        if(Tile == 26){
            return '*'; //Star = blank tile
        }else{
            Tile = Tile + 65; //Adding 65 Ascii value to Tile
            return (char)Tile;
        }
    }

    /**
     * checks if the bag is empty
     * @return true/false value
     */
    public boolean isEmpty(){
        boolean isEmpty = true;
        for(int i = 0;i<27;i++){
            if(Bag[i] != 0){
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * resets the bag
     */
    public void BagReset(){
        for(int i = 0;i<Bag.length;i++){
            Bag[i] = BagBase[i];
        }
    }

    /**
     * Checks number of tiles in the bag
     * @return number of tiles
     */
    public int NumberOfTiles(){
        int sum = 0;
        for(int i = 0;i<Bag.length;i++){
            sum = sum + Bag[i];
        }
        return sum;
    }

    /**
     * prints current tiles in bag
     * @return String of tiles in the bag
     */
    public String CurrentTiles(){
        int Number = 0;
        String out = "";
        for(int i = 0;i<Bag.length;i++){
            out = out + Bag[i] + " ";
            out = out + this.ConvertTile(i) + "'s, ";
        }
        return out;
    }

    /**
     * Takes tile from player frame and puts it in the bag
     * @param Tile Tile received from player
     */
    public void TileToBag(char Tile){
        /**
         * Allows players to put tiles bag in bag
         */
        int TileCode = (int)Tile - 65;
        for(int i = 0;i<Bag.length;i++){
            if(TileCode == i){
                Bag[i]++;
            }
        }
    }
}