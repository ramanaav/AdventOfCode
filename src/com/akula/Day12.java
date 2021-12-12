package com.akula;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * created by Sirisha on 12/11/2021 at 12:51 AM
 */
public class Day12 {
    Day12() {}
    ArrayList<String> allPaths = new ArrayList<>();

    class Node {
        String label;
        ArrayList<Node> linkedNodes;
        int type = 0; //0 - small , 1 - large, 2 - start or end
        public boolean isUpperCase(String s) {
            char[] charArray = s.toCharArray();
            boolean flag = false;
            for(char c : charArray) {
                if (Character.isUpperCase(c)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        }

        public Node(String s) {
            linkedNodes = new ArrayList<>();
            label = s;
            char[] charArray = label.toCharArray();
            if(label.equalsIgnoreCase("start") || label.equalsIgnoreCase("end"))
                type = 2;
            else {
                if(isUpperCase(label))
                    type = 1; //Large cave
                else
                    type = 0; //small cave
            }
        }

        public void addLink(Node n) {
            linkedNodes.add(n);
        }

        public String getLabel() {return label;}
        public int getType() {return type;}
    }

    // Current path contains the current node name already
    public void traverseNode(Node inputNode, String currentPath, ArrayList<String> traversedSegments) {
        //If this is the end node, then this path is complete
        if(inputNode.getLabel().equalsIgnoreCase("end")) {
            //currentPath=currentPath+"-"+inputNode.getLabel();
            allPaths.add(currentPath);
        } else {
            //Go through each of the linked nodes
            for (Node n : inputNode.linkedNodes) {
                if(inputNode.getLabel().equalsIgnoreCase("start")) {
                    //We are at the start node. So reset the traversed segments
                    traversedSegments.clear();
                    currentPath="start";
                }
                if(!n.getLabel().equalsIgnoreCase("start")) {
                    String tmpStr = inputNode.getLabel() + "-" + n.getLabel();
                    //If this is a small cave and we have already visited this small cave in this path, then no need to proceed any further
                    if (!traversedSegments.contains(tmpStr) && !(n.getType() == 0 && currentPath.contains(n.getLabel()))) {
                        //We have not traversed this segment. So, proceed
                        traversedSegments.add(tmpStr);
                        String backupPath = currentPath;
                        currentPath = currentPath+"-"+n.getLabel();
                        traverseNode(n, currentPath, traversedSegments);
                        //We are done traversing this node. So, remove it from current path
                        currentPath=backupPath;
                        //Remove this segment from list of traversed segments
                        traversedSegments.remove(tmpStr);
                    }
                }
            }
        }
    }

    public int numOccurrences(String word, String mainStr) {
        String a[] = mainStr.split("-");
        int count = 0;
        for(int i =0; i< a.length; i++) {
            if(word.equals(a[i]))
                count++;
        }
        return count;
    }

    public boolean isLowerCase(String s) {
        char[] charArray = s.toCharArray();
        boolean flag = false;
        for(char c : charArray) {
            if (Character.isLowerCase(c)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public String smallCaveVisitedTwice(String currPath) {
        Hashtable<String,Integer> myTable = new Hashtable<String,Integer>();
        String[] a = currPath.split("-");
        String retval = null;
        for(String s : currPath.split("-")) {
            if(isLowerCase(s)) {
                if(myTable.containsKey(s)) {
                    retval=s;
                    break;
                } else
                    myTable.put(s,1);
            }
        }
        return retval;
    }

    public void traverseNode2(Node inputNode, String currentPath, ArrayList<String> traversedSegments) {
        //If this is the end node, then this path is complete
        if(inputNode.getLabel().equalsIgnoreCase("end")) {
            //currentPath=currentPath+"-"+inputNode.getLabel();
            allPaths.add(currentPath);
        } else {
            //Go through each of the linked nodes
            for (Node n : inputNode.linkedNodes) {
                if(inputNode.getLabel().equalsIgnoreCase("start")) {
                    //We are at the start node. So reset the traversed segments
                    traversedSegments.clear();
                    currentPath="start";
                }
                if(!n.getLabel().equalsIgnoreCase("start")) {
                    String tmpStr = inputNode.getLabel() + "-" + n.getLabel();

                    boolean flag = true;

                    if(n.getType()==0) {
                        //If this is a small cave
                        //A single small cave can be visited atmost twice and teh remaining small caves can be visited at most once.
                        //In the current path, have we already visited any small cave twice?
                        //If this is a small cave and we have already visited this small cave in this path, then no need to proceed any further
                        String twiceSmallCave = smallCaveVisitedTwice(currentPath);
                        if(twiceSmallCave != null) {
                            //There is a small cave that has already been visited twice in the current path
                            //So, the current cave can be visited only if it has not already been visited
                            //Count number of occurrences of the small cave in the current path
                            int num = numOccurrences(n.getLabel(),currentPath);
                            if(num > 0)
                                flag = false;
                        }

                    }

                    //if (!traversedSegments.contains(tmpStr) && (n.getType() == 0 && flag)) {
                    if (flag) {
                        //We have not traversed this segment. So, proceed
                        traversedSegments.add(tmpStr);
                        String backupPath = currentPath;
                        currentPath = currentPath+"-"+n.getLabel();
                        traverseNode2(n, currentPath, traversedSegments);
                        //We are done traversing this node. So, remove it from current path
                        currentPath=backupPath;
                        //Remove this segment from list of traversed segments
                        traversedSegments.remove(tmpStr);
                    }
                }
            }
        }
    }

    public int Func1(String filename) {
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<Node> allNodes = new ArrayList<>();
        ArrayList<String> allNodeLabels = new ArrayList<>();
        Node startNode = null;
        Node endNode = null;
        for(String line : allLines) {
            String[] nodeStrs = line.split("-");
            Node n1 = null;
            Node n2 = null;
            if (!allNodeLabels.contains(nodeStrs[0])) {
                n1 = new Node(nodeStrs[0]);
                allNodes.add(n1);
                allNodeLabels.add(nodeStrs[0]);
                if(nodeStrs[0].equalsIgnoreCase("start"))
                    startNode = n1;
                if(nodeStrs[0].equalsIgnoreCase("end"))
                    endNode = n1;
            } else {
                //Get the existing node
                n1 = allNodes.get(allNodeLabels.indexOf(nodeStrs[0]));
            }
            if (!allNodeLabels.contains(nodeStrs[1])) {
                n2 = new Node(nodeStrs[1]);
                allNodes.add(n2);
                allNodeLabels.add(nodeStrs[1]);
                if(nodeStrs[1].equalsIgnoreCase("start"))
                    startNode = n2;
                if(nodeStrs[1].equalsIgnoreCase("end"))
                    endNode = n2;
            } else {
                //Get the existing node
                n2 = allNodes.get(allNodeLabels.indexOf(nodeStrs[1]));
            }

            //Link these two nodes
            n1.addLink(n2);
            n2.addLink(n1);
        }

        String currPath = "";
        ArrayList<String> traversedSegements = new ArrayList<>();
        traverseNode(startNode,currPath,traversedSegements);
        for(String s : allPaths) {
            System.out.println(s);
        }
        return allPaths.size();
    }

    public int Func2(String filename) {
        ArrayList<String> allLines = CommonUtils.readFromFile(filename);
        ArrayList<Node> allNodes = new ArrayList<>();
        ArrayList<String> allNodeLabels = new ArrayList<>();
        Node startNode = null;
        Node endNode = null;
        for(String line : allLines) {
            String[] nodeStrs = line.split("-");
            Node n1 = null;
            Node n2 = null;
            if (!allNodeLabels.contains(nodeStrs[0])) {
                n1 = new Node(nodeStrs[0]);
                allNodes.add(n1);
                allNodeLabels.add(nodeStrs[0]);
                if(nodeStrs[0].equalsIgnoreCase("start"))
                    startNode = n1;
                if(nodeStrs[0].equalsIgnoreCase("end"))
                    endNode = n1;
            } else {
                //Get the existing node
                n1 = allNodes.get(allNodeLabels.indexOf(nodeStrs[0]));
            }
            if (!allNodeLabels.contains(nodeStrs[1])) {
                n2 = new Node(nodeStrs[1]);
                allNodes.add(n2);
                allNodeLabels.add(nodeStrs[1]);
                if(nodeStrs[1].equalsIgnoreCase("start"))
                    startNode = n2;
                if(nodeStrs[1].equalsIgnoreCase("end"))
                    endNode = n2;
            } else {
                //Get the existing node
                n2 = allNodes.get(allNodeLabels.indexOf(nodeStrs[1]));
            }

            //Link these two nodes
            n1.addLink(n2);
            n2.addLink(n1);
        }

        String currPath = "";
        ArrayList<String> traversedSegements = new ArrayList<>();
        traverseNode2(startNode,currPath,traversedSegements);
        for(String s : allPaths) {
            System.out.println(s);
        }
        return allPaths.size();
    }
}
