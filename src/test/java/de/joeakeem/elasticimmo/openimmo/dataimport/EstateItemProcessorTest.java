package de.joeakeem.elasticimmo.openimmo.dataimport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.dozer.DozerBeanMapper;
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
    private GeoCodingService geoCodingService;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        estateItemProcessor.setDistributor("distributor");
        estateItemProcessor.setPortal("portal");
        
        when(immobilie.getVerwaltungTechn()).thenReturn(verwaltungTechn);
        when(verwaltungTechn.getObjektnrExtern()).thenReturn("obj_nr_ext");
    }

    @Test
    public void testProcessCase() throws Exception {
        Estate estate = new Estate();
        EstateGeo estateGeo = new EstateGeo();
        estate.setEstateGeo(estateGeo);
        
        when(estateRepo.findOne("obj_nr_ext")).thenReturn(estate);
        
        Estate processedEstate = estateItemProcessor.process(immobilie);

        assertNotNull(processedEstate);
        assertEquals("distributor", processedEstate.getDistributor());
        assertEquals("portal", processedEstate.getPortal());
        verify(dozerBeanMapper).map(eq(immobilie), any(Estate.class));
        verify(geoCodingService).geocode(any(EstateGeo.class), eq(estateGeo));
    }
}
