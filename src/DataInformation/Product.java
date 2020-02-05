/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInformation;

/**
 *
 * @author Georgios.Loulakis
 */
public class Product {
        String orderCode;
        String comment;
        String inputdate;
        String primarycode;
        String expunit;
        String expqty;
        String barcode;
        String ONLcode;

   Product (){
       
   }

    public Product(String orderCode, String comment, String inputdate, String primarycode, String expunit, String expqty, String barcode, String ONLcode) {
        this.orderCode = orderCode;
        this.comment = comment;
        this.inputdate = inputdate;
        this.primarycode = primarycode;
        this.expunit = expunit;
        this.expqty = expqty;
        this.barcode = barcode;
        this.ONLcode = ONLcode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }

    public String getPrimarycode() {
        return primarycode;
    }

    public void setPrimarycode(String primarycode) {
        this.primarycode = primarycode;
    }

    public String getExpunit() {
        return expunit;
    }

    public void setExpunit(String expunit) {
        this.expunit = expunit;
    }

    public String getExpqty() {
        return expqty;
    }

    public void setExpqty(String expqty) {
        this.expqty = expqty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getONLcode() {
        return ONLcode;
    }

    public void setONLcode(String ONLcode) {
        this.ONLcode = ONLcode;
    }
   
   
              
}