package minesweeper;

import java.util.Scanner;

/**@author Paulius*/
public class Minesweeper 
{
    public static void main(String[] args) 
    {
        GameBoard board = new GameBoard(6,6,5);
        GameLogic logic = new GameLogic(board);
        ConsoleRender render = new ConsoleRender(board);
        Scanner scan = new Scanner(System.in);
        render.render();
        
        while(true)
        {
            if(logic.isGameWon())
            {
                System.out.println("YAY! you win");
                break;
            }
            
            if(logic.isGameOver())
            {
                System.out.println("Aww.. you lose");
                break;
            }
            
            System.out.println("Iveskite koordinates ir ejimo tipa (eilute, stulpelis, click(0) ar flag(1))");
            int r = scan.nextInt();
            int c = scan.nextInt();
            r--; c--;
            int type = scan.nextInt();
            
            if(type==0)logic.processClick(r, c);
            else if(type==1) logic.processFlagClick(r, c);
            
            render.render();
        }
    }
}
