package de.joeakeem.elasticimmo.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document( indexName = "elasticestate" , type = "estate")
public class Estate {

	@Id
    private String id;
	
	@Field(type = FieldType.Boolean)
	private boolean livingEstate;
	
	@Transient
	private String mode;
	
	@Field(type = FieldType.Date)
	private Date lastModified = new Date();
	
	@Field(type = FieldType.String)
	private String title;
	
	@Field(type = FieldType.Nested)
	private EstateGeo estateGeo;
	
	@Field(type = FieldType.Boolean)
	private boolean showAddress = Boolean.FALSE;
	
	@Field(type = FieldType.Nested)
	private List<Attachment> attachments;
	
	@Field(type = FieldType.Nested)
	private EstateContact estateContact;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLivingEstate() {
		return livingEstate;
	}

	public void setLivingEstate(boolean livingEstate) {
		this.livingEstate = livingEstate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public EstateGeo getEstateGeo() {
		return estateGeo;
	}

	public void setEstateGeo(EstateGeo estateGeo) {
		this.estateGeo = estateGeo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShowAddress() {
		return showAddress;
	}

	public void setShowAddress(boolean showAddress) {
		this.showAddress = showAddress;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public List<Attachment> getTop5Attachments() {
		int max = attachments.size();
		return attachments.subList(0, max < 5 ? max : 5);
	}

	public EstateContact getEstateContact() {
		return estateContact;
	}

	public void setEstateContact(EstateContact estateContact) {
		this.estateContact = estateContact;
	}
}
