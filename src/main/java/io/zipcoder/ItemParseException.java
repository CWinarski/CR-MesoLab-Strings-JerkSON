package io.zipcoder;

public class ItemParseException extends Exception {
    private static int count = 0;
    public ItemParseException(){
        count++;
    }
    public static int getCount(){
        return count;
    }

}
