package Model;


import java.util.LinkedHashMap;

public class CacheSet {
    private LinkedHashMap<Integer, CacheEntry> entries;
    private Integer cacheIndex;

    public LinkedHashMap<Integer, CacheEntry> getEntries() {
        return entries;
    }

    public void setEntries(LinkedHashMap<Integer, CacheEntry> entries) {
        this.entries = entries;
    }

    public Integer getCacheIndex() {
        return cacheIndex;
    }

    public void setCacheIndex(Integer cacheIndex) {
        this.cacheIndex = cacheIndex;
    }

    public void initializeEntries(Integer type){
        entries = new LinkedHashMap<Integer, CacheEntry>(type , .99f, true);
        for (int i = 0; i < type; i++) {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.setSet(i);
            entries.put(i,cacheEntry);
        }

    }
}
