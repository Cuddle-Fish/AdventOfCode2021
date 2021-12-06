import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day04 {

    public static void main(String[] args) {
        File bingoInput = new File("src\\Bingo_Subsystem.txt");
        Bingo newGame = new Bingo(bingoInput);
        PlayGame play = new PlayGame(newGame);
        System.out.println("Task 1: " + play.winningScore);
        System.out.println("Task 2: " + play.losingScore);
    }
}

class Bingo {
    LinkedList<Integer> drawnNumbers;
    LinkedList<Board> boards;

    public Bingo (File bingoInput) {
        drawnNumbers = new LinkedList<>();
        boards = new LinkedList<>();

        try {
            Scanner input = new Scanner(bingoInput);

            // Find drawn numbers
            String[] splitDraw = input.next().split(",");
            for (String draw : splitDraw) {
                drawnNumbers.add(Integer.parseInt(draw.trim()));
            }

            // Find boards
            while (input.hasNextLine()) {
                boards.add(new Board(input));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found");
        }
    }


    public void resetAllBoards() {
        for (Board currentBoard : boards) {
            currentBoard.resetBoard();
        }
    }
}

class Board {
    Num[] board;
    int[] markedInRow;
    int[] markedInColumn;

    //TODO could probably do something with modulus
    public Board(Scanner input) {
        board = new Num[25];
        markedInRow = new int[5];
        markedInColumn = new int[5];

        int count = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[count++] = new Num(input.nextInt(), i, j);
            }
        }
    }


    public void resetBoard() {
        for (Num number : board) {
            number.marked = false;
        }
        for (int i = 0; i < 5; i++) {
            markedInRow[i] = 0;
            markedInColumn[i] = 0;
        }
    }


    public void printBoard() {
        int count = 0;
        for (Num val : board) {
            System.out.print(val.value + " ");
            ++count;
            if (count == 5) {
                System.out.println();
                count = 0;
            }
        }
    }
}

class Num {
    int value;
    int row;
    int column;
    boolean marked;

    public Num (int value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
        this.marked = false;
    }
}

class PlayGame {
    int winningScore;
    int losingScore;

    public PlayGame(Bingo game) {
        findWinningBoard(game);
        findLosingBoard(game);
    }


    private void findWinningBoard(Bingo game) {
        for (int i = 0; i < game.drawnNumbers.size(); i++) {
            for (Board currentBoard : game.boards) {
                for (int j = 0; j < 25; j++) {
                    if (currentBoard.board[j].value == game.drawnNumbers.get(i)) {
                        currentBoard.board[j].marked = true;
                        ++currentBoard.markedInColumn[currentBoard.board[j].column];
                        ++currentBoard.markedInRow[currentBoard.board[j].row];
                    }
                    if (currentBoard.markedInRow[currentBoard.board[j].row] == 5
                            || currentBoard.markedInColumn[currentBoard.board[j].column] == 5) {
                        this.winningScore = calcScore(currentBoard, game.drawnNumbers.get(i));
                        game.resetAllBoards();
                        return;
                    }
                }
            }
        }
    }


    public void findLosingBoard(Bingo game) {
        LinkedList<Board> winningBoardsFound = new LinkedList<>();

        for (int i = 0; i < game.drawnNumbers.size(); i++) {
            for (Board currentBoard : game.boards) {
                if (!winningBoardsFound.contains(currentBoard)) {
                    for (int j = 0; j < 25; j++) {
                        if (currentBoard.board[j].value == game.drawnNumbers.get(i)) {
                            currentBoard.board[j].marked = true;
                            ++currentBoard.markedInRow[currentBoard.board[j].row];
                            ++currentBoard.markedInColumn[currentBoard.board[j].column];
                        }
                        if (currentBoard.markedInColumn[currentBoard.board[j].column] == 5
                                || currentBoard.markedInRow[currentBoard.board[j].column] == 5) {
                            if (winningBoardsFound.size() == game.boards.size() - 1) {
                                this.losingScore = calcScore(currentBoard, game.drawnNumbers.get(i));
                                return;
                            } else {
                                winningBoardsFound.add(currentBoard);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    private int calcScore(Board board, int lastDrawnNum) {
        int score = 0;

        for (Num number : board.board) {
            if (!number.marked) {
                score += number.value;
            }
        }

        return score * lastDrawnNum;
    }
}
