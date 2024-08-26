package com.eoutlet.Eoutlet.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpgradeReturnResponseFormOrderItems {
    private String item_id;
    private String name;
    private String sku;
    private String ordered_qty;
    private String image;
    private List<UpgradeReturnResponseOrderFormOptions> options = null;
    private List<UpgradeReturnResponseFormReturnReasons> returnreason = null;
    private List<UpgradeReturnResponseFormItemCondition> itemcondition = null;
    private List<UpgradeReturnResponseFormRmaResolution> rmaresolution = null;
    private Integer itemstatus;
    private String rma_id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private String selectedPosition;
    private String itemConditionPosition;
    private String resolutionPosition;
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOrdered_qty() {
        return ordered_qty;
    }

    public void setOrdered_qty(String ordered_qty) {
        this.ordered_qty = ordered_qty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<UpgradeReturnResponseOrderFormOptions> getOptions() {
        return options;
    }

    public void setOptions(List<UpgradeReturnResponseOrderFormOptions> options) {
        this.options = options;
    }

    public List<UpgradeReturnResponseFormReturnReasons> getReturnreason() {
        return returnreason;
    }

    public void setReturnreason(List<UpgradeReturnResponseFormReturnReasons> returnreason) {
        this.returnreason = returnreason;
    }

    public List<UpgradeReturnResponseFormItemCondition> getItemcondition() {
        return itemcondition;
    }

    public void setItemcondition(List<UpgradeReturnResponseFormItemCondition> itemcondition) {
        this.itemcondition = itemcondition;
    }

    public List<UpgradeReturnResponseFormRmaResolution> getRmaresolution() {
        return rmaresolution;
    }

    public void setRmaresolution(List<UpgradeReturnResponseFormRmaResolution> rmaresolution) {
        this.rmaresolution = rmaresolution;
    }

    public Integer getItemstatus() {
        return itemstatus;
    }

    public void setItemstatus(Integer itemstatus) {
        this.itemstatus = itemstatus;
    }

    public String getRma_id() {
        return rma_id;
    }

    public void setRma_id(String rma_id) {
        this.rma_id = rma_id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public String getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(String selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getItemConditionPosition() {
        return itemConditionPosition;
    }

    public void setItemConditionPosition(String itemConditionPosition) {
        this.itemConditionPosition = itemConditionPosition;
    }

    public String getResolutionPosition() {
        return resolutionPosition;
    }

    public void setResolutionPosition(String resolutionPosition) {
        this.resolutionPosition = resolutionPosition;
    }
}
