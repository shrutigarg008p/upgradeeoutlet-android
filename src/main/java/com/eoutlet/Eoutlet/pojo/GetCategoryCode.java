
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
    "children_id",
        "newarrival_id",
    "social_password",
        "sortby_newarrival",
        "paylater_min_amount",
        "paylater_max_amount",
        "payinstallment_min_amount",
        "payinstallment_max_amount"

})
public class GetCategoryCode {

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
    @JsonProperty("newarrival_id")
    public String newarrivalId;
    @JsonProperty("social_password")
    public String socialPassword;
    @JsonProperty("sortby_newarrival")
    public String sortby_newarrival;

    @JsonProperty("paylater_min_amount")
    public String paylaterMinAmount;
    @JsonProperty("paylater_max_amount")
    public String paylaterMaxAmount;
    @JsonProperty("payinstallment_min_amount")
    public String payinstallmentMinAmount;
    @JsonProperty("payinstallment_max_amount")
    public String payinstallmentMaxAmount;

}
