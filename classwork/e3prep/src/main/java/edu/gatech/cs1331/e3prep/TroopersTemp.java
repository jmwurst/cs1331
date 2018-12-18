package edu.gatech.cs1331.e3prep;

import java.io.IOException;

public class TroopersTemp {
    public static void main(String[] args){
        System.out.println(method(5));
        System.out.println();
        System.out.println(method(4));
    }
    public static int method(int n){
        try {
            if(n%2 == 1)
                throw new NullPointerException("error");
            else
                throw new IOException("IOError");
        } catch(IOException e) {
            System.out.println("caught IO"); return 3;
        } finally {
            return 5;
        }
    }
}