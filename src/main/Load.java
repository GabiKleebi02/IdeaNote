package main;

import java.io.*;

public class Load {
    private static File file;
    private static BufferedReader reader;
    private static DetailPanel detail;
    private static MetaPanel meta;

    public void load(File file) {
        meta = Main.frame.metaPanel;
        detail = Main.frame.detailPanel;

        Load.file = file;

        //Den BufferedReader erstellen und bei Fehler returnen
        if(!createReader()) {
            Main.frame.setInfoPanelError("Fehler beim Laden (Erstellen des Readers)");
            return;
        }

        //Die Daten lesen und bei Fehler returnen
        if(!readMeta() || !readDetail()) {
            try {
                reader.close();
                Main.frame.setInfoPanelError("Fehler beim Laden (Lesen der Daten)");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Main.frame.setInfoPanelError("Fehler beim Laden (Lesen der Daten & Schließen des Readers)");
                return;
            }
        }

        try {
            reader.close();
            Main.frame.setInfoPanelInfo("Datei erfolgreich geladen!");
        } catch (IOException e) {
            e.printStackTrace();
            Main.frame.setInfoPanelError("Fehler beim Laden! (Schließen des Readers, aber erfolgreich geladen)");
        }
    }

    private boolean createReader() {
        try {
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean readMeta() {
        //Reihenfolge: Titel, Autor, Pfad

        try {
            String title = reader.readLine().replace("Titel:", "");
            meta.setTitle(title);

            String authors = reader.readLine().replace("Autor:", "");
            meta.setAuthors(authors);

            String path = reader.readLine().replace("Pfad:", "");
            meta.setPath(path);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean readDetail() {
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] lineArr = line.split(":");

                //Die line zusammensetzen und den ersten Index entfernen, da es der Titel ist
                String title = lineArr[0];
                String text = "";

                for(int i = 1; i < lineArr.length; i++) {
                    text += lineArr[i];
                }

                //Ein neues Feld im DetailPanel hinzufügen
                detail.addPanel(title, text);
            }

            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
