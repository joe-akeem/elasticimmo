package de.joeakeem.elasticimmo.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Represents an attachment of a real estate such as a picture, a PDF file etc.
 * @author Joachim Lengacher
 *
 */
public class Attachment {

    @Field(type = FieldType.String)
    private String title;

    @Field(type = FieldType.String)
    private String group;

    @Field(type = FieldType.String)
    private String format;

    @Field(type = FieldType.String)
    private String path;

    /**
     * @return a descriptive title that can be used e.g. as immage caption.
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the attachment group such as 'FILM' 'IMAGE'
     */
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * @return the format of the attachment such as JPG, PDF etc. 
     */
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the physical location of the attachment (e.g. a file name/path or a URL)
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
