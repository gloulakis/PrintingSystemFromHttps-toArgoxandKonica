/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Application;

import DataInformation.Product;
import DataInformation.ProductDAO;
import com.sun.xml.fastinfoset.alphabet.BuiltInRestrictedAlphabets;
import java.awt.Color;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor.URL;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Date;
import java.util.stream.Stream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Fidelity;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterResolution;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.print.event.PrintServiceAttributeEvent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Georgios.Loulakis
 * 
 */

public final class mainform extends javax.swing.JFrame {
static int xx,yy;

    /**
     * Creates new form Application
     * @throws java.lang.ClassNotFoundException
     */

    public mainform() throws ClassNotFoundException {
       initComponents();
       setResizable(false);
       fileCreator("Waybill");
       fileCreator("Voucher");
       fileCreator("Stock Receipt");
       selectMultiRows();
       headerColor(mytable);
       headerColor(mytable1);
       today();
   }
    
    
    public void headerColor(JTable table){
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.getHSBColor(51, 51, 51));
        header.setForeground(Color.BLACK);
    } 
    
    public void writeToTXTfile(String value) throws IOException{
           try{
               File file = new File("C:\\Users\\Public\\Downloads\\Kidso\\Text.txt");
               FileWriter fw = new FileWriter(file.getAbsoluteFile());
               BufferedWriter bw = new BufferedWriter(fw);
               bw.write(value+",");
               bw.write("\n ");
               bw.close();
               fw.close();
           }catch(Exception ex){
                   ex.printStackTrace();
               }
        
    }
    
    
    private void JasperReport() throws ClassNotFoundException, JRException, SQLException{
        int column = 2;
        int [] row = mytable.getSelectedRows();
        
        for (int i = 0; i < row.length; i++) {
            String value = mytable.getModel().getValueAt(row[i], column).toString();
            System.out.println("The Value name is : "+value);
                    Connection con;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://192.168.21.3;databaseName=bers;user=sa;password=q2w3e4r%";
        con = DriverManager.getConnection(url);
        JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Public\\Downloads\\Kidso\\Stock Receipt\\report1.jrxml");
        String query =  "SELECT DISTINCT v.order_code ,v.comment,v.inputdate,v.primarycode,v.expunit, v.expqty,v.баркод,v.ONLcode\n" +
                        "FROM V_expedition_note AS v \n" +
                        "LEFT JOIN orders AS o\n" +
                        "ON v.order_code = o.order_code\n" +
                        "Where v.order_code = '"+value+"'AND ( o.StatusID = 1) AND v.inputdate = o.inputdate ";
        JRDesignQuery updateQuery = new JRDesignQuery();
        updateQuery.setText(query);
        jdesign.setQuery(updateQuery);
        JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            try{
                JasperPrint jprint=JasperFillManager.fillReport(jreport,null,con);
                //JasperExportManager.exportReportToPdfFile(jprint, filePathFolder("Stock Receipt") +"\\Files\\"+value+".pdf");
                //JasperViewer.viewReport(jprint);
                JasperPrintManager.printReport(jprint,false);
            }catch (Exception x){
                JOptionPane.showMessageDialog(null,"Внимание - " + value +" - "+x);
            } 
        }
    }


    public String filePath(){
        return "C:\\Users\\Public\\Downloads\\Kidso\\";
    }
    
    public String filePathFolder(String foldername){
        return "C:\\Users\\Public\\Downloads\\Kidso\\"+foldername+"\\";
    }
    
    public void fileCreator(String filename){
        File files = new File(filePath()+filename);
        if (!files.exists()) {
            if (files.mkdirs()) {
                System.out.println("Multiple directories are created!");
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }
    }
     
    public void saveSelectedRows(String foldername1,String filePrefix1,String foldername2,String filePrefix2) throws IOException{    
       TableModel model = mytable.getModel();
       int [] selectedRows = mytable.getSelectedRows();
       Object [] vucherRowCol = new Object[selectedRows.length];
       Object [] waybillCol = new Object[selectedRows.length];
       Object [] NameCol = new Object[selectedRows.length];
       
       boolean success = false;
       for (int i = 0; i < selectedRows.length;i++){
           vucherRowCol[i] = model.getValueAt(selectedRows[i], 5);
           waybillCol[i] = model.getValueAt(selectedRows[i], 6);
           NameCol[i] = model.getValueAt(selectedRows[i], 2);
           try {
               File file = new File("C:\\Users\\Public\\Downloads\\Kidso\\Text.txt");
               FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
               fw.write(NameCol[i].toString() + "\n ");
               fw.close();
           } catch (IOException ex) {
               Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
           }
        savePDF((String)NameCol[i],(String) vucherRowCol[i],filePathFolder(foldername1)+filePrefix1);
        savePDF((String)NameCol[i],(String) waybillCol[i],filePathFolder(foldername2)+filePrefix2);
        
        
       }  
    }  

    private void today() {                                             
         String Fromdate;
         String Todate;
         Format formatter;
         Date date = new Date();
         formatter = new SimpleDateFormat("yyyy-MM-dd");
         Fromdate = formatter.format(date);
         fromDate.setText(Fromdate);
         Date tomorrow = new Date(date.getTime() + (1000 * 60 * 60 * 24));
         Todate = formatter.format(tomorrow);
         toDate.setText(Todate);
    }      

    public void savePDF(String Name, String file , String path){
       TrustManager[] trustAllCerts = new TrustManager[]{
       new X509TrustManager() {
       public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return null; }
       public void checkClientTrusted(
        java.security.cert.X509Certificate[] certs, String authType) {
       }
          public void checkServerTrusted(
        java.security.cert.X509Certificate[] certs, String authType) {
        }
        }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
        }
         try
	{
            java.net.URL url = new java.net.URL(file);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(60000);
            connection.setReadTimeout(60000);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

            InputStream input = connection.getInputStream();


            File destination = new File(path+Name+".pdf");
            FileOutputStream output = new FileOutputStream(destination, false);

            byte[] buffer = new byte[2048];
            int read;


            while((read = input.read(buffer)) > -1)
            {
                output.write(buffer, 0, read);
            }			
            output.flush();
            output.close();
            input.close();
	}		
        catch (Exception e) 
        {
            e.printStackTrace();
        }  
   }
   
    public void selectMultiRows(){
      mytable.setRowSelectionAllowed(true);
      mytable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
   }
   
    public void SelectedRowsOrders(){    
       String ordercode;
       TableModel model = mytable.getModel();
       int [] selectedRows = mytable.getSelectedRows();
       Object [] vucherRowCol = new Object[selectedRows.length];
       Object [] waybillCol = new Object[selectedRows.length];
       Object [] NameCol = new Object[selectedRows.length];

       boolean success = false;
       for (int i = 0; i < selectedRows.length;i++){
           vucherRowCol[i] = model.getValueAt(selectedRows[i], 5);
           waybillCol[i] = model.getValueAt(selectedRows[i], 6);
           NameCol[i] = model.getValueAt(selectedRows[i], 2);
           String filename = (String) NameCol[i];
           System.out.println(filename);
           
           TableProducts(filename);
       }  
    }
     
    public void TableProducts(String ordercode){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            String url = "jdbc:sqlserver://192.168.21.3;databaseName=bers;user=sa;password=q2w3e4r%";
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT v.order_code ,v.comment,v.inputdate,v.primarycode,v.expunit, v.expqty,v.баркод,v.ONLcode\n" +
                                            "FROM V_expedition_note AS v \n" +
                                            "LEFT JOIN orders AS o\n" +
                                            "ON v.order_code = o.order_code\n" +
                                            "WHERE v.order_code = '"+ordercode+"'AND ( o.StatusID = 1) AND v.inputdate = o.inputdate");
            ResultSetMetaData rsmetadata = rs.getMetaData();
            int columns = rsmetadata.getColumnCount();
            int rowNum = 0;
            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            for (int i = 1; i <= columns; i++) {
                columns_name.addElement(rsmetadata.getColumnName(i));
            }
            dtm.setColumnIdentifiers(columns_name);
            while(rs.next()){
                data_rows = new Vector();
            for(int j=1;j<=columns;j++){
                data_rows.addElement(rs.getString(j));
            }
            dtm.addRow(data_rows);
            }
            mytable1.setModel(dtm);
            String Rowsnum = String.valueOf(mytable1.getRowCount());           
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
    public void cleanFolder(String path){
          try (Stream<Path> files = Files.list(Paths.get(path))) {
            long count = files.count();
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
              System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory " + listOfFiles[i].getName());
            }
            if (listOfFiles[i].isFile()){
                listOfFiles[i].delete();
            }
            else System.out.println("Failed to delete the file.");
          }
           } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, ex);
       }
    }
    
    public void cleanFile(String path, String filename){
          try (Stream<Path> files = Files.list(Paths.get(path))) {
            long count = files.count();
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                  System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                  System.out.println("Directory " + listOfFiles[i].getName());
                }
                if (listOfFiles[i].getName().equals(filename)){
                    listOfFiles[i].delete();
                }
            }
           } catch (IOException ex) {
           Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
       }
    } 
    
    public void printWaybill(String pathfile) throws IOException, PrinterException {
        File folder = new File(pathfile);
        File[] listOfFiles = folder.listFiles();
        int listcounter = listOfFiles.length;
        if (listcounter >0) {
             PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pfDefault = PrinterJob.getPrinterJob().defaultPage();
        Paper defaultPaper = new Paper();
        defaultPaper.setImageableArea(0, 0, 500, 600);
        pfDefault.setPaper(defaultPaper);
        pfDefault = PrinterJob.getPrinterJob().pageDialog(pfDefault);
        if (pjob.printDialog()) {
            for (int i = 0; i < listcounter; i++) {   
            PDDocument document = PDDocument.load(new File(pathfile+listOfFiles[i].getName()));
            pjob.setPageable(new PDFPageable(document));
                pfDefault = pjob.validatePage(pfDefault);
                Book book = new Book();
                book.append(new PDFPrintable(document), pfDefault, document.getNumberOfPages());
                pjob.setPageable(book);
                try{
                pjob.print();
                document.close();
                }
                catch (Exception pe) {pe.printStackTrace();}
                cleanFile(pathfile,listOfFiles[i].getName());
                } 
            }
        }
        else JOptionPane.showMessageDialog(null, "Empty Folder");
        }   
    
    public void printVoucher(String pathfile) throws IOException, PrinterException {
        File folder = new File(pathfile);
        File[] listOfFiles = folder.listFiles();
        int listcounter = listOfFiles.length;
        if (listcounter > 0) {
           PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pfDefault = PrinterJob.getPrinterJob().defaultPage();
        Paper defaultPaper = new Paper();
        defaultPaper.setImageableArea(0, 0, 1000, 1500);
        pfDefault.setPaper(defaultPaper);
        //pfDefault = PrinterJob.getPrinterJob().pageDialog(pfDefault);
            if (pjob.printDialog()) {

                for (int i = 0; i < listcounter; i++) {  
                    PDDocument document = PDDocument.load(new File(pathfile+listOfFiles[i].getName()));
                    try{
                    pjob.setPageable(new PDFPageable(document));
                    pfDefault = pjob.validatePage(pfDefault);
                    Book book = new Book();
                    book.append(new PDFPrintable(document), pfDefault, document.getNumberOfPages());
                    pjob.setPageable(book);
                    pjob.print();
                    document.close();
                    }
                    catch (Exception pe) {JOptionPane.showMessageDialog(null,"Внимание - " + document);}
                    cleanFile(pathfile,listOfFiles[i].getName());
                }
            }  
        }else JOptionPane.showMessageDialog(null, "Empty Folder");  
    }    
   
    public void Tableorders(){
       try {
            String d;
            d = fromDate.getText();
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            String url = "jdbc:sqlserver://192.168.21.3;databaseName=bers;user=sa;password=q2w3e4r%";
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT Distinct(O.Id) AS Order_ID,v.comment,O.order_code AS Code,O.StatusID,O.InputDate AS Input_Date,O.Address,O.billoflading\n" +
                                            "FROM Orders O\n" +
                                            "LEFT JOIN order_execution OE\n" +
                                            "ON O.DepositorID =  OE.DepositorID\n" +
                                            "LEFT JOIN V_expedition_note  v\n" +
                                            "ON v.order_code = o.order_code" +
                                            "WHERE (O.InputDate BETWEEN '"+fromDate.getText()+" "+fromTime.getText()+".0' AND '"+toDate.getText()+" "+toTime.getText()+".0' )\n" +
                                            "AND (OE.DepositorID = 217)AND ( O.StatusID = 1)AND (O.Address <>'') \n" +
                                            "ORDER BY o.order_code ASC;");
            
            ResultSetMetaData rsmetadata = rs.getMetaData();

            int columns = rsmetadata.getColumnCount();
            int rowNum = 0;

            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            for (int i = 1; i <= columns; i++) {
                columns_name.addElement(rsmetadata.getColumnName(i));
            }
            dtm.setColumnIdentifiers(columns_name);
            while(rs.next()){
                data_rows = new Vector();
            for(int j=1;j<=columns;j++){
                data_rows.addElement(rs.getString(j));
            }
            dtm.addRow(data_rows);
            }

            mytable.setModel(dtm);
            String Rowsnum = String.valueOf(mytable.getRowCount());
            jtextCountRows.setText(Rowsnum);
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
        }
   }  
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        fromDate = new javax.swing.JTextField();
        toDate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDateInfo = new javax.swing.JLabel();
        fromTime = new javax.swing.JTextField();
        toTime = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        SelectCountry = new javax.swing.JComboBox<>();
        n = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnWaybill = new javax.swing.JToggleButton();
        lblDeleteInfo = new javax.swing.JLabel();
        btnOpenFolder = new javax.swing.JButton();
        btnPrintVoucher = new javax.swing.JToggleButton();
        StockReceipt = new javax.swing.JButton();
        btnCleantxt = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        mytable = new javax.swing.JTable();
        jtextCountRows = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblExit = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mytable1 = new javax.swing.JTable();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setUndecorated(true);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fromDate.setText("0000-00-00");

        toDate.setText("0000-00-00");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("From:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("To:");

        lblDateInfo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDateInfo.setForeground(new java.awt.Color(240, 240, 240));
        lblDateInfo.setText("Format: yyyy-mm-dd");

        fromTime.setText("00:00:01");

        toTime.setText("08:00:00");

        btnSearch.setBackground(new java.awt.Color(0, 102, 102));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        SelectCountry.setBackground(new java.awt.Color(0, 102, 102));
        SelectCountry.setForeground(new java.awt.Color(255, 255, 255));
        SelectCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BG", "RO" }));
        SelectCountry.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateInfo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(16, 16, 16)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(SelectCountry, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(toDate)
                                    .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toTime, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fromTime, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDateInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(fromTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(toTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SelectCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        n.setBackground(new java.awt.Color(102, 102, 102));
        n.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSave.setBackground(new java.awt.Color(0, 102, 102));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnWaybill.setBackground(new java.awt.Color(0, 102, 102));
        btnWaybill.setForeground(new java.awt.Color(255, 255, 255));
        btnWaybill.setText("Print Waybill");
        btnWaybill.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnWaybill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWaybillActionPerformed(evt);
            }
        });

        btnOpenFolder.setBackground(new java.awt.Color(0, 102, 102));
        btnOpenFolder.setForeground(new java.awt.Color(255, 255, 255));
        btnOpenFolder.setText("Open Folder");
        btnOpenFolder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFolderActionPerformed(evt);
            }
        });

        btnPrintVoucher.setBackground(new java.awt.Color(0, 102, 102));
        btnPrintVoucher.setForeground(new java.awt.Color(255, 255, 255));
        btnPrintVoucher.setText("Print voucher");
        btnPrintVoucher.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnPrintVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintVoucherActionPerformed(evt);
            }
        });

        StockReceipt.setBackground(new java.awt.Color(0, 102, 102));
        StockReceipt.setForeground(new java.awt.Color(255, 255, 255));
        StockReceipt.setText("Stock Receipt");
        StockReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StockReceiptActionPerformed(evt);
            }
        });

        btnCleantxt.setBackground(new java.awt.Color(0, 102, 102));
        btnCleantxt.setForeground(new java.awt.Color(255, 255, 255));
        btnCleantxt.setText("Clean txt");
        btnCleantxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        btnCleantxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleantxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout nLayout = new javax.swing.GroupLayout(n);
        n.setLayout(nLayout);
        nLayout.setHorizontalGroup(
            nLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(nLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nLayout.createSequentialGroup()
                        .addComponent(btnCleantxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(lblDeleteInfo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nLayout.createSequentialGroup()
                        .addGroup(nLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(StockReceipt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPrintVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(btnWaybill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(nLayout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnOpenFolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        nLayout.setVerticalGroup(
            nLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnWaybill, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StockReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOpenFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCleantxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDeleteInfo)
                .addGap(46, 46, 46))
        );

        jScrollPane2.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mytable.setAutoCreateRowSorter(true);
        mytable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        mytable.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        mytable.setForeground(new java.awt.Color(102, 0, 0));
        mytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        mytable.setColumnSelectionAllowed(true);
        mytable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mytable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        mytable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mytableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mytable);
        mytable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jtextCountRows.setBackground(new java.awt.Color(51, 51, 51));
        jtextCountRows.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtextCountRows.setForeground(new java.awt.Color(255, 255, 0));
        jtextCountRows.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtextCountRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextCountRowsActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(153, 0, 0));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblExit.setBackground(new java.awt.Color(255, 255, 255));
        lblExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setText("   X");
        lblExit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("bERS Logistics");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblExit))
                .addGap(3, 3, 3))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Counter :");

        jScrollPane3.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        mytable1.setAutoCreateRowSorter(true);
        mytable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        mytable1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        mytable1.setForeground(new java.awt.Color(102, 0, 0));
        mytable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        mytable1.setColumnSelectionAllowed(true);
        mytable1.setSelectionBackground(new java.awt.Color(204, 255, 255));
        jScrollPane3.setViewportView(mytable1);
        mytable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jtextCountRows, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtextCountRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnWaybillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWaybillActionPerformed
        try {
            printWaybill(filePathFolder("Waybill"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnWaybillActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String country = SelectCountry.getSelectedItem().toString();
        try {
            String d;
            d = fromDate.getText();
            
            //Load the driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Create connection
            String url = "jdbc:sqlserver://192.168.21.3;databaseName=bers;user=sa;password=q2w3e4r%";
            Connection con = DriverManager.getConnection(url);
            Statement st = con.createStatement();
            
            // Return a result set
             ResultSet rs = st.executeQuery("SELECT Distinct(O.Id) AS Order_ID,v.comment,O.order_code AS Code,O.StatusID,O.InputDate AS Input_Date,O.Address,O.billoflading\n" +
                                            "FROM Orders O\n" +
                                            "LEFT JOIN order_execution OE\n" +
                                            "ON O.DepositorID =  OE.DepositorID\n" +
                                            "LEFT JOIN V_expedition_note  v\n" +
                                            "ON v.order_code = o.order_code\n" +
                                            "WHERE (O.InputDate BETWEEN '"+fromDate.getText()+" "+fromTime.getText()+".0' AND '"+toDate.getText()+" "+toTime.getText()+".0' )\n" +
                                            "AND (OE.DepositorID = 217)AND ( O.StatusID = 1)AND (O.Address <> '') AND v.comment = '"+country+"' \n" +
                                            "ORDER BY O.order_code ASC;");
            
            ResultSetMetaData rsmetadata = rs.getMetaData();
            // It stores the number of columns
            int columns = rsmetadata.getColumnCount();
            
            // This object will pass data into jtable
            DefaultTableModel dtm = new DefaultTableModel();
            Vector columns_name = new Vector();
            Vector data_rows = new Vector();
            for (int i = 1; i <= columns; i++) {
                columns_name.addElement(rsmetadata.getColumnName(i));
            }
            dtm.setColumnIdentifiers(columns_name);
            while(rs.next()){
                data_rows = new Vector();
            for(int j=1;j<=columns;j++){
                data_rows.addElement(rs.getString(j));
            }
            dtm.addRow(data_rows);
            }
            // pass Default Table Object into myTable 
            mytable.setModel(dtm);
             String Rowsnum = String.valueOf(mytable.getRowCount());
             jtextCountRows.setText(Rowsnum);
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Empty " + ex );
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        cleanFolder(filePathFolder("Voucher"));
        cleanFolder(filePathFolder("Waybill")); 
    try {
        saveSelectedRows("Voucher","V_","Waybill","W_");
    } catch (IOException ex) {
        Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnOpenFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFolderActionPerformed
        try {
               Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePath());
            } catch (IOException ex) {
                      JOptionPane.showMessageDialog(null, ex);
                    }     
    }//GEN-LAST:event_btnOpenFolderActionPerformed
    
    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        xx = evt.getX();
        yy = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-yy);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void btnPrintVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintVoucherActionPerformed
        try {
            printVoucher(filePathFolder("Voucher"));
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, ex);
        } catch (PrinterException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnPrintVoucherActionPerformed

    private void mytableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mytableMouseClicked
        SelectedRowsOrders();       
    }//GEN-LAST:event_mytableMouseClicked

    private void jtextCountRowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextCountRowsActionPerformed
  
    }//GEN-LAST:event_jtextCountRowsActionPerformed

    private void StockReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StockReceiptActionPerformed
    try {
        JasperReport();
    } catch (ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, ex);
    } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null, ex);;
    } catch (JRException ex) {
        JOptionPane.showMessageDialog(null, ex);;
      }
    }//GEN-LAST:event_StockReceiptActionPerformed

    private void btnCleantxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleantxtActionPerformed
        FileOutputStream writer;
    try {
        writer = new FileOutputStream("C:\\Users\\Public\\Downloads\\Kidso\\Text.txt");
        writer.write(("").getBytes());
        writer.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(mainform.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }//GEN-LAST:event_btnCleantxtActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
             JOptionPane.showMessageDialog(null, ex);
        } catch (InstantiationException ex) {
             JOptionPane.showMessageDialog(null, ex);
        } catch (IllegalAccessException ex) {
             JOptionPane.showMessageDialog(null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
             JOptionPane.showMessageDialog(null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new mainform().setVisible(true);
                    
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
            
        });
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SelectCountry;
    private javax.swing.JButton StockReceipt;
    private javax.swing.JButton btnCleantxt;
    private javax.swing.JButton btnOpenFolder;
    private javax.swing.JToggleButton btnPrintVoucher;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JToggleButton btnWaybill;
    private javax.swing.JTextField fromDate;
    private javax.swing.JTextField fromTime;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtextCountRows;
    private javax.swing.JLabel lblDateInfo;
    private javax.swing.JLabel lblDeleteInfo;
    private javax.swing.JLabel lblExit;
    private javax.swing.JTable mytable;
    private javax.swing.JTable mytable1;
    private javax.swing.JPanel n;
    private javax.swing.JTextField toDate;
    private javax.swing.JTextField toTime;
    // End of variables declaration//GEN-END:variables

    void setVisible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
