package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {
        /*
        cd /Users/nikitafilippov/Desktop/Java/PreReqChecker
        javac -d bin src/prereqchecker/*.java
        java -cp bin prereqchecker.Eligible adjlist.in eligible.in eligible.out
        */
        String str = "";
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            str = args[1];
            StdOut.setFile(args[2]);
        }
        //StdIn.setFile("adjlist.in");
        //StdOut.setFile("eligible.out");
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
        StdIn.readLine();
        ArrayList<String> coursesTaken = new ArrayList<>();
        while(StdIn.hasNextLine()){
            coursesTaken.add(StdIn.readLine());
        }
        ModifiedHashMap hm = new ModifiedHashMap(adjList);
        ArrayList<String> el = hm.eligible(coursesTaken);
        for(int i = 0; i < el.size() - 1; i++){
            StdOut.println(el.get(i));
        }
        StdOut.print(el.get(el.size() - 1));
    }
}
