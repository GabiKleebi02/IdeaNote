package main;

import javax.swing.*;
import java.io.File;

public class Main {
    public static Frame frame;

    public static void main(String[] args) {

        //Das Look-And-Feel ändern
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //Das Frame erstellen
        frame = new Frame();
    }

}
