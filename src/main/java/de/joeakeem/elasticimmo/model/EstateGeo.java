package de.joeakeem.elasticimmo.model;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents the geographical location of an estate.
 * 
 * @author joe
 *
 */
public class EstateGeo {
    
    private static final int HASH_CODE_MULTIPLIER_PRIME = 31;
    
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String isoCountryCode;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String zipCode;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String city;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String street;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String houseNo;
    
    private GeoPoint location;
    
    public String getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(String isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
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
    
    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    /**
     * @return a representation of the coordinates suitable for display e.g. on a bettermap in Kibana.
     */
    public double[] getLonLat() {
        if (location != null) {
            return new double[] {location.getLon(), location.getLat()};
        }
        return new double[] {};
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        EstateGeo estateGeo = (EstateGeo)o;
        if (this.zipCode != null && estateGeo.zipCode != null && !this.zipCode.equals(estateGeo.zipCode)) {
            return false;
        }
        if (this.city != null && estateGeo.city != null && !this.city.equals(estateGeo.city)) {
            return false;
        }
        if (this.street != null && estateGeo.street != null && !this.street.equals(estateGeo.street)) {
            return false;
        }
        if (this.houseNo != null && estateGeo.houseNo != null && !this.houseNo.equals(estateGeo.houseNo)) {
            return false;
        }
        if (this.location != null && estateGeo.location != null && !this.location.equals(estateGeo.location)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (isoCountryCode == null ? 0 : isoCountryCode.hashCode());
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (zipCode == null ? 0 : zipCode.hashCode());
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (city == null ? 0 : city.hashCode());
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (street == null ? 0 : street.hashCode());
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (houseNo == null ? 0 : houseNo.hashCode());
        hashCode = HASH_CODE_MULTIPLIER_PRIME * hashCode + (location == null ? 0 : location.hashCode());
        return hashCode;
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[isoCountryCode=");
        str.append(isoCountryCode);
        str.append(", zipCode=");
        str.append(zipCode);
        str.append(", city=");
        str.append(city);
        str.append(", street=");
        str.append(street);
        str.append(", houseNo=");
        str.append(houseNo);
        str.append(", location=");
        str.append(location != null ? location.toString() : null);
        str.append("]");
        return str.toString();
    }
}
