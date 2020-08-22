package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Saving {
    private static File file;
    private static BufferedWriter writer;
    private static MetaPanel meta;
    private static DetailPanel detail;

    public static void save() throws IOException {
        meta = Frame.metaPanel;
        detail = Frame.detailPanel;

        file = new File(meta.getPath() + "/" + meta.getTitle().replaceAll(" ", "_") + ".ideanote");
        file.createNewFile();



        //Writer erstellen und bei Fehler returnen
        if(!createWriter())
            return;

        //Daten speichern und bei Fehler returnen
        if(!saveMetaData() || !saveDetailData())
            return;

        writer.close();
    }

    private static boolean createWriter() {
        try {
            FileWriter writer = new FileWriter(file);
            Saving.writer = new BufferedWriter(writer);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean saveMetaData() {
        try {
            writer.write("Titel:" + meta.getTitle() + System.lineSeparator());
            writer.write("Autor:" + meta.getAuthors() + System.lineSeparator());
            writer.write("Pfad:" + meta.getPath() + System.lineSeparator());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean saveDetailData() {
        System.out.println("saveDetailData");

        try {
            for(InputPanel panel : detail.getDetailPanels()) {
                String title = panel.getTitle();
                String text = panel.getTextfieldText().replaceAll("\n", "");
                writer.write(title + ":" + text + System.lineSeparator());
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
