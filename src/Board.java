public class Board {
    //2-D array of squares holds the information for the board
    protected static Square[][] boardArray = new Square[15][15];

    enum tileVal {//enum holds the value of the different special tiles
        Standard("    "),
        Centre(" () "),
        DoubleWord(" DW "),
        TripleWord(" TW "),
        DoubleLetter(" DL "),
        TripleLetter(" TL ");

        private final String tile;

        tileVal(String tile)//setter for tile value
        {
            this.tile = tile;
        }

        @Override
        public String toString()//Overridden toString for the enum
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
            output += "|" + "\n";
        }
        return output;
    }


    public static void main(String[] args) {
        Board b = new Board();
        b.createBoard();
        System.out.println(b.printBoard());
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

        if (boardArray[x][y].getCharacterVal() == ' ') {
            throw new IllegalArgumentException("Already a tile placed on this square");
        }

        char a = f.getLetterIndex(letterIndex);
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
    public void boardReset(){
        createBoard();
    }
    public void createBoard(){
        boardInit();
        createHalf();
        createCenter();
        flipHalf();
    }
    private void boardInit(){
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                boardArray[i][j] = new Board.Square(' ', Board.tileVal.Standard);
            }
        }
    }
    private void createHalf(){
        for(int v = 0;v<7;v++){
            for(int h = 0;h<15;h++){
                switch (v){
                    case 0:
                        if(h == 0 || h == 7 || h == 14){ //Creates triple word values
                            boardArray[v][h].setTileVal(Board.tileVal.TripleWord);
                        }
                        if(h == 3 || h == 11){ //Double letter
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                    case 1:
                        if(h == 1 || h == 13){ //Double Word
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        if(h == 5 || h == 9){ //Triple letter
                            boardArray[v][h].setTileVal(Board.tileVal.TripleLetter);
                        }
                        break;
                    case 2:
                        if(h == 2 || h == 12){ //Double Word
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        if(h == 6 || h == 8){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                    case 3:
                        if(h == 0 || h == 7 || h == 14){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        if(h == 3 || h == 11){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                    case 4:
                        if(h == 4 || h == 10){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        break;
                    case 5:
                        if(h == 1 || h == 5 || h == 9 || h == 13){
                            boardArray[v][h].setTileVal(Board.tileVal.TripleWord);
                        }
                        break;
                    case 6:
                        if(h == 2 || h == 6 || h == 8 || h == 12){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                }
            }
        }
    }
    private void createCenter(){
        for(int i = 0;i<15;i++){
            if(i == 0 || i == 14){
                boardArray[7][i].setTileVal(Board.tileVal.TripleWord);
            }
            if(i == 3 || i == 11){
                boardArray[7][i].setTileVal(Board.tileVal.DoubleLetter);
            }
            if(i == 7){
                boardArray[7][i].setTileVal(Board.tileVal.Centre);
            }
        }
    }
    private void flipHalf(){
        for(int v = 14;v>7;v--){
            for(int h = 0;h<15;h++){
                switch (v){
                    case 14:
                        if(h == 0 || h == 7 || h == 14){ //Creates triple word values
                            boardArray[v][h].setTileVal(Board.tileVal.TripleWord);
                        }
                        if(h == 3 || h == 11){ //Double letter
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                    case 13:
                        if(h == 1 || h == 13){ //Double Word
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        if(h == 5 || h == 9){ //Triple letter
                            boardArray[v][h].setTileVal(Board.tileVal.TripleLetter);
                        }
                        break;
                    case 12:
                        if(h == 2 || h == 12){ //Double Word
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        if(h == 6 || h == 8){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                    case 10:
                        if(h == 0 || h == 7 || h == 14){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        if(h == 3 || h == 11){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                    case 9:
                        if(h == 4 || h == 10){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleWord);
                        }
                        break;
                    case 8:
                        if(h == 1 || h == 5 || h == 9 || h == 13){
                            boardArray[v][h].setTileVal(Board.tileVal.TripleWord);
                        }
                        break;
                    case 7:
                        if(h == 2 || h == 6 || h == 8 || h == 12){
                            boardArray[v][h].setTileVal(Board.tileVal.DoubleLetter);
                        }
                        break;
                }
            }
        }
    }
}
