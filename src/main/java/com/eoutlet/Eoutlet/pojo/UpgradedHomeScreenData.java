package com.eoutlet.Eoutlet.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradedHomeScreenData {

    private List<UpgradedHomeScreenMainBanner> main_banner = null;
    private List<UpgradedHomeScreenCategory> category = null;
    private List<UpgradeHomeScreenFlashSale> flash_sale = null;
    private List<UpgradedHomeScreenHighlightedSlider> highlighted_slider = null;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<UpgradedHomeScreenMainBanner> getMain_banner() {
        return main_banner;
    }

    public void setMain_banner(List<UpgradedHomeScreenMainBanner> main_banner) {
        this.main_banner = main_banner;
    }

    public List<UpgradedHomeScreenCategory> getCategory() {
        return category;
    }

    public void setCategory(List<UpgradedHomeScreenCategory> category) {
        this.category = category;
    }

    public List<UpgradeHomeScreenFlashSale> getFlash_sale() {
        return flash_sale;
    }

    public void setFlash_sale(List<UpgradeHomeScreenFlashSale> flash_sale) {
        this.flash_sale = flash_sale;
    }

    public List<UpgradedHomeScreenHighlightedSlider> getHighlighted_slider() {
        return highlighted_slider;
    }

    public void setHighlighted_slider(List<UpgradedHomeScreenHighlightedSlider> highlighted_slider) {
        this.highlighted_slider = highlighted_slider;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}