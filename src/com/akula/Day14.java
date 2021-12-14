package com.akula;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

/**
 * created by Sirisha on 12/13/2021 at 1:01 AM
 */
public class Day14 {
    public Day14() {}

    public int countNumOccurences(String line, String word) {
        int count = 0;
        int startIndx = 0;
        while(startIndx < line.length()-1 && line.indexOf(word,startIndx+1) > 0) {
            count++;
            startIndx+=1;
        }
        return count;
    }

    public long Func1(String filename, int steps) {
        long retval = 0;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        String template = "";
        HashMap<String,String> rules = new HashMap<>();
        Hashtable<String,Integer> allElements = new Hashtable<>();

        HashMap<String, Long> polymerPairs = new HashMap<>();
        template = allLines.get(0);
        for(int i = 0; i < template.length()-1;i++) {
            polymerPairs.putIfAbsent(template.substring(i,i+2),(long)1);
        }

        for(int i = 2; i < allLines.size(); i++) {
            rules.put((allLines.get(i).split(" -> "))[0],(allLines.get(i).split(" -> "))[1]);
        }

        for(int loop = 0; loop < steps; loop++) {
            HashMap<String,Long> newPairs = new HashMap<>();
            //apply the rules to each polymer pair
            for(String pair : polymerPairs.keySet()) {
                String newPair1 = pair.substring(0,1)+rules.get(pair);
                String newPair2 = rules.get(pair)+pair.substring(1,2);
                newPairs.merge(newPair1,polymerPairs.get(pair), (a, b) -> (a+b));
                newPairs.merge(newPair2,polymerPairs.get(pair), (a, b) -> (a+b));
            }
            //Merge the newPairs into the Polymer pairs
            polymerPairs = newPairs;
        }

        //Find the max and min count of characters
        HashMap<String, Long> counters = new HashMap<>();
        for(String key : polymerPairs.keySet()) {
            counters.merge(key.substring(0,1), polymerPairs.get(key), (a,b) -> (a+b));
            counters.merge(key.substring(1,2), polymerPairs.get(key), (a,b) -> (a+b));
        }

        long max = 0;
        long min = (long)1000 * 1000 * 1000 * 1000 * 100;
        for(String key : counters.keySet()) {
            max = Math.max(counters.get(key),max);
            min = Math.min(counters.get(key),min);
        }

        System.out.println("Max is "+max);
        System.out.println("Min is "+min);


        return ((max-min)/2)+1;
    }

    public BigInteger Func2(String filename, int steps) {
        BigInteger retval = BigInteger.ZERO;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        String template = "";
        Hashtable<String,String> rules = new Hashtable<>();
        Hashtable<String,Integer> allElements = new Hashtable<>();

        //Initialize the allElements HashTable
        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            allElements.put(String.valueOf(alphabet),0);
        }

        //First line is the template
        int indx=0;
        for(String line : allLines) {
            if(indx == 0)
                template = line;
            else {
                if(line.length() > 0) {
                    //Pair insertion rule
                    rules.put((line.split("->"))[0].trim(),(line.split("->"))[1].trim());
                }
            }
            indx++;
        }

        for(int loop = 0; loop < steps; loop++) {
            Hashtable<Integer, String> insertStrings = new Hashtable<>();
            Set<String> setOfKeys = rules.keySet();
            for (String ruleKey : setOfKeys) {
                int start = 0;
                while (template.indexOf(ruleKey, start) >= 0) {
                    int indx1 = template.indexOf(ruleKey,start);
                    insertStrings.put(indx1, rules.get(ruleKey));
                    start = indx1+1;
                    String a;
                }
            }

            //Went through all rules once. Now generate the new template
            StringBuffer sb = new StringBuffer();
            int counter = 0;
            for (char c : template.toCharArray()) {
                sb.append(String.valueOf(c));
                if (insertStrings.containsKey(counter))
                    sb.append(insertStrings.get(counter));
                counter++;
            }

            template = sb.toString();
            //System.out.println(" After step "+(loop+1)+": "+template);
            System.out.println("After step "+(loop+1)+": length is "+template.length());
            if((loop+1) == 22)
                System.out.println("HERE");
        }

        Hashtable<String, BigInteger> countAlphabets = new Hashtable<>();
        int i = 0;
        for(char c : template.toCharArray()) {
            String s = String.valueOf(c);
            if(countAlphabets.containsKey(s)) {
                countAlphabets.put(s,countAlphabets.get(s).add(BigInteger.ONE));
            } else {
                countAlphabets.put(s,BigInteger.ONE);
            }
        }

        BigInteger mostCommon = BigInteger.valueOf(-1);
        BigInteger leastCommon = BigInteger.valueOf(-1);
        Set<String> setOfKeys = countAlphabets.keySet();
        for(String key : setOfKeys) {
            BigInteger count = countAlphabets.get(key);
            if(leastCommon.equals(BigInteger.valueOf(-1))) leastCommon = count;
            if(mostCommon.equals(BigInteger.valueOf(-1))) mostCommon = count;

            if(count.compareTo(mostCommon) == 1)
                mostCommon = count;

            if(count.compareTo(leastCommon) == -1)
                leastCommon = count;
        }

        retval = mostCommon.subtract(leastCommon);

        return retval;
    }

    public String getPolymer(String template,Hashtable<String,String> rules, int steps) {

        for(int loop = 0; loop < steps; loop++) {
            Hashtable<Integer, String> insertStrings = new Hashtable<>();
            Set<String> setOfKeys = rules.keySet();
            for (String ruleKey : setOfKeys) {
                int start = 0;
                while (template.indexOf(ruleKey, start) >= 0) {
                    int indx1 = template.indexOf(ruleKey,start);
                    insertStrings.put(indx1, rules.get(ruleKey));
                    start = indx1+1;
                }
            }

            //Went through all rules once. Now generate the new template
            StringBuffer sb = new StringBuffer();
            int counter = 0;
            for (char c : template.toCharArray()) {
                sb.append(String.valueOf(c));
                if (insertStrings.containsKey(counter))
                    sb.append(insertStrings.get(counter));
                counter++;
            }

            template = sb.toString();
            //System.out.println(" After step "+(loop+1)+": "+template);
        }
        return template;
    }


    public BigInteger Func3(String filename, int steps) {

        BigInteger retval = BigInteger.ZERO;
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        String template = "";
        Hashtable<String,String> rules = new Hashtable<>();
        Hashtable<String,Integer> allElements = new Hashtable<>();

        //Initialize the allElements HashTable
        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            allElements.put(String.valueOf(alphabet),0);
        }

        //First line is the template
        int indx=0;
        for(String line : allLines) {
            if(indx == 0)
                template = line;
            else {
                if(line.length() > 0) {
                    //Pair insertion rule
                    rules.put((line.split("->"))[0].trim(),(line.split("->"))[1].trim());
                }
            }
            indx++;
        }

        //Run half the steps
        int halfsteps = steps/2;
        if(steps%2 != 0) {
            halfsteps++;
        }

        template = getPolymer(template, rules, halfsteps);
        //Split the template into two strings

        //Default assume template is odd length
        String template1 = template.substring(0,template.length()/2+1);
        String template2 = template.substring(template.length()/2,template.length());
        String template3 = template.substring(template.length()/2,(template.length()/2)+1);

        template1 = getPolymer(template1,rules,(steps-halfsteps));
        template2 = getPolymer(template2, rules, (steps=halfsteps));
        if(template.length()%2 != 0)
            template3 = getPolymer(template3, rules, (steps-halfsteps));
        else
            template3 = "";

        template = template1+template3+template2;
        System.out.println("After step "+steps+": length is "+template.length());

        Hashtable<String, BigInteger> countAlphabets = new Hashtable<>();
        int i = 0;
        for(char c : template.toCharArray()) {
            String s = String.valueOf(c);
            if(countAlphabets.containsKey(s)) {
                countAlphabets.put(s,countAlphabets.get(s).add(BigInteger.ONE));
            } else {
                countAlphabets.put(s,BigInteger.ONE);
            }
        }

        BigInteger mostCommon = BigInteger.valueOf(-1);
        BigInteger leastCommon = BigInteger.valueOf(-1);
        Set<String> setOfKeys = countAlphabets.keySet();
        for(String key : setOfKeys) {
            BigInteger count = countAlphabets.get(key);
            if(leastCommon.equals(BigInteger.valueOf(-1))) leastCommon = count;
            if(mostCommon.equals(BigInteger.valueOf(-1))) mostCommon = count;

            if(count.compareTo(mostCommon) == 1)
                mostCommon = count;

            if(count.compareTo(leastCommon) == -1)
                leastCommon = count;
        }

        retval = mostCommon.subtract(leastCommon);

        return retval;
    }
}
