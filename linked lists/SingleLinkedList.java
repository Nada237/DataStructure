import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface ILinkedList {

    public void addToIndex(int index, Object element);

    public void add(Object element);

    public Object get(int index);

    public void set(int index, Object element);

    public void clear();

    public boolean isEmpty();

    public void remove(int index);

    public int size();

    public ILinkedList sublist(int fromIndex, int toIndex);

    public boolean contains(Object o);
}


public class SingleLinkedList implements ILinkedList {
    public class Node {
        Object element;
        Node next;
        public Node (Object E){ //A constructor for the Node class to initialize the objects
            element=E;
            next=null;
        }
    }
    public Node head = null;
    public Node tail = null;
    public int size =0;

    public void LPrint()
    {
        System.out.print("[");
        Node p=head;
        for(int i = 0; i < size; i++)
        {
            System.out.print(p.element);
            p=p.next;
            if(i < size - 1)
                System.out.print(", ");
        }
        System.out.print("]");
    }

    public void add(Object element) {
        Node newNode = new Node(element);
        if(head==null)//checking if the list is empty
        {
            head=newNode;
            tail=newNode;
            size++;
        }
        else
        {
            //entering the newNode at the end of the sequence
            tail.next=newNode;
            //now the newNode is the last node so the tail will point to it
            tail=newNode;
            size++;
        }
    }


    public void addToIndex(int index, Object element) {
        Node newNode = new Node(element);
        Node p = head;
        if(index==0){
            newNode.next = p;
            head=newNode;
            size++;}
        else{
        int i = 0;
        while (i != index - 1) {
            p = p.next;
            i++;
        }
        newNode.next = p.next;
        p.next = newNode;
        size++;}
    }


    public Object get(int index) {
        Node p=head;
        int i = 0;
        while (i != index) {
            p = p.next;
            i++;
        }
        return p.element;

    }

    @Override
    public void set(int index, Object element) {
        Node p = head;
        int i = 0;
        while (i != index) {
            p = p.next;
            i++;
        }
        p.element = element;

    }


    public void clear() {
        Node p=head;
        int iteration=size;
        for (int i=0;i<iteration;i++)
        {
            head=head.next;
            p.next=null;
            p=head;
            size--;
        }
    }


    public boolean isEmpty() {
        if (head==null)
            return true;
        else return false;
    }


    public void remove(int index) {
        Node p = head;
        Node q = p.next;
        if(p==null)
            System.out.print("Error");
        else{
            if (index == 0) {
                head = q; // Change head
                p.next = null;
                size--;
            }
            else{
                int i = 0;
                while (i != index - 1) {
                    p = p.next;
                    q = p.next;
                    i++;
                }
                p.next = q.next;
                q.next = null;
                size--;}}
    }


    public int size() {
        return size;
    }


    public ILinkedList sublist(int fromIndex, int toIndex) {
        System.out.print("[");
        Node p=head;
        int i = 0;
        while (i != fromIndex) {
            p = p.next;
            i++;
        }
        for(i =fromIndex ; i <= toIndex; i++) {
            System.out.print(p.element);
            p=p.next;
            if(i != toIndex)
                System.out.print(", ");
        }
        System.out.print("]");
        return null;
    }


    public boolean contains(Object o) {
        Node p = head;
        while (p!=null ) {
            if(o.equals(p.element))
                return true;
            else p = p.next;
        }
        return false;
    }

    public static void main(String[] args) {
        try{
            SingleLinkedList myObj=new SingleLinkedList();
            Scanner sc = new Scanner(System.in);
            String sin = sc.nextLine().replaceAll("\\[|\\]", "");//getting the list elements
            String[] s = sin.split(", ");
            if(!(s[0].isEmpty())){
                for(int i=0;i<s.length;i++)//inserting the list elements
                    myObj.add((Object)s[i]);}

            String st= sc.nextLine(); //get the keyword from the user
            Object element;
            int index;
            switch(st) {//each case here represents a keyword according to it a specific method is called
                case "add":
                    element=sc.nextInt();//taking integer input to the element value
                    myObj.add(element);
                    myObj.LPrint();//printing the list elements
                    break;
                case "addToIndex":
                    index=sc.nextInt();//getting the index of the element
                    element=sc.nextInt();
                    if(index>myObj.size-1||index<0)
                        System.out.print("Error");
                    else {
                        myObj.addToIndex(index,element);
                        myObj.LPrint();//printing the list elements
                    }
                    break;
                case "get":
                    index=sc.nextInt();
                    if(index>myObj.size-1||index<0)
                        System.out.print("Error");
                    else System.out.print(myObj.get(index));
                    break;
                case "set":
                    index=sc.nextInt();//getting the index of the element
                    element=sc.nextInt();
                    if(index>myObj.size-1||index<0)
                        System.out.print("Error");
                    else {
                        myObj.set(index,element);
                        myObj.LPrint();//printing the list elements
                    }
                    break;
                case "clear":
                    myObj.clear();
                    myObj.LPrint();//printing the list elements
                    break;
                case "isEmpty":
                    if (myObj.isEmpty())
                        System.out.print("True");
                    else System.out.print("False");
                    break;
                case "remove":
                    index=sc.nextInt();
                    if(index>myObj.size-1||index<0)
                        System.out.print("Error");
                    else {
                        myObj.remove(index);
                        myObj.LPrint();//printing the list elements
                    }
                    break;
                case "size":
                    System.out.print(myObj.size());
                    break;
                case "sublist":
                    int fromIndex=sc.nextInt();
                    int toIndex=sc.nextInt();
                    if(fromIndex>myObj.size-1||toIndex>myObj.size-1||fromIndex<0||toIndex<0||fromIndex>toIndex)
                        System.out.print("Error");
                    else myObj.sublist(fromIndex,toIndex);
                    break;
                case "contains":
                    element=sc.next();
                    if (myObj.contains(element))
                        System.out.print("True");
                    else System.out.print("False");
                    break;
            }
        }catch (Exception e)
        {System.out.print("Error");}
    }
}