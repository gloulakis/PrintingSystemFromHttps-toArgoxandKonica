/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument; 
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author Georgios.Loulakis
 */
public class createPDFfile {
    public static void main(String[] args) throws IOException {
        String filename = "sample.pdf";
        String photo = "C:\\Users\\Georgios.Loulakis\\Documents\\MyProject\\Online\\otherfile\\Logo\\kidso Logo.png";
        String message = "/...................test................/";
        
        
        addimage(filename,photo);
        writeinPDFfile(filename,message,10);

    }
    
    
    public static void writeinPDFfile(String filename,String message,int setfont) throws IOException{
         PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage();
            doc.addPage(page);
            
            PDFont font = PDType1Font.HELVETICA_BOLD;
 
            PDPageContentStream contents = new PDPageContentStream(doc, page,AppendMode.APPEND, true);
            contents.beginText();
            contents.setFont(font, setfont);
            contents.newLineAtOffset(40, 700);
            contents.showText(message);
            contents.endText();
            contents.close();
            
            doc.save(filename);
        }
        finally {
            doc.close();
        }
    }
    
    public static void addimage(String filename,String photo ) throws IOException{
      File file = new File(filename);
      PDDocument doc = PDDocument.load(file);
      PDPage page = doc.getPage(0);
      PDImageXObject pdImage = PDImageXObject.createFromFile(photo,doc);
      PDPageContentStream contents = new PDPageContentStream(doc, page, AppendMode.APPEND, true);
      contents.drawImage(pdImage, 70, 250);
      System.out.println("Image inserted");
      contents.close();		
      doc.save(filename);
      doc.close();
        
    }
    
    
    
}
