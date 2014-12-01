package de.joeakeem.elasticimmo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Impressum {
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String company;
    
    @Field(type = FieldType.String)
    private String companyAddress;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String phone;
    
    // Vertretungsberechtigter
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String representrative;
    
    // Berufsaufsichtsbehoerde
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String businessSupervisionOffice;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String tradeRegister;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String tradeRegisterNo;
    
    // Umsst-id (value added tax id)
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String vatId;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String taxNumber;
    
    @Field(type = FieldType.String, searchAnalyzer="german", indexAnalyzer="german")
    private String more;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRepresentrative() {
        return representrative;
    }

    public void setRepresentrative(String representrative) {
        this.representrative = representrative;
    }

    public String getBusinessSupervisionOffice() {
        return businessSupervisionOffice;
    }

    public void setBusinessSupervisionOffice(String businessSupervisionOffice) {
        this.businessSupervisionOffice = businessSupervisionOffice;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public String getTradeRegisterNo() {
        return tradeRegisterNo;
    }

    public void setTradeRegisterNo(String tradeRegisterNo) {
        this.tradeRegisterNo = tradeRegisterNo;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

}
