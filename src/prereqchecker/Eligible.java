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
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            StdIn.setFile(args[1]);
            StdOut.setFile(args[2]);
        }
        */
        StdIn.setFile("adjlist.in");
        StdOut.setFile("eligible.out");
        HashMap<String, ArrayList<String>> adjList = new  HashMap<>();
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
        StdIn.setFile("eligible.in");
        StdIn.readLine();
        ArrayList<String> coursesTaken = new ArrayList<>();
        while(StdIn.hasNextLine()){
            coursesTaken.add(StdIn.readLine());
        }
        ModifiedHashMap hm = new ModifiedHashMap(adjList);
        ArrayList<String> el = hm.eligible(coursesTaken);
        for(int i = 0; i < el.size(); i++){
            StdOut.println(el.get(i));
        }
    }
}
