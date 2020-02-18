public class Board {

    protected static Square[][] boardArray = new Square[15][15];

    enum tileVal{
        Standard(" "),
        Star("*"),
        DoubleWord("DW"),
        TripleWord("TW"),
        DoubleLetter("DL"),
        TripleLetter("TL");

        private final String tile;

        tileVal(String tile)
        {
            this.tile = tile;
        }

        @Override
        public String toString()
        {
            return this.tile;
        }
    }

    private class Square{

        private char characterVal;
        private tileVal tile;

        Square(char CharacterVal, tileVal t){
            setCharacterVal(CharacterVal);
            setTileVal(t);
        }

        Square()
        {
        }

        public char getCharacterVal() {
            return characterVal;
        }

        public tileVal getTileVal() {
            return tile;
        }

        public void setCharacterVal(char CharacterVal) {
            characterVal = CharacterVal;
        }

        public void setTileVal(tileVal t) {
            this.tile = t;
        }
    }

    protected void createBoard()
    {
        for(int row_count = 0; row_count < 15; row_count++)  //For loops that initially create a new square object for each space on the board
        {
            for(int column_count = 0; column_count < 15; column_count++)
            {
                boardArray[row_count][column_count] = new Square(); //Creating new square object
            }
        }

        //CREATING THE BOARD WITH TILE VALUES

        for(int row_count = 0; row_count < 8; row_count++)
        {
            for(int column_count = 0; column_count < 8; column_count++)
            {
                if((row_count == 7 && column_count == 0) || (row_count == 0 && column_count == 7) || (row_count == 0 && column_count == 0))
                {
                    boardArray[row_count][column_count].setTileVal(tileVal.TripleLetter);
                }

                if(row_count == 7 && column_count == 7)
                {
                    boardArray[row_count][column_count].setTileVal(tileVal.Star);
                }

                if((row_count == column_count) && (row_count > 0 && row_count < 5) && (column_count > 0 && column_count < 5))
                {
                    boardArray[row_count][column_count].setTileVal(tileVal.DoubleWord);
                }
            }
        }
    }

    protected void boardReset(){

    }

    public void printBoard(){

    }

    public String toString()
    {
        String output = "";

        return output;
    }

    public static void main(String[] args) {
        /*Board b = new Board();
        b.createBoard();
        System.out.println(boardArray[0][0].getTileVal());
        System.out.println(boardArray[1][1].getTileVal());
        System.out.println(boardArray[2][2].getTileVal());
        System.out.println(boardArray[3][3].getTileVal());
        System.out.println(boardArray[4][4].getTileVal());
        System.out.println(boardArray[5][5].getTileVal());*/
    }

    protected boolean Connecting(int x, int y){ //returns true if a tile is found in a conjoining tile
        if(boardArray[x-1][y].getCharacterVal() != ' '){
            return true;
        }

        if(boardArray[x+1][y].getCharacterVal() != ' '){
            return true;
        }

        if(boardArray[x][y+1].getCharacterVal() != ' '){
            return true;
        }

        if(boardArray[x][y-1].getCharacterVal() != ' '){
            return true;
        }

        return false;
    }

    protected boolean inLine(int arr[]){//Needs input of the previous
        int x = arr.length;


        for(int count = 0;count < arr.length;count++){

        }
        return false;
    }

    protected char placeLetter(int letterIndex, int x, int y){
        Frame f = new Frame();

        if(boardArray[x][y].getCharacterVal() == ' '){
            throw new IllegalArgumentException("Already a tile placed on this square");
        }

        char a = f.getLetterIndex(letterIndex);
        boardArray[x][y].setCharacterVal(f.playLetter(letterIndex));
        return a;
    }

}