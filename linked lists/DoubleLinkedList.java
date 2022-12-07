import java.util.*;

interface ILinkedList {
    /**
     * Inserts a specified element at the specified position in the list.
     * @param index
     * @param element
     */
    public void addToIndex(int index, Object element);
    /**
     * Inserts the specified element at the end of the list.
     * @param element
     */
    public void add(Object element);
    /**
     * @param index
     * @return the element at the specified position in this list.
     */
    public Object get(int index);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     * @param index
     * @param element
     */
    public void set(int index, Object element);
    /**
     * Removes all of the elements from this list.
     */
    public void clear();
    /**
     * @return true if this list contains no elements.
     */
    public boolean isEmpty();
    /**
     * Removes the element at the specified position in this list.
     * @param index
     */
    public void remove(int index);
    /**
     * @return the number of elements in this list.
     */
    public int size();

    /**
     * @param fromIndex
     * @param toIndex
     * @return a view of the portion of this list between the specified fromIndex and toIndex, inclusively.
     */
    public ILinkedList sublist(int fromIndex, int toIndex);
    /**
     * @param o
     * @return true if this list contains an element with the same value as the specified element.
     */
    public boolean contains(Object o);
}


public class DoubleLinkedList implements ILinkedList {
    static class DNode{
        Object data;
        DNode next;
        DNode prev;

        // Constructor that creates a node with given fields
        DNode(Object element) {
            data = element;
            next = null;
            prev = null;
        }
    }
    DNode header, trailer;
    static int size = 0;
    static DoubleLinkedList myObj = new DoubleLinkedList();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String list = sc.nextLine().replaceAll("\\[|]", "");
        String[] list_new = list.split(", ");
        String opr = sc.next();

        if (!(list_new[0].isEmpty() && list_new.length == 1)) {
            for (int i = 0; i < list_new.length; i++) {
                myObj.add(list_new[i]);
            }
        }
        try {
            switch (opr) {
                case "addToIndex": {
                    int index = sc.nextInt();
                    if (index > size - 1 || index < 0)
                    {System.out.println("Error");}
                    else {
                        Object element = sc.next();
                        myObj.addToIndex(index, element);
                        myObj.prints();
                    }
                    break;
                }
                case "add": {
                    Object element = sc.next();
                    myObj.add(element);
                    myObj.prints();
                    break;
                }
                case "get": {
                    int index = sc.nextInt();
                    if (index > size - 1 || index < 0)
                    {System.out.println("Error");}
                    else{
                        myObj.get(index);
                    }
                    break;
                }
                case "set": {
                    int index = sc.nextInt();
                    if (index > size - 1 || index < 0)
                    {System.out.println("Error");}
                    else {
                        Object element = sc.next();
                        myObj.set(index, element);
                        myObj.prints();
                    }
                    break;
                }
                case "clear":
                    myObj.clear();
                    myObj.prints();
                    break;
                case "isEmpty":
                    if (myObj.isEmpty()) {
                        System.out.println("True");
                    } else {
                        System.out.println("False");
                    }
                    break;
                case "remove": {
                    int index = sc.nextInt();
                    if (index > size - 1 || index < 0)
                    {System.out.println("Error");}
                    else {
                        myObj.remove(index);
                        myObj.prints();
                    }
                    break;
                }
                case "size":
                    System.out.println(myObj.size());
                    break;
                case "sublist": {
                    int index1 = sc.nextInt();
                    int index2 = sc.nextInt();
                    if (index1 > size - 1 || index2 > size - 1 || index1 < 0 || index2 < 0 || index1 > index2) {
                        System.out.println("Error");
                    } else {
                        myObj.sublist(index1, index2);
                    }
                    break;
                }
                case "contains":
                    Object i = sc.next();
                    if (myObj.contains(i)) {
                        System.out.println("True");
                    } else {
                        System.out.println("False");
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    @Override
    public void addToIndex(int index, Object element) {
        if (index < 0 || index > size)
        {System.out.println("Error");}
        else {
            DNode n = new DNode(element);
            if (index==0){
                if (size==0){
                    header=trailer=n;
                }
                else {
                    n.next=header;
                    header.prev=n;
                    header=n;
                }
                size++;
            }
            else if (index==size){
                add(element);
            }
            else {
                DNode current = header;
                for(int i=1;i<index;i++)
                {current=current.next;}
                n.next=current.next;
                n.prev=current;
                current.next=n;
                current.next.prev=n;
                size++;
            }
        }
    }

    @Override
    public void add(Object element) {
        DNode n = new DNode(element);
        if (size==0){
            header=trailer=n;
        }
        else {
            n.prev=trailer;
            trailer.next=n;
            trailer=n;
        }
        size++;
    }

    @Override
    public Object get(int index) {
        DNode current = header;
        for (int i = 0; i<index; i++)
        {current = current.next;}
        System.out.println(current.data);
        return null;
    }

    @Override
    public void set(int index, Object element) {
        DNode current = header;
        for (int i = 0; i<index; i++)
        {current = current.next;}
        current.data=element;
    }

    @Override
    public void clear() {
        myObj.header = null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void remove(int index) {
        DNode temp = header;
        if (index == 0) {
            header = temp.next; // Change head
            size--;
        }
        // Find previous node of the node to be deleted
        for (int i = 0; temp != null && i < index - 1; i++)
            temp = temp.next;
        // Node temp.next is the node to be deleted
        // Store pointer to the next of node to be deleted
        DNode next = temp.next.next;
        temp.next = next; // Unlink the deleted node from list
        size--;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ILinkedList sublist(int fromIndex, int toIndex) {
        System.out.print("[");
        DNode p=header;
        int i = 0;
        while (i != fromIndex) {
            p = p.next;
            i++;
        }
        for(i =fromIndex ; i <= toIndex; i++) {
            System.out.print(p.data);
            p=p.next;
            if(i != toIndex)
                System.out.print(", ");
        }
        System.out.print("]");
        return null;
    }

    @Override
    public boolean contains(Object o) {
        DNode current = header;
        while (current != null) {
            if (o.equals(current.data)) return true;
            else current = current.next;
        }
        return false;
    }
    public void prints() {
        //Node current will point to head
        DNode current = header;
        if (header == null) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        while (current != null) {
            //Print each node and then go to next.
            if (current.next != null) {
                System.out.print(current.data + ", ");
            } else {
                System.out.print(current.data);
            }
            current = current.next;
        }
        System.out.print("]");
    }
}