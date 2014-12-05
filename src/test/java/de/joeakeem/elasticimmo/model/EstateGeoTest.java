package de.joeakeem.elasticimmo.model;

import static org.junit.Assert.*;

import org.elasticsearch.common.geo.GeoPoint;
import org.junit.Test;

public class EstateGeoTest {
    
    @Test
    public void testIsZipCodeEquals() {
        EstateGeo estateGeo = new EstateGeo();
        estateGeo.setZipCode(null);
        
        assertTrue(estateGeo.isZipCodeEquals(null));
        assertFalse(estateGeo.isZipCodeEquals("123"));
        
        estateGeo.setZipCode("123");
        assertTrue(estateGeo.isZipCodeEquals("123"));
        assertFalse(estateGeo.isZipCodeEquals("124"));
        assertFalse(estateGeo.isZipCodeEquals(null));
    }
    
    @Test
    public void testIsCityEquals() {
        EstateGeo estateGeo = new EstateGeo();
        estateGeo.setCity(null);
        
        assertTrue(estateGeo.isCityEquals(null));
        assertFalse(estateGeo.isCityEquals("New York"));
        
        estateGeo.setCity("Springfield");
        assertTrue(estateGeo.isCityEquals("Springfield"));
        assertFalse(estateGeo.isCityEquals("New York"));
        assertFalse(estateGeo.isCityEquals(null));
    }
    
    @Test
    public void testIsStreetEquals() {
        EstateGeo estateGeo = new EstateGeo();
        estateGeo.setStreet(null);
        
        assertTrue(estateGeo.isStreetEquals(null));
        assertFalse(estateGeo.isStreetEquals("Main Street"));
        
        estateGeo.setStreet("Main Street");
        assertTrue(estateGeo.isStreetEquals("Main Street"));
        assertFalse(estateGeo.isStreetEquals("Long Street"));
        assertFalse(estateGeo.isStreetEquals(null));
    }
    
    @Test
    public void testIsHouseNoEquals() {
        EstateGeo estateGeo = new EstateGeo();
        estateGeo.setHouseNo(null);
        
        assertTrue(estateGeo.isHouseNoEquals(null));
        assertFalse(estateGeo.isHouseNoEquals("1a"));
        
        estateGeo.setHouseNo("1a");
        assertTrue(estateGeo.isHouseNoEquals("1a"));
        assertFalse(estateGeo.isHouseNoEquals("2b"));
        assertFalse(estateGeo.isHouseNoEquals(null));
    }
    
    @Test
    public void testIsLocationEquals() {
        EstateGeo estateGeo = new EstateGeo();
        estateGeo.setLocation(null);
        
        assertTrue(estateGeo.isLocationEquals(null));
        assertFalse(estateGeo.isLocationEquals(new GeoPoint(1.0, 2.0)));
        
        estateGeo.setLocation(new GeoPoint(1.0, 2.0));
        assertTrue(estateGeo.isLocationEquals(new GeoPoint(1.0, 2.0)));
        assertFalse(estateGeo.isLocationEquals(new GeoPoint(1.0, 3.0)));
        assertFalse(estateGeo.isLocationEquals(null));
    }
}
