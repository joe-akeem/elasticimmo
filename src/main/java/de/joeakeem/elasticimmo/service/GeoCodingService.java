package de.joeakeem.elasticimmo.service;

import java.io.IOException;

import org.springframework.web.client.RestClientException;

import de.joeakeem.elasticimmo.model.EstateGeo;


/**
 * Represents a geo coding service.
 * 
 * @author Joachim Lengacher
 *
 */
public interface GeoCodingService {
    
    EstateGeo geocode(EstateGeo newGeo, EstateGeo oldGeo) throws RestClientException, IOException;

}