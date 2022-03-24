import Model.CacheEntry;
import Model.CacheMemory;
import Model.CacheSet;
import Model.CacheSimulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestConversions {
    @Test
    public void getAddress(){

        CacheSimulator cacheSimulator = new CacheSimulator();
        String resultTest;

        resultTest = cacheSimulator.getAddress("4f",16);
        String resultOk = "0000000001001111";
        Boolean check = resultOk.equals(resultTest);

        assertTrue(check);
    }

    @Test
    public void calculateLengths(){
        CacheSimulator cacheSimulator = new CacheSimulator();
        cacheSimulator.setCacheMemory(new CacheMemory());
        cacheSimulator.setAddressLength(8);
        cacheSimulator.calculateLengths(3,5,2);


        Boolean check = cacheSimulator.getCacheMemory().getIndexLength().equals(1)
                & cacheSimulator.getCacheMemory().getByteOffsetLength().equals(3)
                & cacheSimulator.getCacheMemory().getTagLength().equals(4);

        assertTrue(check);
    }

    @Test
    public void parseAddress(){
        CacheSimulator cacheSimulator = new CacheSimulator();
        cacheSimulator.setCacheMemory(new CacheMemory());
        cacheSimulator.setAddressLength(8);
        cacheSimulator.calculateLengths(3,5,2);
        HashMap<String,Integer> parsedAddress = new HashMap<>();
        parsedAddress.put("tag",4);
        parsedAddress.put("index",1);
        parsedAddress.put("byte offset",2);

        HashMap<String,Integer> parsedAddressResult = cacheSimulator.parseAddress("01001010");

        Boolean check = parsedAddress.equals(parsedAddressResult);
        assertTrue(check);

    }

    @Test
    public void cacheOperation(){
        CacheSimulator cacheSimulator = new CacheSimulator();

        cacheSimulator.setData(32,"b",4,"b",8,4);
        cacheSimulator.generateMainMemory(8,2);
        Byte myByte = 0;

        CacheMemory cacheMemory = cacheSimulator.getCacheMemory();
        ArrayList<CacheSet> sets = cacheMemory.getSets();
        for (CacheSet set : sets) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (CacheEntry entry : entries.values()) {
                entry.printEntry();
            }
        }


        /////////////////////////////
        String myAddress = cacheSimulator.getAddress("14",8);

        HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(myAddress);

        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                "14",myByte,false);

        Boolean check = cacheMemory.getSets().get(1).getEntries().get(0).getValid().equals(Boolean.TRUE)
                &cacheMemory.getSets().get(1).getEntries().get(0).getCacheTag().equals(2)
                &cacheMemory.getSets().get(1).getEntries().get(0).getDirty().equals(Boolean.FALSE);
        if(cacheMemory.getSets().get(1).getEntries().get(0).getCacheData() == null){
            check = false;
        }
        assertTrue(check);
    }

    @Test
    public void cacheOperation2(){
        CacheSimulator cacheSimulator = new CacheSimulator();

        cacheSimulator.setData(32,"b",4,"b",8,4);
        cacheSimulator.generateMainMemory(8,2);
        Byte myByte = 0;

        CacheMemory cacheMemory = cacheSimulator.getCacheMemory();
        ArrayList<CacheSet> sets = cacheMemory.getSets();
        for (CacheSet set : sets) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (CacheEntry entry : entries.values()) {
                entry.printEntry();
            }
        }

        Byte toWrite = (byte) 0x11;
        /////////////////////////////
        String myAddress = cacheSimulator.getAddress("54",8);

        HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(myAddress);

        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                "54",toWrite,true);

        Boolean check = cacheMemory.getSets().get(1).getEntries().get(0).getValid().equals(Boolean.TRUE)
                &cacheMemory.getSets().get(1).getEntries().get(0).getCacheTag().equals(10)
                &cacheMemory.getSets().get(1).getEntries().get(0).getDirty().equals(Boolean.TRUE)
                &cacheMemory.getSets().get(1).getEntries().get(0).getCacheData().get(0).equals((byte) 0x11);

        assertTrue(check);
    }

}
