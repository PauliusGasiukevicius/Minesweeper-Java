package minesweeper;

/**@author Paulius*/
public class GameLogic 
{
    private GameBoard board;
    private boolean gameOver;
    private int cellsLeftToOpen;

    public GameLogic(GameBoard board) 
    {
        this.board = board;
        this.gameOver = false;
        this.cellsLeftToOpen = board.getHeight()*board.getWidth() - board.getMineCount();
    }

    public boolean isGameOver() 
    {
        return gameOver;
    }
    
    public boolean isGameWon()
    {
        return cellsLeftToOpen==0;
    }
    
    public void openCell(int r, int c)
    {
        board.setCellState(r, c, CELL.OPEN);
        cellsLeftToOpen--;
    }
    
    public void openZeroCells(int r, int c)
    {
        if(!board.isValid(r, c) || board.getCellState(r, c)==CELL.OPEN)return;
        openCell(r,c);
        if(board.cellNeighbouringMinesCount(r, c)!=0)return;
        
        int[] dr = {-1,0,1,0,-1,-1,1,1};
        int[] dc = {0,-1,0,1,-1,1,-1,1};
        
        for(int k=0; k<dr.length; k++)
        openZeroCells(r+dr[k],c+dc[k]);
    }
    
    public void processClick(int r, int c)
    {
        if(!board.isValid(r, c))return;
        CELL current = board.getCellState(r, c);
        
        switch(current)
        {
            case FLAGM :
            case MINE :
                gameOver = true;
                board.setCellState(r, c, CELL.EXPLOSION);
                break;
                
            case FLAG :
            case EMPTY :
                openZeroCells(r,c);
                break;
        }
    }
    
    public void processFlagClick(int r, int c)
    {
        if(!board.isValid(r, c))return;
        CELL current = board.getCellState(r, c);
        
        switch(current)
        {
            case EMPTY :
                board.setCellState(r, c, CELL.FLAG);
                break;
                
            case MINE :
                board.setCellState(r, c, CELL.FLAGM);
                break;
                
            case FLAG :
                board.setCellState(r, c, CELL.EMPTY);
                break;
                
            case FLAGM :
                board.setCellState(r, c, CELL.MINE);
                break;
        }
    }
}
