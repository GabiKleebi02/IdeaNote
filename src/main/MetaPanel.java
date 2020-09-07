package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MetaPanel extends JPanel {
    private InputPanel panelTitle, panelAuthors, panelPath;

    public MetaPanel() {
        setSize(new Dimension(Main.frame.screen.width, Main.frame.screen.height/3 * 2));

        //Das Layout erstellen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        //Das Layout konfigurieren
        setLayout(gridbag);
        setBorder(BorderFactory.createTitledBorder("Meta-Daten"));

        //Die Constraints konfigurieren
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        //Die einzelnen Panels erstellen
        panelAuthors = new InputPanel("Autor");
        panelAuthors.setTextfieldText("Hier steht der Autor");

        panelTitle = new InputPanel("Titel");
        panelTitle.setTextfieldText("Hier steht der Titel");

        panelPath = new InputPanel("Pfad");
        panelPath.setTextfieldText("Hier steht der Pfad");
//        panelLocation.add(createFileChooserForSaveLocation(panelLocation));

        //Die Panels hinzufügen
        gridbag.setConstraints(panelPath, constraints);
        add(panelPath);
        add(createFileChooserForSaveLocation(panelPath));

        constraints.gridy = 1;
        gridbag.setConstraints(panelTitle, constraints);
        add(panelTitle);

        constraints.gridy = 2;
        gridbag.setConstraints(panelAuthors, constraints);
        add(panelAuthors);
    }

    private JButton createFileChooserForSaveLocation(InputPanel panel) {
        GridBagConstraints constraints = new GridBagConstraints();

        JButton button = new JButton("Speicherort wählen");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int output = fc.showSaveDialog(Main.frame);

                if(output == JFileChooser.APPROVE_OPTION) {
                    panel.setTextfieldText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        });

        return button;
    }

    public String getTitle() {
        String title = panelTitle.getTextfieldText();
        title = title.replaceAll("\n", "");

        return title;
    }

    public String getAuthors() {
        String author = panelAuthors.getTextfieldText();
        author = author.replaceAll("\n", "");

        return author;
    }

    public String getPath() {
        String path = panelPath.getTextfieldText();
        path = path.replaceAll("\n", "");

        return path;
    }

    public void setTitle(String title) {
        panelTitle.setTextfieldText(title);
    }

    public void setPath(String path) {
        panelPath.setTextfieldText(path);
    }

    public void setAuthors(String authors) {
        panelAuthors.setTextfieldText(authors);
    }

}
