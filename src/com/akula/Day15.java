package com.akula;

import java.util.ArrayList;

/**
 * created by Sirisha on 12/15/2021 at 7:29 PM
 */
public class Day15 {
    int[][] riskMap; // 2D Array to contain the input


    public int evaluateRisk(int[][] riskMap) {
        // A 2d array to track the total risk involved to travel from each and every
        // node to the bottom-right corner of the map.
        int[][] riskSums = new int[riskMap.length][riskMap[0].length];
        // Initialize the riskSum at every node to a large number. 1,000,000 should do
        // fine. We can't use Integer.MAX_VALUE since we will loop through and possibly
        // add to these values. We don't want to overflow.
        for (int r = 0; r < riskSums.length; r++) {
            for (int c = 0; c < riskSums[0].length; c++) {
                riskSums[r][c] = 1_000_000;
            }
        }
        // The riskSum for the bottom-right node is 0.
        riskSums[riskSums.length - 1][riskSums[0].length - 1] = 0;

        // The idea is to loop through the riskSums array from the bottom-right to the
        // top-left and update the riskSum at each node based on the riskSums of it's
        // neighbors. We will change the riskSum at each node to reflect the minimum
        // risk+riskSum of each of it's neighbors. If a change is made to the map, we
        // will loop back through in case that change triggered another potential
        // improvement to the graph. We will continue to loop through until no changes
        // are made.
        boolean changeMade = true;
        while (changeMade) {
            changeMade = false;
            for (int r = riskSums.length - 1; r >= 0; r--) {
                for (int c = riskSums[0].length - 1; c >= 0; c--) {
                    // Four neighbors:
                    // riskMap[r][c] : the risk to enter this 1 node.
                    // riskSum[r][c] : the total risk involved in traveling to the bottom-right
                    // from this node.
                    int min = Integer.MAX_VALUE;
                    if (r - 1 >= 0)
                        min = Math.min(min, riskMap[r - 1][c] + riskSums[r - 1][c]);
                    if (r + 1 < riskSums.length)
                        min = Math.min(min, riskMap[r + 1][c] + riskSums[r + 1][c]);
                    if (c - 1 >= 0)
                        min = Math.min(min, riskMap[r][c - 1] + riskSums[r][c - 1]);
                    if (c + 1 < riskSums[0].length)
                        min = Math.min(min, riskMap[r][c + 1] + riskSums[r][c + 1]);

                    // If a change is being made to a node, we will have to loop back through again.
                    int oldRisk = riskSums[r][c];
                    riskSums[r][c] = Math.min(riskSums[r][c], min);
                    if (riskSums[r][c] != oldRisk)
                        changeMade = true;
                }
            }
        }
        // We now know the riskSum at every single node, but all we wanted was the
        // riskSum at 0,0.
        return (riskSums[0][0]);
    }

    public int[][] expandMap(int[][] map) {
        int[][] newMap = new int[5 * map.length][5 * map[0].length];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                int val = map[r][c]; // a value from original small map
                // We will place increments of this value (wrapped around 9) at corresponding
                // locations in the large grid (25 locations all together).
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        // Here, we will calculate the 'newNum' to be placed in the large grid. The
                        // 'newNum'
                        // will be 'val' incremented by how many 5x5 grids we moved left AND how many
                        // 5x5
                        // grids we moved down into the expansion of the map (so, i+j).
                        int newNum = val + i + j;
                        // The largest increment to 'val' will be 8, since the bottom-right 5x5 grid is
                        // over 4 and down 4 from the upper-left grid. Since, the largest value for
                        // 'val' is 9, the largeset value of 'newNum' is 17. So, let's just check if
                        // newNum > 9 and decrement by 9 if it is.
                        if (newNum > 9)
                            newNum -= 9;
                        // Place newNum all over the new larger map.
                        newMap[r + i * map.length][c + j * map[0].length] = newNum;
                    }
                }

            }
        }

        return newMap;
    }


    public void readValuesIntoMatrix(ArrayList<String> lines) {
        //Read all values into this matrix
        int row = 0;
        int col =0;
        for(String line : lines) {
            col = 0;
            for(char c : line.toCharArray()) {
                riskMap[row][col++] = Integer.parseInt(String.valueOf(c));
            }
            row++;
        }
    }

    public int Func1(String filename) {
        int retval = 0;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        int max_row = allLines.size();
        int max_col = (allLines.get(0)).length();
        riskMap = new int[max_row][max_col];

        readValuesIntoMatrix(allLines);
        return evaluateRisk(riskMap);
    }

    public int Func2(String filename) {
        Func1(filename);
        int[][] expandedMap = expandMap(riskMap);
        return evaluateRisk(expandedMap);
    }
}
