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
public class EstateItemWriter implements ItemWriter<Estate> {

    private static final Logger LOG = LoggerFactory
            .getLogger(EstateItemWriter.class);

    @Inject
    private EstateRepository estateRepo;

    @Override
    public void write(List<? extends Estate> estates) throws Exception {
        for (Estate estate : estates) {

            if ("DELETE".equals(estate.getMode())) {
                estateRepo.delete(estate);
                LOG.debug("Deleted estate with id = '{}'", estate.getId());
            } else {
                estateRepo.save(estate);
                LOG.debug("Saved or updated estate with id = '{}'",
                        estate.getId());
            }
        }
    }
}
