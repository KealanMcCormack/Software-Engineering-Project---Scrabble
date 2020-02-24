/**
 *
 * @author Gerard Colman - 18327576
 * @author Lukasz Filanowski - 18414616
 * @author Kealan McCormack - 18312236
 *
 * Board hold data structures and methods for use of the board
 */


public class Board{
    //2-D array of squares holds the information for the board

    /**
     *  boardArray holds information for the board
     */

    protected static Square[][] boardArray = new Square[15][15];

    public Board() { //constructor for board
        createBoard();
    }

    /**
     * enum to hold information on tile values
     */
    enum tileVal {//enum holds the value of the different special tiles and how they will be displayed
        Standard("    "),
        Centre(" () "),
        DoubleWord(" DW "),
        TripleWord(" TW "),
        DoubleLetter(" DL "),
        TripleLetter(" TL ");

        /**
         * @param tile enum to hold tile values
         */

        private final String tile; //initialising enum

        tileVal(String tile)//setter for tile value
        {
            this.tile = tile;
        }

        @Override
        public String toString()//Overridden toString for the enum to display the string form rather than name of enum(DW instead of DoubleWord)
        {
            return this.tile;
        }
    }

    /**
     * Square class that holds information on each square (its value and the tile placed there)
     */
    private class Square {//Holds the information for the Square

        private char characterVal;    //Holds placed character
        private tileVal tile;         //Type of tile, refers to enum

        /**
         *
         * @param CharacterVal stores the value of tile placed
         * @param t stores value of tile on board
         */
        Square(char CharacterVal, tileVal t) {//Constructor for Square
            setCharacterVal(CharacterVal);
            setTileVal(t);
        }

        /**
         *
         * @return
         * getCharacterVal returns the character value
         */
        public char getCharacterVal() {//getter for character value
            return characterVal;
        }

        /**
         *
         * @return
         * getTileVal returns the tile value
         */

        public tileVal getTileVal() {//getter for tile value
            return tile;
        }

        /**
         *
         * @param CharacterVal
         * setCharacterVal sets the character value
         */

        public void setCharacterVal(char CharacterVal) {//setter for character value
            characterVal = CharacterVal;
        }

        /**
         *
         * @param t
         * setTileVal sets the tile value
         */

        public void setTileVal(tileVal t) {//setter for tile value
            this.tile = t;
        }
    }

    /**
     *
     * @param x row coordinate
     * @param y column coordinate
     * @return
     * Connecting returns true if a character is found in a conjoining tile
     */
    protected boolean Connecting(int x, int y) { //returns true if a character is found in a conjoining tile
        if (x != 0) {//Stops any calls outside the array
            if (boardArray[x - 1][y].getCharacterVal() != ' ') {//checks each possible tile around the co-ordinates
                return true;
            }
        }

        if (x != 14) {
            if (boardArray[x + 1][y].getCharacterVal() != ' ') {
                return true;
            }
        }

        if (y != 14) {
            if (boardArray[x][y + 1].getCharacterVal() != ' ') {
                return true;
            }
        }

        if (y != 0) {
            if (boardArray[x][y - 1].getCharacterVal() != ' ') {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param arr input from previous 3 placements
     * @return
     * inLine checks if tiles placed are a horizontal or vertical vector
     */

    protected boolean inLine(int arr[]) {//Needs input of the previous 3 inputs
        int x = 0, y = 0;         //Checks if tiles placed are in a line

        if(arr.length == 2){//base case
            return Connecting(arr[0], arr[1]);
        }

        x = arr[0] - arr[2];
        y = arr[1] - arr[3];

        if(x != 0 && y == 0){//Makes sure the tiles are placed on same axis
            for(int count = 0;count < arr.length - 2;count += 2){//increments across x values in array
                x = arr[count] - arr[count + 2];
                y = arr[count + 1] - arr[count + 3];
                if(x > 0){
                    for(int i = arr[count];i > arr[count + 2];i--){
                        if(boardArray[i][arr[1]].getCharacterVal() == ' '){
                            return false;
                        }
                    }
                }
                if(x < 0){
                    for(int i = arr[count];i < arr[count + 2];i++){
                        if(boardArray[i][arr[1]].getCharacterVal() == ' '){
                            return false;
                        }
                    }
                }
                if(y != 0){
                    return false;
                }
            }
            return true;
        }
        if(y != 0 && x == 0){
            for(int count = 1;count < arr.length - 2;count += 2){
                x = arr[count - 1] - arr[count + 1];
                y = arr[count] - arr[count + 2];
                if(y > 0){
                    for(int i = arr[count];i > arr[count + 2];i--){
                        if(boardArray[arr[0]][i].getCharacterVal() == ' '){
                            return false;
                        }
                    }
                }
                if(y < 0){
                    for(int i = arr[count];i < arr[count + 2];i++){
                        if(boardArray[arr[0]][i].getCharacterVal() == ' '){
                            return false;
                        }
                    }
                }
                if(x != 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @param f instance of frame
     * @param letterIndex index of letter to be placed
     * @param x row coordinate
     * @param y column coordinate
     * @return
     * placeLetter is used to place a letter on the board in the coordinates specified
     */
    protected char placeLetter(Frame f, int letterIndex, int x, int y) {//Places a character in the provided co-ordinates

        if (boardArray[x][y].getCharacterVal() != ' ') {//Checks whether there is already a letter placed
            throw new IllegalArgumentException("Already a tile placed on this square");
        }

        char a = f.getLetterIndex(letterIndex);

        if(!(f.getLetter(f.getLetterIndex(letterIndex))))//Checks the character is in the frame
        {
            throw new IllegalArgumentException("Tile not in frame");
        }

        boardArray[x][y].setCharacterVal(f.playLetter(letterIndex));//Places character on the board
        return a;
    }

    /**
     *
     * @param x row coordinate
     * @param y column coordinate
     * @return
     * inBounds returns true if tile to be placed is in bounds of array
     */
    protected boolean inBounds(int x, int y) {//Checks any tile being placed is within bounds
        if (x > -1 && x < 15 && y > -1 && y < 15) {//Checks bounds
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param f instance of frame
     * @param letterIndex index of letter to be placed
     * firstPlacement places first tile in centre of board
     */
    protected void firstPlacement(Frame f, int letterIndex) {//Places the first tile in the centre
        placeLetter(f, letterIndex, 7,7);
    }

    /**
     *
     * @param x row coordinate
     * @param y column coordinate
     * @return
     * tileOnTile returns true if t a tile is being laced on a space with a tile already there
     */
    protected boolean tileOnTile(int x, int y) {//Ensures a character isn't placed on another
        if (boardArray[x][y].getCharacterVal() != ' ') {//Checks if no character has been placed
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param frame instance of frame
     * @return
     * hasTiles returns true if frame has tiles
     */
    protected boolean hasTiles(Frame frame)//Checks if the frame is empty
    {
        return !(frame.empty());
    }

    /**
     *
     * @param x row coordinate
     * @param y column coordinate
     * @return
     * getCharVal used for testing to know the value in a given Square of the array
     */
    protected char getCharVal(int x, int y){//Used for testing to know the value in a given Square of the array
        return boardArray[x][y].getCharacterVal();
    }

    //METHODS TO DO WITH THE BOARD

    /**
     * boardReset resets the board
     */
    public void boardReset(){ //resets board
        createBoard();
    }

    /**
     * createBoard creates the board
     */
    public void createBoard(){ //calls methods to initialise and create the board
        boardInit();
        createHalf();
        createCenter();
        flipHalf();
    }

    /**
     * boardInit initialises each square of the board to be an instance of the Square class
     */
    private void boardInit(){  //initialises each square of the board to be an instance of the Square class
        for(int count = 0; count <15; count++){
            for(int count2 = 0;count2<15;count2++){
                boardArray[count][count2] = new Board.Square(' ', Board.tileVal.Standard);
            }
        }
    }

    /**
     * createHalf creates top half of the board
     */
    private void createHalf(){  //creates top half of the board
        for(int row = 0;row<8;row++){
            for(int column = 0;column<15;column++){
                switch (row){
                    case 0:
                        if(column == 0 || column == 7 || column == 14){ //Creates triple word values
                            boardArray[row][column].setTileVal(tileVal.TripleWord);
                        }
                        if(column == 3 || column == 11){ //Double letter
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                    case 1:
                        if(column == 1 || column == 13){ //Double Word
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        if(column == 5 || column == 9){ //Triple letter
                            boardArray[row][column].setTileVal(tileVal.TripleLetter);
                        }
                        break;
                    case 2:
                        if(column == 2 || column == 12){ //Double Word
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        if(column == 6 || column == 8){
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                    case 3:
                        if(column == 0 || column == 7 || column == 14){
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        if(column == 3 || column == 11){
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        break;
                    case 4:
                        if(column == 4 || column == 10){
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        break;
                    case 5:
                        if(column == 1 || column == 5 || column == 9 || column == 13){
                            boardArray[row][column].setTileVal(tileVal.TripleLetter);
                        }
                        break;
                    case 6:
                        if(column == 2 || column == 6 || column == 8 || column == 12){
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                }
            }
        }
    }

    /**
     * createCenter creates the centre row of the board
     */
    private void createCenter(){  //creating the centre row of the board
        for(int column = 0;column<15;column++){
            if(column == 0 || column == 14){
                boardArray[7][column].setTileVal(tileVal.TripleWord);
            }
            if(column == 3 || column == 11){
                boardArray[7][column].setTileVal(tileVal.DoubleLetter);
            }
            if(column == 7){
                boardArray[7][column].setTileVal(tileVal.Centre);
            }
        }
    }

    /**
     * flipHalf flips the top half of the board and copies it in the bottom half
     */
    private void flipHalf(){ //flipping the top half to complete board and copies it in the bottom half
        for(int row = 15;row>7;row--){
            for(int column = 0;column<15;column++){
                switch (row){
                    case 14:
                        if(column == 0 || column == 7 || column == 14){ //Creates triple word values
                            boardArray[row][column].setTileVal(tileVal.TripleWord);
                        }
                        if(column == 3 || column == 11){ //Double letter
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                    case 13:
                        if(column == 1 || column == 13){ //Double Word
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        if(column == 5 || column == 9){ //Triple letter
                            boardArray[row][column].setTileVal(tileVal.TripleLetter);
                        }
                        break;
                    case 12:
                        if(column == 2 || column == 12){ //Double Word
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        if(column == 6 || column == 8){
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                    case 11:
                        if(column == 7 || column == 0 || column == 14)
                        {
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }

                        if(column == 3 || column == 11){
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        break;
                    case 10:
                        if(column == 4 || column == 10){
                            boardArray[row][column].setTileVal(tileVal.DoubleWord);
                        }
                        break;
                    case 9:
                        if(column == 1 || column == 13)
                        {
                            boardArray[row][column].setTileVal(tileVal.TripleLetter);
                        }

                        if(column == 5 || column == 9){
                            boardArray[row][column].setTileVal(tileVal.TripleWord);
                        }
                        break;
                    case 8:
                        if(column == 2 || column == 12)
                        {
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }

                        if(column == 6 || column == 8)
                        {
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                    case 7:
                        if(column == 2 || column == 6 || column == 8 || column == 12){
                            boardArray[row][column].setTileVal(tileVal.DoubleLetter);
                        }
                        break;
                }
            }
        }
    }

    /**
     *
     * @return
     * printBoard prints completed board to console
     */
    public String printBoard() { //prints board to console
        String output = "";

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++)//Loops through array of Squares
            {
                output += "|";
                if (boardArray[i][j].getCharacterVal() == ' ') {
                    output += boardArray[i][j].getTileVal();//Prints tile value if character not placed
                } else {
                    output += " " + boardArray[i][j].getCharacterVal() + "  ";
                }
            }
            output += "|" + "\n";  //adds new line and vertical divisor
        }
        return output;  //returns the formatted board
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.createBoard();
        System.out.println(b.printBoard());
        Frame Letters = new Frame();
        b.placeLetter(Letters, 1, 6, 6);
        b.placeLetter(Letters, 1, 6, 7);
        b.placeLetter(Letters, 1, 6, 8);
        b.firstPlacement(Letters, 1);
        int[] arr = new int[6];
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 5;
        arr[4] = 6;
        arr[5] = 4;
        System.out.println(b.printBoard());
        System.out.println(b.inLine(arr));
    }

}