package com.eoutlet.Eoutlet.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "website_id",
        "entity_id",
        "email",
        "group_id",
        "increment_id",
        "store_id",
        "created_at",
        "updated_at",
        "is_active",
        "disable_auto_group_change",
        "created_in",
        "prefix",
        "firstname",
        "middlename",
        "lastname",
        "suffix",
        "dob",
        "password_hash",
        "rp_token",
        "rp_token_created_at",
        "default_billing",
        "default_shipping",
        "taxvat",
        "confirmation",
        "gender",
        "mobilenumber",
        "failures_num",
        "first_failure",
        "lock_expires",
        "is_disabled",
        "enable_wallet_system",
        "device_used"
})
public class SignupData {

    @JsonProperty("website_id")
    public String websiteId;
    @JsonProperty("entity_id")
    public String entityId;
    @JsonProperty("email")
    public String email;
    @JsonProperty("group_id")
    public String groupId;
    @JsonProperty("increment_id")
    public Object incrementId;
    @JsonProperty("store_id")
    public String storeId;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("is_active")
    public String isActive;
    @JsonProperty("disable_auto_group_change")
    public String disableAutoGroupChange;
    @JsonProperty("created_in")
    public String createdIn;
    @JsonProperty("prefix")
    public Object prefix;
    @JsonProperty("firstname")
    public String firstname;
    @JsonProperty("middlename")
    public Object middlename;
    @JsonProperty("lastname")
    public String lastname;
    @JsonProperty("suffix")
    public Object suffix;
    @JsonProperty("dob")
    public Object dob;
    @JsonProperty("password_hash")
    public String passwordHash;
    @JsonProperty("rp_token")
    public String rpToken;
    @JsonProperty("rp_token_created_at")
    public String rpTokenCreatedAt;
    @JsonProperty("default_billing")
    public Object defaultBilling;
    @JsonProperty("default_shipping")
    public Object defaultShipping;
    @JsonProperty("taxvat")
    public Object taxvat;
    @JsonProperty("confirmation")
    public Object confirmation;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("mobilenumber")
    public Object mobilenumber;
    @JsonProperty("failures_num")
    public String failuresNum;
    @JsonProperty("first_failure")
    public String firstFailure;
    @JsonProperty("lock_expires")
    public Object lockExpires;
    @JsonProperty("is_disabled")
    public String isDisabled;
    @JsonProperty("enable_wallet_system")
    public String enableWalletSystem;
    @JsonProperty("device_used")
    public String deviceUsed;


}
