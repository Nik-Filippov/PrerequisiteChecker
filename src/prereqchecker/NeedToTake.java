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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {
        String str = "";
        /*
        cd /Users/nikitafilippov/Desktop/Java/PreReqChecker
        javac -d bin src/prereqchecker/*.java
        java -cp bin prereqchecker.NeedToTake adjlist.in needtotake.in needtotake.out
        */
        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            str = args[1];
            StdOut.setFile(args[2]);
        }
        //StdIn.setFile("adjlist.in");
        //StdOut.setFile("needtotake.out");
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
        StdIn.setFile(str);
        String targetCourse = StdIn.readLine();
        int count = Integer.parseInt(StdIn.readLine());
        ArrayList<String> coursesTaken = new ArrayList<>();
        for(int i = 0; i < count; i++){
            coursesTaken.add(StdIn.readLine());
        }
        ModifiedHashMap hm = new ModifiedHashMap(adjList);
        ArrayList<String> need = hm.needToTake(targetCourse, coursesTaken);
        //MIGHT RESULT AN ERROR
        for(int i = 0; i < need.size() - 1; i++){
            StdOut.println(need.get(i));
        }
        StdOut.print(need.get(need.size() - 1));
    }
}
