package com.codis.backend;

public class Address {
    String line1,line2,country;
    
    long postalCode;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }
    
    public Address(){
        super();
    }
    public Address(String l1,String l2,String l3,long pc){
        this.line1 = l1;
        this.line2 = l2;
        this.country = l3;
        this.postalCode = pc;
                
    }
    
    public String toString(){
        return "Line1: "+this.line1+" Line2: "+this.line2+" Country: "+this.country+" PostalCode: "+this.postalCode;
    }
    
    public String newLineString(){
        return "Line1: "+this.line1+"\n"+" Line2: "+this.line2+"\n"+" Country: "+this.country+"\n"+" PostalCode: "+this.postalCode;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((line1 == null) ? 0 : line1.hashCode());
        result = prime * result + ((line2 == null) ? 0 : line2.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        return result;
    }
        
    @Override
    public boolean equals(Object obj){
        {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (line1 == null)
        {
            if (other.line1 != null)
            return false;
        }
        else if (!line1.equalsIgnoreCase(other.line1))
            return false;
        if (line2 == null)
        {
            if (other.line2 != null)
            return false;
        }
        else if (!line2.equalsIgnoreCase(other.line2))
            return false;
        if (country == null)
        {
            if (other.country != null)
            return false;
        }
        else if (!country.equalsIgnoreCase(other.country))
            return false;
        if (String.valueOf(postalCode) == null)
        {
            if (String.valueOf(other.postalCode) != null)
            return false;
        }
        else if ( postalCode != other.postalCode)
            return false;
        return true;
        }
    }
}
