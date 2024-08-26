
package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "brand_banner"
})

public class GetBrandBannerdetail {

    @JsonProperty("brand_banner")
    public BrandBanner brandBanner = null;

}
