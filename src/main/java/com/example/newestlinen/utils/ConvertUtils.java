package com.example.newestlinen.utils;

public class ConvertUtils {

    private ConvertUtils(){
        throw new UnsupportedOperationException();
    }
    public static Long convertStringToLong(String input){
        try {
            return  Long.parseLong(input);
        }catch (Exception e){
            return  Long.valueOf(0);
        }
    }
}
