package ua.edu.ucu.apps;

import lombok.AllArgsConstructor;

import java.io.File;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@AllArgsConstructor
public class SmartDocument implements Document {
    @Override
    public String parse(String imagePath) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.x.x/share/");
        tesseract.setLanguage("eng");

        try {
            return tesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            e.printStackTrace();
            return "Error occurred while parsing the document.";
        }
    }
}