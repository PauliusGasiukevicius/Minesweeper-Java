package minesweeper;

import java.util.Scanner;

public class ConsoleGame extends Game {

    private Scanner scan;

    @Override
    public void launch() {
        scan = new Scanner(System.in);
        while (true) {
            System.out.println("Pasirinkite zaidimo sudetinguma ivesdami E,M arba H - atitinkamai EASY, MEDIUM, HARD");
            String difficulty = scan.next();

            switch (difficulty) {
                case "E":
                    playGame(10, 10);
                    break;

                case "M":
                    playGame(15, 40);
                    break;

                case "H":
                    playGame(20, 99);
                    break;

                default:
                    System.out.println("Ivestas blogas simbolis, zaidimo sudetingumas parenkamais kaip E - EASY");
                    playGame(10, 10);
                    break;
            }

            System.out.println("Ar norite dar zaisti? - iveskite (Y/N)");
            String continuePlaying = scan.next();
            if (continuePlaying.compareTo("Y") != 0) {
                break;
            }
        }
        scan.close();
    }

    private void playGame(int boardSize, int mineCount) {
        board = new GameBoard(boardSize, boardSize, mineCount);
        logic = new GameLogic(board);
        printGameBoard();

        while (true) {
            if (logic.isGameWon()) {
                System.out.println("YAY! you win");
                break;
            }

            if (logic.isGameOver()) {
                System.out.println("Aww.. you lose");
                break;
            }

            System.out.println("Iveskite koordinates ir ejimo tipa (eilute, stulpelis, click(0) ar flag(1))");
            int r = scan.nextInt();
            int c = scan.nextInt();
            r--;
            c--;
            int type = scan.nextInt();

            if (type == 0) {
                logic.processClick(r, c);
            } else if (type == 1) {
                logic.processFlagClick(r, c);
            }

            printGameBoard();
        }
    }

    public void printGameBoard() {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                CELL current = board.getCellState(i, j);
                switch (current) {
                    case OPEN:
                        int neighbouringMines = board.cellNeighbouringMinesCount(i, j);
                        if (neighbouringMines == 0) {
                            System.out.print(" ");
                        } else {
                            System.out.print(neighbouringMines);
                        }
                        break;

                    case FLAG:
                    case FLAGM:
                        System.out.print("f");
                        break;

                    case EXPLOSION:
                        System.out.print("*");
                        break;

                    default:
                        System.out.print("#");
                        break;
                }

                if (j + 1 == board.getWidth()) {
                    System.out.print("\n");
                }
            }
        }
    }
}
