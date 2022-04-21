package prereqchecker;

import java.nio.channels.NonReadableChannelException;
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
    private ArrayList<String> temp = new ArrayList<>();
    private ArrayList<String> orderedNeed = new ArrayList<>();

    public ModifiedHashMap(HashMap<String, ArrayList<String>> adjList) {
        this.adjList = adjList;
    }

    //course1 - Advanced course
    //course2 - Prereq course
    //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
    //Set course 2 as a prerequizite for course 1
    public String isValid(String course1, String course2) {
        addAllPrereq(course2);
        if(allPrereq.isEmpty()){
            return "YES";
        }
        if (!allPrereq.contains(course1))
            return "YES"; 
        else 
            return "NO";
    }

    private ArrayList<String> findAllPrereq(ArrayList<String> coursesTaken){
        ArrayList<String> taken = new ArrayList<>();
        for(int i = 0; i < coursesTaken.size(); i++){
            if(!taken.contains(coursesTaken.get(i))){
                taken.add(i, coursesTaken.get(i));
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

    public ArrayList<ArrayList<String>> schedule(String target, ArrayList<String> taken){
        ArrayList<ArrayList<String>> semesters = new ArrayList();
        ArrayList<String> need = needToTake(target, taken);
        need.addAll(taken);
        need.add(target);
        orderCourses(target, need);
        orderedNeed.remove(target);
        orderedNeed.removeAll(taken);
        int counter = 0; 
        while(!orderedNeed.isEmpty()){
            semesters.add(counter, new ArrayList<>());
            for(int i = 0; i < orderedNeed.size(); i++){
                String cur = orderedNeed.get(i);
                //If course 1 is a prerequisite to course 2, print "NO", otherwise "YES"
                if(!semesters.get(counter).isEmpty()){
                    boolean canAdd = true;
                    for(int j = 0; j < semesters.get(counter).size(); j++){
                        if(adjList.get(cur).contains(semesters.get(counter).get(j))){
                            canAdd = false;
                        }
                    }
                    if(!canAdd){
                        i = orderedNeed.size();
                        break;
                    }
                    else{
                        semesters.get(counter).add(cur);
                        break;
                    }
                }
                else{
                    semesters.get(counter).add(cur);
                }
            }
            orderedNeed.removeAll(semesters.get(counter));
            counter++;
        }
        return semesters;
    }

    public void orderCourses(String target, ArrayList<String> need){
        while(!orderedNeed.contains(target)){
            for(String key : eligible(temp)){
                if(need.contains(key) && !orderedNeed.contains(key)){
                    orderedNeed.add(key);
                    temp.add(key);
                }
            }
        }
    }
}