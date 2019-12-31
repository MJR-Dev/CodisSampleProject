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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ekiryan
 */
public class DeleteJSON {
    static File jsonFile1 = new File("Person1.json");
    ObjectMapper mapper;
    ReadJSON readJson;   
    WriteJSON writeJson;
    UpdateJSON updateJson;
    boolean deleteRecord = false;
    Person personToBeDeleted;
    List<Person> existingPersons;
    
    public DeleteJSON(){
        readJson = new ReadJSON();
        writeJson = new WriteJSON();
        updateJson = new UpdateJSON();
         mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        existingPersons = readJson.displayPerson();
       
    }
    public boolean deletePerson(String firstName,String lastName){
        deleteRecord = updateJson.checkPersonPresent(firstName,lastName,existingPersons);
        if(deleteRecord){
            for(Person p: existingPersons){
                if(p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                    personToBeDeleted = p;
            }
            existingPersons.remove(personToBeDeleted);
            writeJson.addPersonToFile(mapper, existingPersons);
        } 
        return deleteRecord;
    }
    
    public boolean deleteAddress(String firstName,String lastName,Address adr){
        deleteRecord = false;
        
        if(updateJson.checkPersonPresent(firstName,lastName,existingPersons)){
            for(Person p: existingPersons){
                if(p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName)){
                    List<Address> adrLst = p.getAddressList();                    
                    Address adrToBeDeleted = new Address();
                    if(adrLst.size() == 1)
                        return false;
                    else{
                        for(Address existingAdr: adrLst){
                            if(existingAdr.equals(adr)){
                                adrToBeDeleted = existingAdr;
                                deleteRecord = true;
                            }
                            if(deleteRecord){
                                adrLst.remove(adrToBeDeleted);
                                break;
                            } 
                        }
                        p.setAddressList(adrLst);
                    }
                    
                }
            }
            writeJson.addPersonToFile(mapper, existingPersons);
        }
        
        
        return deleteRecord;
    }
}
