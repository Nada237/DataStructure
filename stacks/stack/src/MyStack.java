import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
interface IStack {
    public Object pop();
    public Object peek();
    public void push(Object element);
    public boolean isEmpty();
    public int size();
}
interface ILinkedList {

    public void addToIndex(int index, Object element);
    public Object get(int index);
    public boolean isEmpty();
    public void remove(int index);
    public int size();

}

/**
 * this is MyStack class which is implementated by Single linked lists
 */
public class MyStack implements IStack{
    SingleLinkedList list=new SingleLinkedList();

    /**
     * here the create method is used to push the given sequence that is already stored in the stack
     * before applying operations
     * this is done by using the method addToIndex from the Single linked list class
     * at index zero where the top of the stack exists
     * @param element
     */
    public void creat(Object element) {
        list.addToIndex(0,element);
    }

    /**
     * here the push method which used also to add elements at the top of the stack
     * by using the method addToIndex
     * @param element
     */
    @Override
    public void push(Object element) {
        list.addToIndex(0,element);
        list.LPrint();
    }
    /**
     * here pop method is used to return the last element pushed and remove it from the stack
     * and this is done by using the remove method from the Single linked list class
     * at index zero
     * @return
     */
    @Override
    public Object pop() {
        Object p;
        p=list.head.element;
        list.remove(0);
        list.LPrint();
        return p;
    }

    /**
     * here peek method which returns the element at the top of the stack but without removing it
     * and it was implemented by the 'get' method from the Single linked list class
     * at index zero
     * @return
     */
    @Override
    public Object peek() {
        return list.get(0);
    }

    /**
     * here 'isEmpty' method which checks if the stack is empty or not
     * and it was implemented by the 'isEmpty' method from the Single linked list class
     * @return
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * here 'size' method that returns number of elements in the stack
     * and this done by using 'size' method from the Single linked list class
     * @return
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * here 'main' method which get the previous stored elements in the stack and then
     * get the key word of the operation that the user want to apply
     * and get the parameters of this operation if exist
     * and finally prints the output after applying the chosen operation
     * @param args
     */
    public static void main(String[] args) {
        try{
        MyStack myObj = new MyStack();
        Scanner sc = new Scanner(System.in);
        String sin = sc.nextLine().replaceAll("\\[|\\]", "");//getting the list elements
        String[] s = sin.split(", ");
        if (!(s[0].isEmpty())) {
            for (int i = s.length-1; i >=0; i--)//inserting the list elements
                myObj.creat((Object) s[i]);
        }

        String st = sc.nextLine(); //get the keyword from the user
        Object element;
        switch (st) {//each case here represents a keyword according to it a specific method is called
            case "pop":
                myObj.pop();
                break;
            case "peek":
                System.out.print(myObj.peek());
                break;
            case "push":
                element = sc.nextInt();//taking integer input to the element value
                myObj.push(element);
                break;

            case "isEmpty":
                if (myObj.isEmpty())
                    System.out.print("True");
                else System.out.print("False");
                break;
            case "size":
                System.out.print(myObj.size());
                break;
        }
    } catch (Exception e) {
            System.out.print("Error");
        }
    }

}

class SingleLinkedList implements ILinkedList {
    public class Node {
        Object element;
        Node next;

        public Node(Object E) { //A constructor for the Node class to initialize the objects
            element = E;
            next = null;
        }
    }

    public Node head = null;
    public Node tail = null;
    public int size = 0;

    public void LPrint() {
        System.out.print("[");
        Node p = head;
        for (int i = 0; i < size; i++) {
            System.out.print(p.element);
            p = p.next;
            if (i < size - 1)
                System.out.print(", ");
        }
        System.out.print("]");
    }
    public void addToIndex(int index, Object element) {
        Node newNode = new Node(element);
        Node p = head;
        if (index == 0) {
            newNode.next = p;
            head = newNode;
            size++;
        } else {
            int i = 0;
            while (i != index - 1) {
                p = p.next;
                i++;
            }
            newNode.next = p.next;
            p.next = newNode;
            size++;
        }
    }


    public Object get(int index) {
        Node p = head;
        int i = 0;
        while (i != index) {
            p = p.next;
            i++;
        }
        return p.element;

    }

    public boolean isEmpty() {
        if (head == null)
            return true;
        else return false;
    }


    public void remove(int index) {
        Node p = head;
        Node q = p.next;
        if (p == null)
            System.out.print("Error");
        else {
            if (index == 0) {
                head = q; // Change head
                p.next = null;
                size--;
            } else {
                int i = 0;
                while (i != index - 1) {
                    p = p.next;
                    q = p.next;
                    i++;
                }
                p.next = q.next;
                q.next = null;
                size--;
            }
        }
    }


    public int size() {
        return size;
    }

}