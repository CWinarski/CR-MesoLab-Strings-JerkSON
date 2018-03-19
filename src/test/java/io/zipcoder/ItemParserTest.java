package io.zipcoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemParserTest {

    private String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawSingleItemIrregularSeperatorSample = "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";

    private String rawBrokenSingleItem =    "naMe:Milk;price:;;type:Food;expiration:1/25/2016##";

    private String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                                      +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                                      +"NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";


    private ItemParser itemParser;

    @Before
    public void setUp(){
        itemParser = new ItemParser();
    }

    @Test
    public void parseRawDataIntoStringArrayTest(){
        Integer expectedArraySize = 3;
        ArrayList<String> items = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        Integer actualArraySize = items.size();
        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    public void parseStringIntoItemTest() throws ItemParseException{
        Item expected = new Item("milk", 3.23, "food","1/25/2016");
        Item actual = itemParser.parseStringIntoItem(rawSingleItem);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = ItemParseException.class)
    public void parseBrokenStringIntoItemTest() throws ItemParseException{
        itemParser.parseStringIntoItem(rawBrokenSingleItem);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTest(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItem).size();
        assertEquals(expected, actual);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTestIrregular(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItemIrregularSeperatorSample).size();
        assertEquals(expected, actual);
    }

    @Test
    public void addNewItemToMapTest() {
        //Given
        boolean expected = true;
        ArrayList<String> testList = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        //When
        itemParser.addItemToList(testList);
        boolean actual = itemParser.getRealFoodList().keySet().contains("milk");
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addItemToListTest(){
        //Given
        boolean expected = true;
        ArrayList<String> testList;
        testList = itemParser.parseRawDataIntoStringArray(rawMultipleItems);
        itemParser.addItemToList(testList);
        //When
        boolean actual = itemParser.getRealFoodList().keySet().contains("milk");
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void countNumberOfDifferentPricesTest(){
        //Given
        int expected = 2;
        ArrayList<Item> testList = new ArrayList<>();
        Item cake = new Item("cake", 2.50, "Food", "1/24/18");
        Item cake2 = new Item("cake", 2.50, "Food", "1/24/18");
        Item pie = new Item("cie", 2.00, "Food", "1/18/18");
        testList.add(cake);
        testList.add(cake2);
        testList.add(pie);
        //When
        int actual = itemParser.countNumberOfDifferentPrices(testList, 2.50);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void fixCookieTest() throws ItemParseException {
        //Given
        String expected = "cookies";
        String messedUpCookie = "c00kies";
        //When
       String actual = itemParser.fixCo0kie(messedUpCookie);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findNameTest() throws ItemParseException {
        //Given
        String expected = "cake";
        String messedUpName = "NaME:cAkE";
        //When
        String actual = itemParser.findName(messedUpName);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findCo0kieName() throws ItemParseException {
        //Given
        String expected = "cookies";
        String messedUpName = "NaME:co0kies";
        //When
        String actual = itemParser.findName(messedUpName);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findPriceTest() throws ItemParseException {
        //Given
        String expected = "2.50";
        String messedUpPrice = "PriCe:2.50";
        //When
        String actual = itemParser.findPrice(messedUpPrice);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findTypeTest() throws ItemParseException {
        //Given
        String expected = "food";
        String messedUpType = "TypE:FoOd";
        //When
        String actual = itemParser.findType(messedUpType);
        //Then
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void findExpirationTest() throws ItemParseException {
        //Given
        String expected = "1/24/2018";
        String messedUpExpiration = "expirATIOn:1/24/2018";
        //When
        String actual = itemParser.findExpiration(messedUpExpiration);
        //Then
        Assert.assertEquals(expected,actual);
    }
}
