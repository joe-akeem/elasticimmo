package de.joeakeem.elasticimmo.openimmo.dataimport;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;

/**
 * Maps am Immobilie instance to an Estate instance using a DozerBeanMapper.
 * 
 * @author Joachim Lengacher
 */
@Component
public class EstateItemProcessor implements ItemProcessor<Immobilie, Estate> {

    @Inject
    private DozerBeanMapper dozerBeanMapper;
    
    @Value("${distributor}")
    private String distributor;
    
    @Value("${portal}")
    private String portal;

    /**
     * Maps an Immobilie instance to an Estate instance using a DozerBeanMapper.
     */
    @Override
    public Estate process(Immobilie immobilie) throws Exception {
        Estate estate = new Estate();
        dozerBeanMapper.map(immobilie, estate);
        estate.setDistributor(distributor);
        estate.setPortal(portal);
        return estate;
    }
    
    protected void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

}
