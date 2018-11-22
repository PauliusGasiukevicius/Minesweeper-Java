package minesweeper;

public class Main
{
    public static void main(String[] args) 
    {
        GameFactory factory = new GameFactory();
        Game game = factory.getGame("GUI");
        game.launch();
    }
}
