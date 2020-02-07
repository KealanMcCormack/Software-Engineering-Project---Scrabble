
public class Frame_Test {
    //Can't test refill until bag exists
    public static void main(String[] args) {
        Frame a = new Frame();
        char temp;

        System.out.println(a.empty() + " " + a.hasLetters());//checks that functions work on frame
        System.out.println(a.displayFrame());

        a.playLetter(1);
        a.playLetter(2);
        a.playLetter(3);

        System.out.println(a.displayFrame());
        System.out.println(a.empty() + " " + a.hasLetters());//checks that functions work on an empty frame

        temp = a.playLetter(1);//Tests removal of tile and addition of tile
        a.setPlayerTiles('A');
        System.out.println(a.displayFrame());

        a.playLetter(3);
        System.out.println(a.displayFrame());
        a.playLetter(1);
        System.out.println(a.displayFrame());
        a.playLetter(1);
        System.out.println(a.displayFrame());
        a.playLetter(1);

        System.out.println(a.displayFrame());//Further tests on removal and refill
        a.refill();
        System.out.println(a.displayFrame());


    }
}
