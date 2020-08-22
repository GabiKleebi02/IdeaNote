package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame {
    public static Dimension screen;
    public static MetaPanel metaPanel;
    public static DetailPanel detailPanel;

    public Frame() {
        super("IdeaNote");
        setMinimumSize(new Dimension(750, 480));
        screen = Toolkit.getDefaultToolkit().getScreenSize();

        //Das Layout erstellen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        //Das Frame konfigurieren
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(gridbag);

        //Die Panels erstellen
        metaPanel = new MetaPanel();
        detailPanel = new DetailPanel();

        //Die Constraints konfigurieren
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 1;
        constraints.gridx = 0;

        //Das MetaPanel hinzufügen
        constraints.gridy = 0;
        constraints.weighty = 0.5;
        JScrollPane scrollMeta = new JScrollPane(metaPanel);
        gridbag.setConstraints(metaPanel, constraints);
        add(metaPanel);

        //Das DetailPanel hinzufügen
        constraints.gridy = 1;
        constraints.weighty = 4;
        JScrollPane scrollDetails = new JScrollPane(detailPanel);
        scrollDetails.getVerticalScrollBar().setUnitIncrement(10);
        gridbag.setConstraints(scrollDetails, constraints);
        add(scrollDetails);

        //Den Button zum Speichern hinzufügen
        JButton saveButton = createSaveButton();
        constraints.gridy = 2;
        constraints.weighty = 0.5;
        gridbag.setConstraints(saveButton, constraints);
        add(saveButton);

        //Die Menüleiste hinzufügen
        addMenuBar();

        //Das Frame öffnen
        setVisible(true);
    }

    private JButton createSaveButton() {
        JButton button = new JButton("Speichern");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Saving.save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        return button;
    }

    private void addMenuBar() {
        //Die MenuBar erstellen
        JMenuBar menuBar = new JMenuBar();
        menuBar.setForeground(Color.DARK_GRAY);

        //Das Datei-Menu erstellen
        JMenu fileMenu = new JMenu("Datei");

        //Das Item zum Speichern erstellen
        JMenuItem fileSaveItem = new JMenuItem("Speichern");
        fileSaveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Saving.save();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        //Das Item zum Öffnen erstellen
        JMenuItem fileOpenItem = new JMenuItem("Öffnen");
        fileOpenItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("IdeaNote", "ideanote"));
                int output = fc.showOpenDialog(null);

                if(output == JFileChooser.APPROVE_OPTION) {
                    Load.load(fc.getSelectedFile());
                }
            }
        });

        //Alles zum Datei-Eintrag hinzufügen
        fileMenu.add(fileSaveItem);
        fileMenu.add(fileOpenItem);

        //Alles zur MenuBar hinzufügen
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

}
