/**
 * Tests the scrabble bag class.
 */
public class ScrabbleBagTest {
    public static void main(String[] args){
        ScrabbleBag SB = new ScrabbleBag();

        for(int i = 0;i<7;i++){
            System.out.print(SB.TileToPlayer() + ", ");
            System.out.print("\n");
        }
        System.out.println(SB.isEmpty());
        SB.BagReset();
        System.out.println(SB.NumberOfTiles());
        System.out.println(SB.CurrentTiles());


    }
}