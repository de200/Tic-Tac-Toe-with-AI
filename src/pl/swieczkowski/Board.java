package pl.swieczkowski;


public class Board {

    public static final int ROWS = 3;
    public static final int COLUMNS = 3;
    private final char[][] playBoard;


    public Board() {
        playBoard = new char[ROWS][COLUMNS];
        String openingState = "         ";
        fillBoard(openingState);
    }

    public char[][] getPlayBoard() {
        return playBoard;
    }


    private void fillBoard(String s) {
        int index = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                playBoard[row][column] = s.charAt(index++);
            }
        }
    }


    public void printBoard() {
        System.out.println("---------");
        for (char[] row : playBoard) {
            for (int column = 0; column < row.length; column++) {
                System.out.print(column == 0 ? "| " + row[column] + " " : column == playBoard.length - 1 ? row[column] + " |" : row[column] + " ");
            }
            System.out.println();
        }
        System.out.println("---------");
    }
}
