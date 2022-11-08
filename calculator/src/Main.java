import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x=0, y=0;
        char sign;
        Scanner inObj = new Scanner(System.in);
        //System.out.println("Welcome to Calculator!");
        //System.out.println("Enter First Number: ");
        x=inObj.nextInt();
        //System.out.println("Enter the sign of needed operation: ");
        sign=inObj.next().charAt(0);
        //System.out.println("Enter Second Number: ");
        y=inObj.nextInt();

        if (sign=='+')
        {
            ICalculator addition = new Calculator();
            System.out.println(addition.add(x,y));
        } else if (sign=='/') {
            ICalculator division = new Calculator();
            try{
                division.divide(x,y);
            }catch (RuntimeException e){
                System.out.println("Error");
            }
            System.out.println(division.divide(x,y));
        }

    }
}