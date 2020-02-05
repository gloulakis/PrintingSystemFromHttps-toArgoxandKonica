/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testPackage;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

/**
 *
 * @author Georgios.Loulakis
 */
public class PrinterSetup {
     public static void main(String[] args) throws Exception
    {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();
        pjob.setPrintable(null, pf);

        if (pjob.printDialog()) {
          pjob.print();
        }
    }
}
