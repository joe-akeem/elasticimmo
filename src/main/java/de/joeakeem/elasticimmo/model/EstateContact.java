package de.joeakeem.elasticimmo.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents a contact of an estate such as the owner or a real estate agent.
 * 
 * @author Joachim Lengacher
 *
 */
public class EstateContact {

    @Field(type = FieldType.String)
    private String firstName;

    @Field(type = FieldType.String)
    private String lastName;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String street;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String houseNo;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String zipCode;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String city;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String emailDirect;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String mobile;

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

    public String getEmailDirect() {
        return emailDirect;
    }

    public void setEmailDirect(String emailDirect) {
        this.emailDirect = emailDirect;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
