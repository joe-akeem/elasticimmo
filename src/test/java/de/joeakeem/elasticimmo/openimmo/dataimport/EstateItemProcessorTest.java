package de.joeakeem.elasticimmo.openimmo.dataimport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.openimmo.model.Aktion;
import de.joeakeem.elasticimmo.openimmo.model.Freitexte;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;
import de.joeakeem.elasticimmo.openimmo.model.VerwaltungTechn;

public class EstateItemProcessorTest {
    
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
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);        
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("de/joeakeem/elasticimmo/openimmo/dozer-mapping.xml");
        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiles);
        estateItemProcessor = new EstateItemProcessor();
        estateItemProcessor.setDozerBeanMapper(dozerBeanMapper);
    }

    @Test
    public void testProcess() throws Exception {
        when(immobilie.getVerwaltungTechn()).thenReturn(verwaltungTechn);
        when(verwaltungTechn.getObjektnrExtern()).thenReturn("obj_nr_ext");
        when(verwaltungTechn.getAktion()).thenReturn(aktion);
        when(aktion.getAktionart()).thenReturn("UPDATE");
        when(immobilie.getFreitexte()).thenReturn(freitexte);
        when(freitexte.getObjekttitel()).thenReturn("objekttitel");
        
        Estate estate = estateItemProcessor.process(immobilie);
        
        assertEquals(estate.getId(), immobilie.getVerwaltungTechn().getObjektnrExtern());
        assertEquals(estate.getTitle(), immobilie.getFreitexte().getObjekttitel());
        assertEquals(estate.getMode(), immobilie.getVerwaltungTechn().getAktion().getAktionart());
    }
}
