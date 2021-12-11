package com.akula;

import java.util.*;

/**
 * created by Sirisha on 12/8/2021 at 10:45 PM
 */
public class Day9 {
    class Point {
        int row, col;
        public Point(int r, int c) {
            row = r;
            col=c;
        }
        public int getRow() {return row;}
        public int getCol() {return col;}

        @Override
        public int hashCode() {
            return Objects.hash(row,col);
        }

        public boolean equals(Object obj) {
            return (obj instanceof Point && ((Point) obj).row == this.row && ((Point) obj).col == this.col);
        }
    };


    public Day9() {}

    int max_row = 0;
    int max_col = 0;
    int[][] map = new int[1][1];
    ArrayList<Integer> lowPoints = new ArrayList<>();
    Set<Point> basinPoints = new HashSet<>();

    public int Func1(String filename) {

        int retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        int max_row = allLines.size();
        int max_col = allLines.get(0).length();
        int[][] map = new int[max_row][max_col];
        ArrayList<Integer> lowPoints = new ArrayList<>();

        int row = 0;
        int col = 0;
        for (String s : allLines) {
            col = 0;
            //parse each character of the line and store in the matrix
            for(char c : s.toCharArray()) {
                map[row][col++] = Integer.parseInt(String.valueOf(c));
            }
            row++;
        }
/*
        for (int i = 0 ;i < max_row ; i++) {
            for (int j = 0; j < max_col; j++)
                System.out.print(map[i][j] + " ");
            System.out.println("");
        }
*/

        //Start finding the low points. A low point is lower than any of its adjacent points. Adjacent is up, down, right or left
        for (int i = 0; i < max_row; i++) {
            for (int j = 0; j < max_col; j++) {
                int currpos = map[i][j];
                boolean flag = true; //by default assume this number is the lowest

                //Is there a number above this number
                if(i > 0) {
                    //This is not the top row. So there is a number above this number
                    if(map[i-1][j] <= currpos)
                        flag = false;
                }
                //Is there a number below this one
                if (flag && i < max_row-1) {
                    if (map[i+1][j] <= currpos)
                        flag = false;
                }
                //Is there a number to the right of this one
                if (flag && j < max_col-1) {
                    if (map[i][j + 1] <= currpos)
                        flag = false;
                }

                    // Is there a number to the left of this one
                if (flag && j > 0) {
                    if (map[i][j - 1] <= currpos)
                        flag = false;
                }

                if(flag)
                    lowPoints.add(currpos);
            }
        }

        for(Integer i : lowPoints)
            retval+=i+1;
        return retval;
    }


    public void findDownBasin(int row, int col) {
        if(row < max_row-1) {
            int num=map[row][col];
            if(map[row+1][col] != 9 && map[row+1][col] > num) {
                //This number above qualifies as a basin point
                basinPoints.add(new Point(row+1,col));
                findDownBasin(row+1,col);
                findLeftBasin(row+1,col);
                findRightBasin(row+1,col);
            }
        }
    }

    public void findRightBasin( int row, int col) {
        if(col < max_col-1) {
            int num=map[row][col];
            int new_num = map[row][col+1];
            if(new_num != 9 && new_num > num) {
                //This number above qualifies as a basin point
                basinPoints.add(new Point(row,col+1));
                findRightBasin(row,col+1);
                findUpBasin(row,col+1);
                findDownBasin(row,col+1);
            }
        }
    }

    public void findUpBasin(int row, int col) {
        if(row > 0) {
            int num=map[row][col];
            if(map[row-1][col] != 9 && map[row-1][col] > num) {
                //This number above qualifies as a basin point
                basinPoints.add(new Point(row-1,col));
                findUpBasin(row-1,col);
                findLeftBasin(row-1,col);
                findRightBasin(row-1,col);
            }
        }
    }

    public void findLeftBasin(int row, int col) {
        if(col > 0) {
            int num=map[row][col];
            if(map[row][col-1] != 9 && map[row][col-1] > num) {
                //This number above qualifies as a basin point
                basinPoints.add(new Point(row,col-1));
                findLeftBasin(row,col-1);
                findUpBasin(row,col-1);
                findDownBasin(row,col-1);
            }
        }
    }


    public int Func2(String filename) {

        int retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        max_row = allLines.size();
        max_col = allLines.get(0).length();
        map = new int[max_row][max_col];
        ArrayList<Integer> basinSizes = new ArrayList<>();

        int row = 0;
        int col = 0;
        for (String s : allLines) {
            col = 0;
            //parse each character of the line and store in the matrix
            for(char c : s.toCharArray()) {
                map[row][col++] = Integer.parseInt(String.valueOf(c));
            }
            row++;
        }

        //Start finding the low points. A low point is lower than any of its adjacent points. Adjacent is up, down, right or left
        for (int i = 0; i < max_row; i++) {
            for (int j = 0; j < max_col; j++) {
                int currpos = map[i][j];
                boolean flag = true; //by default assume this number is the lowest

                    //Is there a number above this number
                    if(i > 0) {
                        //This is not the top row. So there is a number above this number
                        if(map[i-1][j] <= currpos)
                            flag = false;
                    }
                //Is there a number below this one
                if (flag && i < max_row-1) {
                    if (map[i+1][j] <= currpos)
                        flag = false;
                }
                //Is there a number to the right of this one
                if (flag && j < max_col-1) {
                    if (map[i][j + 1] <= currpos)
                        flag = false;
                }

                // Is there a number to the left of this one
                if (flag && j > 0) {
                    if (map[i][j - 1] <= currpos)
                        flag = false;
                }

                if(flag) {
                    lowPoints.add(currpos);

                    //Find the basin size for this low point
                    //Find left basin
                    basinPoints.clear();
                    basinPoints.add(new Point(i,j));
                    findLeftBasin(i, j);
                    findRightBasin( i, j);
                    findUpBasin( i, j);
                    findDownBasin( i, j);

                    //Find the count of unique points in this set. Why is the set not enforcing this?
                    //getSizeOfUniquePoints(basinPoints);

                    basinSizes.add(basinPoints.size());

                }
            }
        }

        int [] sortedSizes = new int[basinSizes.size()];
        int indx = 0;
        for(Integer val : basinSizes) {
            sortedSizes[indx++] = val;
        }
        Arrays.sort(sortedSizes);

        indx = sortedSizes.length-1;
        retval = sortedSizes[indx] * sortedSizes[indx-1] * sortedSizes[indx-2];

        return retval;
    }
}
