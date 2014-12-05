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

public class EstateItemWriterTest {
    
    @Mock
    private EstateRepository estateRepo;
    
    @InjectMocks
    private EstateItemWriter estateItemWriter;
    
    @Before
    public void init() {
        estateItemWriter = new EstateItemWriter();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWrite() throws Exception {
        Estate estateToSave = new Estate();
        estateToSave.setMode("UPDATE");
        
        Estate estateToDelete = new Estate();
        estateToDelete.setMode("DELETE");
        
        List<Estate> estates = new ArrayList<Estate>(2);
        estates.add(estateToDelete);
        estates.add(estateToSave);
        
        estateItemWriter.write(estates);
        
        verify(estateRepo).save(estateToSave);
        verify(estateRepo).delete(estateToDelete);
    }
}
