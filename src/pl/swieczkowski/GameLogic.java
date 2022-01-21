package pl.swieczkowski;

public class GameLogic {

    public final static int THREE_MARKS = 3;
    public final static int TWO_MARKS = 2;

    public boolean isCellEmpty(Board board, Coordinates coordinates, char mark, String player) {
        char[][] playBoard = board.getPlayBoard();

        int x = coordinates.getAxisX();
        int y = coordinates.getAxisY();

        if (playBoard[x - 1][y - 1] == ' ') {
            playBoard[x - 1][y - 1] = mark;
            board.printBoard();
            return true;

        } else if (player.equalsIgnoreCase("user")) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;

        } else {
            return false;
        }
    }

    public boolean hasWinner(char mark, char[][] playBoard) {

        int winningRow = checkRows(mark, playBoard, THREE_MARKS);
        int winningColumn = checkColumns(mark, playBoard, THREE_MARKS);
        int winningDiagonal1 = checkDiagonal1(mark, playBoard, THREE_MARKS);
        int winningDiagonal2 = checkDiagonal2(mark, playBoard, THREE_MARKS);

        return winningRow != -1 || winningColumn != -1 || winningDiagonal1 != -1 || winningDiagonal2 != -1;
    }


    public int checkRows(char mark, char[][] playBoard, int marksNumber) {
        int counter;

        for (int row = 0; row < Board.ROWS; row++) {
            counter = 0;
            for (int column = 0; column < Board.COLUMNS; column++) {
                if (mark == playBoard[row][column]) {
                    counter++;
                }
            }
            if (counter == marksNumber) {
                return row;
            }
        }
        return -1;
    }

    public Coordinates getEmptySpotInRow(int row, char[][] playBoard) {
        for (int column = 0; column < Board.COLUMNS; column++) {
            if (playBoard[row][column] == ' ') {
                int x = row + 1;
                int y = column + 1;
                return new Coordinates(x, y);
            }
        }
        return null;
    }


    public int checkColumns(char mark, char[][] playBoard, int marksNumber) {

        for (int column = 0; column < Board.COLUMNS; column++) {
            int counter = 0;
            for (int row = 0; row < Board.ROWS; row++) {
                if (mark == playBoard[row][column]) {
                    counter++;
                }
            }
            if (counter == marksNumber) {
                return column;
            }
        }
        return -1;
    }

    public Coordinates getEmptySpotInColumn(int column, char[][] playBoard) {
        for (int row = 0; row < Board.ROWS; row++) {
            if (playBoard[row][column] == ' ') {
                int x = row + 1;
                int y = column + 1;
                return new Coordinates(x, y);
            }
        }
        return null;
    }

    public int checkDiagonal1(char mark, char[][] playBoard, int marksNumber) {

        int counter = 0;

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = row; column < row + 1; column++) {
                if (mark == playBoard[row][column]) {
                    counter++;
                }
                if (counter == marksNumber) {
                    return 1;
                }
            }
        }
        return -1;
    }

    public int checkDiagonal2(char mark, char[][] playBoard, int marksNumber) {

        int counter = 0;

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = Board.COLUMNS - 1 - row; column > Board.COLUMNS - 2 - row; column--) {
                if (mark == playBoard[row][column]) {
                    counter++;
                }
                if (counter == marksNumber) {
                    return 1;
                }
            }
        }
        return -1;
    }

    public Coordinates getEmptySpotDiagonal1(char[][] playBoard) {

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = row; column < row + 1; column++) {
                if (playBoard[row][column] == ' ') {
                    return new Coordinates(row + 1, column + 1);
                }
            }
        }
        return null;
    }

    public Coordinates getEmptySpotDiagonal2(char[][] playBoard) {

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = Board.COLUMNS - 1 - row; column > Board.COLUMNS - 2 - row; column--) {
                if (playBoard[row][column] == ' ') {
                    return new Coordinates(row + 1, column + 1);
                }
            }
        }
        return null;
    }

    public boolean hasEmptySpace(char[][] playBoard) {
        for (char[] row : playBoard) {
            for (char column : row) {
                if (column == ' ') {
                    return true;
                }
            }
        }
        return false;
    }


    private int evaluate(char[][] playBoard, char playerMark, char opponentMark) {

        for (int row = 0; row < Board.ROWS; row++) {
            if (playBoard[row][0] == playBoard[row][1] && playBoard[row][1] == playBoard[row][2]) {
                if (playBoard[row][0] == playerMark) {
                    return 10;
                } else if (playBoard[row][0] == opponentMark) {
                    return -10;
                }
            }
        }

        for (int column = 0; column < Board.COLUMNS; column++) {
            if (playBoard[0][column] == playBoard[1][column] && playBoard[1][column] == playBoard[2][column])
                if (playBoard[0][column] == playerMark) {
                    return 10;
                } else if (playBoard[0][column] == opponentMark) {
                    return -10;
                }
        }

        if (playBoard[0][0] == playBoard[1][1] && playBoard[1][1] == playBoard[2][2]) {
            if (playBoard[0][0] == playerMark) {
                return 10;
            } else if (playBoard[0][0] == opponentMark) {
                return -10;
            }
        }

        if (playBoard[0][2] == playBoard[1][1] && playBoard[1][1] == playBoard[2][0]) {
            if (playBoard[0][2] == playerMark) {
                return 10;
            } else if (playBoard[0][2] == opponentMark) {
                return -10;
            }
        }
        return 0;
    }

    private int minimax(char[][] playBoard, int depth, boolean isMax, char playerMark, char opponentMark) {

        int score = evaluate(playBoard, playerMark, opponentMark);

        if (score == 10) {
            return score;
        }

        if (score == -10) {
            return score;
        }

        if (!hasEmptySpace(playBoard)) {
            return 0;
        }

        int best;
        if (isMax) {
            best = Integer.MIN_VALUE;

            for (int row = 0; row < Board.ROWS; row++) {
                for (int column = 0; column < Board.COLUMNS; column++) {
                    if (playBoard[row][column] == ' ') {
                        playBoard[row][column] = playerMark;
                        best = Math.max(best, minimax(playBoard, depth + 1, !isMax, playerMark, opponentMark));
                        playBoard[row][column] = ' ';
                    }
                }
            }

        } else {
            best = Integer.MAX_VALUE;

            for (int row = 0; row < Board.ROWS; row++) {
                for (int column = 0; column < Board.COLUMNS; column++) {
                    if (playBoard[row][column] == ' ') {
                        playBoard[row][column] = opponentMark;
                        best = Math.min(best, minimax(playBoard, depth + 1, !isMax, playerMark, opponentMark));
                        playBoard[row][column] = ' ';
                    }
                }
            }
        }
        return best;
    }


    public Coordinates findBestMove(char[][] playBoard, char playerMark, char opponentMark) {

        int bestValue = Integer.MIN_VALUE;
        Coordinates bestMove = null;

        for (int row = 0; row < Board.ROWS; row++) {
            for (int column = 0; column < Board.COLUMNS; column++) {
                if (playBoard[row][column] == ' ') {
                    playBoard[row][column] = playerMark;
                    int moveValue = minimax(playBoard, 0, false, playerMark, opponentMark);
                    playBoard[row][column] = ' ';
                    if (moveValue > bestValue) {
                        bestMove = new Coordinates(row + 1, column + 1);
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }


}
