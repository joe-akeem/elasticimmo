package de.joeakeem.elasticimmo.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.inject.Inject;

import org.elasticsearch.common.geo.GeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.service.GeoCodingService;
import de.joeakeem.elasticimmo.wigeogis.model.GEOCODE;
import de.joeakeem.elasticimmo.wigeogis.model.GETKEY;

/**
 * Provides access to the WiGeoGis geo coding functions.
 * 
 * @author Joachim Lengacher
 *
 */
@Service
public class GeoCodingServiceImpl implements GeoCodingService {

    private static final Logger LOG = LoggerFactory
            .getLogger(GeoCodingServiceImpl.class);

    /**
     * The amount of milliseconds until the a newly received key times out. This
     * is 300 per documentation, with 290 seconds we're on the save side.
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
     * The WiGeoGis request projection variable.
     */
    private static final String PRJ = "PRJ";

    /**
     * The WiGeoGis default projection that we use.
     */
    private static final String DEFAULT_PRJ = "WGS84";

    /**
     * The WiGeoGis request key variable.
     */
    private static final String KEY = "KEY";

    /**
     * The WiGeoGis request city variable.
     */
    private static final String CITY = "CITY";

    /**
     * The WiGeoGis request ZIP code variable.
     */
    private static final String ZIP = "ZIP";

    /**
     * The WiGeoGis request street variable.
     */
    private static final String STR = "STR";

    /**
     * The WiGeoGis request house number variable.
     */
    private static final String HNR = "HNR";

    /**
     * The WiGeoGis request ISO country variable.
     */
    private static final String CTRISO = "CTRISO";

    /**
     * Status code of the WiGeoGis response for successful requests.
     */
    private static final String OK_STATUS = "OK";

    /**
     * Error code 0: valid geo coding
     */
    private static final int ERRORCODE_0 = 0;

    private static final String URL_CHAR_ENCODING = "UTF-8";

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
     * The key of the current WiGeoGis session which is valid for
     * <code>KEY_EXPIRATION_TIMEOUT</code> milliseconds until it must be
     * renewed.
     */
    private String key;

    /**
     * The time in milliseconds when the current key expires
     */
    private long keyExpirationTime = 0;

    @Inject
    private RestTemplate wiGeoGisRestTemplate;

    @Override
    public EstateGeo geocode(EstateGeo newGeo, EstateGeo oldGeo)
            throws IOException {
        if (oldGeo != null && oldGeo.getLocation() != null
                && oldGeo.equals(newGeo)) {
            // we already had a loaction and it hasn't changed
            // -> no need to recalculate cooridnates
            return oldGeo;
        }
        if (newGeo == null) {
            // no need to geo code if we have no address information
            return new EstateGeo();
        }
        if (newGeo.getLocation() != null) {
            // we assume that the coordinates the estateGeo already had are
            // correct
            return newGeo;
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
    private GeoPoint getCoordinatesFromWiGeoGis(EstateGeo geo)
            throws IOException {
        authenticate();

        StringBuilder params = new StringBuilder();
        params.append("?");
        params.append(USR);
        params.append("=");
        params.append(usr);
        params.append("&");
        params.append(KEY);
        params.append("=");
        params.append(key);
        params.append("&");
        params.append(PRJ);
        params.append("=");
        params.append(DEFAULT_PRJ);

        if (geo.getCity() != null) {
            params.append("&");
            params.append(CITY);
            params.append("=");
            params.append(URLEncoder.encode(geo.getCity(), URL_CHAR_ENCODING));
        }
        if (geo.getZipCode() != null) {
            params.append("&");
            params.append(ZIP);
            params.append("=");
            params.append(geo.getZipCode());
        }
        if (geo.getStreet() != null) {
            params.append("&");
            params.append(STR);
            params.append("=");
            params.append(URLEncoder.encode(geo.getStreet(), URL_CHAR_ENCODING));
        }
        if (geo.getHouseNo() != null) {
            params.append("&");
            params.append(HNR);
            params.append("=");
            params.append(URLEncoder.encode(geo.getHouseNo(), URL_CHAR_ENCODING));
        }
        params.append("&");
        params.append(CTRISO);
        params.append("=");
        params.append(geo.getIsoCountryCode() != null ? geo.getIsoCountryCode()
                : "DEU");

        GEOCODE response = wiGeoGisRestTemplate.getForObject(geocodeUrl
                + params.toString(), GEOCODE.class);

        if (response.getSUMMARY().getERRORCODE() == ERRORCODE_0) {
            BigDecimal lat = response.getOUTPUT().getSET().get(0).getYCOOR();
            BigDecimal lon = response.getOUTPUT().getSET().get(0).getXCOOR();
            return new GeoPoint(lat.doubleValue(), lon.doubleValue());
        }
        throw new IOException("Failed to geo code " + geo.toString() + ". ("
                + response.getSUMMARY().getMESSAGE() + ")");
    }

    /**
     * Authenticates with the WiGeoGis JoinAddress services and stores the
     * session key as a member variable.
     * 
     * @throws IOException
     * @throws RestClientException
     */
    private void authenticate() throws IOException {
        if (isKeyExpired()) {
            StringBuilder params = new StringBuilder();
            params.append("?");
            params.append(USR);
            params.append("=");
            params.append(usr);
            params.append("&");
            params.append(CID);
            params.append("=");
            params.append(cid);
            params.append("&");
            params.append(TYPE);
            params.append("=geocode");

            GETKEY response;
            response = wiGeoGisRestTemplate.getForObject(authenticationUrl
                    + params.toString(), GETKEY.class);
            if (OK_STATUS.equals(response.getSTATUS())) {
                key = response.getKEY();
                keyExpirationTime = System.currentTimeMillis()
                        + KEY_EXPIRATION_TIMEOUT;
                LOG.info("Successfully authneticated with the geo coding service.");
            } else {
                throw new IOException(
                        "failed to authenticate with the geo coding service: "
                                + response.getMESSAGE() + " (error code: "
                                + response.getERRORCODE() + ")");
            }
        }
    }

    /**
     * @return true if the current WiGeoGis session key is expired, false
     *         otherwise.
     */
    private boolean isKeyExpired() {
        return keyExpirationTime < System.currentTimeMillis();
    }
}
