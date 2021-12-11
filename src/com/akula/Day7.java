package com.akula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

/**
 * created by Sirisha on 12/6/2021 at 10:47 AM
 */
public class Day7 {
    public Day7() {}

    public long day7HighFrequencyPosition(String filename) {
        long fuelcost= 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        Hashtable<Integer,Integer> allPositions = new Hashtable<>();

        for (String s : allLines) {
            for (String val : s.split(",")) {
                Integer key = new Integer(Integer.parseInt(val));
                if (allPositions.get(key) != null)
                    allPositions.put(key, (allPositions.get(key)) + 1);
                else
                    allPositions.put(key, 1);
            }
        }

        //Method 1: Find the position that is most frequently occuring. If there are more than one positions that occur at the same high frequency, get all of them
        int highFreqPosition = 0;
        int highFreqValue = 0;
        Set<Integer> setOfKeys = allPositions.keySet();
        for(Integer key : setOfKeys) {
            if (allPositions.get(key) > highFreqValue) {
                highFreqPosition = key;
                highFreqValue = allPositions.get(key);
            }
        }

        //Now find the fuel cost for all positions to align to this high frequency position
        for(Integer key : setOfKeys) {
                //Find the fuelcost
                int tmpval = Math.abs(key - highFreqPosition)*allPositions.get(key);
                fuelcost+=Math.abs(key - highFreqPosition)*allPositions.get(key);
                System.out.println("Move from "+key+" to "+highFreqPosition+": "+tmpval+" fuel");
        }

        return fuelcost;
    }

    public int getSum(int val) {
        int retval = 0;
        for (int i = 1; i < val+1; i++)
            retval+=i;
        return retval;
    }

    public long day7AllFuelCostsConstantRate(String filename) {
        long fuelcost= 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        Hashtable<Integer,Integer> allPositions = new Hashtable<>();
        Hashtable<Integer,Integer> allFuelCosts = new Hashtable<>();

        for (String s : allLines) {
            String[] values = s.split(",");
            int indx = 0;
            for (String val : values) {
                Integer key = new Integer(Integer.parseInt(val));
                if (allPositions.get(key) != null)
                    allPositions.put(key, (allPositions.get(key)) + 1);
                else {
                    allPositions.put(key, 1);
                    allFuelCosts.put(key,0);
                }
            }
        }


        //Method 3 : Find the cost of aligning to each position
        Set<Integer> setOfPositionKeys = allPositions.keySet();
        Set<Integer> setOfFuelKeys = allFuelCosts.keySet();
        for(Integer fuelKey : setOfFuelKeys) {
            int tmpfuelcost = 0;
            for(Integer positionKey : setOfPositionKeys) {
                tmpfuelcost+=Math.abs(positionKey - fuelKey)*allPositions.get(positionKey);
            }
            //System.out.println("Cost to align to "+fuelKey+" : "+tmpfuelcost);
            allFuelCosts.put(fuelKey,new Integer(tmpfuelcost));
        }

        //Find the lowest fuel cost and that will be the position to align to
        boolean flag = false;
        for(Integer fuelKey : setOfFuelKeys) {
            if (!flag) {
                //First entry
                fuelcost = allFuelCosts.get(fuelKey);
                flag = true;
            } else {
                if (allFuelCosts.get(fuelKey) < fuelcost)
                    fuelcost = allFuelCosts.get(fuelKey);
            }
        }


        return fuelcost;
    }

    public long day7AllFuelCostsVariableRate1(String filename) {
        long fuelcost= 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        Hashtable<Integer,Integer> allPositions = new Hashtable<>();
        Hashtable<Integer,Integer> allFuelCosts = new Hashtable<>();

        for (String s : allLines) {
            String[] values = s.split(",");
            int indx = 0;
            for (String val : values) {
                Integer key = new Integer(Integer.parseInt(val));
                if (allPositions.get(key) != null)
                    allPositions.put(key, (allPositions.get(key)) + 1);
                else {
                    allPositions.put(key, 1);
                    allFuelCosts.put(key,0);
                }
            }
        }

        //Method 3 : Find the cost of aligning to each position
        Set<Integer> setOfPositionKeys = allPositions.keySet();
        Set<Integer> setOfFuelKeys = allFuelCosts.keySet();
        for(Integer fuelKey : setOfFuelKeys) {
            int tmpfuelcost = 0;
            for(Integer positionKey : setOfPositionKeys) {
                tmpfuelcost+=getSum(Math.abs(positionKey - fuelKey))*allPositions.get(positionKey);
            }
            System.out.println("Cost to align to "+fuelKey+" : "+tmpfuelcost);
            allFuelCosts.put(fuelKey,new Integer(tmpfuelcost));
        }

        //Find the lowest fuel cost and that will be the position to align to
        boolean flag = false;
        for(Integer fuelKey : setOfFuelKeys) {
            if (!flag) {
                //First entry
                fuelcost = allFuelCosts.get(fuelKey);
                flag = true;
            } else {
                if (allFuelCosts.get(fuelKey) < fuelcost)
                    fuelcost = allFuelCosts.get(fuelKey);
            }
        }


        return fuelcost;
    }

    public long day7AllFuelCostsVariableRate(String filename) {
        long fuelcost= 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        Hashtable<Integer,Integer> allPositions = new Hashtable<>();
        Hashtable<Integer,Integer> allFuelCosts = new Hashtable<>();
        int highestPosition = 0;

        for (String s : allLines) {
            String[] values = s.split(",");
            for (String val : values) {
                Integer key = new Integer(Integer.parseInt(val));
                if (key > highestPosition) highestPosition = key;
                if (allPositions.get(key) != null)
                    allPositions.put(key, (allPositions.get(key)) + 1);
                else {
                    allPositions.put(key, 1);
                    allFuelCosts.put(key,0);
                }
            }
        }

        //Fill the gaps
        for (int i = 0; i <= highestPosition; i++) {
            //If there is no entry for this position key, create one and set the value to zero
            if (allPositions.get(i) == null) {
                allPositions.put(i,0);
                allFuelCosts.put(i,0);
            }
        }

        //Method 3 : Find the cost of aligning to each position
        Set<Integer> setOfPositionKeys = allPositions.keySet();
        Set<Integer> setOfFuelKeys = allFuelCosts.keySet();
        for(Integer fuelKey : setOfFuelKeys) {
            int tmpfuelcost = 0;
            for(Integer positionKey : setOfPositionKeys) {
                tmpfuelcost+=getSum(Math.abs(positionKey - fuelKey))*allPositions.get(positionKey);
            }
            System.out.println("Cost to align to "+fuelKey+" : "+tmpfuelcost);
            allFuelCosts.put(fuelKey,new Integer(tmpfuelcost));
        }

        //Find the lowest fuel cost and that will be the position to align to
        boolean flag = false;
        for(Integer fuelKey : setOfFuelKeys) {
            if (!flag) {
                //First entry
                fuelcost = allFuelCosts.get(fuelKey);
                flag = true;
            } else {
                if (allFuelCosts.get(fuelKey) < fuelcost)
                    fuelcost = allFuelCosts.get(fuelKey);
            }
        }


        return fuelcost;
    }
}
