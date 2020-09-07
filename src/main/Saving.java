package main;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.soap.Detail;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Saving {
    private static File file;
    private static BufferedWriter writer;
    private static MetaPanel meta;
    private static DetailPanel detail;

    public void save() throws IOException {
        meta = Main.frame.metaPanel;
        detail = Main.frame.detailPanel;

        file = new File(getPathWithoutExtension() + ".ideanote");
        file.mkdir();
        file.createNewFile();



        //Writer erstellen und bei Fehler returnen
        if(!createWriter()) {
            Main.frame.setInfoPanelError("Fehler beim Speichern! (Erstellen des Writers)");
            return;
        }

        //Daten speichern und bei Fehler returnen
        if(!saveMetaData() || !saveDetailData()) {
            Main.frame.setInfoPanelError("Fehler beim Speichern! (Schreiben der Daten)");
            return;
        }

        writer.close();
        Main.frame.setInfoPanelInfo("Datei erfolgreich gespeichert!");
    }

    private String getPathWithoutExtension() {
        return meta.getPath() + "/" + meta.getTitle().replaceAll(" ", "_");
    }

    private boolean createWriter() {
        try {
            FileWriter writer = new FileWriter(file);
            Saving.writer = new BufferedWriter(writer);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveMetaData() {
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

    private boolean saveDetailData() {
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
    //WRITE_WORD FUNKTIONEN
    final String spaceSmall = "  ", spaceBig = "    ", spaceHuge = spaceBig + spaceBig;

    public void writeWord() throws IOException {
        meta = Main.frame.metaPanel;
        detail = Main.frame.detailPanel;

        XWPFDocument doc = new XWPFDocument();
        File file = new File(getPathWithoutExtension() + ".docx");
        FileOutputStream outputStream = new FileOutputStream(file);

        //Die Tabelle erstellen
        XWPFTable table = doc.createTable();

        //In die erste Reihe Schreiben
        XWPFTableRow rowOne = table.getRow(0);
        XWPFTableCell[] cellsOne = {rowOne.getCell(0),
                                    rowOne.addNewTableCell(),
                                    rowOne.addNewTableCell(),
                                    rowOne.addNewTableCell() };

        cellsOne[0].getCTTc().addNewTcPr().addNewShd().setFill("D0CECE");
        setTableCellText(cellsOne[0], "Autoren:", false);

        cellsOne[1].getCTTc().addNewTcPr().addNewShd().setFill("E7E6E6");
        setTableCellText(cellsOne[1], meta.getAuthors(), true);

        cellsOne[2].getCTTc().addNewTcPr().addNewShd().setFill("D0CECE");
        setTableCellText(cellsOne[2], "Datum:", false);

        cellsOne[3].getCTTc().addNewTcPr().addNewShd().setFill("E7E6E6");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        LocalDateTime time = LocalDateTime.now();
        setTableCellText(cellsOne[3], dtf.format(time) + " Uhr", true);

        //die Breite der Zellen setzen, da die erste Reihe die Breite angibt
        cellsOne[0].getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3072));
        cellsOne[1].getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(4096));
        cellsOne[2].getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3072));
        cellsOne[3].getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(4096));

        //In die zweite Reihe schreiben
        XWPFTableRow rowTwo = table.insertNewTableRow(1);
        XWPFTableCell[] cellsTwo = {rowTwo.addNewTableCell(),   //Jede Reihe muss vier Spalten haben, da die Reihe mit den meisten Spalten 4 Spalten hat
                                    rowTwo.addNewTableCell(),
                                    rowTwo.addNewTableCell(),
                                    rowTwo.addNewTableCell()};

        cellsTwo[0].getCTTc().addNewTcPr().addNewShd().setFill("D0CECE");
        setTableCellText(cellsTwo[0], "Titel:", false);

        cellsTwo[1].getCTTc().addNewTcPr().addNewShd().setFill("E7E6E6");
        setTableCellText(cellsTwo[1], meta.getTitle(), true);

        cellsTwo[1].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        cellsTwo[2].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        cellsTwo[3].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

        //Die ganzen Details hinzufügen
        int currentRow = 2;

        for(InputPanel detail : detail.getDetailPanels()) {
            String title = detail.getTitle();
            String text = detail.getTextfieldText();

            //die Reihe und Zellen erstellen
            XWPFTableRow row = table.insertNewTableRow(currentRow);
            XWPFTableCell cells[] = {row.addNewTableCell(),
                                     row.addNewTableCell(),
                                     row.addNewTableCell(),
                                     row.addNewTableCell()};

            //den Titel in die erste Zelle schreiben
            cells[0].getCTTc().addNewTcPr().addNewShd().setFill("D0CECE");
            setTableCellText(cells[0], title, false);

            //den Text in die zweite Zelle schreiben
            cells[1].getCTTc().addNewTcPr().addNewShd().setFill("E7E6E6");
            cells[1].setText(text);

            //den hmerge hinzufügen
            cells[1].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            cells[2].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            cells[3].getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);

            currentRow++;
        }

        doc.write(outputStream);
        outputStream.close();

    }

    private void setTableCellText(XWPFTableCell cell, String text, boolean huge) {
        if(huge) {
            cell.setText(spaceSmall + text + spaceHuge);
        }

        else {
            cell.setText(spaceSmall + text + spaceBig);
        }
    }

}
