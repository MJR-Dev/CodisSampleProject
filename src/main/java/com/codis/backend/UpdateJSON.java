/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codis.backend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ekiryan
 */
public class UpdateJSON {
    static File jsonFile1 = new File("Person1.json");
    ReadJSON readJson;
    ObjectMapper mapper;
    WriteJSON writeJson;
    boolean updateRecord1 = false,updateRecord2 = false;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public boolean checkPersonPresent(String firstName,String lastName, List<Person> existingList){
        for(Person p: existingList){
            if(p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                return true;
        }
        return false;
    }
    public boolean updatePerson(String firstName, String lastName,String newFirstName, String newLastName, String newDate, String newNickName){
       
        readJson = new ReadJSON();
        writeJson = new WriteJSON();
        List<Person> existingPersons = readJson.displayPerson();
        List<Person> updatedPersonsList = new ArrayList<Person>();
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        updateRecord1 = checkPersonPresent(firstName,lastName,existingPersons);
        updateRecord2 = checkPersonPresent(newFirstName,newLastName,existingPersons);
        if(updateRecord1 && !updateRecord2){
            for(Person person: existingPersons){
                if(person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)){
                    person.setFirstName(newFirstName);
                    person.setLastName(newLastName);
                    Date date = null;
                    try {
                        LocalDate localDate = LocalDate.parse(newDate, formatter);
                        date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    } catch (DateTimeParseException ex) {
                    Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    person.setDateOfBirth(date);
                    person.setNickName(newNickName);
                    person.setAddressList(person.getAddressList());                    
                } 
                updatedPersonsList.add(person);
                writeJson.addPersonToFile(mapper, updatedPersonsList);
            }       
            
        }
        return (updateRecord1 && !updateRecord2);
    }
    public boolean checkPersonAddressPresent(String firstName,String lastName, List<Person> existingList,Address oldAdr,Address newAdr){
        boolean personAddressExists = false;
        
        for(Person p: existingList){
            if(p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)){
                List<Address> adrLst = p.getAddressList();
                for(Address existingAdr: adrLst){
                    if(existingAdr.equals(oldAdr) && !existingAdr.equals(newAdr))
                        personAddressExists = true;
                }
            }
        }
        return personAddressExists;
    }
    
    public boolean updateAddress(String firstName, String lastName,Address oldAdr, Address newAdr){
        boolean updateStatus = false;
        readJson = new ReadJSON();
        writeJson = new WriteJSON();
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<Person> existingPersons = readJson.displayPerson();
        List<Person> updatedPersonsList = new ArrayList<Person>();
        if(checkPersonAddressPresent(firstName,lastName,existingPersons,oldAdr,newAdr)){
            for(Person p: existingPersons){
                if(p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)){
                    List<Address> adrLst = p.getAddressList();
                    for(Address existingAdr: adrLst){
                        if(existingAdr.equals(oldAdr)){
                            existingAdr.setLine1(newAdr.getLine1());
                            existingAdr.setLine2(newAdr.getLine2());
                            existingAdr.setcountry(newAdr.getcountry());
                            existingAdr.setPostalCode(newAdr.getPostalCode());
                            updateStatus = true;
                        }
                    }
                }
                updatedPersonsList.add(p);
                writeJson.addPersonToFile(mapper, updatedPersonsList);
            }
        }
        
        return updateStatus;
    }
}
