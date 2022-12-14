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

    /**
     * here this method used for checking the precedence of the operations so we can set there priority
     * @param op
     * @return
     */
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

    /**
     * here this method to check if the current char in the expression is operator
     * @param op
     * @return
     */
    public boolean ifOperator(char op){
        if(op=='+' || op=='-' || op=='/' || op=='*' || op=='^')
            return true;
        return false;
    }

    /**
     *  here this method to check if the current char in the expression is alphapet which represents the variables
     * @param alph
     * @return
     */
    public boolean checkChar(char alph){//checking if alphapets (variables)(given to use only a,b,c)
        if (alph=='a'||alph=='b'||alph=='c')
            return true;
        return false;
    }

    /**
     *  here this method to check if the current char in the expression is Parentheses
     * @param par
     * @return
     */
    public boolean ifPar (char par){ //if Parentheses
        if (par=='('||par==')')
            return true;
        return false;
    }

    /**
     *  here this method to check if the current char in the expression is number
     * @param Num
     * @return
     */
    public boolean ifNum (char Num){
        if (Num>='0' && Num<='9')
            return true;
        return false;
    }

    /**
     * this Method converts infix to postfix by analysing the input expression and diffrentiate between variables, numbers, parentheses, and operators
     * it also reorganize the operators according their priority so when come to evaluation it will be correct
     * and this done by firstly remove illegal expressions and operations from the input expression then
     * searching in the input expression if found variable or number will be pushed in stack 'post' and if found operator or parentheses also
     * pushed but after checking there precedence
     * @param expression
     * @return
     */
    @Override
    public String infixToPostfix(String expression) {
        String post;
        post = "";
        int lIndcator;
        if((expression.compareTo("a--")!=0 &&expression.compareTo("b--")!=0&&expression.compareTo("c--")!=0 )){
            if(expression.startsWith("--")||expression.matches("\\(--"))
                expression=expression.replaceFirst("--","");
            expression=expression.replace("+--","+");
            expression=expression.replace("*--","*");
            expression=expression.replace("/--","/");
            expression=expression.replace("^--","^");
            expression=expression.replace("--","+");

            lIndcator=expression.length();

            if(expression.contains("++"))
                return post="Error";
        }

        else{return post="Error";}

        int eIndicator=expression.length();
        boolean indicator=false;
        if(lIndcator==eIndicator)
            indicator =true;//means if operator exists then '+' exist get error

        int crtPar=0;//correct parentheses to check that '(' has ')'
        for (int i=0; i<expression.length();i++)
        {
            if(checkChar(expression.charAt(i)) || ifNum(expression.charAt(i))) {//check alphapets that represent the variables to print them in there correct place in the postfix formula
                if((i!=expression.length()-1) && (checkChar(expression.charAt(i+1)) || ifNum(expression.charAt(i+1))))
                    return post="Error";
                post = post + expression.charAt(i);//adding the variable to the postfix formula
            }
            else if((ifOperator(expression.charAt(i)) && i!=0) || (i==0 && expression.charAt(i)=='-') || ifPar(expression.charAt(i))){// in case the char that has index i is not a variable(operator or parentheses)

                if(!ifPar(expression.charAt(i))&&(((i==expression.length()-1) && ifOperator(expression.charAt(i))) || ((i!=expression.length()-1) && ifOperator(expression.charAt(i+1)) ))){
                    if((i==expression.length()-1) || (expression.charAt(i)=='+'&& expression.charAt(i+1) =='+'&&indicator) || !(expression.charAt(i+1) == '-' || expression.charAt(i+1) =='+' )) {
                    return post="Error";}
                }
                if(!(stk.isEmpty()))//in case there operators already exist in the stack and we are going to add another operator
                {
                    while(!(stk.isEmpty()) && (checkPrecedence(expression.charAt(i)) >= checkPrecedence((char) stk.peek()))){//if the operator to be pushed has
                        // Precedence lower or equal to the one exist at the top of the stack we pop the one on the top of the stack to be
                        // excuted first and add it to the postfix
                        if(((char)stk.peek()!='(')&&((char)stk.peek()!=')')){
                            if(!ifOperator(expression.charAt(i-1)))
                                post = post + stk.pop();
                            else break;}

                        else if ((char)stk.peek()=='(' ){
                            break;}
                    }

                }
                if (expression.charAt(i)=='(') {
                    if((i!=0 && (checkChar(expression.charAt(i-1))|| ifNum(expression.charAt(i-1)))) || (i!=expression.length()-1) && ((ifOperator(expression.charAt(i+1)) && expression.charAt(i+1)!='-') || expression.charAt(i+1)==')'))
                        return post="Error";
                    crtPar++;//counter for the parentheses
                }
                else if (expression.charAt(i)==')') {
                    if(ifOperator(expression.charAt(i-1)) || ((i!=expression.length()-1) && checkChar(expression.charAt(i+1))))
                        return post="Error";
                }

                stk.push(expression.charAt(i));//finally pushing the operator to the stack after the previous check or even when the stack is empty

                char exp=(char)stk.pop();
                if(stk.isEmpty())
                    stk.push(exp);
                else if(!stk.isEmpty() && !((char)stk.peek()=='^' && exp=='+'))
                    stk.push(exp);


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

                    /*else{
                        post="Error";
                        return post;
                    }*/

                }

            }
           else return post="Error";
        }
        while (!(stk.isEmpty()))//to pop the remaining operation in the stack that there variables where already added to the postfix
        {
             post = post + stk.pop();
        }
        if(crtPar!=0){// if this counter equal zero means all the opened parentheses are closed if positive integer means
            //there are opened parentheses not closed and if negative there are closed parentheses without its opening ones
            post="Error";
            return post;
        }
        return post;
    }

    /**
     * here this method evaluates the expression generated by the function infixToPostfix
     * and this done by searching in the postfix expression if variable or number exist they are pushed to 'evalSt' stack
     * and if operator found we pop the operands from the stack and apply the operations then push it again in the stack till the
     * end of the expression then pop the final result from the stack
     * @param expression
     * @return
     */
    @Override
    public int evaluate(String expression) {
        MyStack evalSt=new MyStack();// evaluate stack
        int result=0;
        /*int charCount=0;
        int opCount=0;
        boolean nevIndicator=false;
        for(int i=0; i<expression.length();i++){
            if(checkChar(expression.charAt(i)))
                charCount++;
            if(ifOperator(expression.charAt(i)))
                opCount++;
        }
        if(charCount==opCount)
            nevIndicator=true;
*/
        int a=0;  int b=0;  int c=0;
        a= Integer.parseInt(aS.replace("a=",""));
        b= Integer.parseInt(bS.replace("b=",""));
        c= Integer.parseInt(cS.replace("c=",""));

        int var1= 0;//first variable
        int var2= 0;//second variable

        for (int i=0;i<expression.length();i++){

            if(checkChar(expression.charAt(i))|| (expression.charAt(i)>='0' && expression.charAt(i)<='9')){
                switch (expression.charAt(i)){
                    case('a'):
                        evalSt.push(a);
                        break;
                    case('b'):
                        evalSt.push(b);
                        break;
                    case('c'):
                        evalSt.push(c);
                        break;
                    default:
                        evalSt.push(Integer.parseInt(String.valueOf(expression.charAt(i))));
                }
            }
            if(ifOperator(expression.charAt(i)) && !evalSt.isEmpty()){

                if(expression.charAt(i)=='+'){
                    var2 = (int)evalSt.pop();
                    var1 = (int)evalSt.pop();
                    evalSt.push(var1 + var2);}

                else if (expression.charAt(i)=='-'){
                    //if((i == expression.length()-1 && stk.size()==1) || expression.charAt(i+1)=='*' || expression.charAt(i+1)=='/' || expression.charAt(i+1)=='^')
                    if(evalSt.size()==1 || (i != expression.length()-1 && evalSt.size()>1 && ifOperator(expression.charAt(i+1))))
                        evalSt.push((int)evalSt.pop()*-1);
                    else{
                        var2 = (int)evalSt.pop();
                        var1 = (int)evalSt.pop();
                        evalSt.push(var1 - var2);}
                }


                else if (expression.charAt(i)=='*'){
                    var2 = (int)evalSt.pop();
                    var1 = (int)evalSt.pop();
                    evalSt.push(var1 * var2);}

                else if (expression.charAt(i)=='/'){
                    var2 = (int)evalSt.pop();
                    var1 = (int)evalSt.pop();
                    evalSt.push(var1 / var2);}

                else if (expression.charAt(i)=='^'){
                    var2 = (int)evalSt.pop();
                    var1 = (int)evalSt.pop();
                    evalSt.push((int) Math.pow((double) var1, (double)var2));}

                }

        }
        return (int)evalSt.pop();
    }
    static String aS ;
    static String bS ;
    static String cS ;

    /**
     * here main function where we take inputs and print outputs after applying all previous methods to generate the postfix expression and its value
     * @param args
     */
    public static void main(String[] args) {
        try{
        Evaluator myObj = new Evaluator();
        Scanner sc = new Scanner(System.in);
        String sin = sc.nextLine().toLowerCase();
        aS =sc.next();
        bS = sc.next();
        cS = sc.next();
        String postfix="";
        postfix= myObj.infixToPostfix(sin);
        int result = 0;
        for(int i=0;i<postfix.length();i++)
        {
            System.out.print(postfix.charAt(i));
        }
        System.out.print('\n');
        if(postfix!="Error") {
            result = myObj.evaluate(postfix);
            System.out.println(result);
        }}
        catch (Exception e)
        {System.out.print("Error");}
    }
}

/**
 * Impelemention of stack class with push, pop, peek, size, and isEmpty methods so we can interact with these stacks
 * it will be used for the implementtion of the postfix
 */
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

/**
 * here the class 'SingleLinkedList' which used to implement the stack
 */
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
