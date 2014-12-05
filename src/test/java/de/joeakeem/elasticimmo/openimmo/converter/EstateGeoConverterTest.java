package de.joeakeem.elasticimmo.openimmo.converter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.openimmo.model.Geo;
import de.joeakeem.elasticimmo.openimmo.model.Geokoordinaten;

public class EstateGeoConverterTest {
    
    @InjectMocks
    private EstateGeoConverter estateGeoConverter;
    
    @Mock
    private Geo geoSource;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this); 
    }

    @Test
    public void testConvert() {
        EstateGeo converted = null;
        assertNull(estateGeoConverter.convert(converted, null, EstateGeo.class, Geo.class));
        
        List<Object> content = new ArrayList<Object>();
        content.add(new JAXBElement<String>(new QName("land"), String.class, "DEU"));
        content.add(new JAXBElement<String>(new QName("plz"), String.class, "plz"));
        content.add(new JAXBElement<String>(new QName("ort"), String.class, "ort"));
        content.add(new JAXBElement<String>(new QName("strasse"), String.class, "strasse"));
        content.add(new JAXBElement<String>(new QName("hausnummer"), String.class, "hausnummer"));
        
        Geokoordinaten koordinaten = new Geokoordinaten();
        koordinaten.setBreitengrad(48.135125f);
        koordinaten.setLaengengrad(11.581981f);
        
        content.add(new JAXBElement<Geokoordinaten>(new QName("geokoordinaten"), Geokoordinaten.class, koordinaten));

        when(geoSource.getContent()).thenReturn(content);
        
        converted = (EstateGeo) estateGeoConverter.convert(converted, geoSource, EstateGeo.class, Geo.class);
        
        assertEquals(converted.getIsoCountryCode(), "DEU");
        assertEquals(converted.getZipCode(), "plz");
        assertEquals(converted.getCity(), "ort");
        assertEquals(converted.getStreet(), "strasse");
        assertEquals(converted.getHouseNo(), "hausnummer");
        assertTrue(Math.abs(converted.getLocation().getLat() - 48.135125) < 0.00001);
        assertTrue(Math.abs(converted.getLocation().getLon() - 11.581981) < 0.00001);
    }
}
