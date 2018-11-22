package minesweeper;

public class GameFactory 
{
    public Game getGame(String gameType)
    {
        switch(gameType)
        {
            case "CONSOLE" : return new ConsoleGame();
            case "GUI" : return new GUIGame();
            default : return new NullGame();
        }
    }
}
