package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.PriorityBlockingQueue;

/*
We want to find if course 1 is a prerequisite to course 2. If this is true, setting course 2 to be a prerequisite for course 1 
will create a loop that is impossible to enter. This would be an invalid graph. We do this by utilizing depth first recursive 
searching starting at course 2, with the termination condition being if there are no more prerequisites(we are at the beginning 
of the courses). For every prerequisite(both direct and indirect) of course 2, we want to mark it as checked(you can do this 
with a HashMap) and add it to a list storing all the prerequisites of course 2. After that, we can check this list of course 2's 
prerequisites, and if we find course 1, we can return "NO". Else,  return "YES".
*/

public class ModifiedHashMap {
   
    private HashMap<String, ArrayList<String>> adjList;
    private HashSet<String> allPrereq = new HashSet<>();

    public ModifiedHashMap(HashMap<String, ArrayList<String>> adjList) {
        this.adjList = adjList;
    }
    //course1 - Advanced course
    //course2 - Prereq course
    public String isValid(String course1, String course2) {

        addAllPrereq(course1);
        if (!allPrereq.contains(course2) && !allPrereq.isEmpty())
            return "YES"; 
        else 
            return "NO";
    }

    private ArrayList<String> findAllPrereq(ArrayList<String> coursesTaken){
        ArrayList<String> taken = new ArrayList<>();
        for(int i = 0; i < coursesTaken.size(); i++){
            if(!taken.contains(coursesTaken.get(i))){
                taken.add(i, coursesTaken.get(i));
                taken.set(i, taken.get(i).replaceAll(" ", ""));
            }
            addAllPrereq(coursesTaken.get(i));
            for(String key : allPrereq){
                if(!taken.contains(key)){
                    taken.add(key);
                }
            }
            allPrereq.clear();
        }
        return taken;
    }

    public void addAllPrereq(String course2){
        course2 = course2.replaceAll(" ", "");
        for(String key : adjList.keySet()){
            if(course2.equals(key)){
                for(int i = 0; i < adjList.get(key).size(); i++){
                    allPrereq.add(adjList.get(key).get(i).replaceAll(" ", ""));
                }
                if(adjList.get(key).size() != 0){
                    for(int i = 0; i < adjList.get(key).size(); i++){
                        addAllPrereq(adjList.get(key).get(i));
                    }
                }
                break;
            }
        }
    }

    public ArrayList<String> eligible(ArrayList<String> coursesTaken){
        ArrayList<String> taken = findAllPrereq(coursesTaken);
        ArrayList<String> coursesEligible = new ArrayList<>();
        for(String key : adjList.keySet()){
            addAllPrereq(key);
            if(taken.containsAll(allPrereq) && !taken.contains(key)){
                coursesEligible.add(key);
            }
            allPrereq.clear();
        }
        return coursesEligible;
    }

    public ArrayList<String> needToTake(String targetCourse, ArrayList<String> coursesTaken){
        ArrayList<String> taken = findAllPrereq(coursesTaken);
        ArrayList<String> needed = new ArrayList<>();
        ArrayList<String> required = findAllPrereq(adjList.get(targetCourse));
        for(int i = 0; i < required.size(); i++){
            if(!taken.contains(required.get(i))){
                needed.add(required.get(i));
            }
        }
        return needed;
    }
}