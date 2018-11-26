package minesweeper;

public class NullGame extends Game {

    @Override
    public void launch() {
        System.err.println("Such game type does not exist");
    }
}
