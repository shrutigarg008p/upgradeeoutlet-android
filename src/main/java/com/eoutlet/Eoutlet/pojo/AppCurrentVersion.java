
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "msg",
    "ios_version",
    "android_version"
})
public class AppCurrentVersion {

    @JsonProperty("msg")
    public String msg;
    @JsonProperty("ios_version")
    public String iosVersion;
    @JsonProperty("android_version")
    public String androidVersion;

}
