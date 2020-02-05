/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInformation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Georgios.Loulakis
 */
public class ProductDAO {
    public List<Product> findAll(){
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("p01","p01","p01","p01","p01","p01","p01","p01"));
        
        
   
        return products;
    }
}
