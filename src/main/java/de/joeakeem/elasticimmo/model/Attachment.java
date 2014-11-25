package de.joeakeem.elasticimmo.model;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Attachment {

    @Field(type = FieldType.String)
    private String title;

    @Field(type = FieldType.String)
    private String group;

    @Field(type = FieldType.String)
    private String location;

    @Field(type = FieldType.String)
    private String format;

    @Field(type = FieldType.String)
    private String path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
