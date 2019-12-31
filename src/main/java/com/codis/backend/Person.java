package com.codis.backend;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ekiryan
 */
public class Person {
    String firstName,lastName;
    Date dateOfBirth;
    String nickName;
    List<Address> addressList;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressLst) {
        this.addressList = addressLst;
    }
    public Person(){ super(); }
    public Person(String fn, String ln, String dob, String nn, List<Address> adrLst){
        this.firstName = fn;
        this.lastName = ln;
        try {
            LocalDate localDate = LocalDate.parse(dob, formatter);
            this.dateOfBirth = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.nickName = nn;
        this.addressList = adrLst;
    }
    
    private String dateString(){
        LocalDate localDate1 = this.dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.format(formatter);
        
    }
    public String toString(){        
        return " FirstName: "+this.firstName+" LastName: "+this.lastName+" DOB: "+dateString()+" NickName: "+this.nickName+" "+this.getAddressList().toString();
    }
    
    public String newLineString(){
        return " FirstName: "+this.firstName+"\n"+" LastName: "+this.lastName+"\n"+" DOB: "+dateString()+"\n"+" NickName: "+this.nickName;
    }
}

