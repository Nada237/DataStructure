import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IQueue {
    /*** Inserts an item at the queue front.*/
    public void enqueue(Object item);
    /*** Removes the object at the queue rear and returnsit.*/
    public Object dequeue();
    /*** Tests if this queue is empty.*/
    public boolean isEmpty();
    /*** Returns the number of elements in the queue*/
    public int size();
}

public class ArrayQueue implements IQueue {

    private Object[] array;
    private int front;
    private int rear;
    private int size;
    public ArrayQueue(int n) {
        array = new Object[n];
        front = n-1;
        rear = n-1;
        size = 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Read in the initial queue elements
        String[] elements = scanner.nextLine().replaceAll("[\\[|\\]]| ", "").split(",");
        int n=100;
        ArrayQueue queue = new ArrayQueue(n);

        /*if(!(elements.length == 1 && elements[0].isEmpty()))
        {
            for (int i=elements.length-1;i>=0;i--) {
                queue.enqueue(elements[i]);
            }
        }*/

        if(!(elements.length == 1 && elements[0].isEmpty()))
        {
            for (String element : elements) {
                queue.enqueue(Integer.parseInt(element.trim()));
            }
        }

        // Read in the required operation
        String operation = scanner.nextLine();
        try {
            switch (operation) {
                case "enqueue":
                    // Read in the value to enqueue
                    int value = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print(queue.front + "  "+ queue.rear +"  ");
                    queue.enqueue(value);
                    queue.prints(queue.array);
                    System.out.print( queue.rear +"  ");
                    System.out.print( "  "+ queue.array[94] +"  ");
                    System.out.print( "  "+ queue.array[99] +"  ");
                    break;

                case "dequeue":
                    queue.dequeue();
                    queue.prints(queue.array);
                    break;

                case "isEmpty":
                    if (queue.isEmpty()) {
                        System.out.println("True");
                    }
                    else {
                        System.out.println("False");
                    }
                    break;

                case "size":
                    System.out.println(queue.size());
                    break;
                default:
                    System.out.println("Error");
            }

        }catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void prints(Object queueArr[])
    {
        System.out.print("[");
        for(int i=front;i>rear;i--)
        {
            System.out.print(queueArr[i]);
            if(i!=rear+1)
                System.out.print(", ");

        }
        System.out.print("]");
    }



    @Override
    public void enqueue(Object item) {
        if (size == array.length) {
            throw new IllegalStateException("Error");
        }
        array[rear] = item;
        size++;
        rear--;
    }

    @Override
    public Object dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Error");
        }
        Object temp = array[front];
        array[front] = null;
        front--;
        size--;
        return temp;
    }

    @Override
    public boolean isEmpty() {
        if(size==0)
            return true;

        return false;
    }

    @Override
    public int size() {
        return size;
    }
}