package de.joeakeem.elasticimmo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents the geographical location of an estate.
 * 
 * @author joe
 *
 */
public class EstateGeo {

    @Id
    private String id;

    @Field(type = FieldType.String)
    private String zipCode;

    @Field(type = FieldType.String)
    private String city;

    @Field(type = FieldType.String)
    private String street;

    @Field(type = FieldType.String)
    private String houseNo;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
}
