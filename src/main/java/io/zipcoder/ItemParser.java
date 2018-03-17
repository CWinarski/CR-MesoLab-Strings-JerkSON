package io.zipcoder;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    //split string with regex
    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }
    //feed data from string split into arrayList
    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016 these chunks stored at each index
        return response;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|^|!|%|*|@]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        //ex {name:cake,price:2.50, type:food, expiration:1/4/2018}
        //actually does this when param passed in? {name, cake , price, 2.50, type, food, expiration, 1/4/2018}
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {
        String name;
        Double price;
        String type;
        String expiration;

        ArrayList<String> temp = findKeyValuePairsInRawItemData(rawItem);
 //       ArrayList<Item> myItem = new ArrayList<Item>();
//        for (int i = 0; i < temp.size(); i++) {
//            if (findKeyValuePairsInRawItemData(temp.get(i)).equals(null)) {
//
//                throw new ItemParseException();
//            } else if (findKeyValuePairsInRawItemData(temp.get(i)).size() == 4) {
//                   for (int j = 0; j < temp.size();j++){
//
//                   }
//            }
//
//        }

        for (int i = 1; i < temp.size(); i+=2){
            
        }
        return new Item(name,price,type,expiration);
    }
        // need checks for actual values to these types don't really need to worry aout name type etc,
        // need actual item fields because formatting will take care of names
    public String checkAndReplaceName(String name){
        String regexName = "(N|n)...(E|e)";
        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(name);
        return matcher.replaceAll("name");
    }

    public String checkPrice(String price){
        String regexPrice = "\\d\\.\\d\\d";
        Pattern pattern = Pattern.compile(regexPrice);
        Matcher matcher = pattern.matcher(price);
        return matcher.replaceAll("Price");
    }

    public String checkType(String type){
        String regexType = "(T|t)..(E|e)";
        Pattern pattern = Pattern.compile(regexType);
        Matcher matcher = pattern.matcher(type);
        return matcher.replaceAll("type");
    }

    public String checkExpiration(String expiration){
        String regexExpiration = "(E|e)........(N|n)";
        Pattern pattern = Pattern.compile(regexExpiration);
        Matcher matcher = pattern.matcher(expiration);
        return matcher.replaceAll("expiration");

    }

    public String findMatchAndReplaceValue(String regex, String value){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.replaceAll("Placeholder");
    }


}

