package Validator;

import org.junit.platform.commons.util.StringUtils;

public class DataValidator {


    public int validateInt(String s){
        int number;
        try{
            number = Integer.parseInt(s);
        }catch (NumberFormatException e){
            throw new RuntimeException("field must be a integer number");
        }
        return number;
    }

    public int isPowerOfTwo(int n)
    {
        if(n==0){
            throw new RuntimeException("number should be power of 2");
        } else if((int)(Math.ceil((Math.log(n) / Math.log(2)))) !=
                (int)(Math.floor(((Math.log(n) / Math.log(2)))))){
            throw new RuntimeException("number should be power of 2");
        }
        return n;
    }

    public String isNotEmpty(String s){
        if (StringUtils.isBlank(s)) {
            throw new RuntimeException("All field must be filled with a value!");
        }
        return s;
    }

    public String isRadioBoxSelected(String s){
        if(s == "none"){
            throw new RuntimeException("Measurement unit not selected");
        }
        return s;
    }

    public String isByte(String s, int length) {
        String possibleCharacters = new String("abcdefABCDEF1234567890");
        if ((s.length() > length)) {
            throw new RuntimeException("Value is grater than the capacity of the memory");
        } if(StringUtils.isBlank(s)){
            throw new RuntimeException("Field is empty");
        }
        for (int i = 0; i < s.length(); i++) {
            if (possibleCharacters.indexOf(s.charAt(i)) == -1) {
                throw new RuntimeException("Wrong character");
            }
        }
        return s;
    }

    public int isGreaterThan2(int n){
        if(n == 2){
            throw new RuntimeException("Address length should be at least 4");
        }
        return n;
    }
}
