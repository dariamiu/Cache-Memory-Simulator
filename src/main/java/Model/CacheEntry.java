package Model;

import java.util.ArrayList;
import java.util.List;

public class CacheEntry {

    private Boolean valid;
    private Integer cacheTag;
    private List<Byte> cacheData;
    private Boolean dirty;
    private Integer set;

    public CacheEntry(Boolean valid, Integer cacheTag, List<Byte> cacheData, Boolean dirty) {
        this.valid = valid;
        this.cacheTag = cacheTag;
        this.cacheData = new ArrayList<>();
        this.cacheData = cacheData;
        this.dirty = dirty;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Integer getSet() {
        return set;
    }

    public CacheEntry() {
        this.valid = false;
        this.cacheTag = 999999;
        this.cacheData = null;
        this.dirty = false;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getCacheTag() {
        return cacheTag;
    }

    public void setCacheTag(Integer cacheTag) {
        this.cacheTag = cacheTag;
    }

    public List<Byte> getCacheData() {
        return cacheData;
    }

    public void setCacheData(List<Byte> cacheData) {
        this.cacheData = cacheData;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public String[] printEntry(){
     /*   System.out.println("valid " + valid);
        System.out.println("dirty " + dirty);
        System.out.println("tag " + cacheTag);
        System.out.print("data :");
        if (cacheData == null){
            System.out.println("----");
        }else{
            for (byte b : cacheData){
                System.out.print(Integer.toHexString(b  & 0xFF ) + " ");
            }
        }
        System.out.print('\n');*/
        String result[] = new String[5];
        if(valid) result[0] = "1";
            else result[0] = "0";
       if(dirty) result[1] = "1";
            else result[1] = "0";

       if(cacheTag == 999999){
           result[3] = "----";
       }else{
           result[3] = cacheTag.toString();
       }
        StringBuilder sb = new StringBuilder();
        if ( cacheData == null){
            result[4] =  "----";
        }else{
            for (byte b : cacheData){
                System.out.print(Integer.toHexString(b  & 0xFF ) + " ");
                String s = Integer.toHexString(b  & 0xFF);
                sb.append(s);
            }
            result[4] = sb.toString();
        }
        return result;
    }

}
