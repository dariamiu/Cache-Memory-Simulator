package VIew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InitialView extends JFrame {
    private JTextArea instructions = new JTextArea(20,20);
    private JButton startApp  = new JButton("Start");;
    private JPanel panel = new JPanel();

    public InitialView() {

        this.setSize(500, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        initialize();
        this.setContentPane(panel);
        this.setVisible(true);
    }
    private void initialize(){
        panel.setLayout(null);
        panel.setBackground(new Color(218,165,238));
        startApp.setBounds(200,270,70,20);
        panel.add(startApp);

        instructions = new JTextArea(10,30);
        instructions.setBounds(50,10,400,200);
        instructions.setEditable(false);
        panel.add(instructions);
    }

    public void setStartAppActionListener(final ActionListener actionListener){
        startApp.addActionListener(actionListener);
    }

    public void setInstructions(String text){
        instructions.setText(text);
    }

}
