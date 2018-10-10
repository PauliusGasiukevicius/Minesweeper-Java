package minesweeper;

/**@author Paulius*/
public class ConsoleRender 
{
    private GameBoard board;
    
    public ConsoleRender(GameBoard board) 
    {
        this.board = board;
    }
    
     public void render()
     {
         for(int i=0; i<board.getHeight(); i++)
             for(int j=0; j<board.getWidth(); j++)
             {
                 CELL current = board.getCellState(i, j);
                 switch(current)
                 {
                     case OPEN :
                         int neighbouringMines = board.cellNeighbouringMinesCount(i, j);
                         if(neighbouringMines==0)System.out.print(" ");
                         else System.out.print(neighbouringMines);
                         break;
                     
                     case FLAG :
                     case FLAGM :
                         System.out.print("f");
                        break;
                        
                     case EXPLOSION :
                         System.out.print("*");
                         break;
                     
                     default :
                         System.out.print("#");
                         break;
                 }
                 
                 if(j+1==board.getWidth())
                     System.out.print("\n");
             }
     }
    
}
