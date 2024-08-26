package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "media_type",
        "label",
        "position",
        "disabled",
        "types",
        "file"
})
public class MediaGalleryEntry {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("media_type")
    public String mediaType;
    @JsonProperty("label")
    public Object label;
    @JsonProperty("position")
    public Integer position;
    @JsonProperty("disabled")
    public Boolean disabled;
    @JsonProperty("types")
    public List<String> types = null;
    @JsonProperty("file")
    public String file;

}