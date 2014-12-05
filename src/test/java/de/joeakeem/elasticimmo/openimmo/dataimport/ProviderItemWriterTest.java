package de.joeakeem.elasticimmo.openimmo.dataimport;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.repository.EstateRepository;

public class ProviderItemWriterTest {
    
    @Mock
    private EstateRepository estateRepo;
    
    @InjectMocks
    private ProviderItemWriter providerItemWriter;
    
    @Before
    public void init() {
        providerItemWriter = new ProviderItemWriter();
        MockitoAnnotations.initMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testWrite() throws Exception {
        List<Estate> estates = new ArrayList<Estate>();
        List<Estate> moreEstates = new ArrayList<Estate>();
        List<List<Estate>> estatesList = new ArrayList<List<Estate>>(2);
        estatesList.add(estates);
        estatesList.add(moreEstates);
        
        providerItemWriter.write(estatesList);
        
        verify(estateRepo, times(2)).save(anyList());
    }
}
