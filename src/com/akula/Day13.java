package com.akula;

import java.util.ArrayList;

/**
 * created by Sirisha on 12/12/2021 at 3:11 AM
 */
public class Day13 {
    public Day13() {}

    public int countDots(int[][] inputMatrix) {
        int num = 0;
        for(int i = 0; i < inputMatrix.length; i++)
            for(int j = 0; j < inputMatrix[0].length; j++)
                if(inputMatrix[i][j] > 0)
                    num++;

        return num;
    }

    public int[][] foldPaper(int[][] inputMatrix, String instruction) {
        int max_row = inputMatrix.length;
        int max_col = inputMatrix[0].length;
        int[][] retArr = new int[1][1];
        //Get the axis along which the paper needs to be folded
        String tmp = instruction.split(" ")[2];
        String axis = tmp.split("=")[0];
        int axis_line= Integer.parseInt(tmp.split("=")[1]);

        if(axis.equalsIgnoreCase("y")) {
            //paper will be folded along the row axis_line.
            //dots below the line have to be mirrored to be above the line
            retArr = new int[axis_line][max_col];
            //First populate everything upto the axis_line
            for(int row = 0; row < axis_line ; row++) {
                for(int col = 0; col < max_col ; col++) {
                    retArr[row][col] = inputMatrix[row][col];
                }
            }

            //Now start mirroring dots below the line, to dots above the line
            for(int row = axis_line+1; row < max_row; row++) {
                for(int col = 0; col < max_col; col++) {
                    int transposed_row = axis_line - (row-axis_line);
                    retArr[transposed_row][col]+= inputMatrix[row][col];
                    if(retArr[transposed_row][col] > 1)
                        retArr[transposed_row][col] = 1;
                }
            }
        } else {
            //paper will be folded along the column axis_line
            //dots to the right of the column axis line have to be mirrored to the left of the line
            retArr = new int[max_row][axis_line];
            //First populte everything upto the axis_line
            for(int row = 0; row < max_row; row++) {
                for(int col = 0; col < axis_line; col++) {
                    retArr[row][col] = inputMatrix[row][col];
                }
            }

            //Now start mirroring dots to the right of the axis line, to dots on the left of the axis_line
            for(int row = 0; row < max_row; row++) {
                for(int col = axis_line+1; col < max_col; col++) {
                    int transposed_col = axis_line - (col - axis_line);
                    retArr[row][transposed_col]+=inputMatrix[row][col];
                    if(retArr[row][transposed_col] > 1)
                        retArr[row][transposed_col] = 1;
                }
            }
        }

        return retArr;
    }

    public int Func1(String filename) {
        int retval = 0;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<String> instructions = new ArrayList<>();
        ArrayList<String> dotLines = new ArrayList<>();


        int max_row = 0;
        int max_col = 0;

        //Find the number of dots. The dots are separated from instructions by an empty line
        boolean emptyLine = false;
        for(String s : allLines) {
            if(!emptyLine) {
                if(s.length() == 0)
                    emptyLine = true;
                else {
                    dotLines.add(s);
                    String[] tmpArr = s.split(",");
                    for (int i = 0; i < tmpArr.length; i++) {
                        if( i == 0) {
                            //This is x coordinate. Translates to the columns in our matrix
                            if(Integer.parseInt(tmpArr[i]) > max_col)
                                max_col = Integer.parseInt(tmpArr[i]);
                        } else {
                            //Else, this is y coordinate. Translates to the rows in our matrix
                            if(Integer.parseInt(tmpArr[i]) > max_row)
                                max_row = Integer.parseInt(tmpArr[i]);
                        }
                    }
                }
            } else {
                //This line is after an empty line. So must be an instruction
                if (s.length() > 0 )
                    instructions.add(s);
            }
        }
        max_row++;
        max_col++;
        int[][] paper = new int[max_row][max_col];

        //Read all dots into the matrix
        for(String line : dotLines) {
            String[] tmpArr = line.split(",");
            int row = Integer.parseInt(tmpArr[1]); //Same as y coordinate
            int col = Integer.parseInt(tmpArr[0]); //Same as x coordinate
            paper[row][col] = 1;
        }

        //CommonUtils.printMatrix(paper);

        int foldCount = 0;
        int[][] newArr = paper;
        for(String instr : instructions) {
            newArr = foldPaper(newArr, instr);
            //CommonUtils.printMatrix(newArr);
            System.out.println("After fold #" + foldCount + ", and instruction " + instr + ", number of dots = " + countDots(newArr));
            foldCount++;
        }
        CommonUtils.printMatrix(newArr);

        System.out.println("Num columns = "+newArr[0].length);
        System.out.println("Num rows = "+newArr.length);
        //The output always has 8 capital letters. Try to print 5 columns at a time
        for(int i = 0; i < 8; i++) {
            for (int row = 0; row < newArr.length; row++) {
                for (int col = i*5; col < (i*5)+5; col++) {
                    String s = ".";
                    if (newArr[row][col] == 1)
                        s = "#";
                    System.out.print(" " + s);
                }
                System.out.println("");
            }
            System.out.println("------------------");
        }
        return retval;
    }

    public int Func2(String filename) {
        int retval = 0;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        return retval;
    }
}
