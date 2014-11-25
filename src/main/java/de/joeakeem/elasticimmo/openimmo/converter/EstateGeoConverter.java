package de.joeakeem.elasticimmo.openimmo.converter;

import javax.xml.bind.JAXBElement;

import org.dozer.CustomConverter;

import de.joeakeem.elasticimmo.model.EstateGeo;
import de.joeakeem.elasticimmo.openimmo.model.Geo;

public class EstateGeoConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source,
            Class<?> destinationClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        Geo sourceGeo = (Geo) source;
        EstateGeo destinationGeo = destination != null ? (EstateGeo) destination
                : new EstateGeo();
        for (Object o : sourceGeo.getContent()) {
            if (o instanceof JAXBElement) {
                JAXBElement e = (JAXBElement) o;
                String name = e.getName().getLocalPart();
                Object value = e.getValue();
                if ("plz".equals(name)) {
                    destinationGeo.setZipCode((String) e.getValue());
                } else if ("ort".equals(name)) {
                    destinationGeo.setCity((String) value);
                } else if ("strasse".equals(name)) {
                    destinationGeo.setStreet((String) value);
                } else if ("hausnummer".equals(name)) {
                    destinationGeo.setHouseNo((String) value);
                }
            }
        }
        return destinationGeo;
    }

}
