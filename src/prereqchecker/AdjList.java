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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {
        /*
        cd /Users/nikitafilippov/Desktop/Java/PreReqChecker
        javac -d bin src/prereqchecker/*.java
        java -cp bin prereqchecker.AdjList adjlist.in adjlist.out
        */
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            StdOut.setFile(args[1]);
        }
        //StdIn.setFile("adjlist.in");
        //StdOut.setFile("adjlist.out");
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
        int counter = 0;
        for(String key : adjList.keySet()){
            counter++;
            String prereq = "";
            for(int i = 0; i < adjList.get(key).size(); i++){
                prereq += adjList.get(key).get(i);
                if(i != adjList.get(key).size() - 1){
                    prereq += " ";
                }
            }
            if(counter < numEntries){
                StdOut.println(key + " " + prereq);
            }
            else{
                StdOut.print(key + " " + prereq);
            }
        }
    }
}
