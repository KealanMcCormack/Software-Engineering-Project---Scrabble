package com.company;

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

}
