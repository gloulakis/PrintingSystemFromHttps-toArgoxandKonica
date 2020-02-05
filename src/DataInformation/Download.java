/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInformation;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor.URL;

/**
 *
 * @author Georgios.Loulakis
 */
public class Download implements Runnable {
    String query;
    File out;
    public Download(String link, File out){
        this.query = link;
        this.out = out;
        
    }
    
    @Override
    public void run(){
        HttpURLConnection connection = null;
    
        try{
        connection = (HttpURLConnection) new URI(query).toURL().openConnection();
        double fileSize = (double)connection.getContentLengthLong();
        BufferedInputStream in = new BufferedInputStream (connection.getInputStream());
        FileOutputStream fos = new FileOutputStream(this.out);
        BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
        byte[] buffer = new byte [1024];
        double downloaded = 0.00;
        int read =0;
        double percentDownloaded = 0.00;
        while ((read = in.read(buffer,0,1024)) >=0){
            bout.write(buffer,0,read);
            downloaded += read;
            percentDownloaded = (downloaded*100)/fileSize;
            String percent = String.format("%.4f", percentDownloaded);
            System.out.println("Downloaded"+percent + "% of a file."); 
        }
        bout.close();
        in.close();
        System.out.println("Downloaded complete");
        }
        catch(IOException ex){
            ex.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            
        }
    }
}
