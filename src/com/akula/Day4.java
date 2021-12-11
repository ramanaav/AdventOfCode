package com.akula;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * created by Sirisha on 12/3/2021 at 11:03 PM
 */
public class Day4 {

    ArrayList<Integer> draws = new ArrayList<Integer>();
    ArrayList<int[][]> allboards = new ArrayList<int[][]>();

    public void Day4() {

    }

    public void readDrawsAndBoardInfo(String filename) {
        int line_count =0;
        int[][] newBoard = new int[1][1];
        int row = 0;
        int col = 0;

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line is of format <direction> <value>.
                //Direction is one of forward, down, up
                String line = sc.nextLine();
                if (line_count==0) {
                    //This is the first line and this represents the random draws
                    String drawsStr=line;
                    for (String s : drawsStr.split(","))
                        draws.add(Integer.parseInt(s));
                } else {
                    //if line has 0 length, then start a new board
                    if (line.length() == 0) {
                        //Check if the board is full. If yes, save it to the allboards arraylist. If not, create a new board
                        if(newBoard.length == 5)
                            allboards.add(newBoard);

                        newBoard = new int[5][5];
                        row=col=0;
                    } else {
                        //This is a line that contains the numbers of a board. Populate the board
                        for(String s : line.split(" ")) {
                            if(s.length() > 0) {
                                newBoard[row][col] = Integer.parseInt(s);
                                col++;
                            }
                        }
                        col=0;
                        row++;
                    }
                }
                line_count++;
            }
            //There are no more lines. But there maybe unprocessed data
            if(row!=0 || col!=0) {
                allboards.add(newBoard);
            }
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    public int checkAndMarkAllBoards(int drawNum) {
        int retVal = -1;
        int boardIndx = 0;
        for(int[][] board : allboards) {
            int row = 0;
            int col = 0;
            for (row = 0; row < board.length; row++) {
                col = 0;
                for (; col < board.length; col++) {
                    if (board[row][col] == drawNum) {
                        //The draw number is present on this board. Mark the position as drawn - replace the number with a -1
                        board[row][col] = -1;
                        //In a bingo board, a number appears only once. Since we already saw the draw number, it will not repeat. So we can break
                        break;
                    }
                }
                if (col < board.length) {
                    //Inner loop was broken. This means a match was found. So break from the outer loop
                    break;
                }
            }
                //Replace the board with this updated board
                if (row < board.length || col < board.length) {
                    //We need to replace the board
                    allboards.set(boardIndx, board);
                    //Check if this board is a winner. A winner can have either all numbers in a row marked or all numbers in a column marked
                    for(int i = 0; i < board.length; i++) {
                        int marked_row_count = 0;
                        for (int j = 0; j < board.length; j++) {
                            if (board[i][j] == -1)
                                marked_row_count++;
                        }
                        if (marked_row_count == board.length) {
                            //We have a winner. One row is all marked
                            if(retVal == -1)
                                retVal = boardIndx;
                            break;
                        }
                    }
                    if(retVal == -1) {
                        //Check if any column in this board is completely marked
                        for(int i = 0; i < board.length; i++) {
                            int marked_col_count = 0;
                            for (int j = 0; j < board.length; j++) {
                                if (board[j][i] == -1)
                                    marked_col_count++;
                            }
                            if(marked_col_count == board.length) {
                                //We have a winner. One column is all marked;
                                if (retVal == -1)
                                    retVal = boardIndx;
                                break;
                            }
                        }
                    }
                    //if(retVal != -1) break;
                }
            boardIndx++;
        }

        return retVal;
    }



    public int getFirstWinningBoardProduct(String filename) {
        int product = 0;
        //Read the draws and board info
        readDrawsAndBoardInfo(filename);

        //For each number, check each board and mark the board if it has the number
        for(Integer i : draws) {
            int boardIndx = checkAndMarkAllBoards(i);
            if(boardIndx != -1) {
                //We have winning board
                //Get the board
                int[][] board = allboards.get(boardIndx);
                //Get the sum of all unmarked numbers on this board
                int sum = 0;
                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board.length; col++) {
                        if (board[row][col] >= 0)
                            sum+=board[row][col];
                    }
                }
                product=sum*i;
                break;
            }
        }
        return product;
    }

    public void removeAllMarkedBoards() {
        ArrayList<Integer> boardsToRemove = new ArrayList<Integer>();
        int boardIndx = 0;
        for (int[][] board : allboards) {
            //Check if this board is a winner. A winner can have either all numbers in a row marked or all numbers in a column marked
            boolean winningboard = false;
            //Check each row to determine if this is a winning board
            for (int i = 0; i < board.length; i++) {
                int marked_row_count = 0;
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == -1)
                        marked_row_count++;
                }
                if (marked_row_count == board.length) {
                    //We have a winner. One row is all marked. Mark this board for removal
                    winningboard = true;
                    break;
                }
            }
            if (winningboard == false) {
                //Check each column to determine if this is a winning board
                for (int i = 0; i < board.length; i++) {
                    int marked_col_count = 0;
                    for (int j = 0; j < board.length; j++) {
                        if (board[j][i] == -1)
                            marked_col_count++;
                    }
                    if (marked_col_count == board.length) {
                        //We have a winner. One column is all marked;
                        winningboard = true;
                        break;
                    }
                }
            } //if winningboard
            if(winningboard)
                boardsToRemove.add(boardIndx);

            boardIndx++;
        }

        if(boardsToRemove.size() > 0) {
            //Copy all the boards that should remain, to a new array list
            Collections.sort(boardsToRemove, Collections.reverseOrder());
            for(int i : boardsToRemove)
                allboards.remove(i);
        }
    }

    public int getLastWinningBoardProduct(String filename) {
        int product = 0;
        //Read the draws and board info
        readDrawsAndBoardInfo(filename);

        //For each number, check each board and mark the board if it has the number
        for(Integer i : draws) {
            int boardIndx = checkAndMarkAllBoards(i);
            if(boardIndx != -1) {
                //We have winning board.
                //Is this the last board to win?
                if(allboards.size() == 1) {
                    //This is the last board to win. Get the product for this board
                    //Get the board
                    int[][] board = allboards.get(boardIndx);
                    //Get the sum of all unmarked numbers on this board
                    int sum = 0;
                    for (int row = 0; row < board.length; row++) {
                        for (int col = 0; col < board.length; col++) {
                            if (board[row][col] >= 0)
                                sum+=board[row][col];
                        }
                    }
                    product=sum*i;
                    break;
                } else {
                    //This is not the last board to win. So remove this board from the allboards
                    allboards.remove(boardIndx);
                    //Remove any other winning boards
                    removeAllMarkedBoards();
                }
            }
        }
        return product;
    }
}
