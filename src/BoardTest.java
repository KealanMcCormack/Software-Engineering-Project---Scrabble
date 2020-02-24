/**
 * Gerard Colman - 18327576
 * Lukasz Filanowski - 18414616
 * Kealan McCormack - 18312236
 * @author Kealan McCormack
 *
 * BoardTests is a test class for Board
 */
public class BoardTest {

    public static void main(String[] args) {
        BoardTest a = new BoardTest();
        a.createBoardTest();
        a.boardResetTest();
        a.connectingTest();

        a.firstPlacementTest();
        a.hasTilesTest();
        a.inBoundsTest();
        a.inLineTest();
        a.placeLetterTest();
        a.printBoardTest();
        a.tileOnTileTest();
        System.out.println("If no issues printed then All tests passed");
    }
    /**
     * createBoard tests createBoard method
     */

    void createBoardTest() {//Tests createBoard
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        if(Tester.getCharVal(4, 4) == ' ') {
            //Checks that the board exists
        }else {
            throw new IllegalArgumentException();
        }

    }
    /**
     * printBoard tests printBoard method
     */
    void printBoardTest() { //Tests printBoard
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        if(Tester.printBoard() != " "){//Checks that More than an empty String is returned
            assert(true);
        }
    }
    /**
     * placeLetter tests placeLetter method
     */
    void placeLetterTest() { //Tests placeLetter
        Board Tester = new Board(); //New instance of Board
        Frame Letters = new Frame();
        Tester.createBoard();

        Letters.setPlayerTiles('A');//Adds a know character to frame

        Tester.placeLetter(Letters, 8, 2,2);//Places letter
        //Checks if letter has been placed correctly
        if(Tester.getCharVal(2, 2) == 'A') {
            //Checks that the board exists
        }else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * connecting tests Connecting method
     */

    void connectingTest() { //Tests Connect
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();


        Tester.placeLetter(Letters, 1, 8, 7);//Places letters in know positions
        Tester.placeLetter(Letters, 1, 8, 9);
        Tester.placeLetter(Letters, 1, 7, 8);
        Tester.placeLetter(Letters, 1, 9, 8);

        //Checks different orientations of connecting letters
        if(Tester.Connecting(8, 8) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with connecting");
        }

        if(Tester.Connecting(7, 6) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with connecting");
        }
        //Ensures diagonals don't work

        Tester.placeLetter(Letters, 1, 0,0);
        //Checks edge of board won't break method
        if(Tester.Connecting(0, 1) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with connecting");
        }
    }

    /**
     * inLine tests inLine method
     */

    void inLineTest() { //Tests inLine
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 6, 6);//Places letters to allow testing
        Tester.placeLetter(Letters, 1, 6, 7);
        Tester.placeLetter(Letters, 1, 6, 8);

        Tester.placeLetter(Letters, 1, 6, 5);
        Tester.placeLetter(Letters, 1, 6, 4);
        Letters.refill();//Stops frame running out of letters
        Tester.placeLetter(Letters, 1, 7, 6);
        Tester.placeLetter(Letters, 1, 8, 6);

        Tester.placeLetter(Letters, 1, 5, 6);
        Tester.placeLetter(Letters, 1, 4, 6);

        int[] arr = new int[6];
        arr[0] = 6;//Sets up array with co-ordinates being tested
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 7;
        arr[4] = 6;
        arr[5] = 8;

        //Tests each direction tiles can be placed in
        if(Tester.inLine(arr) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inLine");
        }
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 6;
        arr[3] = 5;
        arr[4] = 6;
        arr[5] = 4;
        if(Tester.inLine(arr) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inLine");
        }

        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 7;
        arr[3] = 6;
        arr[4] = 8;
        arr[5] = 6;

        if(Tester.inLine(arr) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inLine");
        }
        arr[0] = 6;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 6;
        arr[4] = 4;
        arr[5] = 6;
        if(Tester.inLine(arr) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inLine");
        }
        arr[0] = 4;
        arr[1] = 6;
        arr[2] = 5;
        arr[3] = 8;
        arr[4] = 4;
        arr[5] = 6;
        if(Tester.inLine(arr) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inLine");
        }//Ensures random placements aren't allowed
    }

    /**
     * inBounds tests inBounds method
     */

    void inBoundsTest() {//Tests inBounds
        Board Tester = new Board();
        Tester.createBoard();
        if(Tester.inBounds(-1, 0) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inBounds");
        }
        //Tests if placements are inBounds
        if(Tester.inBounds(0, 15) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inBounds");
        }
        if(Tester.inBounds(8, -1) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inBounds");
        }
        if(Tester.inBounds(15, 0) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inBounds");
        }
        if(Tester.inBounds(6, 0) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with inBounds");
        }

    }

    /**
     * firstPlacement tests firstPlacement
     */

    void firstPlacementTest() {//Tests firstPlacement
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Letters.setPlayerTiles('A');
        Tester.firstPlacement(Letters, 8);
        //Checks character has been placed int the middle of the board
        if(Tester.getCharVal(7, 7) == 'A') {
            //Checks that the board exists
        }else {
            System.out.println("Issue with FirstPlacement");
        }
    }

    /**
     * tileOnTile tests tileOnTile
     */

    void tileOnTileTest() {//Tests tileOnTile
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame();

        Tester.placeLetter(Letters, 1, 2,2);//Plays character

        //Checks known used tile

        if(Tester.tileOnTile(2,2) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with TileOnTile");
        }
        if(Tester.tileOnTile(4, 4) == false) {//Checks known empty tile
            //Checks that the board exists
        }else {
            System.out.println("Issue with TileOnTile");
        }

    }

    /**
     * hasTiles tests hasTiles
     */

    void hasTilesTest() { //Tests hasTiles
        Frame Letters = new Frame(); //New instance of Frame
        Board Tester = new Board(); //New instance of Board
        //Frame starts with tiles so should be true
        if(Tester.hasTiles(Letters) == true) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with hasTiles");
        }
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        Letters.playLetter(1);
        //Played all tiles so frame should be empty
        if(Tester.hasTiles(Letters) == false) {
            //Checks that the board exists
        }else {
            System.out.println("Issue with hasTiles");
        }
    }

    /**
     * boardReset tests boardReset
     */

    void boardResetTest() {
        Board Tester = new Board(); //New instance of Board
        Tester.createBoard();
        Frame Letters = new Frame(); //New instance of Frame
        Letters.setPlayerTiles('A');

        Tester.placeLetter(Letters, 8, 6, 6);//Plays a letter on the board

        if(Tester.getCharVal( 6, 6) == 'A') {
            //Checks that the board exists
        }else {
            System.out.println("Issue with BoardReset");
        }
        Tester.boardReset();
        //Checks the letter is no longer present after reset
        if(Tester.getCharVal(6, 6) == ' ') {
            //Checks that the board exists
        }else {
            System.out.println("Issue with BoardReset");
        }
    }

}


