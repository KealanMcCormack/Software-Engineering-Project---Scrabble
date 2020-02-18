

public class Board {

    protected Square[][] boardArray = new Square[15][15];
    public enum tileVal {Standard, DoubleWord, TripleWord, DoubleLetter, TripleLetter};

    private class Square{

        private char characterVal = ' ';
        private tileVal tile;

        Square(char CharacterVal, tileVal t){
            setCharacterVal(CharacterVal);
            setTileVal(t);
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

    protected void boardReset(){

    }

    public void printBoard(){

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
