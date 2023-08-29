import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.awt.Point;
import java.util.Scanner;


interface IPlayersFinder {

    java.awt.Point[] findPlayers(String[] photo, int team, int threshold);
}


public class PlayersFinder implements IPlayersFinder{
    static int[] searchForGroup(int down, int up, int right, int left, int i, int j, int team, char photo[][]) {
        int counter = 0;
        int dCounter = 0;
        int uCounter = 0;
        int rCounter = 0;
        int lCounter = 0;
        int [] recDown={0,0,0,0,0};
        int [] recUp={0,0,0,0,0};
        int [] recLeft={0,0,0,0,0};
        int [] recRight={0,0,0,0,0};

        int k = i + 1;
        int l = i - 1;
        int m = j + 1;
        int n = j - 1;

        if(k< photo.length && j>=0 && j<photo[0].length){
            if (photo[k][j] == team)//down
            {
                dCounter++;
                photo[k][j] = 'e';
                if(down<k)
                    down=k;
                recDown=searchForGroup(down, up, right, left, k, j, team, photo);
                if(down<recDown[0])
                    down=recDown[0];
                if(up>recDown[1])
                    up=recDown[1];
                if(right<recDown[2])
                    right=recDown[2];
                if(left>recDown[3])
                    left=recDown[3];
                dCounter = dCounter + recDown[4];
            }}
        if(l>=0 && j>=0 && j<photo[0].length){
            if (photo[l][j] == team)//up
            {
                uCounter++;
                photo[l][j] = 'e';
                if (up>l)
                    up=l;
                recUp=searchForGroup(down, up, right, left, l, j, team, photo);
                if(down<recUp[0])
                    down=recUp[0];
                if(up>recUp[1])
                    up=recUp[1];
                if(right<recUp[2])
                    right=recUp[2];
                if(left>recUp[3])
                    left=recUp[3];
                uCounter = uCounter + recUp[4];
            }}
        if(i>=0 && i< photo.length && m< photo[0].length){
            if (photo[i][m] == team)//right
            {
                rCounter++;
                photo[i][m] = 'e';
                if(right<m)
                    right=m;
                recRight=searchForGroup(down, up, right, left, i, m, team, photo);
                if(down<recRight[0])
                    down=recRight[0];
                if(up>recRight[1])
                    up=recRight[1];
                if(right<recRight[2])
                    right=recRight[2];
                if(left>recRight[3])
                    left=recRight[3];
                rCounter = rCounter + recRight[4];
            }}
        if(i>=0 && i< photo.length && n>=0){
            if (photo[i][n] == team)//left
            {
                lCounter++;
                photo[i][n] = 'e';
                if(left>n)
                    left=n;
                recLeft=searchForGroup(down, up, right, left, i, n, team, photo);
                if(down<recLeft[0])
                    down=recLeft[0];
                if(up>recLeft[1])
                    up=recLeft[1];
                if(right<recLeft[2])
                    right=recLeft[2];
                if(left>recLeft[3])
                    left=recLeft[3];
                lCounter = lCounter + recLeft[4];
            }}

        counter = dCounter + uCounter + rCounter + lCounter;

        int [] output=new int[5];
        output[0]=down;
        output[1]=up;
        output[2]=right;
        output[3]=left;
        output[4]=counter;
        return output;
    }

    public java.awt.Point[] findPlayers(String[] photo, int team, int threshold)
    {
        //turning the array of strings to 2D array of char
        char arr[][] = new char[photo.length][photo[0].length()];
        for (int i = 0; i < photo.length; i++) {
            for (int j = 0; j < photo[0].length(); j++) {
                arr[i][j] = photo[i].toUpperCase().charAt(j);
            }
        }

        int count = 0;
        int group = 0;
        java.awt.Point [] points=new Point[photo.length*photo[0].length()]; //this is the worst case for the length
        for (int i = 0; i < photo.length; i++) {
            for (int j = 0; j < photo[0].length(); j++) {
                count = 0;
                int[] start = new int[2];
                int left = 0;
                int right = 0;
                int down = 0;
                int up = 0;
                if (arr[i][j] == team) {
                    start[0] = i;
                    start[1] = j;
                    left = j;
                    right = j;
                    up = i;
                    down = i;
                    count++;
                    arr[i][j]='e';//"e" means that we counted this element before
                    int [] parameters=new int[5];
                    parameters=searchForGroup(down, up, right, left, i, j, team, arr);
                    if (((count+parameters[4])*4>=threshold)) //total area of adjacent cells & compare with threshold
                    {
                        group++;
                        points[group-1]=new Point((parameters[2]+parameters[3]+1),(parameters[0]+parameters[1]+1));

                    }

                }

            }
        }
        java.awt.Point [] finalPoints=new Point[group];
        for(int n=0;n<group;n++)
        {
            finalPoints[n]=points[n];
        }
        return finalPoints;
    }
    public static void main(String[] args) {
        //Scanning input into array of strings

        Scanner SC = new Scanner(System.in);
        String dim = SC.nextLine();
        String[] s = dim.split(", ");
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);
        String [] photo=new String[x];
        for (int i = 0; i < x; i++) {
            photo[i] = SC.nextLine().toUpperCase();
        }
        char id = SC.next().charAt(0);
        int th = SC.nextInt();

        PlayersFinder Obj=new PlayersFinder();
        java.awt.Point[] points=Obj.findPlayers(photo,id,th);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point temp =new Point (0,0);
                if (points[j].getX() < points[i].getX()) {
                    temp = points[i];
                    points[i] = points[j];
                    points[j] = temp;
                }
                else if (points[j].getX() == points[i].getX()) {
                    if (points[j].getY() < points[i].getY()) {
                        temp = points[i];
                        points[i] = points[j];
                        points[j] = temp;
                    }}
            }}

        System.out.print("[");
        for (int c=0;c<points.length;c++)
        {
            System.out.print("("+(int)points[c].getX()+", "+(int)points[c].getY()+")");
            if(c != points.length - 1)
                System.out.print(", ");
        }
        System.out.print("]");
    }
}



