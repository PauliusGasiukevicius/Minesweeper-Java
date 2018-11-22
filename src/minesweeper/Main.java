package minesweeper;

import java.util.Scanner;

/**@author Paulius*/
public class MinesweeperSwingMain
{
    public static void main(String[] args) 
    {
        GameBoard board = new GameBoard(9,9,10);
        GameLogic logic = new GameLogic(board);
        MinesweeperSwingRender render = new MinesweeperSwingRender(board, logic);
        render.render();
    }
}
