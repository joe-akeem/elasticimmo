package de.joeakeem.elasticimmo.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.service.GeoCodingService;
import de.joeakeem.elasticimmo.wigeogis.model.GETKEY;

/**
 * Provides access to the WiGeoGis geo coding functions.
 * 
 * @author Joachim Lengacher
 *
 */
@Service
public class GeoCodingServiceImpl implements GeoCodingService {
	
	private static final Logger LOG = LoggerFactory.getLogger(GeoCodingServiceImpl.class);
    
    /**
     * The amount of milliseconds until the a newly received key times out.
     * This is 300 per documentation, with 290 seconds we're on the save side.
     */
	private static final long KEY_EXPIRATION_TIMEOUT = 290000L;
    
    /**
     * The WiGeoGis account name variable.
     */
	private static final String USR = "USR";
    
    /**
     * The WiGeoGis account password variable.
     */
	private static final String CID = "CID";
    
    /**
     * The WiGeoGis request type variable.
     */
	private static final String TYPE = "TYPE";
	
	/**
	 * Status code of the WiGeoGis response for successfule requests.
	 */
	private static final String OK_STATUS = "OK";
	
	/**
	 * The URL of the WiGeoGis authentication resource.
	 */
	@Value("${wiGeoGis.authentication.url}")
	private String authenticationUrl;
	
	/**
	 * The URL of the WiGeoGis geo coding resource.
	 */
	@Value("${wiGeoGis.geocode.url}")
	private String geocodeUrl;
    
	/**
	 * The WiGeoGis account name.
	 */
	@Value("${wiGeoGis.usr}")
    private String usr;
    
    /**
	 * The WiGeoGis account password.
	 */
	@Value("${wiGeoGis.cid}")
    private String cid;
	
	/**
	 * The key of the current WiGeoGis session which is valid
	 * for <code>KEY_EXPIRATION_TIMEOUT</code> milliseconds until it must be renewed.
	 */
	private String key;

    /**
     * The time in milliseconds when the current key expires
     */
    private long keyExpirationTime = 0;
    
    @Inject
    private RestTemplate wiGeoGisRestTemplate;
    
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
        	Map<String, String> urlVariables = new HashMap<String, String>();
        	urlVariables.put(USR, usr);
        	urlVariables.put(CID, cid);
        	urlVariables.put(TYPE, "geocode");
        	
        	GETKEY response = wiGeoGisRestTemplate.getForObject(authenticationUrl, GETKEY.class, urlVariables);
        	
        	if (OK_STATUS.equals(response.getSTATUS())) {
        		key = response.getKEY();
        		keyExpirationTime = System.currentTimeMillis() + KEY_EXPIRATION_TIMEOUT;
        		LOG.info("Successfully authneticated with the geo coding service.");
        	} else {
        		LOG.error("failed to authenticate with the geo coding service: {} (error code: {})",
        				response.getMESSAGE(), response.getERRORCODE());
        	}
        }
    }
    
    /**
     * @return true if the current WiGeoGis session key is expired, false otherwise.
     */
    private boolean isKeyExpired() {
        return keyExpirationTime < System.currentTimeMillis();
    }
}
