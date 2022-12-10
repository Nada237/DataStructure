import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IExpressionEvaluator {
    public String infixToPostfix(String expression);
    public int evaluate(String expression);
}
interface IStack {
    public Object pop();
    public Object peek();
    public void push(Object element);
    public boolean isEmpty();
    public int size();
}
interface ILinkedList {
    public void addToIndex(int index, Object element);
    public void add(Object element);
    public Object get(int index);
    public boolean isEmpty();
    public void remove(int index);
    public int size();
}



public class Evaluator implements IExpressionEvaluator {
    MyStack stk = new MyStack();
    public int checkPrecedence(char op){//assigning priority to each operator
        if (op=='(' || op==')'){
            return 0;
        }
        else if (op=='^') {
            return 1;
        }
        else if (op=='/'||op=='*') {
            return 2;
        }
        else if (op=='+'||op=='-')
        {
            return 3;
        }
        return 0;
    }
    public boolean ifOperator(char op){
        if(op=='+' || op=='-' || op=='/' || op=='*' || op=='^')
            return true;
        return false;
    }
    public boolean checkChar(char alph){//checking if alphapets (variables)(given to use only a,b,c)
        if (alph=='a'||alph=='b'||alph=='c')
            return true;
        return false;
    }
    public boolean ifPar (char par){ //if Parentheses
        if (par=='('||par==')')
            return true;
        return false;
    }
    @Override
    public String infixToPostfix(String expression) {
        String post;
        post = "";
        int crtPar=0;//correct parentheses to check that '(' has ')'
        for (int i=0; i<expression.length();i++)
        {
            if(checkChar(expression.charAt(i))) {//check alphapets that represent the variables to print them in there correct place in the postfix formula
                if((i!=expression.length()-1) && checkChar(expression.charAt(i+1)))
                    return post="Error";
                post = post + expression.charAt(i);//adding the variable to the postfix formula
                //System.out.print(expression.charAt(i));
            }
            else if(ifOperator(expression.charAt(i)) || ifPar(expression.charAt(i))){// in case the char that has index i is not a variable(operator or parentheses)
                if(((i==expression.length()-1) && ifOperator(expression.charAt(i))) ||((i!=expression.length()-1) && ifOperator(expression.charAt(i+1))))
                    return post="Error";
                if(!(stk.isEmpty()))//in case there operators already exist in the stack and we are going to add another operator
                {
                    while(!(stk.isEmpty()) && (checkPrecedence(expression.charAt(i)) >= checkPrecedence((char) stk.peek()))){//if the operator to be pushed has
                        // Precedence lower or equal to the one exist at the top of the stack we pop the one on the top of the stack to be
                        // excuted first and add it to the postfix
                        if(((char)stk.peek()!='(')&&((char)stk.peek()!=')'))
                            post = post + stk.pop();
                        else if ((char)stk.peek()=='('){
                            break;}
                    }

                }
                if (expression.charAt(i)=='(') {
                    if((i!=0 && checkChar(expression.charAt(i-1)))|| (i!=expression.length()-1) && (ifOperator(expression.charAt(i+1)) || expression.charAt(i+1)==')'))
                        return post="Error";
                    crtPar++;//counter for the parentheses
                }
                else if (expression.charAt(i)==')') {
                    if(ifOperator(expression.charAt(i-1)) || ((i!=expression.length()-1)&&checkChar(expression.charAt(i-1))))
                        return post="Error";
                }
                stk.push(expression.charAt(i));//finally pushing the operator to the stack after the previous check or even when the stack is empty

                if ((char)stk.peek()==')') {
                    crtPar--;//if there is '(' in the the stack and then found ')' so it will zero if no '(' it not equal zero
                    stk.pop();
                    //if(!stk.isEmpty()){//to handel the condition when there is closed parentheses and no opened one
                    while(!stk.isEmpty() && (char)stk.peek()!='(')
                    {
                        post = post + stk.pop();
                    }
                    if(!stk.isEmpty() && (char)stk.peek()!=')')
                        stk.pop();
                    //}
                    /*else{
                        post="Error";
                        return post;
                    }*/

                }

            }
            else return post="Error";
        }
        while (!(stk.isEmpty()))//to pop the remaining operation in the stack that there variables where already added to the postfix
        {   //if ((char)stk.peek()!='('||(char)stk.peek()!=')')
             post = post + stk.pop();
            //else if ((char)stk.peek()=='('||(char)stk.peek()==')') {
            // stk.pop();
             //   }

        }
        if(crtPar!=0){// if this counter equal zero means all the opened parentheses are closed if positive integer means
            //there are opened parentheses not closed and if negative there are closed parentheses without its opening ones
            post="Error";
            return post;
        }



        return post;
    }

    @Override
    public int evaluate(String expression) {
        Scanner sc = new Scanner(System.in);
        int result=0;

        int a=0; int aC=0;//counter of how many times we have a in the expression
        int b=0; int bC=0;//counter of how many times we have b in the expression
        int c=0; int cC=0;//counter of how many times we have c in the expression

        int var1= 0;//first variable
        int var2= 0;//second variable

        for (int i=0;i<expression.length();i++){

            if(checkChar(expression.charAt(i))){
                switch (expression.charAt(i)){
                    case('a'):
                        if (aC==0){
                            a=sc.nextInt();
                            aC++;}
                        if(i==0)
                            var1=a;
                        else var2=a;
                        break;
                    case('b'):
                        if (bC==0) {
                            b = sc.nextInt();
                            bC++;}
                        if(i==0)
                            var1=b;
                        else var2=b;
                        break;
                    case('c'):
                        if (cC==0) {
                            c = sc.nextInt();
                            cC++;}
                        if(i==0)
                            var1=c;
                        else var2=c;
                        break;
                }
            }
            if(ifOperator(expression.charAt(i))){
                switch (expression.charAt(i)){
                case('+'):
                    result =var1 + var2;
                    break;
                case('-'):
                    result =var1 - var2;
                    break;
                case('*'):
                    result =var1 * var2;
                    break;
                case('/'):
                    result =var1 / var2;
                    break;
                case('^'):
                    result =(int) Math.pow((double) var1, (double)var2);
                    break;
                }
                var1 = result;
            }

        }
        return result;
    }
    public static void main(String[] args) {
        Evaluator myObj = new Evaluator();
        Scanner sc = new Scanner(System.in);
        String sin = sc.nextLine().toLowerCase();
        sin=sin.replaceFirst("--","");
        sin=sin.replace("--","+");
        String postfix= myObj.infixToPostfix(sin);
        int result = myObj.evaluate(postfix);
        for(int i=0;i<postfix.length();i++)
        {
            System.out.print(postfix.charAt(i));
        }
        System.out.print('\n');
        if(postfix!="Error")
            System.out.println(result);




    }


}



class MyStack implements IStack{
    SingleLinkedList list=new SingleLinkedList();
    @Override
    public Object pop() {
        Object p;
        p=list.head.element;
        list.remove(0);
        return p;
    }

    @Override
    public Object peek() {
        return list.get(0);
    }

    @Override
    public void push(Object element) {
        list.addToIndex(0,element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
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

    public void add(Object element) {
        Node newNode = new Node(element);
        if (head == null)//checking if the list is empty
        {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            //entering the newNode at the end of the sequence
            tail.next = newNode;
            //now the newNode is the last node so the tail will point to it
            tail = newNode;
            size++;
        }
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
