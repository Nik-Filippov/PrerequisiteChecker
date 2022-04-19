package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    private HashMap<String, Boolean> adjListBool;
    private HashSet allPrereq = new HashSet<>();

    public ModifiedHashMap(HashMap<String, ArrayList<String>> adjList, HashMap<String, Boolean> adjListBool) {
        this.adjList = adjList;
        this.adjListBool = adjListBool;
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

    public void addAllPrereq(String course2){
        course2 = course2.replaceAll(" ", "");
        for(String key : adjList.keySet()){
            if(course2.equals(key)){
                for(int i = 0; i < adjList.get(key).size(); i++){
                    allPrereq.add(adjList.get(key).get(i));
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
}