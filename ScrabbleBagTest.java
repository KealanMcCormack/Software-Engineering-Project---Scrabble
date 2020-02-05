public class ScrabbleBagTest {
    public static void main(String[] args){
        ScrabbleBag SB = new ScrabbleBag();
        System.out.println("Tile to Player: " + SB.TileToPlayer());
        System.out.println("isEmpty: " + SB.isEmpty());
        System.out.println("Number of Tiles: " + SB.NumberOfTiles());
        System.out.println("Current Tiles: " + SB.CurrentTiles());
    }
}