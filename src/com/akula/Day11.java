package com.akula;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * created by Sirisha on 12/10/2021 at 12:23 AM
 */
public class Day11 {
    int[][] octopusMatrix = new int[1][1];
    int numFlashes = 0;
    public Day11() {}

    public void printMatrix(int[][] inputArray) {
        for(int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++)
                System.out.print(inputArray[i][j]+" ");
            System.out.println("");
        }

        System.out.println("-------------------");
    }

    public void increaseEneryLevelOfAdjacentOctopi(int row, int col, boolean[][] fm) {
        //Increase the energy levels of adjacent octopus
        //Check the octopus to the north of the current one
        if(row > 0) {
            if(octopusMatrix[row-1][col] < 10 && fm[row-1][col]==false)
                octopusMatrix[row-1][col]++;
        }

        //Check the octopus to the south of the current one
        if(row < octopusMatrix.length-1)
            if(octopusMatrix[row+1][col] < 10 && !fm[row+1][col])
                octopusMatrix[row+1][col]++;

            // Check the Octopus to the west of the current one
        if(col > 0)
            if(octopusMatrix[row][col-1] < 10 && !fm[row][col-1])
                octopusMatrix[row][col-1]++;

            //Check the octopus to the east of the current one
        if(col < (octopusMatrix[0]).length-1)
            if(octopusMatrix[row][col+1] < 10 && !fm[row][col+1])
                octopusMatrix[row][col+1]++;

        //Check the octopus to the north west of the current one
        if(row > 0 && col > 0)
            if(octopusMatrix[row-1][col-1]<10 && !fm[row-1][col-1])
                octopusMatrix[row-1][col-1]++;

        //Check the octopus to the north east of the current one
        if(row > 0 && col < (octopusMatrix[0]).length-1)
            if(octopusMatrix[row-1][col+1]<10 && !fm[row-1][col+1])
                octopusMatrix[row-1][col+1]++;

        //Check the octopus to the south west of the current one
        if(row < octopusMatrix.length-1 && col > 0)
            if(octopusMatrix[row+1][col-1]<10 && !fm[row+1][col-1])
                octopusMatrix[row+1][col-1]++;

        //Check the octopus to the south east of the current one
        if(row < octopusMatrix.length-1 && col < (octopusMatrix[0]).length-1)
            if(octopusMatrix[row+1][col+1]<10 && !fm[row+1][col+1])
                octopusMatrix[row+1][col+1]++;
    }

    public int Func1(String filename, int numCycles) {

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        int max_row = allLines.size();
        int max_col = (allLines.get(0)).length();
        octopusMatrix = new int[max_row][max_col];

        //Read all values into this matrix
        int row = 0;
        int col =0;
        for(String line : allLines) {
            col = 0;
            for(char c : line.toCharArray()) {
                octopusMatrix[row][col++] = Integer.parseInt(String.valueOf(c));
            }
            row++;
        }

        printMatrix(octopusMatrix);
        for(int cycle = 0; cycle < numCycles; cycle++) {
            boolean[][] flashMatrix = new boolean[max_row][max_col];
            //Increase energy level of each octopus by 1
            for (row = 0; row < max_row; row++) {
                for(col = 0; col < max_col; col++) {
                    if(octopusMatrix[row][col] < 10)
                    octopusMatrix[row][col]++;
                }
            }
            printMatrix(octopusMatrix);

            //Now check if any octopi should flash
            boolean allFlashesCounted = false;
            while(!allFlashesCounted) {
                int tmp_flashes = 0;
                for (row = 0; row < max_row; row++) {
                    for(col =0; col < max_col;col++) {
                        if(octopusMatrix[row][col] > 9) {
                            //This octopus should flash, if it has not already flashed in this cycle
                            if(!flashMatrix[row][col]) {
                                numFlashes++;
                                tmp_flashes++;
                            }
                            octopusMatrix[row][col]=0;
                            //Mark this octopus as flashed
                            flashMatrix[row][col]=true;
                            increaseEneryLevelOfAdjacentOctopi(row,col,flashMatrix);
                        }
                    }
                }
                //printMatrix(octopusMatrix);
                if(tmp_flashes == 0)
                    allFlashesCounted = true;
            }

            System.out.println("After Step "+(cycle+1));
            printMatrix(octopusMatrix);
            System.out.println("Number of flashes = "+numFlashes);
        }
        //printMatrix(octopusMatrix);
        return numFlashes;
    }

    public int Func2(String filename) {

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        int max_row = allLines.size();
        int max_col = (allLines.get(0)).length();
        octopusMatrix = new int[max_row][max_col];

        //Read all values into this matrix
        int row = 0;
        int col =0;
        for(String line : allLines) {
            col = 0;
            for(char c : line.toCharArray()) {
                octopusMatrix[row][col++] = Integer.parseInt(String.valueOf(c));
            }
            row++;
        }

        printMatrix(octopusMatrix);
        boolean allOctopiInSync = false;
        int cycle = 0;
        while(!allOctopiInSync) {
            boolean[][] flashMatrix = new boolean[max_row][max_col];
            //Increase energy level of each octopus by 1
            for (row = 0; row < max_row; row++) {
                for(col = 0; col < max_col; col++) {
                    if(octopusMatrix[row][col] < 10)
                        octopusMatrix[row][col]++;
                }
            }
            printMatrix(octopusMatrix);

            //Now check if any octopi should flash
            boolean allFlashesCounted = false;
            while(!allFlashesCounted) {
                int tmp_flashes = 0;
                for (row = 0; row < max_row; row++) {
                    for(col =0; col < max_col;col++) {
                        if(octopusMatrix[row][col] > 9) {
                            //This octopus should flash, if it has not already flashed in this cycle
                            if(!flashMatrix[row][col]) {
                                numFlashes++;
                                tmp_flashes++;
                            }
                            octopusMatrix[row][col]=0;
                            //Mark this octopus as flashed
                            flashMatrix[row][col]=true;
                            increaseEneryLevelOfAdjacentOctopi(row,col,flashMatrix);
                        }
                    }
                }
                //printMatrix(octopusMatrix);
                if(tmp_flashes == 0)
                    allFlashesCounted = true;
            }

            System.out.println("After Step "+(cycle+1));
            printMatrix(octopusMatrix);
            System.out.println("Number of flashes = "+numFlashes);

            //Are all octopi flashing? Even if a single octopus is set to a non-zero value
            int sum = 0;
            for (int i = 0; i < max_row; i++) {
                for (int j = 0; j < max_col; j++) {
                    sum+=octopusMatrix[i][j];
                }
            }
            if (sum == 0)
                allOctopiInSync=true;

            cycle++;
        }
        //printMatrix(octopusMatrix);
        return cycle;
    }

}
