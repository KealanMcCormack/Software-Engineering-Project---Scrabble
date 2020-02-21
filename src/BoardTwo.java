public class BoardTwo {
    int rotate = 0;

    protected static Square[][] boardArray = new Square[15][15];


    enum tileVal{
        Standard("    "),
        Centre(" () "),
        DoubleWord(" DW "),
        TripleWord(" TW "),
        DoubleLetter(" DL "),
        TripleLetter(" TL ");

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
    public void createBoard(){
        createHalf();
        createCenter();
        flipHalf();
    }
    private void createHalf(){
        for(int v = 0;v<7;v++){
            for(int h = 0;h<15;h++){
                switch (v){
                    case 0:
                        if(h == 0 || h == 7 || h == 15){ //Creates triple word values
                            boardArray[v][h].setTileVal(tileVal.TripleWord);
                        }
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                }
            }
        }
    }
    private void createCenter(){

    }
    private void flipHalf(){

    }
}