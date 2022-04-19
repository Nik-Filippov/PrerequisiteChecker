package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import prereqchecker.LinkedList.Node;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {
        /*
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            StdIn.setFile(args[1]);
            StdOut.setFile(args[2]);
        }
        */
        StdIn.setFile("adjlist.in");
        StdOut.setFile("validprereq.out");
        HashMap<String, ArrayList<String>> adjList = new  HashMap<>();
        HashMap<String, Boolean> adjListBool = new  HashMap<>();
        StdIn.readLine();
        boolean isPrereq = false;
        while(StdIn.hasNextLine()){
            String input = StdIn.readLine();
            if(Character.isDigit(input.charAt(0))){
                isPrereq = true;
                continue;
            }
            if(!isPrereq){
                adjList.put(input, new ArrayList<>());
                adjListBool.put(input, false);
            }
            else{
                String curCourse = input.substring(0, input.indexOf(" "));
                String prereqCourse = input.substring(input.indexOf(" "));
                for(String key : adjList.keySet()){
                    if(curCourse.equals(key)){
                        adjList.get(key).add(prereqCourse);
                        break;
                    }
                }
            }
        }
        //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
        StdIn.setFile("validprereq.in");
        String course1 = StdIn.readLine(); //Advanced course
        String course2 = StdIn.readLine(); //Prereq course
        ModifiedHashMap hm = new ModifiedHashMap(adjList, adjListBool);
        StdOut.print(hm.isValid("cs314", "mat250"));
    }
}
