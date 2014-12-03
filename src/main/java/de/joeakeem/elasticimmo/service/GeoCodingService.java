package de.joeakeem.elasticimmo.service;

import org.elasticsearch.common.geo.GeoPoint;


/**
 * Represents a geo coding service.
 * 
 * @author Joachim Lengacher
 *
 */
public interface GeoCodingService {
    
    GeoPoint geocode(String city);

}