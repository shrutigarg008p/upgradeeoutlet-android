package com.eoutlet.Eoutlet.pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "msg",
        "data",
        "cod_charges",

        "cod_label",
       // "tax",
       // "cod_charges",
      //  "sa_shipping",
        "subtotal",
        "grandtotal",
        "tax",
        "shipping",
        "goodstax_rate",
        "apply_tax",
        "gift_wrap_fee"


})
public class ViewCart1 {

    @JsonProperty("msg")
    public String msg;




    @JsonProperty("data")
    public List<ViewCartData> data = null;




    @JsonProperty("subtotal")
    public String subtotal;
    @JsonProperty("gift_wrap_fee")
    public String gift_wrap_fee;

    @JsonProperty("grandtotal")
    public String grandtotal;
    @JsonProperty("tax")
    public String tax;

    @JsonProperty("shipping")
    public String shipping;

    @JsonProperty("cod_charges")
    public int cod_charges;
    @JsonProperty("cod_label")
    public String cod_label;

    @JsonProperty("goodstax_rate")
    public int goodstax_rate;

    @JsonProperty("apply_tax")
    public String apply_tax;


}
