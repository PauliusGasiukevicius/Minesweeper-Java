package minesweeper;

public class GameLogic {

    private GameBoard board;
    private boolean gameOver;
    private boolean firstClick;
    private int cellsLeftToOpen;

    public GameLogic(GameBoard board) {
        this.board = board;
        this.gameOver = false;
        this.cellsLeftToOpen = board.getHeight() * board.getWidth() - board.getMineCount();
        this.firstClick = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return cellsLeftToOpen == 0;
    }

    public void openCell(int r, int c) {
        board.setCellState(r, c, CELL.OPEN);
        cellsLeftToOpen--;
    }

    public void openZeroCells(int r, int c) {
        if (!board.isValidCell(r, c) || board.getCellState(r, c) == CELL.OPEN) {
            return;
        }
        openCell(r, c);
        boolean openNeighbours = board.cellNeighbouringMinesCount(r, c) == 0;

        int[] dr = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dc = {0, -1, 0, 1, -1, 1, -1, 1};

        for (int k = 0; k < dr.length; k++) {
            if (openNeighbours) {
                openZeroCells(r + dr[k], c + dc[k]);
            }
        }
    }

    private void swapCellsIfFirstTurn(int r, int c) {
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                if (board.getCellState(i, j) == CELL.EMPTY) {
                    board.setCellState(i, j, board.getCellState(r, c));
                    board.setCellState(r, c, CELL.EMPTY);
                    return;
                }
            }
        }
    }

    public void processClick(int r, int c) {
        if (!board.isValidCell(r, c)) {
            return;
        }

        CELL current = board.getCellState(r, c);

        if (firstClick) {
            firstClick = false;
            if (current == CELL.MINE || current == CELL.FLAGM || current == CELL.EXPLOSION) {
                swapCellsIfFirstTurn(r, c);
                current = board.getCellState(r, c);
            }
        }

        switch (current) {
            case FLAGM:
            case MINE:
                gameOver = true;
                board.setCellState(r, c, CELL.EXPLOSION);
                break;

            case FLAG:
            case EMPTY:
                openZeroCells(r, c);
                break;
        }
    }

    public void processFlagClick(int r, int c) {
        if (!board.isValidCell(r, c)) {
            return;
        }
        CELL current = board.getCellState(r, c);

        switch (current) {
            case EMPTY:
                board.setCellState(r, c, CELL.FLAG);
                break;

            case MINE:
                board.setCellState(r, c, CELL.FLAGM);
                break;

            case FLAG:
                board.setCellState(r, c, CELL.EMPTY);
                break;

            case FLAGM:
                board.setCellState(r, c, CELL.MINE);
                break;
        }
    }
}
