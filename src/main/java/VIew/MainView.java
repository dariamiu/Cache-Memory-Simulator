package VIew;

import Model.CacheEntry;
import Model.CacheMemory;
import Model.CacheSet;
import org.junit.platform.commons.util.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainView extends JFrame {
    private JTextArea textArea1;
    private JComboBox typeComboBox;
    private JButton generateButton;
    private JTextField addressLengthText;
    private JTextField cacheSizeText;
    private JTextField blockSizeText;
    private JTextField offsetText;
    private JTextField indexText;
    private JTextField tagText;
    private JLabel offsetLabel;
    private JLabel tagLabel;
    private JLabel indexLabel;
    private JTextField readAddressText;
    private JTextField writeAddressText;
    private JButton readButton;
    private JButton writeButton;
    private JTextField writeDataText;
    private JLabel addressLengthLabel;
    private JLabel cacheSizeLabel;
    private JLabel blockSizeLabel;
    private JLabel readAddressLabel;
    private JLabel writeAddressLabel;
    private JLabel writeDataLabel;
    private JPanel panel1 = new JPanel();
    private JRadioButton cacheByteRadioButton;
    private JRadioButton cacheKBRadioButton;
    private JRadioButton blockByteRadioButton;
    private JRadioButton blockKBRadioButton;
    private JScrollPane pane;
    private JScrollPane pane4;
    private JScrollPane pane2;
    private JScrollPane pane3;
    private JLabel cacheType;
    private JButton flush;
    private  JScrollPane text;

    public MainView() {
        this.setSize(1300, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        createUIComponents();
        this.setContentPane(panel1);
        this.setVisible(true);
    }

    /**
     * Method to display the errors as messages to the user when the validation of the input fails
     * @param exception message about the error
     */
    public void displayErrorMessage(Exception exception) {
        if (exception != null) {
            String message = exception.getMessage();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to display information message
     * @param message message to display
     */
    public void displayInformationMessage(String message) {
        if (!StringUtils.isBlank(message)) {
            JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setIndex(String index){
        this.indexText.setText(index);
    }

    public void setTag(String tag){
        this.tagText.setText(tag);
    }

    public void setOffset(String offset){
        this.offsetText.setText(offset);
    }
    public void generateButtonAction(final ActionListener actionListener){
        generateButton.addActionListener(actionListener);
    }

    public void readButtonAction(final ActionListener actionListener){
        readButton.addActionListener(actionListener);
    }

    public void writeButtonAction(final ActionListener actionListener){
        writeButton.addActionListener(actionListener);
    }

    public void flushButtonAction(final ActionListener actionListener){
        flush.addActionListener(actionListener);
    }

    public String getAddressLength(){
        return addressLengthText.getText();
    }

    public String getCacheSize(){
        return cacheSizeText.getText();
    }

    public String getBlockSize(){
        return  blockSizeText.getText();
    }

    public String getReadAddress() { return readAddressText.getText();}

    public String getWriteAddress(){ return writeAddressText.getText();}

    public String getWriteData() { return  writeDataText.getText();}

    public void setTextArea1(String s){
        textArea1.revalidate();
        textArea1.setText(s);
    }

    public String measurementUnitCache(){
        if(cacheByteRadioButton.isSelected()){
          return "b";
        }
        if(cacheKBRadioButton.isSelected()){
           return "kb";
        }
        return "none";
    }

    public String measurementUnitBlock(){
        if(blockByteRadioButton.isSelected()){
            return "b";
        }
        if(blockKBRadioButton.isSelected()){
            return "kb";
        }
        return "none";
    }

    public Integer getTypeCache(){
       int type =  switch (typeComboBox.getSelectedItem().toString()){
            case "direct mapped" -> 1;
            case "2-way" -> 2;
            case "4-way" -> 4;
            default -> 0;
        };
        return type;
    }


    private void createUIComponents() {
        panel1.setLayout(null);
        createDataPanel();
        createTextPanel();
        createAddressPanel();
    }

    private void createAddressPanel(){
        indexText = new JTextField();
        tagText = new JTextField();
        offsetText = new JTextField();

        indexText.setEditable(false);
        tagText.setEditable(false);
        offsetText.setEditable(false);

        tagText.setBounds(380,30,200,20);
        indexText.setBounds(580,30,200,20);
        offsetText.setBounds(780,30,200,20);

        indexLabel = new JLabel("index");
        tagLabel = new JLabel("tag");
        offsetLabel = new JLabel("offset");

        tagLabel.setBounds(380,10,200,20);
        indexLabel.setBounds(580,10,200,20);
        offsetLabel.setBounds(780,10,200,20);

        panel1.add(indexLabel);
        panel1.add(indexText);
        panel1.add(tagText);
        panel1.add(tagLabel);
        panel1.add(offsetLabel);
        panel1.add(offsetText);

    }
    private void createDataPanel(){


        addressLengthLabel = new JLabel("Address Length");
        addressLengthLabel.setBounds(30,55,100,25);
        addressLengthText = new JTextField();
        addressLengthText.setBounds(125,55,100,25);
        panel1.add(addressLengthLabel);
        panel1.add(addressLengthText);

        cacheSizeLabel = new JLabel("Cache Size");
        cacheSizeLabel.setBounds(30,100,100,25);
        cacheSizeText = new JTextField();
        cacheSizeText.setBounds(125,100,100,25);
        cacheByteRadioButton = new JRadioButton("B");
        cacheKBRadioButton = new JRadioButton("KB");
        cacheByteRadioButton.setBounds(245,100,40,20);
        cacheKBRadioButton.setBounds(295,100,60,20);
        ButtonGroup cacheButtonGroup = new ButtonGroup();
        cacheButtonGroup.add(cacheByteRadioButton);
        cacheButtonGroup.add(cacheKBRadioButton);
        panel1.add(cacheSizeLabel);
        panel1.add(cacheSizeText);
        panel1.add(cacheByteRadioButton);
        panel1.add(cacheKBRadioButton);

        blockSizeLabel = new JLabel("Block Size");
        blockSizeLabel.setBounds(30,140,100,25);
        blockSizeText = new JTextField();
        blockSizeText.setBounds(125,140,100,25);
        blockByteRadioButton = new JRadioButton("B");
        blockKBRadioButton = new JRadioButton("KB");
        blockByteRadioButton.setBounds(245,140,40,20);
        blockKBRadioButton.setBounds(295,140,60,20);
        ButtonGroup blockButtonGroup = new ButtonGroup();
        blockButtonGroup.add(blockByteRadioButton);
        blockButtonGroup.add(blockKBRadioButton);
        panel1.add(blockByteRadioButton);
        panel1.add(blockKBRadioButton);
        panel1.add(blockSizeLabel);
        panel1.add(blockSizeText);


        typeComboBox = new JComboBox();
        typeComboBox.setBounds(125,185,100,30);
        cacheType = new JLabel("Cache type");
        cacheType.setBounds(30,185,100,25);
        typeComboBox.addItem("direct mapped");
        typeComboBox.addItem("2-way");
        typeComboBox.addItem("4-way");
        panel1.add(typeComboBox);
        generateButton = new JButton("Create");
        generateButton.setBounds(130,225,85,30);
        panel1.add(cacheType);
        panel1.add(generateButton);
        readAddressLabel = new JLabel("Read Address");
        readAddressLabel.setBounds(30,285,100,25);
        readAddressText = new JTextField();
        readAddressText.setBounds(125,285,100,25);
        readButton = new JButton("Read");
        readButton.setBounds(130,325,85,30);
        panel1.add(readAddressLabel);
        panel1.add(readAddressText);
        panel1.add(readButton);

        writeAddressLabel = new JLabel("Write Address");
        writeAddressLabel.setBounds(30,380,100,25);
        writeAddressText = new JTextField();
        writeAddressText.setBounds(125,380,100,25);
        writeDataLabel = new JLabel("Write Data");
        writeDataLabel.setBounds(30,420,100,25);
        writeDataText = new JTextField();
        writeDataText.setBounds(125,420,100,25);
        writeButton = new JButton("Write");
        writeButton.setBounds(130,460,85,30);
        panel1.add(writeAddressLabel);
        panel1.add(writeAddressText);
        panel1.add(writeDataLabel);
        panel1.add(writeDataText);
        panel1.add(writeButton);

        flush = new JButton("Flush");
        flush.setBounds(130,515,85,40);
        panel1.add(flush);

        readButton.setEnabled(false);
        writeButton.setEnabled(false);
        flush.setEnabled(false);
    }

    private void createTextPanel(){

        textArea1 = new JTextArea(30,30);
        textArea1.setEditable(false);
        panel1.add(textArea1);
        text = new JScrollPane(textArea1);
        text.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        text.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        text.setBounds(380,430,900,120);
        panel1.add(text);
    }

    private JTable[] createTable(CacheMemory cacheMemory, Integer type){
        int size = 5;
        String columnNames[] = new String[size];
        columnNames[0] = "V";
        columnNames[1] = "D";
        columnNames[2] = "index";
        columnNames[3] = "tag";
        columnNames[4] = "data";


        JTable tables[] = new JTable[type];
        DefaultTableModel models[] = new DefaultTableModel[type];
        for (int i = 0; i < type; i++) {
            models[i] = new DefaultTableModel(columnNames,0);
        }
        ArrayList<CacheSet> sets = cacheMemory.getSets();
        for (CacheSet set : sets) {
            HashMap<Integer, CacheEntry> entries = set.getEntries();
            for (CacheEntry entry : entries.values()) {
                int integer = entry.getSet();
                String result[] = new String[size];
                result = entry.printEntry();
                result[2] = set.getCacheIndex().toString();
                models[integer].addRow(result);
            }
        }

        for (int i = 0; i < type; i++) {
            tables[i] = new JTable(models[i]);

        }

        TableColumnModel tcm = tables[0].getColumnModel();
        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(50);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(200);
        for (int i = 0; i < type; i++) {
            tables[i].setColumnModel(tcm);
        }

        return tables;

    }

    public void createCachePanel(CacheMemory cacheMemory, Integer type){
        JTable tables[] = createTable(cacheMemory,type);
        if(type == 1){
            pane = new JScrollPane();
            pane.setBounds(380,55,600,300);
            pane.getViewport().add(tables[0]);
            panel1.add(pane);
        }
        else if(type == 2){
            pane = new JScrollPane();
            pane.setBounds(380,55,300,300);
            pane.getViewport().add(tables[0]);
            panel1.add(pane);
            pane2 = new JScrollPane();
            pane2.setBounds(680,55,300,300);
            pane2.getViewport().add(tables[1]);
            panel1.add(pane2);
        }
        else if(type == 4){
            pane = new JScrollPane();
            pane.setBounds(380,55,200,300);
            pane.getViewport().add(tables[0]);
            panel1.add(pane);
            pane2 = new JScrollPane();
            pane2.setBounds(580,55,200,300);
            pane2.getViewport().add(tables[1]);
            panel1.add(pane2);
            pane3 = new JScrollPane();
            pane3.setBounds(780,55,200,300);
            pane3.getViewport().add(tables[2]);
            panel1.add(pane3);
            pane4 = new JScrollPane();
            pane4.setBounds(980,55,200,300);
            pane4.getViewport().add(tables[3]);
            panel1.add(pane4);
        }

    }

    public JButton getReadButton() {
        return readButton;
    }

    public JButton getWriteButton() {
        return writeButton;
    }

    public JButton getFlush() {
        return flush;
    }
}
