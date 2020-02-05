/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import com.sun.javafx.font.FontFactory;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 *
 * @author Georgios.Loulakis
 */
public class invoice {
    public static void inv() throws IOException {
      PDDocument document = new PDDocument();
      document.addPage(new PDPage());
      document.save("C:\\Users\\Georgios.Loulakis\\Downloads\\BlankPdf.pdf");
      document.close();
      
     
    }
    
    
    
    
    public static void main(String[] arg) throws IOException{
        try {
            inv();
        } catch (IOException ex) {
            Logger.getLogger(invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
       
          File file = new File("C:\\Users\\Georgios.Loulakis\\Downloads\\BlankPdf.pdf");  
          PDDocument doc = PDDocument.load(file);  
          PDPage page = new PDPage(PDRectangle.A4);
          doc.addPage(page);
          float startY = page.getMediaBox().getHeight() - 150;
          int startX = 56;

          PDPageContentStream contentStream = new PDPageContentStream(doc, page); 
          
          
      
         

        contentStream.setFont(PDType1Font.HELVETICA, 8.0f);  
        contentStream.beginText();  
       // contentStream.newLineAtOffset(startX, startY - (table.getHeight() + 22));
         

            String text = "Hi!!!";  

        contentStream.showText(text);        

        contentStream.endText();  

              System.out.println("New Text Content is added in the PDF Document.");  

        contentStream.close();  

        doc.save(new File("C:\\Users\\Georgios.Loulakis\\Downloads\\BlankPdf.pdf"));  

        doc.close();  
    }  
}

