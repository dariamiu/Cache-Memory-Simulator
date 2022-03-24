package Controller;

import Model.CacheSimulator;
import VIew.InitialView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InitialViewController {
    private InitialView initialView;

    public InitialViewController(){
        initialView = new InitialView();
        start();
    }
    public void start(){
        initialView.setStartAppActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainController();
            }
        });

        try{
            initialView.setInstructions(getText());
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public String getText() throws IOException {
        String text = Files.readString(Paths.get("instructions.txt"));
        return text;
    }


}
