package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        ItemParser itemParser = new ItemParser();
        String output = (new Main()).readRawDataToString();
        ArrayList<String> temp = itemParser.parseRawDataIntoStringArray(output);
        itemParser.addItemToList(temp);
        System.out.println(itemParser.printParsedJerkSON());
    }
}
