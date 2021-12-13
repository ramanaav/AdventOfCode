package com.akula;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * created by Sirisha on 12/1/2021 at 7:40 AM
 */
public class CommonUtils {
    public void CommonUtils() {}

    public static void printMatrix(int[][] inputArray) {
        for(int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++)
                System.out.print(inputArray[i][j]+" ");
            System.out.println("");
        }

        System.out.println("-------------------");
    }

    public static ArrayList<String> readFromFile(String filename) {

        ArrayList<String> allLines = new ArrayList<String>();
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);
            //No type checks. Assume all inputs are text
            while (sc.hasNextLine()) {
                //Line is of format <direction> <value>.
                //Direction is one of forward, down, up
                String line = sc.nextLine();
                allLines.add(line);
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        return allLines;
    }

    public static void printText(ArrayList<String> inputStrs, String delimiter) {
        boolean flag = false;
        for(String s : inputStrs) {
            if(!flag)
                flag = true;
            else
                System.out.print(delimiter);
            System.out.print(s);
        }
        System.out.println("");
    }
}
