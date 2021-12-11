package com.akula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

/**
 * created by Sirisha on 12/7/2021 at 12:04 AM
 */
public class Day8 {
    public Day8() {}

    public int day8Func1(String filename) {
        int retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<String> digits = new ArrayList<>();

        for (String s : allLines) {
            String outputValues = (s.substring(s.indexOf("|")+1,s.length())).trim();
            String[] outputDigits = outputValues.split(" ");
            for (String val : outputDigits) {
                if (val.length() == 2 || val.length() == 4 || val.length() == 3 || val.length()==7)
                    retval++;
            }
        }
        return retval;
    }

    public int day8Func2(String filename) {
        int retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        //Dictionary to hold strings as keys and digits as values
        Hashtable<String,Integer> myDict = new Hashtable<String,Integer>();
        Hashtable<Integer,String> reverseDict = new Hashtable<>();


        for (String s : allLines) {
            //Reset the dictionaries
            myDict.clear();
            reverseDict.clear();
            String inputValues = (s.substring(0,s.indexOf("|")-1)).trim();
            String outputValues = (s.substring(s.indexOf("|")+1,s.length())).trim();

            //Read all input values into the dictionary. Initialize them to -1, if they are not a unique length string
            for(String inputDigits : inputValues.split(" ")) {
                //Sort the inputDigits alphabetically
                char[] chars = inputDigits.toCharArray();
                Arrays.sort(chars);
                inputDigits = new String(chars);
                switch(inputDigits.length()) {
                    case 2 :
                        myDict.put(inputDigits,1);
                        reverseDict.put(1,inputDigits);
                        break;
                    case 3 :
                        myDict.put(inputDigits,7);
                        reverseDict.put(7,inputDigits);
                        break;
                    case 4:
                        myDict.put(inputDigits,4);
                        reverseDict.put(4,inputDigits);
                        break;
                    case 7:
                        myDict.put(inputDigits,8);
                        reverseDict.put(8,inputDigits);
                        break;
                    default:
                        myDict.put(inputDigits,-1);
                        break;
                }
            }

            //Start the analysis
            //Step 1. Get the string for 1
            String one = reverseDict.get(1);

            //Step 2. 3 digits have 5 segments - 2,3,5. 3 will contain both segments that are in 1. 2 and 5 will miss one of them
            String firstSegmentOfOne = one.substring(0,1);
            String secondSegmentOfOne = one.substring(1,2);
            Set<String> setOfStringKeys = myDict.keySet();
            for(String key : setOfStringKeys) {
                if(key.length() == 5 && key.contains(firstSegmentOfOne) && key.contains(secondSegmentOfOne)) {
                    //Found the string for digit 3
                    myDict.put(key,3);
                    reverseDict.put(3,key);
                    break;
                }
            }

            //Step 3. Which segment is present in 3, but missing in 4
            String three = reverseDict.get(3);
            String four = reverseDict.get(4);
            String upperLeftSegment = "";
            for(char c : four.toCharArray()) {
                if(!three.contains(String.valueOf(c))) {
                    upperLeftSegment=String.valueOf(c);
                    break;
                }
            }

            //Step 4. 5 should contain the upper left segment
            for(String key: setOfStringKeys) {
                if(key.length() == 5) {
                    if(key.contains(upperLeftSegment) && myDict.get(key) == -1) {
                        //Found digit 5
                        myDict.put(key, 5);
                        reverseDict.put(5,key);
                    }
                    else if(!key.equals(three)){
                        myDict.put(key, 2);
                        reverseDict.put(2, key);
                    }
                }
            }


            //Step 5. 6 will contain only 1 segment from 1. 9 and 0 will contain both segments
            for(String key: setOfStringKeys) {
                if(key.length() == 6) {
                    if((key.contains(firstSegmentOfOne) && !key.contains(secondSegmentOfOne)) ||
                            (!key.contains(firstSegmentOfOne) && key.contains(secondSegmentOfOne))) {
                        //Found digit 6
                        myDict.put(key, 6);
                        reverseDict.put(6,key);
                        break;
                    }
                }
            }

            //Step 6. Find the lower left segment. It is present in two, but not in three
            String two = reverseDict.get(2);
            String lowerLeftSegment = "";
            for(char c : two.toCharArray()) {
                if(!three.contains(String.valueOf(c))) {
                    lowerLeftSegment=String.valueOf(c);
                    break;
                }
            }

            //Step 7. Out of the remaining two keys with a value of -1, one is 9 and the other is 0. 9 has the lower left segment missing
            for(String key: setOfStringKeys) {
                if(myDict.get(key) == -1) {
                    if(key.contains(lowerLeftSegment)) {
                        myDict.put(key,0);
                        reverseDict.put(0,key);
                    } else {
                        myDict.put(key,9);
                        reverseDict.put(9,key);
                    }
                }
            }

            //We have the whole dictionary now. Get the values for each of the output digits. Sort the output digit alphabetically, before looking up in the dictionary
            StringBuffer sb = new StringBuffer("");
            for(String outputDigits : outputValues.split(" ")) {
                char[] chars = outputDigits.toCharArray();
                Arrays.sort(chars);
                outputDigits = new String(chars);
                sb.append(myDict.get(outputDigits));
            }
            retval+=Integer.parseInt(sb.toString());
            //System.out.println(outputValues+": "+Integer.parseInt(sb.toString()));

        }
        return retval;
    }
}
