package de.joeakeem.elasticimmo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents a real estate.
 * 
 * @author Joachim Lengacher
 *
 */
@Document(indexName = "elasticimmo", type = "estate")
public class Estate {

    private static final int MAX_ATTACHMENTS = 5;

    @Id
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String providerId;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String distributor;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String portal;

    @Field(type = FieldType.Boolean)
    private boolean residential;

    @Transient
    private String mode;

    @Field(type = FieldType.Date)
    private Date lastModified = new Date();

    @Field(type = FieldType.String, searchAnalyzer = "german", indexAnalyzer = "german")
    private String title;

    @Field
    private EstateGeo estateGeo;

    @Field(type = FieldType.Boolean)
    private boolean showAddress = Boolean.FALSE;

    @Field
    private List<Attachment> attachments;

    @Field
    private EstateContact estateContact;

    @Field(type = FieldType.String, searchAnalyzer = "german", indexAnalyzer = "german")
    private String impressumAsString;

    @Field
    private Impressum impressum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    /**
     * @return true if this is a 'Wohnimmobilie', false otherwise.
     */
    public boolean isResidential() {
        return residential;
    }

    public void setResidential(boolean residential) {
        this.residential = residential;
    }

    /**
     * @return the time this esate was created or last updated.
     */
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return the update mode
     */
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the geographical location of the estate
     */
    public EstateGeo getEstateGeo() {
        return estateGeo;
    }

    public void setEstateGeo(EstateGeo estateGeo) {
        this.estateGeo = estateGeo;
    }

    /**
     * @return a descriptive title that can be used e.g. as headline in search
     *         results.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return true if the the address (estateGeo) of the estate me be
     *         displayed.
     */
    public boolean isShowAddress() {
        return showAddress;
    }

    public void setShowAddress(boolean showAddress) {
        this.showAddress = showAddress;
    }

    /**
     * @return a list of attachemnts like immages, PDF files etc.
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * @return maximum 5 attachments of the estate (or less if it doesn't have
     *         5).
     */
    public List<Attachment> getTop5Attachments() {
        int max = attachments.size();
        return attachments.subList(0, max < MAX_ATTACHMENTS ? max
                : MAX_ATTACHMENTS);
    }

    /**
     * @return the contact for parties interested in the estate - typically a
     *         broker or the owner.
     */
    public EstateContact getEstateContact() {
        return estateContact;
    }

    public void setEstateContact(EstateContact estateContact) {
        this.estateContact = estateContact;
    }

    public String getImpressumAsString() {
        return impressumAsString;
    }

    public void setImpressumAsString(String impressumAsString) {
        this.impressumAsString = impressumAsString;
    }

    public Impressum getImpressum() {
        return impressum;
    }

    public void setImpressum(Impressum impressum) {
        this.impressum = impressum;
    }
}
