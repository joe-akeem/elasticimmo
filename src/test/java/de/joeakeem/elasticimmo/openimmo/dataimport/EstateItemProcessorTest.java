package de.joeakeem.elasticimmo.openimmo.dataimport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.dozer.DozerBeanMapper;
import org.elasticsearch.common.geo.GeoPoint;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.openimmo.model.Aktion;
import de.joeakeem.elasticimmo.openimmo.model.Freitexte;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;
import de.joeakeem.elasticimmo.openimmo.model.VerwaltungTechn;
import de.joeakeem.elasticimmo.repository.EstateRepository;
import de.joeakeem.elasticimmo.service.GeoCodingService;

public class EstateItemProcessorTest {
    
    @Mock
    private DozerBeanMapper dozerBeanMapper;
    
    @InjectMocks
    private EstateItemProcessor estateItemProcessor;
    
    @Mock
    private Immobilie immobilie;
    
    @Mock
    private VerwaltungTechn verwaltungTechn;
    
    @Mock
    private Aktion aktion;
    
    @Mock
    private Freitexte freitexte;
    
    @Mock
    private EstateRepository estateRepo;
    
    @Mock
    private Estate estate;
    
    @Mock
    private GeoCodingService geoCodingService;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        estateItemProcessor.setDistributor("distributor");
        estateItemProcessor.setPortal("portal");
        
        when(immobilie.getVerwaltungTechn()).thenReturn(verwaltungTechn);
        when(verwaltungTechn.getObjektnrExtern()).thenReturn("obj_nr_ext");
    }

    /**
     * Test case 1: estate doesn't exist in repo.
     * @throws Exception
     */
    @Test
    public void testProcessCase1() throws Exception {
        Estate estate = estateItemProcessor.process(immobilie);
        assertNotNull(estate);
        assertEquals(estate.getDistributor(), "distributor");
        assertEquals(estate.getPortal(), "portal");
        verify(dozerBeanMapper).map(eq(immobilie), any(Estate.class));
    }
    
    /**
     * Test case 2: estate exists in repo but has no coordinates yet -> coordinates must be updated
     * @throws Exception
     */
    @Test
    public void testProcessCase2() throws Exception {
        when(estateRepo.findOne("obj_nr_ext")).thenReturn(estate);
        
        GeoPoint geoPoint = new GeoPoint();
        EstateGeo currentEstateGeo = new EstateGeo();
        when(estate.getEstateGeo()).thenReturn(currentEstateGeo);
        when(geoCodingService.geocode(anyString())).thenReturn(geoPoint);
        
        Estate estate = estateItemProcessor.process(immobilie);
        assertTrue(estate.getEstateGeo().getLocation() == geoPoint);
    }

    /**
     * Test case 3: estate exists in repo and already has coordinates -> coordinates must NOT be fetched again!!
     * @throws Exception
     */
    @Test
    public void testProcessCase3() throws Exception {
        when(estateRepo.findOne("obj_nr_ext")).thenReturn(estate);
        EstateGeo currentEstateGeo = new EstateGeo();
        currentEstateGeo.setCity("Springfield");
        GeoPoint geoPoint = new GeoPoint();
        currentEstateGeo.setLocation(geoPoint);
        when(estate.getEstateGeo()).thenReturn(currentEstateGeo);
        
        Estate estate = estateItemProcessor.process(immobilie);
        
        verify(geoCodingService, times(0)).geocode(anyString());
        assertTrue(estate.getEstateGeo().getLocation() == geoPoint);
    }
}
