package Controller;

import Model.CacheSimulator;
import VIew.MainView;
import VIew.MemoryView;
import Validator.DataValidator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;



public class MainController {
    private CacheSimulator cacheSimulator;
    private MainView mainView;
    private Integer addressLength;
    private String mUBlock ;
    private String mUCache ;
    private Integer blockSize ;
    private Integer cacheSize ;
    private Integer type;
    private DataValidator validator;
    private MemoryView memoryView;

    public MainController(){
        mainView = new MainView();
        cacheSimulator = new CacheSimulator();
        validator = new DataValidator();
        initializeActionListeners();
    }


    private void initializeActionListeners(){
        mainView.generateButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //retrieving the data
                try{
                    addressLength =  validator.isGreaterThan2(validator.isPowerOfTwo(validator.validateInt(validator.isNotEmpty(mainView.getAddressLength()))));
                    mUBlock = validator.isRadioBoxSelected(mainView.measurementUnitBlock());
                    mUCache = validator.isRadioBoxSelected(mainView.measurementUnitCache());
                    blockSize = validator.isPowerOfTwo(validator.validateInt(validator.isNotEmpty(mainView.getBlockSize())));
                    cacheSize = validator.isPowerOfTwo(validator.validateInt(validator.isNotEmpty(mainView.getCacheSize())));
                    type = mainView.getTypeCache();

                    //step 1 : configuring memory parameters
                    cacheSimulator.setData(cacheSize,mUCache,blockSize,mUBlock,addressLength,type);

                    //step 2 : generating main memory
                    cacheSimulator.generateMainMemory(addressLength, cacheSimulator.getCacheMemory().getBlockSize());

                    mainView.createCachePanel(cacheSimulator.getCacheMemory(),type);
                    mainView.getReadButton().setEnabled(true);
                    mainView.getWriteButton().setEnabled(true);
                    mainView.getFlush().setEnabled(true);
                    memoryView = new MemoryView();
                    memoryView.createMemory(cacheSimulator.getMemory());
                }catch (RuntimeException ex){
                    mainView.displayErrorMessage(ex);
                }

            }
        });

        mainView.readButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryView.deleteRowSelection();
                    try{
                        int permittedAddressSize = 0;
                        permittedAddressSize = cacheSimulator.getAddressLength()/4;

                        String hexAddress = validator.isByte(mainView.getReadAddress(),permittedAddressSize);
                        String binaryAddress = cacheSimulator.getAddress(hexAddress, cacheSimulator.getAddressLength());
                        HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(binaryAddress);
                        Byte myByte = 0;
                        cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                                hexAddress,myByte,false);
                        mainView.createCachePanel(cacheSimulator.getCacheMemory(),cacheSimulator.getCacheMemory().getType());
                        String index = cacheSimulator.decimalToBinary(parsedAddress.get("index"),cacheSimulator.getCacheMemory().getIndexLength());
                        String tag = cacheSimulator.decimalToBinary(parsedAddress.get("tag"),cacheSimulator.getCacheMemory().getTagLength());
                        String offset = cacheSimulator.decimalToBinary(parsedAddress.get("byte offset"),cacheSimulator.getCacheMemory().getByteOffsetLength());
                        mainView.setIndex(index);
                        mainView.setTag(tag);
                        mainView.setOffset(offset);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("The address given" + "\n" + "in hex (");
                        stringBuilder.append(hexAddress + ") " + "is transformed" + "\n");
                        stringBuilder.append("in binary (");
                        stringBuilder.append(binaryAddress + ") and split into \n");
                        stringBuilder.append("tag " + tag + "\n");
                        stringBuilder.append("index " + index + "\n");
                        stringBuilder.append("offset " + offset + "\n");
                        stringBuilder.append("Go to the index " + Integer.parseInt(index,2 )+  " in the cache and check for the tag " + Integer.parseInt(tag,2)  + "\n");
                        if(cacheSimulator.getHitMiss()){
                            stringBuilder.append("tag is found, that is a HIT!! :) \n");
                        }else{
                            stringBuilder.append("tag is not found, that is a MISS!! :(" + "\n");
                            stringBuilder.append("Search for an available spot (V = 0)" + "\n");
                            if(cacheSimulator.getItWasReplaced()){
                                stringBuilder.append("No available spot, replace the the last recently used cache entry from\n" +
                                        "the index with the current entry\n");
                                if(cacheSimulator.getItWasDirty()){
                                    memoryView.createMemory(cacheSimulator.getMemory());
                                    memoryView.addRowSelection(cacheSimulator.getWriteBackBlock());
                                    stringBuilder.append("The entry that has to be replaced needs to be written back to memory!!(D==1)\n" +
                                            "Write it and set D back to = 0 \n");
                                }
                            }else {
                                stringBuilder.append("Available spot found \n");
                            }
                            stringBuilder.append("Write the block data that contains the address 0x" + hexAddress + " from main memory \n");
                        }
                        stringBuilder.append("DONE!!");
                        mainView.setTextArea1(stringBuilder.toString());
                        memoryView.addRowSelection(cacheSimulator.getCurrentlyProcessedBlock());
                    }catch (RuntimeException ex){
                        mainView.displayErrorMessage(ex);
                    }



            }
        });

        mainView.writeButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memoryView.deleteRowSelection();
                try{
                    int permittedAddressSize = 0;
                    permittedAddressSize = cacheSimulator.getAddressLength()/4;

                    String hexAddress = validator.isByte(mainView.getWriteAddress(),permittedAddressSize);
                    String binaryAddress = cacheSimulator.getAddress(hexAddress,cacheSimulator.getAddressLength());
                    HashMap<String, Integer> parsedAddress = cacheSimulator.parseAddress(binaryAddress);

                    int i = Integer.decode("0x" + validator.isByte(mainView.getWriteData(),2));
                    byte b = (byte) i;
                    Byte myByte = b;
                    cacheSimulator.cacheOpperation(parsedAddress.get("index"), parsedAddress.get("tag"),parsedAddress.get("byte offset"),
                            hexAddress,myByte,true);
                    mainView.createCachePanel(cacheSimulator.getCacheMemory(),cacheSimulator.getCacheMemory().getType());
                    String index = cacheSimulator.decimalToBinary(parsedAddress.get("index"),cacheSimulator.getCacheMemory().getIndexLength());
                    String tag = cacheSimulator.decimalToBinary(parsedAddress.get("tag"),cacheSimulator.getCacheMemory().getTagLength());
                    String offset = cacheSimulator.decimalToBinary(parsedAddress.get("byte offset"),cacheSimulator.getCacheMemory().getByteOffsetLength());
                    mainView.setIndex(index);
                    mainView.setTag(tag);
                    mainView.setOffset(offset);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("The address given" + "\n" + "in hex (");
                    stringBuilder.append(hexAddress + ") " + "is transformed" + "\n");
                    stringBuilder.append("in binary (");
                    stringBuilder.append(binaryAddress + ") and split into \n");
                    stringBuilder.append("tag " + tag + "\n");
                    stringBuilder.append("index " + index + "\n");
                    stringBuilder.append("offset " + offset + "\n");
                    stringBuilder.append("Go to the index " + Integer.parseInt(index,2) +  " in the cache and check for the tag " + Integer.parseInt(tag,2)  + "\n");
                    if(cacheSimulator.getHitMiss()){
                        stringBuilder.append("tag is found, that is a HIT!! :) \n");
                    }else{
                        stringBuilder.append("tag is not found, that is a MISS!! :(" + "\n");
                        stringBuilder.append("Search for an available spot (V = 0)" + "\n");
                        if(cacheSimulator.getItWasReplaced()){
                            stringBuilder.append("No available spot, replace the the last recently used cache entry from\n" +
                                    "the index with the current entry\n");
                            if(cacheSimulator.getItWasDirty()){
                                memoryView.createMemory(cacheSimulator.getMemory());
                                memoryView.addRowSelection(cacheSimulator.getWriteBackBlock());
                                stringBuilder.append("The entry that has to be replaced needs to be written back to memory!!(D==1)\n" +
                                        "Write it and set D back to = 0 \n");
                            }
                        }else {
                            stringBuilder.append("Available spot found \n");
                        }
                        stringBuilder.append("Write the block data that contains the address " + hexAddress + " from main memory \n");
                        stringBuilder.append("Now write the data " + mainView.getWriteData() + " at offset " + Integer.parseInt(offset,2) + " and set the dirty D to 1\n");
                    }
                    stringBuilder.append("DONE!!");
                    mainView.setTextArea1(stringBuilder.toString());
                    memoryView.addRowSelection(cacheSimulator.getCurrentlyProcessedBlock());
                }catch (RuntimeException ex){
                    mainView.displayErrorMessage(ex);
                }



            }
        });

        mainView.flushButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cacheSimulator.setData(cacheSize,mUCache,blockSize,mUBlock,addressLength,type);
                mainView.createCachePanel(cacheSimulator.getCacheMemory(),type);
                mainView.setIndex("");
                mainView.setTag("");
                mainView.setOffset("");
            }
        });
    }


}
