package Model;

import java.util.ArrayList;

public class CacheMemory {
    private Integer cacheSize;
    private Integer blockSize;
    private Integer type; // type : 1 = direct, 2 = 2 way, 4 = 4 way
    private ArrayList<CacheSet> sets;
    private Integer indexLength;
    private Integer tagLength;
    private Integer byteOffsetLength;

    public void initializeSets(){
        sets = new ArrayList<>();
        for (int i = 0 ; i < Math.pow(2,cacheSize)/Math.pow(2,blockSize)/type ; i++) {
            CacheSet cacheSet = new CacheSet();
            cacheSet.setCacheIndex(i);
            cacheSet.initializeEntries(type);
            sets.add(cacheSet);
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(Integer cacheSize) {
        this.cacheSize = cacheSize;
    }

    public Integer getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Integer blockSize) {
        this.blockSize = blockSize;
    }


    public ArrayList<CacheSet> getSets() {
        return sets;
    }


    public Integer getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(Integer indexLength) {
        this.indexLength = indexLength;
    }

    public Integer getTagLength() {
        return tagLength;
    }

    public void setTagLength(Integer tagLength) {
        this.tagLength = tagLength;
    }

    public Integer getByteOffsetLength() {
        return byteOffsetLength;
    }

    public void setByteOffsetLength(Integer byteOffsetLength) {
        this.byteOffsetLength = byteOffsetLength;
    }
}
