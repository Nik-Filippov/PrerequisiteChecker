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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {
        /*
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }
        else{
            StdIn.setFile(args[0]);
            StdIn.setFile(args[1]);
            StdOut.setFile(args[2]);
        }
        */
        StdIn.setFile("adjlist.in");
        StdOut.setFile("scheduleplan.out");
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
                String prereqCourse = input.substring(input.indexOf(" ") + 1);
                for(String key : adjList.keySet()){
                    if(curCourse.equals(key)){
                        adjList.get(key).add(prereqCourse);
                        break;
                    }
                }
            }
        }
        //If course 2 was an immediate prerequisite for course 1, would all courses still be possible to take?
        //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
        //Set course 2 as a prerequizite for course 1
        StdIn.setFile("scheduleplan.in");
        String target = StdIn.readLine();
        StdIn.readLine();
        ArrayList <String> taken = new ArrayList<>();
        while(StdIn.hasNextLine()){
            taken.add(StdIn.readLine());
        }
        ModifiedHashMap hm = new ModifiedHashMap(adjList);
        ArrayList<ArrayList<String>> sc = hm.schedule(target, taken);
        StdOut.println(sc.size());
        for(int i = 0; i < sc.size(); i++){
            String out = "";
            for(int j = 0; j < sc.get(i).size(); j++){
                if(j < sc.get(i).size() - 1){
                    out += sc.get(i).get(j) + " ";
                }
                else{
                    out += sc.get(i).get(j);
                }
            }
            StdOut.println(out);
        }
    }
}
