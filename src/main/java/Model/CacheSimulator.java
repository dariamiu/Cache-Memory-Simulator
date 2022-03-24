package Model;

import java.util.*;

public class CacheSimulator {
    private CacheMemory cacheMemory;
    private Integer addressLength;
    private Integer hit;
    private Integer miss;
    private Boolean hitMiss;
    private Boolean itWasDirty;
    private Boolean itWasReplaced;
    private HashMap<Integer, List<Byte>> memory = new HashMap<>();
    private HashMap<String,Integer> currentlyProcessedAddress = new HashMap<>();
    private Integer currentlyProcessedBlock;
    private Integer writeBackBlock;
    public CacheSimulator(){


    }



    public Integer getCurrentlyProcessedBlock() {
        return currentlyProcessedBlock;
    }

    public Integer getWriteBackBlock() {
        return writeBackBlock;
    }

    public Boolean getHitMiss() {
        return hitMiss;
    }

    public Boolean getItWasDirty() {
        return itWasDirty;
    }

    public Boolean getItWasReplaced() {
        return itWasReplaced;
    }

    public CacheMemory getCacheMemory() {
        return cacheMemory;
    }

    public HashMap<Integer, List<Byte>> getMemory() {
        return memory;
    }

    public Integer getAddressLength() {
        return addressLength;
    }

    public Integer getHit() {
        return hit;
    }

    public Integer getMiss() {
        return miss;
    }

    public HashMap<String, Integer> getCurrentlyProcessedAddress() {
        return currentlyProcessedAddress;
    }

    public void setCacheMemory(CacheMemory cacheMemory) {
        this.cacheMemory = cacheMemory;
    }

    private int powerOfTwo (Integer integer){
         return (int) (Math.log(integer)/Math.log(2));
    }
    public void setAddressLength(Integer addressLength){
        this.addressLength = addressLength;
    }
    private Integer parseSize (Integer i, String s){
        int result = switch (s) {
            case "b" -> powerOfTwo(i);
            case "kb" -> powerOfTwo(i) + 10;
           // case "mb" -> powerOfTwo(i) + 20;
            default -> 0;
        };
        return result;
    }

    /**
     *
     * @param cacheSize got from UI
     * @param s measurement unit of cache size (from UI)
     * @param blockSize got from UI
     * @param s2 measurement unit for block size
     * @param addressLength got from UI
     * @param type direct, 2 way assoc, 4 way assoc
     */
    public void setData (Integer cacheSize, String s, Integer blockSize, String s2, Integer addressLength, Integer type){
        this.cacheMemory = new CacheMemory();
        Integer bs = parseSize(blockSize, s2);
        Integer cs = parseSize(cacheSize,s);
        cacheMemory.setCacheSize(cs);
        cacheMemory.setBlockSize(bs);
        cacheMemory.setType(type);
        setAddressLength(addressLength);
        calculateLengths(bs,cs,type);
        cacheMemory.initializeSets();
        this.hit = 0;
        this.miss = 0;
        this.currentlyProcessedBlock = 0;


    }

    /**
     * important data used : addressLength, blocksSize
     * generates a random main memory which the user will work with
     */
    public void generateMainMemory(Integer addressLength, Integer block){
        memory = new HashMap<>();
        int sizePow = addressLength - block;
        double k = Math.pow(2,sizePow);
        double blocksize = Math.pow(2,block);
        for (double i = 0 ; i < k ; i++){
            byte[] randomBytes = new byte[(int) blocksize];
            new Random().nextBytes(randomBytes);
            Byte[] randomBytes2 = new Byte[(int) blocksize];
            int j=0;
            for(byte b: randomBytes)
                randomBytes2[j++] = b;
            List<Byte> list = Arrays.asList(randomBytes2);
            memory.put((int) i,list);

        }
        //for printing
        for (Integer key: memory.keySet()){
            System.out.print(key+ " : " );
            for (byte b : memory.get(key)){
                System.out.print(Integer.toHexString(b  & 0xFF ) + " ");
            }
            System.out.print('\n');

        }


    }

    /**
     * transform addresss from hex to binary to be able to get the tag index and offsets
     * @param address retrieved from user interface
     * @return address as a string of bits
     */
    public String getAddress(String address, Integer addressLength){
        String binAddr = Integer.toBinaryString(Integer.parseInt(address, 16));

        StringBuffer buf = new StringBuffer(binAddr);

        while (buf.length() < addressLength) {
            buf.insert(0, '0');
        }
        return buf.toString();
    }

    /**
     * sets the lengths of tag index and offsets
     */
    public void calculateLengths(Integer blockSize, Integer cacheSize,Integer type){

        Integer index = cacheSize - blockSize - powerOfTwo(type);
        cacheMemory.setIndexLength(index);
        Integer byteOffset = blockSize;
        cacheMemory.setByteOffsetLength(byteOffset);
        cacheMemory.setTagLength(addressLength - index - byteOffset);;

    }

    public HashMap<String,Integer> parseAddress(String addressRetrievedFromUser){
        Integer tagL = cacheMemory.getTagLength();
        Integer indexL = cacheMemory.getIndexLength();
        Integer byteL = cacheMemory.getByteOffsetLength();
        String address = addressRetrievedFromUser;

        String tag = address.substring(0,tagL);

        address = address.substring(tagL);
        String index = address.substring(0, indexL);

        address = address.substring(indexL);
        String byteOffset = address.substring(0,byteL);


        HashMap<String,Integer> parsedAddress = new HashMap<>();
        parsedAddress.put("tag",Integer.parseInt(tag, 2));
        parsedAddress.put("index",Integer.parseInt(index, 2));
        parsedAddress.put("byte offset",Integer.parseInt(byteOffset, 2));

        this.currentlyProcessedAddress = parsedAddress;
        return parsedAddress;


    }

    public Integer getBlock(Integer blockSize, String address){
        Integer addressDecimal = Integer.parseInt(address,16);
        Integer blockSizeNotPower = ((int) Math.pow(2, blockSize));
        return (addressDecimal / blockSizeNotPower);

    }


    private Integer reconstructAddress(Integer tag,Integer index){
        String binTag = Integer.toBinaryString(tag);
        String binIndex = Integer.toBinaryString(index);
        String binOffset = "0";

        for (int i = 0; i < cacheMemory.getByteOffsetLength() - 1; i++) {
            binOffset = binOffset.concat("0");
        }

        System.out.println("binTag is " + binTag);
        System.out.println("binIndex is " + binIndex);
        System.out.println("binOffset is " + binOffset);
        int addZerosToTag = cacheMemory.getIndexLength() - binIndex.length();
        System.out.println(addZerosToTag);
        for (int i = 0; i < addZerosToTag; i++) {
            binTag = binTag.concat("0");
        }
        System.out.println("binTag is " + binTag);
        System.out.println("binIndex is " + binIndex);
        System.out.println("binOffset is " + binOffset);

        String binAddress = binTag + binIndex + binOffset;
        System.out.println(binAddress);
        StringBuffer binAddressBuffer = new StringBuffer(binAddress);
        while (binAddressBuffer.length() < addressLength) {
            binAddressBuffer.insert(0, '0');
        }

        System.out.println(binAddressBuffer);

        Integer address = Integer.parseInt(binAddressBuffer.toString(),2);

        Integer blockSizeNotPower = ((int) Math.pow(2, cacheMemory.getBlockSize()));

        return (address / blockSizeNotPower);


    }

    public void cacheOpperation(Integer index, Integer tag, Integer offset, String address, Byte writeData, Boolean write){
        itWasDirty = false;
        itWasReplaced = false;
        hitMiss = false;
        ArrayList<CacheSet> sets = cacheMemory.getSets();
        CacheSet set = sets.get(index);
        LinkedHashMap<Integer,CacheEntry> entries = set.getEntries();
        LinkedHashMap<Integer,CacheEntry> entriesCopy = set.getEntries();
        Boolean out = false;
        List<Byte> data = new ArrayList<>();
        Integer theBlock = getBlock(cacheMemory.getBlockSize(),address);
        System.out.println(theBlock);
        data = memory.get(theBlock);
        this.currentlyProcessedBlock = theBlock;


        System.out.println('\n');

        CacheEntry cacheEntry = new CacheEntry(true,tag,data,false);

        Integer setIndex = 0;
        for (CacheEntry entry : entries.values()) {
            if(entry.getValid() == true ){
                if(entry.getCacheTag() == tag){
                    hit++;
                    hitMiss = true;
                    out = true;
                    cacheEntry.setSet(setIndex);
                    entries.put(setIndex,cacheEntry);
                    if(write){
                        data.set(offset,writeData);
                        cacheEntry.setCacheData(data);
                        cacheEntry.setDirty(true);
                        entries.put(setIndex,cacheEntry);
                    }
                    break;
                }
            }

        }
        Optional<Integer> firstKey = entriesCopy.keySet().stream().findFirst();
        Integer key = 9999;
        if (firstKey.isPresent()) {
           key = firstKey.get();
        }
        if(!out){
            miss++;
            hitMiss = false;
            Integer i;
            i = key;
            if(entriesCopy.get(key).getDirty()){
                    itWasDirty = true;
                    Integer reconstructedAddress = reconstructAddress(entries.get(i).getCacheTag(),index);
                    writeBackBlock = reconstructedAddress;
                    memory.put(reconstructedAddress,entries.get(i).getCacheData());
                }
            if(entriesCopy.get(key).getValid()){
                itWasReplaced = true;
            }
                cacheEntry.setSet(i);
                entries.put(i,cacheEntry);
            if(write){
                data.set(offset,writeData);
                cacheEntry.setCacheData(data);
                cacheEntry.setDirty(true);
                entries.put(i,cacheEntry);
            }
        }

    }

    public String decimalToBinary(Integer number, Integer length){
        String binAddr = Integer.toBinaryString(number);

        StringBuffer buf = new StringBuffer(binAddr);

        while (buf.length() < length) {
            buf.insert(0, '0');
        }
        return buf.toString();
    }


}

