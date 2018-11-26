package minesweeper;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import javax.swing.*;

public class GUIGame extends Game {

    private Hashtable<JPanel, Integer> row, col;
    private JPanel panel;
    private JFrame frame;

    @Override
    public void launch() {
        Object[] options = {"Easy", "Medium", "Hard"};

        int difficulty = JOptionPane.showOptionDialog(frame, "Pasirinkite sudetinguma: ", "Minesweeper",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        switch (difficulty) {
            case 0:
                playGame(10, 10);
                break;

            case 1:
                playGame(15, 40);
                break;

            case 2:
                playGame(20, 99);
                break;

            default:
                System.exit(0);
                break;
        }
    }

    public void playGame(int boardSize, int mineCount) {
        board = new GameBoard(boardSize, boardSize, mineCount);
        logic = new GameLogic(board);
        row = new Hashtable();
        col = new Hashtable();

        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(50 * board.getHeight(), 50 * board.getWidth());
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(board.getHeight(), board.getWidth()));

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                JPanel p = new JPanel();
                row.put(p, i);
                col.put(p, j);

                p.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
                p.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent me) {
                        JPanel jp = (JPanel) me.getSource();
                        int r = row.get(jp);
                        int c = col.get(jp);

                        if (SwingUtilities.isLeftMouseButton(me)) {
                            logic.processClick(r, c);
                        } else if (SwingUtilities.isRightMouseButton(me)) {
                            logic.processFlagClick(r, c);
                        }

                        render();
                        if (logic.isGameOver()) {
                            JOptionPane.showMessageDialog(null, "You lose    :(");
                            frame.dispose();
                            launch();
                        } else if (logic.isGameWon()) {
                            JOptionPane.showMessageDialog(null, "Yay, victory!    ^_^");
                            frame.dispose();
                            launch();
                        } else if (!logic.isGameOver() && !logic.isGameWon()) {
                            render();
                        }
                    }
                });

                JLabel jl = new JLabel();
                jl.setFont(new Font(jl.getName(), Font.PLAIN, 20));
                p.setBackground(Color.BLACK);
                p.add(jl);

                panel.add(p);
            }
        }
        frame.add(panel);
        frame.setVisible(true);
    }

    public void render() {
        for (Component comp : panel.getComponents()) {
            JPanel p = (JPanel) comp;
            int r = row.get(p);
            int c = col.get(p);

            CELL current = board.getCellState(r, c);

            switch (current) {
                case OPEN:
                    int neighbouringMines = board.cellNeighbouringMinesCount(r, c);
                    if (neighbouringMines == 0) {
                        JLabel jl = (JLabel) p.getComponents()[0];
                        jl.setText("");
                        jl.setForeground(Color.BLACK);
                        p.add(jl);
                        p.setBackground(Color.WHITE);
                    } else {
                        JLabel jl = (JLabel) p.getComponents()[0];
                        jl.setText("" + neighbouringMines);
                        jl.setForeground(Color.BLACK);
                        p.add(jl);
                        p.setBackground(Color.WHITE);
                    }
                    break;

                case FLAG:
                case FLAGM: {
                    JLabel jl = (JLabel) p.getComponents()[0];
                    jl.setText("🚩");
                    jl.setForeground(Color.BLACK);
                    p.add(jl);
                    p.setBackground(Color.YELLOW);
                }
                break;

                case EXPLOSION: {
                    JLabel jl = (JLabel) p.getComponents()[0];
                    jl.setText("💣");
                    jl.setForeground(Color.BLACK);
                    p.add(jl);
                    p.setBackground(Color.RED);
                }
                break;

                default: {
                    p.setBackground(Color.BLACK);
                }
                break;
            }
        }
    }
}
