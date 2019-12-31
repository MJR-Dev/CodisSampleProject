/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codis.backend;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ekiryan
 */
public class WriteJSON {     
    static File jsonFile1 = new File("Person1.json");
    ReadJSON readJson;
    UpdateJSON updateJson;
    ObjectMapper mapper;
    
     
    public boolean addNewPerson(String fn,String ln,String date,String nn,String l1,String l2,String l3,String pc){
        boolean addPersonStatus = true;
        readJson = new ReadJSON();
        List<Person> existingPersons = readJson.displayPerson();
        List<Address> addressesToBeAdded = new ArrayList<Address>();
        Address adr = new Address(l1,l2,l3,Long.parseLong(pc));
        addressesToBeAdded.add(adr);
        Person p1 = new Person(fn,ln,date,nn,addressesToBeAdded);
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        
        if(existingPersons.size() == 0){    
            existingPersons.add(p1);
            addPersonToFile(mapper,existingPersons);
        }
        else{
            if(!checkPersonPresent(existingPersons,p1))
                addPersonStatus= false;
            else{
                existingPersons.add(p1);
                addPersonToFile(mapper,existingPersons);
                addPersonStatus= true;
            }
        }
        return addPersonStatus;
    }
    
    public boolean checkPersonPresent(List<Person> personsList,Person newPerson){
        for(Person p: personsList){
            if(p.getFirstName().equalsIgnoreCase(newPerson.getFirstName()) && p.getLastName().equalsIgnoreCase(newPerson.getLastName()))
                return false;
        }
        return true;
    }
    
    public void addPersonToFile(ObjectMapper mapper,List<Person> personsList){
        try (JsonGenerator jGenerator =
                     mapper.getFactory().createGenerator(
                             jsonFile1, JsonEncoding.UTF8)) {
            jGenerator.useDefaultPrettyPrinter();
           
            jGenerator.writeStartArray();
            for(Person p2:personsList){
                
                jGenerator.writeStartObject(); 
                jGenerator.writeStringField("firstName", p2.getFirstName());
                jGenerator.writeStringField("lastName", p2.getLastName());
                jGenerator.writeStringField("dateOfBirth", p2.getDateOfBirth().toString());
                jGenerator.writeStringField("nickName", p2.getNickName());
                jGenerator.writeArrayFieldStart("addressList");
                for(Address adr:p2.getAddressList())
                    jGenerator.writeObject(adr);
                jGenerator.writeEndArray();
               
                 jGenerator.writeEndObject();
            }
            jGenerator.writeEndArray();
            
            jGenerator.flush();
            jGenerator.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean addAddress(String fn,String ln,String l1,String l2,String l3,String pc){
        boolean addAddressStatus = true;
        readJson = new ReadJSON();
        updateJson = new UpdateJSON();
        List<Person> existingPersons = readJson.displayPerson();
        List<Person> personNewAddress = new ArrayList<Person>();
        List<Address> addressesToBeAdded = new ArrayList<Address>();       
        Address addAdr = new Address(l1,l2,l3,Long.parseLong(pc));
        addressesToBeAdded.add(addAdr);        
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        if(existingPersons.size() == 0){ 
            addAddressStatus  = false;
        }
        else{
            if(updateJson.checkPersonPresent(fn, ln, existingPersons)){
                for(Person p:existingPersons){
                    if(p.getFirstName().equalsIgnoreCase(fn) && p.getLastName().equalsIgnoreCase(ln)){
                        List<Address> adrLst = p.getAddressList();
                        for(Address adr: adrLst){
                            if(adr.getLine1().equalsIgnoreCase(l1) && adr.getLine2().equalsIgnoreCase(l2) && adr.getcountry().equalsIgnoreCase(l3) && adr.getPostalCode() == Long.parseLong(pc))
                                addAddressStatus  = false;
                        }
                        if(addAddressStatus){
                            adrLst.addAll(addressesToBeAdded);
                            p.setAddressList(adrLst);
                        }
                    }
                    personNewAddress.add(p);
                }
                if(addAddressStatus)
                    addPersonToFile(mapper,personNewAddress);
            } else 
                addAddressStatus = false;
                
        }
        return addAddressStatus;
    }
}
