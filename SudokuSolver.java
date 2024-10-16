import java.util.Random;
import java.util.Scanner;

public class SudokuSolver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Interactive Sudoku Solver!");
        System.out.println("Choose a Sudoku grid size:");
        System.out.println("1. 4x4\n2. 6x6\n3. 8x8\n4. 10x10\n5. 12x12\n6. 16x16");

        int size = 0;
        boolean validInput = false;

        // Allow user to select grid size with validation
        while (!validInput) {
            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    size = 4;
                    validInput = true;
                    break;
                case 2:
                    size = 6;
                    validInput = true;
                    break;
                case 3:
                    size = 8;
                    validInput = true;
                    break;
                case 4:
                    size = 10;
                    validInput = true;
                    break;
                case 5:
                    size = 12;
                    validInput = true;
                    break;
                case 6:
                    size = 16;
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number between 1 and 6.");
            }
        }

        int[][] board = new int[size][size];
        System.out.println("\nYou can now enter the Sudoku puzzle row by row (use 0 for empty cells).");
        System.out.println("Would you like to enter the puzzle manually or generate a random one?");
        System.out.println("1. Enter manually\n2. Generate random puzzle");
        System.out.print("Your choice: ");
        int entryChoice = scanner.nextInt();

        if (entryChoice == 1) {
            // Manually input Sudoku puzzle
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.printf("Enter number for cell (%d, %d): ", i + 1, j + 1);
                    board[i][j] = scanner.nextInt();
                }
            }
        } else {
            // Generate a random puzzle based on the difficulty
            generateRandomPuzzle(board, size);
            System.out.println("Random puzzle generated! Here is your Sudoku board:");
            printBoard(board, size);
        }

        // Allow the user to solve the puzzle or get hints
        boolean keepSolving = true;
        while (keepSolving) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Solve the Sudoku\n2. Get a Hint\n3. Exit");
            System.out.print("Your choice: ");
            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    if (solveSudoku(board, size)) {
                        System.out.println("Sudoku Solved! Here is the solution:");
                        printBoard(board, size);
                    } else {
                        System.out.println("No solution exists for the given Sudoku.");
                    }
                    keepSolving = false;
                    break;
                case 2:
                    System.out.println("Providing a hint by filling in one of the empty cells...");
                    if (!provideHint(board, size)) {
                        System.out.println("No empty cells available for hints.");
                    }
                    printBoard(board, size);
                    break;
                case 3:
                    System.out.println("Exiting. Thanks for playing!");
                    keepSolving = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }

        scanner.close();
    }

    // Function to print the Sudoku board with grid lines
    public static void printBoard(int[][] board, int size) {
        int boxSize = (int) Math.sqrt(size);
        for (int r = 0; r < size; r++) {
            if (r % boxSize == 0 && r != 0) {
                System.out.println("-".repeat(size * 2));
            }
            for (int d = 0; d < size; d++) {
                if (d % boxSize == 0 && d != 0) {
                    System.out.print("| ");
                }
                System.out.print((board[r][d] == 0 ? "." : board[r][d]) + " ");
            }
            System.out.println();
        }
    }

    // Function to generate a random Sudoku puzzle
    public static void generateRandomPuzzle(int[][] board, int size) {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Randomly fill some cells (approx 30% filled)
                if (rand.nextInt(100) < 30) {
                    board[i][j] = rand.nextInt(size) + 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
    }

    // Function to provide a hint (fill one empty cell)
    public static boolean provideHint(int[][] board, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= size; num++) {
                        if (isSafe(board, row, col, num, size)) {
                            board[row][col] = num;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Recursive function to solve the Sudoku
    public static boolean solveSudoku(int[][] board, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= size; num++) {
                        if (isSafe(board, row, col, num, size)) {
                            board[row][col] = num;
                            if (solveSudoku(board, size)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Function to check if a number can be placed in the cell
    public static boolean isSafe(int[][] board, int row, int col, int num, int size) {
        for (int x = 0; x < size; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }
        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = row - row % boxSize;
        int boxColStart = col - col % boxSize;
        for (int r = 0; r < boxSize; r++) {
            for (int d = 0; d < boxSize; d++) {
                if (board[r + boxRowStart][d + boxColStart] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
