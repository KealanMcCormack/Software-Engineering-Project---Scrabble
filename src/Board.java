public class Board {
    //2-D array of squares holds the information for the board
    protected static Square[][] boardArray = new Square[15][15];

    public Board() { //constructor for board
        createBoard();
    }

    enum tileVal {//enum holds the value of the different special tiles and how they will be displayed
        Standard("    "),
        Centre(" () "),
        DoubleWord(" DW "),
        TripleWord(" TW "),
        DoubleLetter(" DL "),
        TripleLetter(" TL ");

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

    private class Square {//Holds the information for the Square

        private char characterVal;    //Holds placed character
        private tileVal tile;         //Type of tile, refers to enum

        Square(char CharacterVal, tileVal t) {//Constructor for Square
            setCharacterVal(CharacterVal);
            setTileVal(t);
        }

        public char getCharacterVal() {//getter for character value
            return characterVal;
        }

        public tileVal getTileVal() {//getter for tile value
            return tile;
        }

        public void setCharacterVal(char CharacterVal) {//setter for character value
            characterVal = CharacterVal;
        }

        public void setTileVal(tileVal t) {//setter for tile value
            this.tile = t;
        }
    }


    protected boolean Connecting(int x, int y) { //returns true if a character is found in a conjoining tile
        if (x != 0) {//Stops any calls outside the array
            if (boardArray[x - 1][y].getCharacterVal() != ' ') {
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


    protected boolean inLine(int arr[]) {//Needs input of the previous 3 inputs
        int x = 0, y = 0;         //Checks if tiles placed are in a line

        x = arr[0] - arr[2];
        y = arr[1] - arr[3];

        if (x != 0 && y != 0) {
            return false;
        }

        if (x != 0) {
            for (int i = 2; i < arr.length; i += 2) {
                x = arr[i - 2] - arr[i];
                y = arr[i - 1] - arr[i + 1];
                if (y != 0) {
                    return false;
                }
                if (x > 0) {
                    for (int count = 1; count < x; count++) {
                        if (boardArray[(arr[i] + count)][arr[1]].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

                if (x < 0) {
                    for (int count = x; count < 0; count++) {
                        if (boardArray[(arr[i] + count)][arr[1]].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

            }
        }

        if (y != 0) {
            for (int i = 2; i < arr.length; i += 2) {
                x = arr[i - 2] - arr[i];
                y = arr[i - 1] - arr[i + 1];
                if (x != 0) {
                    return false;
                }
                if (y > 0) {
                    for (int count = 1; count < y; count++) {
                        if (boardArray[(arr[0])][arr[i] + count].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

                if (y < 0) {
                    for (int count = y; count < 0; count++) {
                        if (boardArray[(arr[0])][arr[i] + count].getCharacterVal() == ' ') {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }

    protected char placeLetter(Frame f, int letterIndex, int x, int y) {//Places a character in the provided co-ordinates

        if (boardArray[x][y].getCharacterVal() != ' ') {
            throw new IllegalArgumentException("Already a tile placed on this square");
        }

        char a = f.getLetterIndex(letterIndex);

        if(!(f.getLetter(f.getLetterIndex(letterIndex))))
        {
            throw new IllegalArgumentException("Tile not in frame");
        }

        boardArray[x][y].setCharacterVal(f.playLetter(letterIndex));
        return a;
    }

    protected boolean inBounds(int x, int y) {//Checks any tile being placed is within bounds
        if (x > -1 && x < 15 && y > -1 && y < 15) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean firstPlacement(int x, int y) {//Checks the first tile is placed in the centre
        if (x == 7 && y == 7) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean tileOnTile(int x, int y) {//Ensures a character isn't placed on another
        if (boardArray[x][y].getCharacterVal() != ' ') {
            return true;
        } else {
            return false;
        }
    }

    protected boolean hasTiles(Frame frame)//Checks if the frame is empty
    {
        return frame.empty();
    }

    //METHODS TO DO WITH THE BOARD

    public void boardReset(){ //resets board
        createBoard();
    }
    public void createBoard(){ //calls methods to initialise and create the board
        boardInit();
        createHalf();
        createCenter();
        flipHalf();
    }
    private void boardInit(){  //initialises each square of the board to be an instance of the square class
        for(int count = 0; count <15; count++){
            for(int count2 = 0;count2<15;count2++){
                boardArray[count][count2] = new Board.Square(' ', Board.tileVal.Standard);
            }
        }
    }
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
    private void flipHalf(){ //flipping the top half to complete board
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

    public String printBoard() { //Prints board to console
        String output = "";

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++)//Loops through array of Squares
            {
                output += "|";
                if (boardArray[i][j].getCharacterVal() == ' ') {
                    output += boardArray[i][j].getTileVal();//Prints tile value if character not placed
                } else {
                    output += boardArray[i][j].getCharacterVal();
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
    }

}