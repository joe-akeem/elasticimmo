package de.joeakeem.elasticimmo.service.impl;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;

import de.joeakeem.elasticimmo.service.GeoCodingService;

/**
 * Provides access to the WiGeoGis geo coding functions.
 * 
 * @author Joachim Lengacher
 *
 */
@Service
public class GeoCodingServiceImpl implements GeoCodingService {
    
    /**
     * The amount of milliseconds until the a newly received key times out.
     * This is 300 per documentation, with 290 seconds we're on the save side.
     */
    public long KEY_EXPIRATION_TIMEOUT = 290000L;

    /**
     * The time in milliseconds when the current key expires
     */
    private long keyExpirationTime;
    
    @Override
    public GeoPoint geocode(String city) {
        return new GeoPoint(48.135125, 11.581981);
    }
}
