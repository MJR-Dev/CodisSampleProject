package com.codis.backend;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ekiryan
 */
public class ReadJSON {
    InputStream inputStream;
    ObjectMapper mapper = new ObjectMapper();
    final DateFormat df = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
    static File jsonFile1 = new File("Person1.json");
    
    public  List<Person> displayPerson(){
        mapper.setDateFormat(df);
        List<Person> persons = new ArrayList<Person>();
        try {
            if(jsonFile1.exists())
                inputStream = new FileInputStream(new File("Person1.json"));
            else
                jsonFile1.createNewFile();
            TypeReference<List<Person>> typeReference = new TypeReference<List<Person>>(){};
            if(jsonFile1.length() > 0)               
                persons = mapper.readValue(inputStream,typeReference);
            for(Person p: persons){
                System.out.println(p.toString());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadJSON.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return persons;
    }
   
}
