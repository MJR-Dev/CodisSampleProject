/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codis.backend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ekiryan
 */
public class ValidateUserInputs extends javax.swing.JDialog{
    
    public ValidateUserInputs(java.awt.Frame parent, boolean modal){
        super(parent,modal);
    }
    
    public ValidateUserInputs(java.awt.Frame parent, boolean modal,String name1, String name2){
        super(parent,modal);
    }
      
    public boolean validateNameAddress(String... args){
        boolean validString = false;
        for(String str:args){
            if(str.matches("^[ A-Za-z]+$"))
                validString = true;
            else{
                validString = false;
                break;
            }
        }
        return validString;
    }
    
    public boolean validateDOB(String dob){
        boolean validDate = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate localDate = LocalDate.parse(dob, formatter);
            System.out.println("localdate: "+localDate.toString());
            validDate = true;
        } catch (DateTimeParseException ex) {
            validDate = false;
            Logger.getLogger(ValidateUserInputs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validDate;
    }
    
    public boolean validatePostalCode(String... postalCode){
        boolean validString = false;
        for(String str:postalCode){
            if(str.matches("[0-9]+"))
                validString = true;
            else{
                validString = false;
                break;
            }
        }
        return validString;        
    }
}
