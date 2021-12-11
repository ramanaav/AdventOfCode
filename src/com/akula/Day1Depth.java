package com.akula;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * created by Sirisha on 12/1/2021 at 7:40 AM
 */
public class Day1Depth {
    public void Day1Depth() {
    }

    ;

    public int getMeasurements(String filename) {
        int count = 0;
        int curr = 0;
        int prev = -1;

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                curr = Integer.parseInt(sc.nextLine());
                if (prev != -1) {
                    if (curr > prev) count++;
                }
                prev = curr;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return count;
    }

    public int slidingWindows(String filename) {

        int count = 0;
        int currSlidingWindowSum = 0;
        int prevSlidingWindowSum = -1;
        int firstOf3 = -1;
        int maxWinSize = 3;
        int win = 0;
        Queue<Integer> myQ = new LinkedList<Integer>();

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {

                int tmp_int = Integer.parseInt(sc.nextLine());
                if (win >= maxWinSize) {
                    //We have three integers in our sliding window queue
                    if (prevSlidingWindowSum != -1) {
                        System.out.println("Current Sliding window sum = " + currSlidingWindowSum + " and Previous Sliding Window sum = " + prevSlidingWindowSum);
                        if (currSlidingWindowSum > prevSlidingWindowSum) count++;
                    }
                    //This is starting the fourth number in the input
                    prevSlidingWindowSum = currSlidingWindowSum;
                    firstOf3 = myQ.poll();
                    currSlidingWindowSum = currSlidingWindowSum - firstOf3 + tmp_int;


                    //Remove the first element that was pushed into the queue
                } else {
                    //This is a case for the first three integers
                    currSlidingWindowSum += tmp_int;
                }
                //push the integer into the queue
                myQ.add(tmp_int);
                win++;
            }
            //Do the last comparison
            System.out.println("Current Sliding window sum = " + currSlidingWindowSum + " and Previous Sliding Window sum = " + prevSlidingWindowSum);
            if (currSlidingWindowSum > prevSlidingWindowSum) count++;
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return count;
    }


    public int calculateMovements(String filename) {
        int product = 0;
        int x_axis=0;
        int y_axis = 0;

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line is of format <direction> <value>.
                //Direction is one of forward, down, up
                String line = sc.nextLine();
                String[] list = line.split(" ");
                switch(list[0]) {
                    case "forward" :
                        x_axis+=Integer.parseInt(list[1]);
                        break;
                    case "down" :
                        y_axis+=Integer.parseInt(list[1]);
                        break;
                    case "up":
                        y_axis-=Integer.parseInt(list[1]);
                        break;
                    default:
                        System.out.println("Unrecognized command "+list[0]);
                        break;
                }

            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return x_axis*y_axis;
    }

    public int calculateMovementsWithAim(String filename) {
        int x_axis=0;
        int y_axis = 0;
        int aim = 0;

        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line is of format <direction> <value>.
                //Direction is one of forward, down, up
                String line = sc.nextLine();
                String[] list = line.split(" ");
                switch(list[0]) {
                    case "forward" :
                        x_axis+=Integer.parseInt(list[1]);
                        y_axis+=aim*Integer.parseInt(list[1]);
                        break;
                    case "down" :
                        aim+=Integer.parseInt(list[1]);
                        break;
                    case "up":
                        aim-=Integer.parseInt(list[1]);
                        break;
                    default:
                        System.out.println("Unrecognized command "+list[0]);
                        break;
                }

            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return x_axis*y_axis;
    }


    public int d3calculateGammaAndEpsilon(String filename) {

        int gammaInt = 0;
        int epsilonInt = 0;
        int zeroCount[] = new int[1];
        int oneCount[] = new int[1];

        int line_count = 0;
        int len = 0;
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line contains a binary string.
                String line = sc.nextLine();
                if (line_count == 0) {
                    //Initialize all arrays to be the same length as the length of the first line in the input file
                    len = line.length();
                    zeroCount = new int[line.length()];
                    oneCount = new int[line.length()];
                }
                int indx = 0;
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '0':
                            zeroCount[indx]++;
                            break;
                        case '1':
                            oneCount[indx]++;
                            break;
                        default:
                            System.out.println("Invalid input -" + c);
                            break;
                    }
                    indx++;
                }
                line_count++;
            }

                StringBuffer gamma = new StringBuffer();
                StringBuffer epsilon = new StringBuffer();
                for (int i = 0; i < len; i++) {
                    //Gamma - For each bit, find the most frequently occuring binary digit
                    //Epsilon - For each bit, find the least frequently occuring binary digit. This will be opposite of gamma
                    if (zeroCount[i] > oneCount[i]) {
                        gamma.append("0");
                        epsilon.append("1");
                    } else {
                        gamma.append("1");
                        epsilon.append("0");
                    }
                }
                gammaInt=Integer.parseInt(gamma.toString(),2);
                epsilonInt=Integer.parseInt(epsilon.toString(),2);

            //Read all lines. Now start comparing
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return gammaInt*epsilonInt;
    }

    public String getMostCommonBit(String[] inputArray, int indx) {
        String mostCommonBit="";
        int zeroCount[] = new int[inputArray[0].length()];
        int oneCount[] = new int[inputArray[0].length()];
        for(int i = 0; i < inputArray.length; i++) {
            int j = 0;
            for (char c : (inputArray[i]).toCharArray()) {
                switch (c) {
                    case '0':
                        zeroCount[j]++;
                        break;
                    case '1':
                        oneCount[j]++;
                        break;
                    default:
                        System.out.println("Invalid input -" + c);
                        break;
                }
                j++;
            }
        }

        //Find the most common bit in the required index
        if(zeroCount[indx] > oneCount[indx])
            mostCommonBit = "0";
        else
            mostCommonBit = "1";

        return mostCommonBit;
    }
    public int d3calculateLifeSupportRating(String filename) {

        int o2_generator_rating = 0;
        int co2_scrubber_rating = 0;
        ArrayList<String> inputLines = new ArrayList<String>();
        ArrayList<String> lcbInputLines = new ArrayList<String>();
        int zeroCount[] = new int[1];
        int oneCount[] = new int[1];

        int line_count = 0;
        int len = 0;
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line contains a binary string.
                String line = sc.nextLine();
                inputLines.add(line);
                lcbInputLines.add(line);
            }

            //Read all lines.
            // Now find the oxygen generator rating
            boolean flag = true;
            int indx = 0;
            while(flag) {
                String[] newArr = new String[inputLines.size()];
                newArr = inputLines.toArray(newArr);
                String mcb = getMostCommonBit(newArr,indx);
                //Count all strings that have this most common bit in the specified index
                int count = 0;
                inputLines.clear();
                for(String s : newArr) {
                    if(s.charAt(indx) == mcb.charAt(0)) {
                        //Save this string to the new list
                        inputLines.add(s);
                    }
                }
                //System.out.println("New List is "+ inputLines);
                if(inputLines.size() == 1) {
                    //We are done. We found the oxygen generator rating
                    o2_generator_rating = Integer.parseInt(inputLines.get(0),2);
                    flag=false;
                } else
                    indx++;
            }


            // Now find the co2 scrubber rating
            flag = true;
            indx = 0;
            while(flag) {
                String[] newArr = new String[lcbInputLines.size()];
                newArr = lcbInputLines.toArray(newArr);
                String mcb = getMostCommonBit(newArr,indx);
                String lcb = "1";
                if (mcb.equals("1")) lcb = "0";
                //Count all strings that have this least common bit in the specified index
                lcbInputLines.clear();
                for(String s : newArr) {
                    if(s.charAt(indx) == lcb.charAt(0)) {
                        //Save this string to the new list
                        lcbInputLines.add(s);
                    }
                }
                //System.out.println("New List is "+ lcbInputLines);
                if(lcbInputLines.size() == 1) {
                    //We are done. We found the oxygen generator rating
                    co2_scrubber_rating = Integer.parseInt(lcbInputLines.get(0),2);
                    flag=false;
                } else
                    indx++;
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return o2_generator_rating*co2_scrubber_rating;
    }
}
