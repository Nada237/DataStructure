import java.util.*;
import static java.lang.Math.pow;


interface IPolynomialSolver {
    void setPolynomial(char poly, int[][] terms);
    String print(char poly);
    void clearPolynomial(char poly);
    float evaluatePolynomial(char poly, float value);
    int[][] add(char poly1, char poly2);
    int[][] subtract(char poly1, char poly2);
    int[][] multiply(char poly1, char poly2);
    void HPrint(SingleLinkedList V);
}

interface ILinkedList {

    void add(Object coff,Object exp);

    Object get(int index);

    void clear();


}

class SingleLinkedList implements ILinkedList {
    public static class Node {
        Object coff;
        Object exp;
        Node next;
        Node (Object val1,Object val2){ //A constructor for the Node class to initialize the objects
            coff=val1;
            exp=val2;
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
            System.out.print(p.coff);
            p=p.next;
            if(i < size - 1)
                System.out.print(", ");
        }
        System.out.print("]");
    }

    public void add(Object coff, Object exp) {
        Node newNode = new Node(coff,exp);
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

    public Object[] get(int index) {
        Node p=head;
        int i = 0;
        while (i != index) {
            p = p.next;
            i++;
        }
        Object[]retArray=new Object[2];
        retArray[0]=p.coff;
        retArray[1]=p.exp;
        return retArray;

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

}

public class PolynomialSolver implements IPolynomialSolver{

    @Override
    public void setPolynomial(char poly, int[][] terms) {
        switch(poly){
            case 'A':
                //inserting the list elements
                for (int i=0;i<terms.length;i++) A.add(terms[i][0], terms[i][1]);
                break;
            case 'B':
                //inserting the list elements
                for (int i=0;i<terms.length;i++) B.add(terms[i][0], terms[i][1]);
                break;
            case 'C':
                //inserting the list elements
                for (int i=0;i<terms.length;i++) C.add(terms[i][0], terms[i][1]);
                break;
            case 'R':
                //inserting the list elements
                for (int i=0;i<terms.length;i++) R.add(terms[i][0], terms[i][1]);
                break;
        }

    }
    public void HPrint(SingleLinkedList V){
        SingleLinkedList.Node v = V.head;
        if (v== null){
            System.out.print("Error");
            System.exit(0);
        }
        else {
            for (int i = V.size - 1; i >= 0; i--) {
                //to print "+" except at the end
                if ((int) V.get(V.size - 1 - i)[0] > 0 && i != V.size - 1 && i >= 0) {//add "+" only when the coff is +ve and not after the last term
                    if ((int) V.get(0)[0] == 0 && i == V.size - 2) {
                        System.out.print("");
                    } // to avoid + if fisrt =0
                    else {
                        System.out.print("+");
                    }
                }
                //covers coff 0, 1,-1, other
                if ((int) V.get(V.size - 1 - i)[0] == 1) {
                    if ((int) V.get(V.size - 1 - i)[1] == 0) System.out.print("1");
                    System.out.print("");
                } else if ((int) V.get(V.size - 1 - i)[0] == -1) {
                    System.out.print("-1");
                    if ((int) V.get(V.size - 1 - i)[1] == 0) System.out.print("1");
                } else if ((int) V.get(V.size - 1 - i)[0] == 0)
                    System.out.print("");//if coff equal 0 no term exist so print nothing
                else System.out.print(V.get(V.size - 1 - i)[0]);

                //covers exp 0,1, other
                if ((int) V.get(V.size - 1 - i)[0] != 0) {//if coff equal 0 no term exist so the "x" not printed
                    if ((int) V.get(V.size - 1 - i)[1] == 0) System.out.print("");
                    else if ((int) V.get(V.size - 1 - i)[1] == 1) System.out.print("x");
                    else System.out.print("x^" + (int) V.get(V.size - 1 - i)[1]);
                } else System.out.print("");
            }
            System.out.print("\n");
        }
    }
    public int[][] HAdd (SingleLinkedList V, SingleLinkedList Z){
        int m_size = Math.max(V.size, Z.size);//number of columns of 2 dim array is number of longest list
        int[][] add_arr = new int[m_size][2];
        SingleLinkedList.Node v = V.head;
        SingleLinkedList.Node z = Z.head;
        if (v== null || z== null ){
            System.out.print("Error");
            System.exit(0);
        }
        else {
            for (int i = 0; i < m_size; i++) {
                if (v.exp == z.exp) {//add numbers according to the exponent
                    add_arr[i][0] = (int) v.coff + (int) z.coff;
                    add_arr[i][1] = (int) v.exp;
                    v = v.next;
                    z = z.next;
                }
                //if exponent is different store the bigger
                else if ((int) v.exp > (int) z.exp) {
                    add_arr[i][0] = (int) v.coff;
                    add_arr[i][1] = (int) z.exp;
                    v = z.next;
                } else if ((int) v.exp < (int) z.exp) {
                    add_arr[i][0] = (int) z.coff;
                    add_arr[i][1] = (int) z.exp;
                    z = z.next;
                }

            }
        }
        return add_arr;// return the 2dim array

    }
    public int[][] HSub (SingleLinkedList V, SingleLinkedList Z){

        int m_size = Math.max(V.size, Z.size);//number of columns of 2 dim array is number of longest list
        int[][] sub_arr = new int[m_size][2];
        SingleLinkedList.Node v = V.head;
        SingleLinkedList.Node z = Z.head;
        if (v== null || z== null ){
            System.out.print("Error");
            System.exit(0);
        }
        else {
            for (int i = 0; i < m_size; i++) {
                if (v.exp == z.exp) {//add numbers according to the exponent
                    sub_arr[i][0] = (int) v.coff - (int) z.coff;
                    sub_arr[i][1] = (int) v.exp;
                    v = v.next;
                    z = z.next;
                }
                //if exponent is different store the bigger
                else if ((int) v.exp > (int) z.exp) {
                    sub_arr[i][0] = 0 - (int) v.coff;
                    sub_arr[i][1] = (int) z.exp;
                    v = z.next;
                } else if ((int) v.exp < (int) z.exp) {
                    sub_arr[i][0] = 0 - (int) z.coff;
                    sub_arr[i][1] = (int) z.exp;
                    z = z.next;
                }
            }
        }
        return sub_arr;// return the 2dim array
    }
    public int[][] HMult (SingleLinkedList V, SingleLinkedList Z){


        int size = (int) V.head.exp + (int) Z.head.exp + 1;  //number of columns of 2 dim array
        int[][] mult_arr = new int[size][2]; //initialize 2 dim array to store value

        SingleLinkedList.Node v = V.head;
        if (v== null){
            System.out.print("Error");
            System.exit(0);
        }
        else {
            for (int i = 0; i < V.size; i++) { // nested loop to multiply the 2 lists
                SingleLinkedList.Node z = Z.head;
                if (v == null) {
                    System.out.print("Error");
                    System.exit(0);
                } else {
                    for (int j = 0; j < Z.size; j++) {
                        int result = (int) v.coff * (int) z.coff;
                        int exp = (int) v.exp + (int) z.exp;
                        mult_arr[size - exp - 1][0] += result; //accumulation sum for the result in first row
                        mult_arr[size - exp - 1][1] = exp; //the exponent of the result in second row
                        z = z.next;
                    }
                    v = v.next;
                }
            }
        }
        return mult_arr;// return the 2dim array
    }
    @Override
    public String print(char poly) {
        switch(poly){
            case 'A':
                HPrint(A);
                break;
            case 'B':
                HPrint(B);
                break;
            case 'C':
                HPrint(C);
                break;
            case 'R':
                HPrint(R);
                break;
        }
        return null;
    }

    @Override
    public void clearPolynomial(char poly) {
        switch(poly){
            case 'A':
                A.clear();
                break;
            case 'B':
                B.clear();
                break;
            case 'C':
                C.clear();
                break;
            case 'R':
                R.clear();
                break;
        }
    }

    @Override
    public float evaluatePolynomial(char poly, float value) {
        SingleLinkedList.Node n;
        float num = 0;
        if (poly == 'A') {//check for the parameter name
            n = A.head;
            while (n != null) {
                num += (float) ((int) n.coff * pow(value, (int) n.exp));//going through the list and evaluating
                n = n.next;
            }
        } else if (poly == 'B') {
            n = B.head;
            while (n != null) {
                num += (float) ((int) n.coff * Math.pow(value, (int) n.exp));
                n = n.next;
            }
        } else if (poly == 'C') {
            n = C.head;
            while (n != null) {
                num += (float) ((int) n.coff * Math.pow(value, (int) n.exp));
                n = n.next;
            }
        }
        return num;//return the evaluation sum
    }

    @Override
    public int[][] add(char poly1, char poly2) {
        //number of columns of 2 dim array is number of longest list
        int m_size1 = Math.max(A.size, B.size);
        int m_size=Math.max(m_size1,C.size);
        int[][] add_arr = new int[m_size][2];
        switch(poly1){
            case 'A':
                switch(poly2){
                    case 'A':
                        add_arr=HAdd(A,A);
                        break;
                    case 'B':
                        add_arr=HAdd(A,B);
                        break;
                    case 'C':
                        add_arr=HAdd(A,C);
                        break;
                }
                break;
            case 'B':
                switch(poly2){
                    case 'A':
                        add_arr=HAdd(B,A);
                        break;
                    case 'B':
                        add_arr=HAdd(B,B);
                        break;
                    case 'C':
                        add_arr=HAdd(B,C);
                        break;
                }
                break;
            case 'C':
                switch(poly2){
                    case 'A':
                        add_arr=HAdd(C,A);
                        break;
                    case 'B':
                        add_arr=HAdd(C,B);
                        break;
                    case 'C':
                        add_arr=HAdd(C,C);
                        break;
                }
                break;
        }
        return add_arr;// return the 2dim array
    }

    @Override
    public int[][] subtract(char poly1, char poly2) {

        //number of columns of 2 dim array is number of longest list
        int m_size1 = Math.max(A.size, B.size);
        int m_size=Math.max(m_size1,C.size);
        int[][] sub_arr = new int[m_size][2];
        switch(poly1){
            case 'A':
                switch(poly2){
                    case 'A':
                        sub_arr=HSub(A,A);
                        break;
                    case 'B':
                        sub_arr=HSub(A,B);
                        break;
                    case 'C':
                        sub_arr=HSub(A,C);
                        break;
                }
                break;
            case 'B':
                switch(poly2){
                    case 'A':
                        sub_arr=HSub(B,A);
                        break;
                    case 'B':
                        sub_arr=HSub(B,B);
                        break;
                    case 'C':
                        sub_arr=HSub(B,C);
                        break;
                }
                break;
            case 'C':
                switch(poly2){
                    case 'A':
                        sub_arr=HSub(C,A);
                        break;
                    case 'B':
                        sub_arr=HSub(C,B);
                        break;
                    case 'C':
                        sub_arr=HSub(C,C);
                        break;
                }
                break;
        }
        return sub_arr;// return the 2dim array
    }

    @Override
    public int[][] multiply(char poly1, char poly2) {
        //number of columns of 2 dim array is number of longest list
        int m_size1 = Math.max(A.size, B.size);
        int m_size=Math.max(m_size1,C.size);
        int[][] mult_arr = new int[m_size][2];
        switch(poly1){
            case 'A':
                switch(poly2){
                    case 'A':
                        mult_arr=HMult(A,A);
                        break;
                    case 'B':
                        mult_arr=HMult(A,B);
                        break;
                    case 'C':
                        mult_arr=HMult(A,C);
                        break;
                }
                break;
            case 'B':
                switch(poly2){
                    case 'A':
                        mult_arr=HMult(B,A);
                        break;
                    case 'B':
                        mult_arr=HMult(B,B);
                        break;
                    case 'C':
                        mult_arr=HMult(B,C);
                        break;
                }
                break;
            case 'C':
                switch(poly2){
                    case 'A':
                        mult_arr=HMult(C,A);
                        break;
                    case 'B':
                        mult_arr=HMult(C,B);
                        break;
                    case 'C':
                        mult_arr=HMult(C,C);
                        break;
                }
                break;
        }
        return mult_arr;// return the 2dim array

    }
    static SingleLinkedList A=new SingleLinkedList();
    static SingleLinkedList B=new SingleLinkedList();
    static SingleLinkedList C=new SingleLinkedList();
    static SingleLinkedList R=new SingleLinkedList();



    public static void main(String[] args) {
        PolynomialSolver myObj=new PolynomialSolver();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String st= sc.next(); //get the keyword from the user
            if (!st.equals("set") && !st.equals("print") //check for the input operation if it something different then print error
                    && !st.equals("clear") && !st.equals("add")
                    && !st.equals("sub") && !st.equals("mult")
                    && !st.equals("eval")) {
                System.out.print("Error");
                System.exit(0);
            }
            else{
                char V,Z,W;
                try {
                    switch(st) {
                        case "set":
                            V=sc.next().charAt(0);
                            String sin = sc.next().replaceAll("\\[|\\]", "");//getting the list elements
                            String[] s = sin.split(",");
                            if ((V != 'A') && (V != 'B') && (V != 'C')){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else{
                                int[][] coff_exp = new int[s.length][2];
                                for (int i = 0; i < s.length; i++) {
                                    coff_exp[i][0] = Integer.parseInt(s[i]);
                                    coff_exp[i][1] = s.length - 1 - i;
                                }
                                myObj.setPolynomial(V, coff_exp);
                            }
                            break;

                        case "print":
                            V=sc.next().charAt(0);
                            if ((V != 'A') && (V != 'B') && (V != 'C')){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else {
                                myObj.print(V);
                            }
                            break;

                        case "clear":
                            V=sc.next().charAt(0);
                            if ((V != 'A') && (V != 'B') && (V != 'C')){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else {
                                myObj.clearPolynomial(V);
                                System.out.print("[]");
                            }
                            break;
                        case "eval":
                            V=sc.next().charAt(0);
                            float x = sc.nextFloat();
                            if ((V != 'A') && (V != 'B') && (V != 'C')){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else {
                                System.out.print((int) myObj.evaluatePolynomial(V,x));
                            }
                            break;
                        case "add":
                            V=sc.next().charAt(0);
                            Z=sc.next().charAt(0);
                            if ((V != 'A') && (V != 'B') && (V != 'C') && (Z != 'A') && (Z != 'B') && (Z != 'C') ){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else {
                                int[][] add_arr = myObj.add(V, Z);
                                myObj.setPolynomial('R', add_arr);
                                myObj.print('R');
                            }
                            break;
                        case "sub":
                            V=sc.next().charAt(0);
                            Z=sc.next().charAt(0);
                            if ((V != 'A') && (V != 'B') && (V != 'C') && (Z != 'A') && (Z != 'B') && (Z != 'C') ){
                                System.out.print("Error");
                                System.exit(0);
                            }
                            else {
                                int[][] sub_arr = myObj.subtract(V, Z);
                                myObj.setPolynomial('R', sub_arr);
                                myObj.print('R');
                            }
                            break;

                        case "mult":
                            V=sc.next().charAt(0);
                            Z=sc.next().charAt(0);
                            if ((V != 'A') && (V != 'B') && (V != 'C') && (Z != 'A') && (Z != 'B') && (Z != 'C') ){
                                System.out.print("Error");
                            }
                            else {
                                int[][] mult_arr = myObj.multiply(V, Z);
                                myObj.setPolynomial('R', mult_arr);
                                myObj.print('R');
                            }
                            break;
                    }
                } catch (Exception e) {
                    System.out.print("Error");
                    System.exit(0);
                }
            }
        }
    }
}