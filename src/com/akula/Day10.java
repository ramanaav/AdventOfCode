package com.akula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * created by Sirisha on 12/9/2021 at 2:08 AM
 */
public class Day10 {

    public Day10() {}

    public int Func1(String filename) {

        int retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<Character> illegalChars = new ArrayList<>();
        Stack<Character> open_stack = new Stack<>();

        for (String line : allLines) {
            open_stack.empty();
            for(char c : line.toCharArray()) {
                //Is this an open character - one of ( , [ , { , <
                if (c== '(' || c == '[' || c == '{' || c == '<') {
                    open_stack.push(new Character(c));
                } else {
                    //This is a close character - one of ), ], }, >.
                    //If this is a close character, then the last open character has to match it
                    if(open_stack.size() == 0) {
                        //There are no open characters, but we have a close character. So this is a corrupted line
                        illegalChars.add(new Character(c));
                        //Done with this line
                        break;
                    } else {
                        Character tmp = open_stack.pop();
                        boolean flag=false;
                        switch(c) {
                            case ')':
                                if(tmp != '(')
                                    flag=true;
                                break;
                            case ']':
                                if(tmp != '[')
                                    flag=true;
                                break;
                            case '}':
                                if(tmp != '{')
                                    flag=true;
                                break;
                            case '>':
                                if(tmp != '<')
                                    flag=true;
                                break;
                        }
                        if(flag) {
                            illegalChars.add(new Character(c));
                            break;
                        }
                    }
                }
            }
        }

        //Find error score for the illegal chars
        for(Character c : illegalChars) {
            switch(c) {
                case ')' :
                    retval+=3;
                    break;
                case ']' :
                    retval+=57;
                    break;
                case '}' :
                    retval+=1197;
                    break;
                case '>' :
                    retval+=25137;
                    break;
            }
        }

        return retval;

    }

    public long Func2(String filename) {

        long retval = 0;

        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<Long> scoresList = new ArrayList<>();
        Stack<Character> open_stack = new Stack<>();

        for (String line : allLines) {
            open_stack.clear();
            boolean incompleteLine = true;
            for(char c : line.toCharArray()) {
                //Is this an open character - one of ( , [ , { , <
                if (c== '(' || c == '[' || c == '{' || c == '<') {
                    open_stack.push(new Character(c));
                } else {
                    //This is a close character - one of ), ], }, >.
                    //If this is a close character, then the last open character has to match it
                    if(open_stack.size() == 0) {
                        //There are no open characters, but we have a close character. So this is a corrupted line.Ignore this line
                        //Done with this line
                        incompleteLine = false; //THis is a corrupted line and not an incomplete line
                        break;
                    } else {
                        Character tmp = open_stack.pop();
                        boolean flag=false;
                        switch(c) {
                            case ')':
                                if(tmp != '(')
                                    flag=true;
                                break;
                            case ']':
                                if(tmp != '[')
                                    flag=true;
                                break;
                            case '}':
                                if(tmp != '{')
                                    flag=true;
                                break;
                            case '>':
                                if(tmp != '<')
                                    flag=true;
                                break;
                        }
                        if(flag) {
                            incompleteLine = false; //This is a corrupted line and not an incomplete line
                            break;
                        }
                    }
                }
            }
            if(incompleteLine) {
                //We need to add the missing
                StringBuffer newLine = new StringBuffer();
                //Start popping out the characters from the open stack and add the corresponding closing characters to the line
                long score = 0;
                while(open_stack.size() > 0) {
                    Character tmp = open_stack.pop();
                    switch(tmp) {
                        case '(' :
                            newLine.append(")");
                            score=score*5+1;
                            break;
                        case '[' :
                            newLine.append("]");
                            score=score*5+2;
                            break;
                        case '{' :
                            newLine.append("}");
                            score=score*5+3;
                            break;
                        case '<' :
                            newLine.append(">");
                            score=score*5+4;
                            break;
                    }
                }
                System.out.println(newLine.toString()+" - "+score);
                scoresList.add(score);
            }
        }

        long[] newList = new long[scoresList.size()];
        int indx = 0;
        for(Long i : scoresList) {
            newList[indx++] = i;
        }
        Arrays.sort(newList);
        retval = newList[newList.length/2];


        return retval;
    }
}
