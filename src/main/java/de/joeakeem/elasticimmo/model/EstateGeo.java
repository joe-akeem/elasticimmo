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
        return isZipCodeEquals(estateGeo.zipCode) && isCityEquals(estateGeo.city) // NOSONAR
                  && isStreetEquals(estateGeo.street) && isHouseNoEquals(estateGeo.houseNo)
                  && isLocationEquals(estateGeo.location);
    }
    
    protected boolean isZipCodeEquals(String otherZipCode) {
        if (this.zipCode == null && otherZipCode != null) {
            return false;
        }
        if (this.zipCode != null) {
            return this.zipCode.equals(otherZipCode);
        }
        return true;
    }
    
    protected boolean isCityEquals(String otherCity) {
        if (this.city == null && otherCity != null) {
            return false;
        }
        if (this.city != null) {
            return this.city.equals(otherCity);
        }
        return true;
    }
    
    protected boolean isStreetEquals(String otherStreet) {
        if (this.street == null && otherStreet != null) {
            return false;
        }
        if (this.street != null) {
            return this.street.equals(otherStreet);
        }
        return true;
    }
    
    protected boolean isHouseNoEquals(String otherHouseNo) {
        if (this.houseNo == null && otherHouseNo != null) {
            return false;
        }
        if (this.houseNo != null) {
            return this.houseNo.equals(otherHouseNo);
        }
        return true;
    }
    
    protected boolean isLocationEquals(GeoPoint otherLocation) {
        if (this.location == null && otherLocation != null) {
            return false;
        }
        if (this.location != null) {
            return this.location.equals(otherLocation);
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
