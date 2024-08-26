
package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({


        "response",
        "ios_version",
        "android_version",
        "api_username",
        "api_password",
        "men_id",
        "women_id",
        "babies_id",
        "children_id"

})
public class UpgradedAppCurrentVersion {

    @JsonProperty("response")
    public String response;
    @JsonProperty("ios_version")
    public String iosVersion;
    @JsonProperty("android_version")
    public String androidVersion;
    @JsonProperty("api_username")
    public String apiUsername;
    @JsonProperty("api_password")
    public String apiPassword;
    @JsonProperty("men_id")
    public String menId;
    @JsonProperty("women_id")
    public String womenId;
    @JsonProperty("babies_id")
    public String babiesId;
    @JsonProperty("children_id")
    public String childrenId;
}
