package de.joeakeem.elasticimmo.openimmo.dataimport;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;
import de.joeakeem.elasticimmo.repository.EstateRepository;
import de.joeakeem.elasticimmo.service.GeoCodingService;

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
    
    @Inject
    private EstateRepository estateRepo;
    
    @Inject
    private GeoCodingService geoCodingService;

    /**
     * Maps an Immobilie instance to an Estate instance using a DozerBeanMapper.
     */
    @Override
    public Estate process(Immobilie immobilie) throws Exception {
        Estate estate = estateRepo.findOne(immobilie.getVerwaltungTechn().getObjektnrExtern());
        if (estate == null) {
            estate = new Estate();
        }
        
        // save current location if it exists
        EstateGeo currentEstateGeo = estate.getEstateGeo();
        GeoPoint currentLocation = null;
        String currentCity = null;
        if (currentEstateGeo != null) {
            currentCity = currentEstateGeo.getCity();
            currentLocation = currentEstateGeo.getLocation();
        }
        
        dozerBeanMapper.map(immobilie, estate);
        
        // save money and network traffic:
        // only call the geo coding service if a) we din't already have coordinates 
        // or b) the address has changed 
        EstateGeo newEstateGeo = estate.getEstateGeo();
        if (newEstateGeo != null) {
            String newCity = estate.getEstateGeo().getCity();
            if (currentLocation == null || currentCity == null || !currentCity.equals(newCity)) {
                currentLocation = geoCodingService.geocode(newCity);
            }
            estate.getEstateGeo().setLocation(currentLocation);
        }
        
        estate.setDistributor(distributor);
        estate.setPortal(portal);
        return estate;
    }
    
    protected void setDistributor(String distributor) {
        this.distributor = distributor;
    }
    
    protected void setPortal(String portal) {
        this.portal = portal;
    }
}
