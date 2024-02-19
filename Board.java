public class Board {

  private Cell[][] board;

  public Board(int rows, int cols) {
    board = new Cell[rows][cols];
    clearBoard();
    placeFirstGen();
    displayBoard();
  }

  // loops through 2D array and sets every char to the default emoji
  public void clearBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = new Cell(false);
      }
    }
  }

  // prints the board
  public void displayBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        System.out.print(board[i][j] + "\t");
      }
      System.out.println();
    }
    System.out.println();
  }

  // places first generation of bacteria on board
  public void placeFirstGen() {
    board[0][3] = new Cell(true);
    board[4][4] = new Cell(true);
  }

  // counts the number of neighbors who are alive, returns the result as an
  // integer
  // counts all eighth neighboring spaces
  public int countLiveNeighbors(int i, int j) {
    int count = 0;
    for (int x = i - 1; x <= i + 1; x++) {
      for (int y = j - 1; y <= j + 1; y++) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
          // non diagonals
          if (board[x][y].getIsAlive() && (x != i || y != j)) {
            count++;
          }
          // diagonals
          if (x > 0 && x < board.length - 1) {
            if (y > 0 && y < board[0].length - 1) {
              if (board[x - 1][y - 1].getIsAlive()) {
                count++;
              }
              if (board[x - 1][y + 1].getIsAlive()) {
                count++;
              }
              if (board[x + 1][y - 1].getIsAlive()) {
                count++;
              }
              if (board[x + 1][y + 1].getIsAlive()) {
                count++;
              }
            }
          }
        }

      }
    }
    return count;
  }

  public void createNewGeneration() {
    // creates a blank board of same size called newGenBoard
    Cell[][] nextGenBoard = new Cell[board.length][board[0].length];

    // all changes should be reflected only on nextGenBoard, and we copy them over
    // on the last line of the method
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        int liveNeighbors = countLiveNeighbors(i, j);

        // Create a new cell in the same state as the current one
        nextGenBoard[i][j] = new Cell(board[i][j].getIsAlive());

        // for each space in the nextGenBoard:
        // a live cell with 2-3 neighbors survives
        // a dead cell with 3 live neighbors becomes live
        // a live cell with 0, 1, or >=4 neighbors dies
        if (board[i][j].getIsAlive()) {
          if (liveNeighbors < 2 || liveNeighbors > 3) {
            nextGenBoard[i][j].setIsAlive(false); // A live cell with <2 or >3 neighbors dies
          }
        } else {
          if (liveNeighbors == 3) {
            nextGenBoard[i][j].setIsAlive(true); // A dead cell with exactly 3 live neighbors becomes a live cell
          }
        }
      }
    }

    // copies all changes simultaneously. this line must be last
    board = nextGenBoard;
  }

}
