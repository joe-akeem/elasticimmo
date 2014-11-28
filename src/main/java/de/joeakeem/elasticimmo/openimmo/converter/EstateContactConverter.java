package de.joeakeem.elasticimmo.openimmo.converter;

import javax.xml.bind.JAXBElement;

import org.dozer.CustomConverter;

import de.joeakeem.elasticimmo.openimmo.model.Kontaktperson;
import de.joeakeem.elasticimmo.model.EstateContact;

/**
 * Converts an OpenImmo Kontaktperson into an EstateContact.
 * 
 * @author Joachim Lengacher
 *
 */
public class EstateContactConverter implements CustomConverter {

    /**
     * Converts an OpenImmo Kontaktperson into an EstateContact.
     */
    @Override
    public Object convert(Object destination, Object source,
            Class<?> destinationClass, Class<?> sourceClass) {
        if (source == null) {
            return null;
        }

        Kontaktperson sourceContact = (Kontaktperson) source;
        EstateContact destinationContact = destination != null ? (EstateContact) destination
                : new EstateContact();
        for (Object o : sourceContact.getContent()) {
            if (o instanceof JAXBElement) {
                JAXBElement e = (JAXBElement) o;
                String name = e.getName().getLocalPart();
                Object value = e.getValue();
                if ("plz".equals(name)) {
                    destinationContact.setZipCode((String) e.getValue());
                } else if ("ort".equals(name)) {
                    destinationContact.setCity((String) value);
                } else if ("strasse".equals(name)) {
                    destinationContact.setStreet((String) value);
                } else if ("hausnummer".equals(name)) {
                    destinationContact.setHouseNo((String) value);
                } else if ("vorname".equals(name)) {
                    destinationContact.setFirstName((String) value);
                } else if ("name".equals(name)) {
                    destinationContact.setLastName((String) value);
                } else if ("email_direkt".equals(name)) {
                    destinationContact.setEmailDirect((String) value);
                } else if ("tel_handy".equals(name)) {
                    destinationContact.setMobile((String) value);
                }
            }
        }
        return destinationContact;
    }

}
