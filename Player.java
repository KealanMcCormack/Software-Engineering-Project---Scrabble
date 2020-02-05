public class Player {

    private String name;    //Instance variables for player data
    private int score;

    public Player (String name, int score) //Constructor
    {
        setName(name);
        setScore(score);

        if(name.isEmpty())   //Exception that's thrown if player doesn't enter name initially (upon instance creation)
        {
            throw new IllegalArgumentException("Name is not initialised");
        }
    }

    public void setName(String name)   //Method to set name of player
    {
        this.name = name;
    }

    public void setScore(int score)    //Method to set score of player
    {
        this.score = score;

        if(score < 0)     //Exception that's thrown if score is less than 0
        {
            throw new IllegalArgumentException("Score is an invalid value");
        }
    }

    public int getScore()      //Method to get score of player
    {
        return score;
    }

    public String getName()  //Method to get name of player
    {
        return name;
    }

    protected void increaseScore(int increment, int score)   //Method that takes in the player score and a value to increment the score by and increases the score
    {
        setScore((score + increment));
    }

    protected void resetScore()  //Method to reset the score of the player
    {
        setScore(0);
    }

    protected void resetName()  //Method to reset the name of the player
    {
        setName("");
    }

    public static void main(String[] args) {

    }
}
