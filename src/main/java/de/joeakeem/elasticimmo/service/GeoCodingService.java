package de.joeakeem.elasticimmo.service;

import de.joeakeem.elasticimmo.model.EstateGeo;


/**
 * Represents a geo coding service.
 * 
 * @author Joachim Lengacher
 *
 */
public interface GeoCodingService {
    
    EstateGeo geocode(EstateGeo newGeo, EstateGeo oldGeo);

}