package prereqchecker;

import java.util.*;

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
        cd /Users/nikitafilippov/Desktop/Java/PreReqChecker
        javac -d bin src/prereqchecker/*.java
        java -cp bin prereqchecker.ValidPrereq adjlist.in validprereq.in validprereq.out
        */
        String str = "";
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            str = args[1];
            StdOut.setFile(args[2]);
        }
        //String str = "validprereq.in";
        //StdIn.setFile("adjlist.in");
        //StdOut.setFile("validprereq.out");
        HashMap<String, ArrayList<String>> adjList = new HashMap<>();
        int numEntries = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < numEntries; i++){
            String input = StdIn.readLine();
            adjList.put(input, new ArrayList<String>());
        }
        int numConnections = Integer.parseInt(StdIn.readLine());
        for(int i = 0; i < numConnections; i++){
            String input = StdIn.readLine();
            String key = input.substring(0,input.indexOf(" "));
            adjList.get(key).add(input.substring(input.indexOf(" ") + 1));
        }
        //If course 2 was an immediate prerequisite for course 1, would all courses still be possible to take?
        //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
        //Set course 2 as a prerequizite for course 1
        StdIn.setFile(str);
        String course1 = StdIn.readLine(); //Advanced course
        String course2 = StdIn.readLine(); //Prereq course
        ModifiedHashMap hm = new ModifiedHashMap(adjList);
        StdOut.print(hm.isValid(course1, course2));
    }
}
