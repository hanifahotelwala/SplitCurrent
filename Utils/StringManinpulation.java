package com.example.android.splitfeatures.Utils;

/**
 ** used in registerScreen activity
 */


public class StringManinpulation {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }
}