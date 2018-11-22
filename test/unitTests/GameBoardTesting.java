package unitTests;

import minesweeper.*;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GameBoardTesting {

    public GameBoardTesting() {
    }

    @Test
    public void test_whenCreatingGameBoardWithMoreMinesThanCells_Expect_MineCountToGetReduced() {
        int boardSize = 10;
        int cellCount = boardSize * boardSize;
        int givenMinesCount = cellCount + 1;
        GameBoard board = new GameBoard(boardSize, boardSize, givenMinesCount);
        int actualMinesCount = board.getMineCount();

        assertTrue(actualMinesCount <= cellCount);
    }
    
    @Test
    public void test_whenClickingOnMine_Expect_GameOverToBecomeTrue()
    {
        GameBoard board = new GameBoard(10,10,100);
        GameLogic logic = new GameLogic(board);
        board.setCellState(0, 0, CELL.MINE);
        logic.processClick(0, 0);
        
        assertTrue(logic.isGameOver());
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
