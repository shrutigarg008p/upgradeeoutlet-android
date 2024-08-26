
package com.eoutlet.Eoutlet.pojo;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "main_banner",
    "category",
    "sales_banners",
    "flash_sale",
    "featured",
    "recommended",
    "new_arrivals",
    "editor_choice"
})

public class HomeScreenAllData {

    @JsonProperty("main_banner")
    public List<MainBanner> mainBanner = null;
    @JsonProperty("category")
    public List<Category> category = null;
    @JsonProperty("sales_banners")
    public List<SalesBanner> salesBanners = null;
    @JsonProperty("flash_sale")
    public List<FlashSale> flashSale = null;
    @JsonProperty("featured")
    public List<Featured> featured = null;
    @JsonProperty("recommended")
    public List<Recommended> recommended = null;
    @JsonProperty("new_arrivals")
    public List<NewArrival> newArrivals = null;
    @JsonProperty("editor_choice")
    public List<EditorChoice> editorChoice = null;

}
