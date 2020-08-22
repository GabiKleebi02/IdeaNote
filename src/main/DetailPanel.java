package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;

public class DetailPanel extends JPanel implements ActionListener {
    private JTextField textfieldOwnText;
    private ButtonGroup chooseButtonGroup;

    private ArrayList<InputPanel> detailPanels;

    private GridBagLayout gridbag;
    private GridBagConstraints constraints;

    public DetailPanel() {
        detailPanels = new ArrayList<InputPanel>();

        //Das Layout erstellen
        gridbag = new GridBagLayout();
        constraints = new GridBagConstraints();

        //Die Constraints konfigurieren
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        //Das Panel konfigurieren
        setLayout(gridbag);
        setBorder(BorderFactory.createTitledBorder("Details"));

        //Das Panel zum Hinzufügen weiterer Panels erstellen
        JPanel addingPanel = createAddingPanel();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        gridbag.setConstraints(addingPanel, constraints);
        add(addingPanel);
        constraints.fill = GridBagConstraints.BOTH;
    }

    private JPanel createAddingPanel() {

        //Das Layout erstellen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();

        //Die Constraints konfigurieren
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridy = 0;

        //Das Panel konfigurieren
        JPanel panel = new JPanel();
        panel.setLayout(gridbag);
        panel.setBorder(BorderFactory.createTitledBorder("Neues Feld hinzufügen"));

        //Die Buttons vorbereiten
        String[] buttonNames = {"Beschreibung", "Notiz", "Nachtrag", "Link"};
        chooseButtonGroup = new ButtonGroup();

        //Die Buttons erstellen
        for (int i = 0; i < buttonNames.length; i++) {
            constraints.gridx = i;

            JRadioButton button = new JRadioButton(buttonNames[i]);
            chooseButtonGroup.add(button);

            //Den Button hinzufügen
            gridbag.setConstraints(button, constraints);
            panel.add(button);
        }

        //Das Panel für den eigenen Text hinzufügen
        JPanel ownTextPanel = new JPanel();
        ownTextPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 0));
        JRadioButton ownTextButton = new JRadioButton("Eigener Text");
        chooseButtonGroup.add(ownTextButton);
        textfieldOwnText = new JTextField();
        textfieldOwnText.setColumns(25);
        ownTextPanel.add(ownTextButton);
        ownTextPanel.add(textfieldOwnText);
        panel.add(ownTextPanel);

        //Den Button zum Hinzufügen hinzufügen
        JButton hinzufügen = new JButton("Hinzufügen");
        hinzufügen.addActionListener(this);


        panel.add(hinzufügen);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String title = "";
        //Den ausgewählten Button bestimmen
        for (Enumeration<AbstractButton> buttons = chooseButtonGroup.getElements(); buttons.hasMoreElements();) {
            JRadioButton currentButton = (JRadioButton) buttons.nextElement();

            if (currentButton.isSelected())
                title = currentButton.getText();
        }

        if(title.equals("")) {  //Kein Button war ausgewählt, also wird *Eigener Text* geprüft
            if(textfieldOwnText.getText().equals(""))
                return;

            title = textfieldOwnText.getText();
        }

        addPanel(title, "");
    }

    public void addPanel(String title, String text) {
        InputPanel panel = new InputPanel(title);
        panel.setTextfieldText(text);

        constraints.gridy += 1;

        gridbag.setConstraints(panel, constraints);
        add(panel);

        detailPanels.add(panel);

        revalidate();
    }

    public ArrayList<InputPanel> getDetailPanels() {
        return detailPanels;
    }
}
