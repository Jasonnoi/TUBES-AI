public class BoardState {
    private int boardSize;
    private int[] board;

    // ------ constructor ----------
    public BoardState(int boardSize) {
        this.boardSize = boardSize;
        this.board = new int[boardSize * boardSize];
    }

    public void setCell(int index, int value) {

        board[index] = value;

    }
    // GET SELF

    public int getSelf(int index) {
        return this.board[index - 1];
    }

    // ------ method cek posisi tetangga yang berada di top ----------
    public int getTop(int currIndex) {
        if (!isValidNeighbourTop(currIndex)) {
            return 0;
        }
        int position = (currIndex) - this.boardSize;
        return this.board[position - 1];
    }

    public int getTopLeft(int currIndex) {
        if (!isValidNeighbourTop(currIndex)) {
            return 0;
        } else if (!isValidNeighbourLeft(currIndex)) {
            return 0;
        }
        int position = (currIndex - this.boardSize) - 1;
        return this.board[position - 1];
    }

    public int getTopRight(int currIndex) {
        if (!isValidNeighbourTop(currIndex)) {
            return 0;
        } else if (!isValidNeighbourRight(currIndex)) {
            return 0;
        }
        int position = ((currIndex - this.boardSize) + 1);
        return this.board[position - 1];

    }

    // ------ method cek posisi tetangga yang berada di kiri dan kanan ----------
    public int getLeft(int currIndex) {
        int positionL = (currIndex) - 1;
        if (!isValidNeighbourLeft(currIndex)) {
            return 0;
        }
        return board[positionL - 1];
    }

    public int getRight(int currIndex) {
        int positionR = (currIndex) + 1;
        if (!isValidNeighbourRight(currIndex)) {
            return 0;
        }
        return board[positionR - 1];
    }

    // ------ method cek posisi tetangga yang berada di bottom ----------
    public int getBottom(int currIndex) {
        if (!isValidNeighbourBottom(currIndex)) {
            return 0;
        }
        int position = (currIndex + this.boardSize);
        return board[position - 1];
    }

    public int getBottomLeft(int currIndex) {
        if (!isValidNeighbourBottom(currIndex)) {
            return 0;
        } else if (!isValidNeighbourLeft(currIndex)) {
            return 0;
        }
        int position = (currIndex + this.boardSize) - 1;
        return (board[position - 1]);
    }

    public int getBottomRight(int currIndex) {
        if (!isValidNeighbourBottom(currIndex)) {
            return 0;
        } else if (!isValidNeighbourRight(currIndex)) {
            return 0;
        }
        int position = (currIndex + this.boardSize) + 1;
        return (board[position - 1]);
    }

    // ------ method menguji tetangga yang valid ----------
    public boolean isValidNeighbourTop(int currIndex) {
        if (currIndex < boardSize) {
            return false;
        }
        return true;
    }

    public boolean isValidNeighbourLeft(int currIndex) {
        if (currIndex % this.boardSize == 1) {
            return false;
        }
        return true;
    }

    public boolean isValidNeighbourRight(int currIndex) {
        if (currIndex % this.boardSize == 0) {
            return false;
        }
        return true;
    }

    public boolean isValidNeighbourBottom(int currIndex) {
        if (currIndex >= (board.length - boardSize)) {
            return false;
        }
        return true;
    }

    // ------ method print isi board ----------
    public void printBoard() {
        for (int i : this.board) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
