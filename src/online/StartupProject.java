/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online;
import Application.loginform;
import DataInformation.Download;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import DataInformation.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Georgios.Loulakis
 */
public class StartupProject {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws URISyntaxException{
       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginform().setVisible(true);
            }
        });
    }        
} 

