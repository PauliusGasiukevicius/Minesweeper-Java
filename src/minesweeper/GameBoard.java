package minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard 
{
    private int width, height, mineCount;
    private CELL[][] board;

    public GameBoard(int width, int height, int mineCount) 
    {
        this.width = width;
        this.height = height;
        this.mineCount = Math.min(mineCount,width*height-1);
        board = new CELL[height][width];
        
        for(int i=0; i<height; i++)
             for(int j=0; j<width; j++)
                 board[i][j] = CELL.EMPTY;
        
        ArrayList<Integer> allCells = new ArrayList();
        for(int i=0; i<width*height; i++)
            allCells.add(i);
        
        Collections.shuffle(allCells);
        
        for(int i=0; i<this.mineCount; i++)
        {
            int r = allCells.get(i)/width;
            int c = allCells.get(i)%width;
            board[r][c] = CELL.MINE;
        }
    }
    
    public CELL getCellState(int r, int c)
    {
        return board[r][c];
    }
    
    public void setCellState(int r, int c, CELL state)
    {
        board[r][c]=state;
    }
    
    public boolean isValidCell(int r, int c)
    {
        return r>=0 && c>=0 && r<height && c<width;
    }

    public int getMineCount() 
    {
        return mineCount;
    }

    public int getWidth() 
    {
        return width;
    }

    public int getHeight() 
    {
        return height;
    }
    
    
    
    public int cellNeighbouringMinesCount(int r, int c)
    {
        int count = 0;
        int[] dr = {-1,0,1,0,-1,-1,1,1};
        int[] dc = {0,-1,0,1,-1,1,-1,1};
        
        for(int k=0; k<dr.length; k++)
            if(isValidCell(r+dr[k], c+dc[k]) && (getCellState(r+dr[k], c+dc[k])==CELL.MINE || getCellState(r+dr[k], c+dc[k])==CELL.FLAGM || getCellState(r+dr[k], c+dc[k])==CELL.EXPLOSION))
                count++;
        
        return count;
    }
}
