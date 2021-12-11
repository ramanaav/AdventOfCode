package com.akula;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * created by Sirisha on 12/5/2021 at 12:57 AM
 */
public class Day5 {
    public void Day5() {}

    public int getNumLinesToAvoid(String filename) {
        int line_count =0;
            int[][] matrix = new int[1][1];
            int row = 0;
            int col = 0;
            int max =0;
            ArrayList<String> inputStrs = new ArrayList<String>();
            int numlines = 0;

            File file = new File(filename);
            try {
                Scanner sc = new Scanner(file);
                //No type checks. Assume all inputs are text
                while (sc.hasNextLine()) {
                    //Line is of format <direction> <value>.
                    //Direction is one of forward, down, up
                    String line = sc.nextLine();
                    inputStrs.add(line);
                    //Get the max x and max y values from this entry
                    String[] endpoints = line.split("->");
                    int x1 = Integer.parseInt((((endpoints[0]).split(","))[0]).trim());
                    int y1 = Integer.parseInt((((endpoints[0]).split(","))[1]).trim());
                    int x2 = Integer.parseInt((((endpoints[1]).split(","))[0]).trim());
                    int y2 = Integer.parseInt((((endpoints[1]).split(","))[1]).trim());
                    if (x1 > max) max = x1;
                    if(x2 > max) max = x2;
                    if(y1 > max) max = y1;
                    if(y2 > max) max = y2;
                    line_count++;
                }
                max++;
                //Initialize our matrix with the max x and y values
                //y is row is y and x is column

                matrix = new int[max][max];

                //For each line, identify the points along the line
                for(String s : inputStrs) {
                    String[] endpoints = s.split("->");
                    int x1 = Integer.parseInt((((endpoints[0]).split(","))[0]).trim());
                    int y1 = Integer.parseInt((((endpoints[0]).split(","))[1]).trim());
                    int x2 = Integer.parseInt((((endpoints[1]).split(","))[0]).trim());
                    int y2 = Integer.parseInt((((endpoints[1]).split(","))[1]).trim());

                    if (x1 == x2) {
                        //y is row. x is column
                        //Walk the segment along the y axis
                        int start = y1;
                        int end = y2;
                        if (y1 > y2) {
                            start = y2;
                            end = y1;
                        }
                        for(int i = start; i <=end; i++) {
                            matrix[i][x1]++;
                        }
                    } else if (y1 == y2) {
                        //y is row. x is column
                        //Walk the segment along the x axis
                        int start = x1;
                        int end = x2;
                        if (x1 > x2) {
                            start = x2;
                            end =x1;
                        }
                        for (int i = start; i <=end; i++) {
                            matrix[y1][i]++;
                        }
                    } else {
                        //Consider this line if it is a 45 degree line. Slope should be 1
                        //Calculate the slope. It should be 1
                        int start_x = x1;
                        int start_y = y1;
                        int end_x = x2;
                        int end_y = y2;
                        if (x1 > x2) {
                            start_x = x2;
                            start_y = y2;
                            end_x = x1;
                            end_y = y1;
                        }
                        int slope = (y2-y1)/(x2-x1);
                        if(slope == 1 || slope == -1) {
                            //This is a 45 degree line. So, walk along this segment
                            int tmp_y = start_y;
                            for(int i = start_x; i < end_x;i++) {
                                matrix[tmp_y][i]++;
                                tmp_y+=slope;
                            }
                            //Mark the end point also
                            matrix[end_y][end_x]++;
                        }
                    }
                }
                //Count number of places atleast 2 lines (>=2) overlap

                for(int i = 0; i < max; i++) {
                    for (int j = 0; j < max; j++) {
                        if(matrix[i][j] >= 2)
                            numlines++;
                    }
                }
            } catch (IOException ioe) {
                System.out.println(ioe);
            }

            return numlines;
    }
}
