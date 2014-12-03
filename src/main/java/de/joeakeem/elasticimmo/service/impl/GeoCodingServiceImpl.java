package de.joeakeem.elasticimmo.service.impl;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.stereotype.Service;

import de.joeakeem.elasticimmo.model.EstateGeo;
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
    private long keyExpirationTime = 0;
    
    @Override
    public EstateGeo geocode(EstateGeo newGeo, EstateGeo oldGeo) {
        if (oldGeo != null && oldGeo.getLocation() != null && oldGeo.equals(newGeo)) {
            // we already had a loaction and it hasn't changed
            // -> no need to recalculate cooridnates
            return oldGeo;
        }
        if (newGeo == null) {
            return new EstateGeo(); // no need to geo code if we have no address information
        }
        if (newGeo.getLocation() != null) {
            return newGeo; // we assume that the coodiates the estateGeo already had are correct
        }
        newGeo.setLocation(getCoordinatesFromWiGeoGis(newGeo));
        return newGeo; 
    }
    
    /**
     * Calculate coordinates by calling the WiGeoGis JoinAddress service.
     * 
     * @param geo
     * @return
     */
    private GeoPoint getCoordinatesFromWiGeoGis(EstateGeo geo) {
        authenticate();
        return new GeoPoint(48.135125, 11.581981);  // TODO: calculate new coordinates by calling WiGeoGis
    }
    
    /**
     * Authenticates with the WiGeoGis JoinAddress services and stores
     * the session key as a member variable.
     */
    private void authenticate() {
        if (isKeyExpired()) {
            // TODO: implement authentication and save session key
            keyExpirationTime = System.currentTimeMillis() + KEY_EXPIRATION_TIMEOUT;
        }
    }
    
    /**
     * @return true if the current WiGeoGis session key is expired, false otherwise.
     */
    private boolean isKeyExpired() {
        return keyExpirationTime < System.currentTimeMillis();
    }
}
