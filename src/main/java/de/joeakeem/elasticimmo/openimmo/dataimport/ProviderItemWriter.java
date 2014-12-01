package de.joeakeem.elasticimmo.openimmo.dataimport;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.repository.EstateRepository;

@Component
public class ProviderItemWriter implements ItemWriter<List<Estate>> {
    
    private static final Logger LOG = LoggerFactory
            .getLogger(EstateItemWriter.class);

    @Inject
    private EstateRepository estateRepo;

    @Override
    public void write(List<? extends List<Estate>> estatesList) throws Exception {
        
        for (List<Estate> estates : estatesList) {
            estateRepo.save(estates);
        }
    }
}
