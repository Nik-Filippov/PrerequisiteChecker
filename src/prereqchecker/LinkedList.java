package prereqchecker;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Custom Linked List structure
 */

public class LinkedList {
   
    private Node head;

    public class Node {
        Object data;
        Node next;
        Node(Object b)
        {
            data = b;
            next = null;
        }
    }
   
    public Node getHead(){
        return head;
    }

    public void add(Object data)
    {
        Node new_node = new Node(data);
        new_node.next = null;
   
        if (head == null) {
            head = new_node;
        }
        else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = new_node;
        }
    }
   
    public String toString()
    {
        if(head == null){
            return "";
        }
        Node currNode = head;
        String out = "";
        while (currNode != null) {
            out += (currNode.data + " ");
            currNode = currNode.next;
        }
        return out.substring(0, out.length() - 1);
    }
}