package pl.swieczkowski;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Move {

    public final static Scanner SCANNER = new Scanner(System.in);

    public Coordinates userMove() {

        boolean isInvalid = true;
        int x = 0;
        int y = 0;
        while (isInvalid) {
            System.out.println("Enter the coordinates:");

            try {
                x = SCANNER.nextInt();
                y = SCANNER.nextInt();

                while (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    System.out.println("Enter the coordinates:");
                    x = SCANNER.nextInt();
                    y = SCANNER.nextInt();
                }
                isInvalid = false;
            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                SCANNER.nextLine();
            }
        }
        return new Coordinates(x, y);
    }

    public Coordinates randomComputerMove() {
        Random random = new Random();
        int x = random.nextInt(3) + 1;
        int y = random.nextInt(3) + 1;
        return new Coordinates(x, y);
    }

    public Coordinates mediumAIComputersMove(char mark, GameLogic gameLogic, Board board) {

        Coordinates coordinates = null;

        int rowCheck = gameLogic.checkRows(mark, board.getPlayBoard(), GameLogic.TWO_MARKS);
        int columnCheck = gameLogic.checkColumns(mark, board.getPlayBoard(), GameLogic.TWO_MARKS);
        int diagonal1Check = gameLogic.checkDiagonal1(mark, board.getPlayBoard(), GameLogic.TWO_MARKS);
        int diagonal2Check = gameLogic.checkDiagonal2(mark, board.getPlayBoard(), GameLogic.TWO_MARKS);

        if (rowCheck != -1) {
            coordinates = gameLogic.getEmptySpotInRow(rowCheck, board.getPlayBoard());
        }
        if (coordinates == null && columnCheck != -1) {
            coordinates = gameLogic.getEmptySpotInColumn(columnCheck, board.getPlayBoard());
        }
        if (coordinates == null && diagonal1Check != -1) {
            coordinates = gameLogic.getEmptySpotDiagonal1(board.getPlayBoard());
        }
        if (coordinates == null && diagonal2Check != -1) {
            coordinates = gameLogic.getEmptySpotDiagonal2(board.getPlayBoard());
        }

        return coordinates;
    }

}
