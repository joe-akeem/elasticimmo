package de.joeakeem.elasticimmo.openimmo.dataimport;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.model.Impressum;
import de.joeakeem.elasticimmo.openimmo.model.Anbieter;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;
import de.joeakeem.elasticimmo.openimmo.model.ImpressumStrukt;
import de.joeakeem.elasticimmo.repository.EstateRepository;

@Component
public class ProviderItemProcessor implements ItemProcessor<Anbieter, List<Estate>> {

    @Inject
    private EstateRepository estateRepo;
    
    @Inject
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public List<Estate> process(Anbieter anbieter) throws Exception {
        List<Estate> estates = new ArrayList<Estate>(anbieter.getImmobilie().size());
        for (Immobilie immbobilie : anbieter.getImmobilie()) {
            Estate estate = estateRepo.findOne(immbobilie.getVerwaltungTechn().getObjektnrIntern());
            if (estate != null) {
                estate.setProviderId(anbieter.getAnbieternr());
                Impressum impressum = new Impressum();
                ImpressumStrukt impressumStrukt = anbieter.getImpressumStrukt();
                if (impressumStrukt != null) {
                    dozerBeanMapper.map(impressumStrukt, impressum);
                    estate.setImpressum(impressum);
                }
                estate.setImpressumAsString(anbieter.getImpressum());
                estates.add(estate);
            }
        }
        return estates;
    }
}
