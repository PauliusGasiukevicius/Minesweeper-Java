package minesweeper;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.Border;

/**@author Paulius*/
public class MinesweeperSwingRender
{
    private GameBoard board;
    private GameLogic logic;
    private Hashtable<JPanel,Integer>row,col;
    private JPanel panel;
    private JFrame frame;
    
    public MinesweeperSwingRender(GameBoard board, GameLogic logic) 
    {
        this.board = board;
        this.logic = logic;
        
        this.row = new Hashtable();
        this.col = new Hashtable();
        
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(60*board.getHeight(), 60*board.getWidth());
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(board.getHeight(),board.getWidth()));
        
         for(int i=0; i<board.getHeight(); i++)
             for(int j=0; j<board.getWidth(); j++)
             {
                 CELL current = board.getCellState(i, j);
                 
                 JPanel p = new JPanel();
                 row.put(p, i);
                 col.put(p, j);
                             
                p.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
                p.addMouseListener(new MouseAdapter() { 
                   public void mousePressed(MouseEvent me) 
                   { 
                       JPanel jp = (JPanel) me.getSource();
                       int r = row.get(jp);
                       int c = col.get(jp);
                       
                     if(SwingUtilities.isLeftMouseButton(me))
                         logic.processClick(r, c);
                     else if(SwingUtilities.isRightMouseButton(me))
                         logic.processFlagClick(r, c);
                         
                         render();
                         if(logic.isGameOver())
                         {
                             JOptionPane.showMessageDialog(null, "You lose    :(");
                             System.exit(0);
                         }
                         else if(logic.isGameWon())
                         {
                             JOptionPane.showMessageDialog(null, "Yay, victory!    ^_^");
                             System.exit(0);
                         }
                         else if(!logic.isGameOver() && !logic.isGameWon())render();
                    } 
                 }); 
                
                JLabel jl = new JLabel();
                p.setBackground(Color.BLACK);
                p.add(jl);
                 
                panel.add(p);
             }
        frame.add(panel);
        frame.setVisible(true);
    }
    
     public void render()
     {
        for(Component comp : panel.getComponents())
        {
            JPanel p = (JPanel)comp;
            int r = row.get(p);
            int c = col.get(p);
            
            CELL current = board.getCellState(r, c);
            
            switch(current)
                 {
                     case OPEN :
                         int neighbouringMines = board.cellNeighbouringMinesCount(r, c);
                         if(neighbouringMines==0)
                         {
                             p.setBackground(Color.WHITE);
                         }
                         else
                         {
                             JLabel jl = (JLabel)p.getComponents()[0];
                             jl.setText(""+neighbouringMines);
                             jl.setForeground(Color.BLACK);
                             p.add(jl);
                             p.setBackground(Color.WHITE);
                         }
                         break;
                     
                     case FLAG :
                     case FLAGM :
                         {
                             JLabel jl = (JLabel)p.getComponents()[0];
                             jl.setText("F");
                             jl.setForeground(Color.BLACK);
                             p.add(jl);
                             p.setBackground(Color.YELLOW);
                         }
                        break;
                        
                     case EXPLOSION :
                         {
                             JLabel jl = (JLabel)p.getComponents()[0];
                             jl.setText("*");
                             jl.setForeground(Color.BLACK);
                             p.add(jl);
                             p.setBackground(Color.RED);
                         }
                         break;
                     
                     default :
                         {
                             p.setBackground(Color.BLACK);
                         }
                         break;
                 }
        }
     }
}
