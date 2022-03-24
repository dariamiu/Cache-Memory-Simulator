package App;

import Controller.InitialViewController;
import Model.CacheEntry;
import Model.CacheMemory;
import Model.CacheSet;
import Model.CacheSimulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainTestInterface {

    private static void f1(){
        CacheSimulator cacheSimulator = new CacheSimulator();

        cacheSimulator.setData(32,"b",4,"b",8,1);
        cacheSimulator.generateMainMemory(8,2);
        Byte myByte = 0;
        Byte toWrite = Byte.valueOf("11",16);



        /////////////////////
        System.out.println("START");
        System.out.println("address length " + cacheSimulator.getAddressLength());
        CacheMemory cacheMemory = cacheSimulator.getCacheMemory();
        System.out.println("cache size " + cacheMemory.getCacheSize());
        System.out.println("block size " + cacheMemory.getBlockSize());
        System.out.println("index length " + cacheMemory.getIndexLength());
        System.out.println("byte offset length " + cacheMemory.getByteOffsetLength());
        //System.out.println("block offset length " + cacheMemory.getBlockOffsetLength());
        System.out.println("tag length " + cacheMemory.getTagLength());
        ArrayList<CacheSet> sets = cacheMemory.getSets();
        for (CacheSet set : sets) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (Integer integer : entries.keySet()) {
                entries.get(integer).printEntry();
            }
        }



        /////////////////////////////
        String myAddress = cacheSimulator.getAddress("12",8);
        System.out.println("address from which i want data " + myAddress);

        HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(myAddress);
        System.out.println("my address split");
        for (String key: parsedAddress.keySet()){
            System.out.println(key+ " = " + parsedAddress.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                "12",myByte,false);

        //////////////////////////////
        String myAddress2 = cacheSimulator.getAddress("2f",8);
        System.out.println("address from which i want data " + myAddress2);

        HashMap<String, Integer> parsedAddress2 = cacheSimulator.parseAddress(myAddress2);
        System.out.println("my address split");
        for (String key: parsedAddress2.keySet()){
            System.out.println(key+ " = " + parsedAddress2.get(key));
        }
        cacheSimulator.cacheOpperation(parsedAddress2.get("index"), parsedAddress2.get("tag"),parsedAddress2.get("byte offset"),
                "2f",myByte,false);

        ////////////////////////////////
        String myAddress3 = cacheSimulator.getAddress("16",8);
        System.out.println("address from which i want data " + myAddress3);

        HashMap<String, Integer> parsedAddress3 = cacheSimulator.parseAddress(myAddress3);
        System.out.println("my address split");
        for (String key: parsedAddress3.keySet()){
            System.out.println(key+ " = " + parsedAddress3.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress3.get("index"), parsedAddress3.get("tag"),parsedAddress3.get("byte offset"),
                "16",toWrite,true);

        /////////////////////////////
        ArrayList<CacheSet> setsAfter = cacheMemory.getSets();
        for (CacheSet set : setsAfter) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (Integer integer : entries.keySet()) {
                entries.get(integer).printEntry();
            }
        }

        /////////////////////////////
        String myAddress4 = cacheSimulator.getAddress("94",8);
        System.out.println("address from which i want data " + myAddress4);

        HashMap<String, Integer> parsedAddress4 = cacheSimulator.parseAddress(myAddress4);
        System.out.println("my address split");
        for (String key: parsedAddress4.keySet()){
            System.out.println(key+ " = " + parsedAddress4.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress4.get("index"), parsedAddress4.get("tag"),parsedAddress4.get("byte offset"),
                "94",myByte,false);

        ////////////////////////////
        String myAddress5 = cacheSimulator.getAddress("13",8);
        System.out.println("address from which i want data " + myAddress5);

        HashMap<String, Integer> parsedAddress5 = cacheSimulator.parseAddress(myAddress5);
        System.out.println("my address split");
        for (String key: parsedAddress5.keySet()){
            System.out.println(key+ " = " + parsedAddress5.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress5.get("index"), parsedAddress5.get("tag"),parsedAddress5.get("byte offset"),
                "13",myByte,false);


        ////////////////////////
        for (Integer key: cacheSimulator.getMemory().keySet()){
            System.out.print(key+ " : " );
            for (byte b : cacheSimulator.getMemory().get(key)){
                System.out.print(Integer.toHexString(b  & 0xFF ) + " ");
            }
            System.out.print('\n');

        }

        ///////////////////
        ArrayList<CacheSet> setsAfter1 = cacheMemory.getSets();
        for (CacheSet set : setsAfter1) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (Integer integer : entries.keySet()) {
                entries.get(integer).printEntry();
            }
        }

        //////////////////////
        System.out.println("hit :"+ cacheSimulator.getHit());
        System.out.println("miss :" + cacheSimulator.getMiss());

    }

    private static void f2(){
        CacheSimulator cacheSimulator = new CacheSimulator();

        cacheSimulator.setData(32,"b",4,"b",8,4);
        cacheSimulator.generateMainMemory(8,2);
        Byte myByte = 0;
        Byte toWrite = (byte) 0x11;



        /////////////////////
        System.out.println("START");
        System.out.println("address length " + cacheSimulator.getAddressLength());
        CacheMemory cacheMemory = cacheSimulator.getCacheMemory();
        System.out.println("cache size " + cacheMemory.getCacheSize());
        System.out.println("block size " + cacheMemory.getBlockSize());
        System.out.println("index length " + cacheMemory.getIndexLength());
        System.out.println("byte offset length " + cacheMemory.getByteOffsetLength());
        //System.out.println("block offset length " + cacheMemory.getBlockOffsetLength());
        System.out.println("tag length " + cacheMemory.getTagLength());
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
        System.out.println("address from which i want data " + myAddress);

        HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(myAddress);
        System.out.println("my address split");
        for (String key: parsedAddress.keySet()){
            System.out.println(key+ " = " + parsedAddress.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                "14",myByte,false);

        //////////////////////////////
        String myAddress2 = cacheSimulator.getAddress("34",8);
        System.out.println("address from which i want data " + myAddress2);

        HashMap<String, Integer> parsedAddress2 = cacheSimulator.parseAddress(myAddress2);
        System.out.println("my address split");
        for (String key: parsedAddress2.keySet()){
            System.out.println(key+ " = " + parsedAddress2.get(key));
        }
        cacheSimulator.cacheOpperation(parsedAddress2.get("index"), parsedAddress2.get("tag"),parsedAddress2.get("byte offset"),
                "34",myByte,false);

        ////////////////////////////////
        String myAddress3 = cacheSimulator.getAddress("54",8);
        System.out.println("address from which i want data " + myAddress3);

        HashMap<String, Integer> parsedAddress3 = cacheSimulator.parseAddress(myAddress3);
        System.out.println("my address split");
        for (String key: parsedAddress3.keySet()){
            System.out.println(key+ " = " + parsedAddress3.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress3.get("index"), parsedAddress3.get("tag"),parsedAddress3.get("byte offset"),
                "54",toWrite,true);


        /////////////////////////////
        String myAddress4 = cacheSimulator.getAddress("94",8);
        System.out.println("address from which i want data " + myAddress4);

        HashMap<String, Integer> parsedAddress4 = cacheSimulator.parseAddress(myAddress4);
        System.out.println("my address split");
        for (String key: parsedAddress4.keySet()){
            System.out.println(key+ " = " + parsedAddress4.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress4.get("index"), parsedAddress4.get("tag"),parsedAddress4.get("byte offset"),
                "94",myByte,false);
        ////////////////////////
        String myAddressExtra = cacheSimulator.getAddress("14",8);
        System.out.println("address from which i want data " + myAddressExtra);

        HashMap<String, Integer> parsedAddressExtra = cacheSimulator.parseAddress(myAddressExtra);
        System.out.println("my address split");
        for (String key: parsedAddressExtra.keySet()){
            System.out.println(key+ " = " + parsedAddressExtra.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                "14",myByte,false);

        ////////////////////////////
        String myAddress5 = cacheSimulator.getAddress("74",8);
        System.out.println("address from which i want data " + myAddress5);

        HashMap<String, Integer> parsedAddress5 = cacheSimulator.parseAddress(myAddress5);
        System.out.println("my address split");
        for (String key: parsedAddress5.keySet()){
            System.out.println(key+ " = " + parsedAddress5.get(key));
        }

        cacheSimulator.cacheOpperation(parsedAddress5.get("index"), parsedAddress5.get("tag"),parsedAddress5.get("byte offset"),
                "74",myByte,false);


        ////////////////////////
        for (Integer key: cacheSimulator.getMemory().keySet()){
            System.out.print(key+ " : " );
            for (byte b : cacheSimulator.getMemory().get(key)){
                System.out.print(Integer.toHexString(b  & 0xFF ) + " ");
            }
            System.out.print('\n');

        }

        ///////////////////
        ArrayList<CacheSet> setsAfter1 = cacheMemory.getSets();
        for (CacheSet set : setsAfter1) {
            System.out.println(set.getCacheIndex());
            LinkedHashMap<Integer, CacheEntry> entries = set.getEntries();
            for (CacheEntry entry : entries.values()) {
                entry.printEntry();
            }
        }

        //////////////////////
        System.out.println("hit :"+ cacheSimulator.getHit());
        System.out.println("miss :" + cacheSimulator.getMiss());

    }
    public static void main(String args[]){
        new InitialViewController();
    }
}
