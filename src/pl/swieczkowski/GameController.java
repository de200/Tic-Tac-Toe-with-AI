package pl.swieczkowski;


import java.util.concurrent.TimeUnit;

public class GameController {

    private static final char MARK_X = 'X';
    private static final char MARK_O = 'O';

    private final Board board;
    private final Move move;
    private final GameLogic gameLogic;


    public GameController() {
        board = new Board();
        move = new Move();
        gameLogic = new GameLogic();
        gameLoop();
    }


    private String[] inputGameMode() {
        String userInput;
        String[] array;

        while (true) {
            System.out.println("Input command:");
            userInput = Move.SCANNER.nextLine();
            array = userInput.split(" ");

            if (!validateStartInput(array)) {
                System.out.println("Bad parameters!");
            } else if (array[0].equalsIgnoreCase("exit")) {
                break;
            } else {
                if (array[1].equalsIgnoreCase("user")) {
                    board.printBoard();
                }
                break;
            }
        }
        return array;
    }


    private boolean validateStartInput(String[] array) {
        if (array[0].equalsIgnoreCase("start")) {
            if (array.length != 3) {
                return false;
            }
            if (!array[1].equalsIgnoreCase("easy") && !array[1].equalsIgnoreCase("medium") && !array[1].equalsIgnoreCase("hard") && !array[1].equalsIgnoreCase("user")) {
                return false;
            }
            return array[2].equalsIgnoreCase("easy") || array[2].equalsIgnoreCase("medium") || array[2].equalsIgnoreCase("hard") || array[2].equalsIgnoreCase("user");
        } else return array[0].equalsIgnoreCase("exit");
    }


    private void printComputersMove(String player) {
        if (player.equalsIgnoreCase("easy")) {
            System.out.println("Making move level \"easy\"");
        } else if (player.equalsIgnoreCase("medium")) {
            System.out.println("Making move level \"medium\"");
        } else if (player.equalsIgnoreCase("hard")) {
            System.out.println("Making move level \"hard\"");
        }
    }


    private void putCoordinatesOnBoard(char playerMark, char opponentsMark, String player) {

        printComputersMove(player);

        Coordinates coordinates;
        do {

            switch (player) {
                case "easy":
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    coordinates = move.randomComputerMove();
                    break;
                case "medium":
                            try {
                                TimeUnit.SECONDS.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                    coordinates = move.mediumAIComputersMove(playerMark, gameLogic, board);

                    if (coordinates == null) {
                        coordinates = move.mediumAIComputersMove(opponentsMark, gameLogic, board);
                    }
                    if (coordinates == null) {
                        coordinates = move.randomComputerMove();
                    }
                    break;
                case "hard":
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        coordinates = gameLogic.findBestMove(board.getPlayBoard(), playerMark, opponentsMark);

                    break;
                case "user":
                    coordinates = move.userMove();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + player);
            }
        } while (!gameLogic.isCellEmpty(board, coordinates, playerMark, player));
    }


    private void gameLoop() {
        String[] gameMode = inputGameMode();

        int turn = 1;
        int markDeterminant;

        char[][] playBoard = board.getPlayBoard();

        if (!gameMode[0].equalsIgnoreCase("exit")) {
            while (true) {
                if (gameLogic.hasEmptySpace(playBoard)) {
                    markDeterminant = turn % 2;
                    switch (markDeterminant) {
                        case 0:
                            putCoordinatesOnBoard(MARK_O, MARK_X, gameMode[2].toLowerCase());
                            break;
                        case 1:
                            putCoordinatesOnBoard(MARK_X, MARK_O, gameMode[1].toLowerCase());
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + markDeterminant);
                    }
                    turn++;
                    if (gameLogic.hasWinner(MARK_X, playBoard)) {
                        System.out.println(MARK_X + " wins");
                        break;
                    } else if (gameLogic.hasWinner(MARK_O, playBoard)) {
                        System.out.println(MARK_O + " wins");
                        break;
                    }
                } else {
                    System.out.println("Draw");
                    break;
                }
            }

        }
        Move.SCANNER.close();
    }
}
