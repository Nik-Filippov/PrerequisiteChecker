package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;

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
        /*
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            //return;
        }
        else{
            StdIn.setFile(args[0]);
            StdOut.setFile(args[1]);
        }
        */
        StdIn.setFile("adjlist.in");
        StdOut.setFile("adjlist.out");
        HashMap<String, ArrayList<String>> adjList = new HashMap<>();
        int numEntries = Integer.parseInt(StdIn.readLine());
        boolean isPrereq = false;
        while(StdIn.hasNextLine()){
            String input = StdIn.readLine();
            if(Character.isDigit(input.charAt(0))){
                isPrereq = true;
                continue;
            }
            if(!isPrereq){
                adjList.put(input, new ArrayList<>());
            }
            else{
                String curCourse = input.substring(0, input.indexOf(" "));
                String prereqCourse = input.substring(input.indexOf(" ") + 1);
                for(String key : adjList.keySet()){
                    if(curCourse.equals(key)){
                        adjList.get(key).add(prereqCourse);
                        break;
                    }
                }
            }
        }
        int counter = 0;
        for(String key : adjList.keySet()){
            counter++;
            String prereq = key + " ";
            if(!adjList.get(key).isEmpty()){
                for(int i = 0; i < adjList.get(key).size(); i++){
                    prereq += adjList.get(key).get(i) + " ";
                }
            }
            prereq = prereq.substring(0, prereq.length() - 1);
            if(counter == numEntries){
                StdOut.print(prereq);
            }
            else{
                StdOut.println(prereq);
            }
        }
    }
}
