package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    Map<String, ArrayList<Item>> realFoodList;

    public ItemParser() {
        realFoodList = new HashMap<>();
    }

    public Map<String, ArrayList<Item>> getRealFoodList() {
        return realFoodList;
    }

    //feed data from string split into arrayList
    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        //naMe:Milk;price:3.23;type:Food;expiration:1/25/2016 these chunks stored at each index
        return response;
    }

    //general method for splitting arraylists with regex
    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split(stringPattern)));
    }

    //Splits arraylist that has chunks into the name:cake arraylist which gets used in testing
    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|^|!|%|*|@]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        //ex {name:cake,price:2.50, type:food, expiration:1/4/2018}
        return response;

    }

    //takes the raw data arraylist(item chunks) parses them into items and then adds the items to the map
    public void addItemToList(ArrayList<String> itemList){
            for (int i = 0; i < itemList.size(); i++) {
                try{
                Item newItem = (parseStringIntoItem(itemList.get(i)));
                addNewItemToMap(realFoodList, newItem);
            }catch (ItemParseException e){
                    continue;
                }

        }
    }

    public void addNewItemToMap(Map<String, ArrayList<Item>> map, Item newItem) {
        if (!map.keySet().contains(newItem.getName())) {
            map.put(newItem.getName(), new ArrayList<>());
            map.get(newItem.getName()).add(newItem);
        } else {
            map.get(newItem.getName()).add(newItem);
        }
    }

    //gets all the prices for the items
    public ArrayList<Double> getPrices(Map.Entry<String, ArrayList<Item>> itemMap) {
        ArrayList<Double> prices = new ArrayList<>();
        for (int i = 0; i < itemMap.getValue().size(); i++) {
            if (!prices.contains(itemMap.getValue().get(i).getPrice())) {
                prices.add(itemMap.getValue().get(i).getPrice());
            }
        }
        return prices;
    }

    public int countNumberOfDifferentPrices(ArrayList<Item> itemList, Double price) {
        int counter = 0;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getPrice().equals(price)) {
                counter++;
            }
        }
        return counter;
    }

        //Create new item
    public Item parseStringIntoItem(String rawItem) throws ItemParseException {
        String name = findName(rawItem);
        Double price = Double.valueOf(findPrice(rawItem));
        String type = findType(rawItem);
        String expiration = findExpiration(rawItem);

        return new Item(name, price, type, expiration);
    }

    //Cookie has a 0 in it so we fix it before parsing into the item
    public String fixCo0kie(String input) {
        String regexCo0kie = "[0]";
        Pattern pattern = Pattern.compile(regexCo0kie);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("o");
    }

    //We find the item name by searching for the name:item pair then return the group that has the item
    public String findName(String name) throws ItemParseException {
        String regexName = "([Nn]..[Ee]:)(\\w+)";
        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            if(matcher.group(2).contains("0")) {
                return fixCo0kie(matcher.group(2).toLowerCase());
            }else {
                return matcher.group(2).toLowerCase();
            }
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

    public String printParsedJerkSON() {
        StringBuilder print = new StringBuilder();
        for (Map.Entry<String, ArrayList<Item>> entry : realFoodList.entrySet()) {
            print.append("Name: ");
            print.append(String.format("%-10s",entry.getKey()));
            print.append("\t\tseen: " + entry.getValue().size() + " times\n");
            print.append("================" + "\t\t" + "==================\n");



            ArrayList<Double> priceList = getPrices(entry);

            for (int i = 0; i < priceList.size(); i++) {
                print.append("Price: ");
                print.append(String.format("%-10s",priceList.get(i)));
                print.append("\t\tseen: " + countNumberOfDifferentPrices(entry.getValue(), priceList.get(i)) + " times\n");
                print.append("----------------" + "\t\t" + "------------------\n");

            }
            print.append("\n");
        }

        print.append("\nErrors" + "\t\t\t\t\tseen: " + ItemParseException.getCount() + " times\n");
        return print.toString();
    }

}




