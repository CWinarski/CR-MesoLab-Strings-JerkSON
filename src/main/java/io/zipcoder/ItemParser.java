package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    Map<String, ArrayList<Item>> actualFoodList;

    public ItemParser(){
    }
    public ItemParser(String rawData){

    }

    //feed data from string split into arrayList
    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016 these chunks stored at each index
        return response;
    }

    //split string with regex
    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }


    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|^|!|%|*|@]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        //ex {name:cake,price:2.50, type:food, expiration:1/4/2018}
        return response;

    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {
        String name = findName(rawItem);
        Double price = Double.valueOf(findPrice(rawItem));
        String type = findType(rawItem);
        String expiration = findExpiration(rawItem);
//      getNameAndPrice(name, price);
        return new Item(name, price, type, expiration);
    }


    public ArrayList<Item> putItemsInArrayList(String rawItem){
        ArrayList<Item> itemList = new ArrayList<Item>();
        try {
            for (Object item : itemList) {
                itemList.add(parseStringIntoItem(rawItem));
            }
        }catch (ItemParseException e){
            new ItemParseException();
        }
        return itemList;
    }
//    public void getNameAndPrice(String food, Double price) {
//        if (!nameAndPrice.containsKey(food)) {
//            nameAndPrice.put(food, new ArrayList<Double>());
//            nameAndPrice.get(food).add(price);
//        } else {
//            nameAndPrice.get(food).add(price);
//        }
//
//    }
//
//    public Integer countNumberFoodItems(String food) {
//        ArrayList<Double> prices = nameAndPrice.get(food);
//        return prices.size();
//    }

    public Integer countNumberOfPrices(Double Price) {
       return null;
    }


    public String fixCookie(String input) {
        String regexCookie = "(C|c).....(S|s)";
        Pattern pattern = Pattern.compile(regexCookie);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("cookies");
    }

    public String findName(String name) throws ItemParseException {
        String ifCookieFix = fixCookie(name);
        String regexName = "([Nn]..[Ee]:)(\\w+)";
        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return matcher.group(2).toLowerCase();
        } else {
            throw new ItemParseException();
        }
    }

    public String findPrice(String price) throws ItemParseException {
        String regexPrice = "([Pp]...[Ee]:)(\\d\\.\\d{2})";
        Pattern pattern = Pattern.compile(regexPrice);
        Matcher matcher = pattern.matcher(price);

        if (matcher.find()) {
            return matcher.group(2);
        } else {
            throw new ItemParseException();
        }
    }

    public String findType(String type) throws ItemParseException {
        String regexType = "([Tt]..[Ee]:)(\\w+)";
        Pattern pattern = Pattern.compile(regexType);
        Matcher matcher = pattern.matcher(type);

        if (matcher.find()) {
            return matcher.group(2).toLowerCase();
        } else {
            throw new ItemParseException();
        }
    }

    public String findExpiration(String expiration) throws ItemParseException {
        String regexExpiration = "([E|e]........[N|n]:)(\\d\\/\\d{2}\\/\\d{4})";
        Pattern pattern = Pattern.compile(regexExpiration);
        Matcher matcher = pattern.matcher(expiration);
        if (matcher.find()) {
            return matcher.group(2).toLowerCase();
        } else {
            throw new ItemParseException();
        }

    }
}



