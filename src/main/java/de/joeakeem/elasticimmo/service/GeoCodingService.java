package de.joeakeem.elasticimmo.service;

import java.io.IOException;

import de.joeakeem.elasticimmo.model.EstateGeo;


/**
 * Represents a geo coding service.
 * 
 * @author Joachim Lengacher
 *
 */
public interface GeoCodingService {
    
    EstateGeo geocode(EstateGeo newGeo, EstateGeo oldGeo) throws IOException;

}