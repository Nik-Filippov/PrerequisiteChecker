package prereqchecker;

import java.util.*;

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
    private HashSet<String> allPrereq = new HashSet<String>();
    private ArrayList<String> temp = new ArrayList<String>();
    private ArrayList<String> orderedNeed = new ArrayList<String>();

    public ModifiedHashMap(HashMap<String, ArrayList<String>> adjList) {
        this.adjList = adjList;
    }

    //course1 - Advanced course
    //course2 - Prereq course
    //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
    //Set course 2 as a prerequizite for course 1
    public String isValid(String course1, String course2) {
        addCoursePrereq(course2);
        if(allPrereq.isEmpty()){
            return "YES";
        }
        if (!allPrereq.contains(course1))
            return "YES"; 
        else 
            return "NO";
    }

    private ArrayList<String> findAllPrereq(ArrayList<String> coursesTaken){
        ArrayList<String> taken = new ArrayList<String>();
        for(int i = 0; i < coursesTaken.size(); i++){
            if(!taken.contains(coursesTaken.get(i))){
                taken.add(i, coursesTaken.get(i));
            }
            addCoursePrereq(coursesTaken.get(i));
            for(String key : allPrereq){
                if(!taken.contains(key)){
                    taken.add(key);
                }
            }
            allPrereq.clear();
        }
        return taken;
    }

    private ArrayList<String> findAllPrereq(String courseTaken){
        ArrayList<String> str = new ArrayList<>();
        str.add(courseTaken);
        ArrayList<String> out = findAllPrereq(str);
        out.remove(courseTaken);
        return out;
    }

    public void addCoursePrereq(String course2){
        for(String key : adjList.keySet()){
            if(course2.equals(key)){
                if(adjList.get(key).size() != 0){
                    for(int i = 0; i < adjList.get(key).size(); i++){
                       if(!allPrereq.contains(adjList.get(key).get(i))){
                            addCoursePrereq(adjList.get(key).get(i));
                        }
                    }
                }               
                for(int i = 0; i < adjList.get(key).size(); i++){
                    allPrereq.add(adjList.get(key).get(i));
                }
                break;
            }
        }
    }

    public ArrayList<String> eligible(ArrayList<String> coursesTaken){
        ArrayList<String> taken = findAllPrereq(coursesTaken);
        ArrayList<String> coursesEligible = new ArrayList<String>();
        for(String key : adjList.keySet()){
            addCoursePrereq(key);
            ArrayList<String> need = findAllPrereq(key);
            if(taken.containsAll(need) && !taken.contains(key)){
                coursesEligible.add(key);
            }
            allPrereq.clear();
        }
        return coursesEligible;
    }

    public ArrayList<String> needToTake(String targetCourse, ArrayList<String> coursesTaken){
        ArrayList<String> taken = findAllPrereq(coursesTaken);
        ArrayList<String> needed = new ArrayList<String>();
        ArrayList<String> required = findAllPrereq(adjList.get(targetCourse));
        for(int i = 0; i < required.size(); i++){
            if(!taken.contains(required.get(i))){
                needed.add(required.get(i));
            }
        }
        return needed;
    }

    public ArrayList<ArrayList<String>> schedule(String target, ArrayList<String> taken){
        ArrayList<ArrayList<String>> semesters = new ArrayList<ArrayList<String>>();
        ArrayList<String> need = needToTake(target, taken);
        ArrayList<String> a = findAllPrereq(taken);
        if(a.contains(target)){
            return semesters;
        }
        ArrayList<String> removeList = new ArrayList<>();
        while(!removeList.containsAll(need)){
            ArrayList<String> cur = new ArrayList<>();
            for(String key : need){
                ArrayList<String> eligibleNow = eligible(taken);
                if(eligibleNow.contains(key) && !removeList.contains(key)){
                    cur.add(key);
                    removeList.add(key);
                }
            }
            taken.addAll(cur);
            if(cur.isEmpty()){
                break;
            }
            semesters.add(cur);
        }
        return semesters;
    }
}