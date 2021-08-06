import java.util.Scanner;

/**
 * CS312 Assignment 10.
 *
 * On my honor, Emiliano Villarreal, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 * email address: ev8559@utexas.edu
 * UTEID: Section 5 digit ID: 8559
 * Grader name: 
 * Number of slip days 
 * used on this assignment: 2
 *
 * Program that allows two people to play Connect Four.
 */

public class ConnectFour {

	// CS312 Students, add your constants here.
	static int NUM_ROWS = 6;
	static int NUM_COLS = 7;

	public static void main(String[] args) {
		Scanner key = new Scanner(System.in);
		intro();
		names(key);

		// Complete this method.
		// Make and use one Scanner connected to System.in.
	}

	// CS312 Students, add your methods here.

	// Show the introduction.
	public static void intro() {
		System.out.println("This program allows two people to play the");
		System.out.println("game of Connect four. Each gameLetter takes turns");
		System.out.println("dropping a checker in one of the open columns");
		System.out.println("on the board. The columns are numbered 1 to 7.");
		System.out.println("The first gameLetter to get four checkers in a row");
		System.out.println("horizontally, vertically, or diagonally wins");
		System.out.println("the game. If no gameLetter gets fours in a row and");
		System.out.println("and all spots are taken the game is a draw.");
		System.out.println("Player one's checkers will appear as r's and");
		System.out.println("Player two's checkers will appear as b's.");
		System.out.println("Open spaces on the board will appear as .'s.\n");
	}

	// Prompt the user for an int. The String prompt will
	// be printed out. key must be connected to System.in.
	public static int getInt(Scanner key, String prompt) {
		while (!key.hasNextInt()) {
			String notAnInt = key.nextLine();
			System.out.println(notAnInt + " is not an integer.");
			System.out.print(prompt);
		}
		int result = key.nextInt();
		key.nextLine();
		return result;
	}

	// Will create the connect four board
	public static char[][] grid() {
		char board[][] = { { '.', '.', '.', '.', '.', '.', '.' },
						   { '.', '.', '.', '.', '.', '.', '.' },
						   { '.', '.', '.', '.', '.', '.', '.' }, 
						   { '.', '.', '.', '.', '.', '.', '.' },
						   { '.', '.', '.', '.', '.', '.', '.' }, 
						   { '.', '.', '.', '.', '.', '.', '.' } };

		return board;
	}

	// Will ask users for names and print out the board
	public static void names(Scanner key) {
		char board[][] = grid();
		System.out.print("Player 1 enter your name: ");
		String nameOne = key.nextLine();
		System.out.print("\nPlayer 2 enter your name: ");
		String nameTwo = key.nextLine();

		game(nameOne, nameTwo, board, key);
	}

	// will run the while loop as long as there's no winner
	public static void game(String nameOne, String nameTwo, char[][] board, Scanner key) {
		int turn = 0;
		boolean win = false;
		boolean tie = false;
		while (!win && !tie) {
			verify(turn, nameOne, nameTwo, key, board);
			win = winner(board, turn);
			tie = tie(board);
			turn ++;
		}
		
		if (tie) {
			System.out.println("\nGame ended in a draw");
		}
		else if(win) {
			if (turn % 2 == 1) {
				System.out.println("\n" + nameOne + " wins!!");

			} else {
				System.out.println("\n" + nameTwo + " wins!!");
			}
		}
		System.out.println("\nFinal Board");
		System.out.println("1 2 3 4 5 6 7 column numbers");
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int x = 0; x < NUM_COLS; x++) {
				System.out.print(board[i][x] + " ");
			}
			System.out.println();
		}
	}

	// this will verify that the user's input is valid. It also calls the method
	// that changes the board.
	public static void verify(int turn, String nameOne, String nameTwo, Scanner key, char[][] board) {
		String name = "";
		char gameLetter = 'r';
		if (turn % 2 == 0) {
			name = nameOne;
			gameLetter = 'b';
		} else {
			name = nameTwo;
			gameLetter = 'r';
		}
		System.out.println("\nCurrent Board");
		System.out.println("1 2 3 4 5 6 7 column numbers");
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int x = 0; x < NUM_COLS; x++) {
				System.out.print(board[i][x] + " ");
			}
			System.out.println();
		}
		System.out.println(name + " it is your turn.");
		System.out.println("Your pieces are the " + gameLetter + "'s.");
		System.out.print(name + ", enter the column to drop your checker: ");
		int playerColumn = getInt(key, name + ", enter the column to drop your checker: ");
		while (playerColumn < 0 || playerColumn > 7) {
			System.out.println(playerColumn + " is not a valid column.");
			System.out.print(name + ", enter the column to drop your checker: ");
			playerColumn = key.nextInt();
			playerColumn = getInt(key, name + ", enter the column to drop your checker: ");
		}
		changeBoard( board, playerColumn, gameLetter, name, key);
	}
		

	// this method will alter the board
	public static void changeBoard( char[][] board, int playerColumn, char gameLetter, String name, Scanner key) {
		boolean fit = false;
		int numRowsCheck = NUM_ROWS - 1;
		playerColumn = checkCol(playerColumn, board, key, name);
		while (!fit) {
			if (board[numRowsCheck][playerColumn - 1] == '.') {
				board[numRowsCheck][playerColumn - 1] = gameLetter;
				fit = true;
			} else {
				numRowsCheck--;
			}
		}
	}

	// determines if there's a winner after each move
	public static boolean winner(char[][] board, int turn) {
		char gameLetter = 'b';
		if (turn % 2 == 0) {
			gameLetter = 'b';
		} else {
			gameLetter = 'r';
		}
		// horizontal check
		for (int j = 0; j < NUM_ROWS - 3; j++) {
			for (int i = 0; i < NUM_COLS -1; i++) {
				if (board[i][j] == gameLetter && board[i][j + 1] == gameLetter && board[i][j + 2] == gameLetter
						&& board[i][j + 3] == gameLetter) {
					return true;
				}
			}
		}
		// vertical check
		for (int i = 0; i < NUM_ROWS - 3; i++) {
			for (int j = 0; j < NUM_COLS ; j++) {
				if (board[i][j] == gameLetter && board[i + 1][j] == gameLetter && board[i + 2][j] == gameLetter
						&& board[i + 3][j] == gameLetter) {
					return true;
				}
			}
		}
		//descending diagonal
		for (int i = 3; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS - 3; j++) {
				if (board[i][j] == gameLetter && board[i - 1][j + 1] == gameLetter && board[i - 2][j + 2] == gameLetter
						&& board[i - 3][j + 3] == gameLetter)
					return true;
			}
		}
		//ascending diagonal
		for (int i = 3; i < NUM_ROWS; i++) {
			for (int j = 3; j < NUM_COLS ; j++) {
				if (board[i][j] == gameLetter && board[i - 1][j - 1] == gameLetter && board[i - 2][j - 2] == gameLetter
						&& board[i - 3][j - 3] == gameLetter)
					return true;
			}
		}
		return false;
	}

	// This method will check if the row indicated by the user is full of b's or r's
	public static int checkCol(int playerColumn, char[][] board, Scanner key, String name) {

		int counter = 0;
		boolean pass = false;
		while (!pass) {
			for (int i = 0; i <= 5; i++) {
				if (board[i][playerColumn - 1] == 'b' || board[i][playerColumn - 1] == 'r') {
					counter++;
				}
			}
			if (counter == 6) {
				System.out.println(playerColumn + " is not a legal column. That column is full");
				System.out.print(name + ", enter the column to drop your checker: ");
				playerColumn = key.nextInt();
				counter = 0;
			} else {
				pass = true;
			}
		}
		return playerColumn;
	}
	
	//method that checks if there's a tie
	public static boolean tie(char[][] board) {
		int counter = 0;
		for (int i = 0; i < NUM_ROWS; i++ ) {
			for (int x = 0; x < NUM_COLS; x++) {
				if (board[i][x] == 'b' ||board[i][x] == 'r') {
					counter++;
				}
			}
		}
		if (counter == 42) {
			return true;
		}
		else {
			return false;
		}
	}
	
}