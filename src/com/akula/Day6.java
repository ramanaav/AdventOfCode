package com.akula;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * created by Sirisha on 12/5/2021 at 10:55 PM
 */
public class Day6 {
    public Day6() {}

    public BigInteger day6Dictionary(String filename, int numdays) {
        BigInteger numFish = BigInteger.ZERO;
        Hashtable<Integer,BigInteger> myDict = new Hashtable<Integer, BigInteger>();

        for (int i = 0; i < 9; i++)
            myDict.put(i,BigInteger.ZERO);

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);

        for(String s : allLines) {
            for (String val : s.split(",")) {
                Integer timer = Integer.parseInt(val);
                BigInteger tmp = (myDict.get(timer)).add(BigInteger.ONE);
                myDict.put(timer,tmp);
            }
        }

        for (int i=0; i < numdays; i++) {
            BigInteger numNewFish = BigInteger.ZERO;

            for(int j = 0; j < 9; j++) {
                if (j==0) {
                    if ((myDict.get(j)).compareTo(BigInteger.ZERO) > 0) {
                        numNewFish = myDict.get(0);
                        myDict.put(0,BigInteger.ZERO);
                    }
                } else {
                    myDict.put(j-1,myDict.get(j));
                    myDict.put(j,BigInteger.ZERO);
                }
            }

            if (numNewFish.compareTo(BigInteger.ZERO) > 0) {
                myDict.put(8,(myDict.get(8)).add(numNewFish));
                myDict.put(6,(myDict.get(6)).add(numNewFish));
            }
        }

        for (int i = 0; i < 9; i++) {
            numFish = numFish.add(myDict.get(i));
        }

        return numFish;
    }
}
