package VIew;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class MemoryView extends JFrame {

    private JPanel panel = new JPanel();
    private JScrollPane scrollPane;
    private JTable table;

    public MemoryView() {
        this.setSize(600, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initialize();
        this.setContentPane(panel);
        this.setVisible(true);
    }
    private void initialize(){
        panel.setLayout(null);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(20,20,560,760);
        panel.add(scrollPane);
    }

    public void deleteRowSelection(){
        table.clearSelection();
    }
    public void addRowSelection(int block){
        if(block > 0){
            table.addRowSelectionInterval(block, block);
            scrollPane.getViewport().add(table);
        }
    }
    public void createMemory(HashMap<Integer, List<Byte>> memory){
        table = new JTable();
        int size = memory.get(0).size();
        String columnNames[] = new String[size+1];

        columnNames[0] = "";
        for (int i = 1; i < size + 1; i++) {
            columnNames[i] = (String.valueOf(i-1));
        }

        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        for (Integer key: memory.keySet()){
            System.out.print(key+ " : " );
            String result[] = new String[size+1];
            result[0] = "block " + key;
            int i = 1;
            for (byte b : memory.get(key)){
                String by = Integer.toHexString(b  & 0xFF );
                result[i] = by;
                i++;
            }
            model.addRow(result);
            System.out.print('\n');
        }

        table = new JTable(model);
        table.setBackground(new Color(218,165,238));

        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(100);

        for (int i = 1; i < size + 1; i++) {
            tcm.getColumn(i).setPreferredWidth(50);
        }
        table.setColumnModel(tcm);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.white);
        table.getColumnModel().getColumn(0).setCellRenderer(renderer);
        scrollPane.getViewport().add(table);

    }
}

