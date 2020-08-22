package main;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private JTextArea text;
    private String title;

    public InputPanel(String title) {
        this.title = title;

        //Das Layout erstellen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        //Die Constraints konfigurieren
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;

        //Das Panel konfigurieren
        setLayout(gridbag);
        setBorder(BorderFactory.createTitledBorder(title));

        //Das Textfeld erstellen und konfigurieren
        text = new JTextArea();
        text.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(text);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Das Textfeld hinzufügen
        constraints.gridx = 0;
        constraints.gridy = 0;
        gridbag.setConstraints(scroll, constraints);
        add(scroll);
    }

    public void setTextfieldText(String newText) {
        text.setText(newText);
    }

    public String getTextfieldText() {
        return text.getText();
    }

    public String getTitle() { return title; }

}
