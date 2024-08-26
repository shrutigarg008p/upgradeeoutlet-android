
package com.eoutlet.Eoutlet.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "new_arrivals",
    "editor_choice",
    "month_deals",
    "recommended",
    "limited_stock"
})

public class ScrolledHomeData {

    @JsonProperty("new_arrivals")
    private List<NewArrival> newArrivals = null;
    @JsonProperty("editor_choice")
    private List<EditorChoice> editorChoice = null;
    @JsonProperty("month_deals")
    private List<MonthDeal> monthDeals = null;
    @JsonProperty("recommended")
    private List<Recommended> recommended = null;
    @JsonProperty("limited_stock")
    private List<LimitedStock> limitedStock = null;

    @JsonProperty("new_arrivals")
    public List<NewArrival> getNewArrivals() {
        return newArrivals;
    }

    @JsonProperty("new_arrivals")
    public void setNewArrivals(List<NewArrival> newArrivals) {
        this.newArrivals = newArrivals;
    }

    @JsonProperty("editor_choice")
    public List<EditorChoice> getEditorChoice() {
        return editorChoice;
    }

    @JsonProperty("editor_choice")
    public void setEditorChoice(List<EditorChoice> editorChoice) {
        this.editorChoice = editorChoice;
    }

    @JsonProperty("month_deals")
    public List<MonthDeal> getMonthDeals() {
        return monthDeals;
    }

    @JsonProperty("month_deals")
    public void setMonthDeals(List<MonthDeal> monthDeals) {
        this.monthDeals = monthDeals;
    }

    @JsonProperty("recommended")
    public List<Recommended> getRecommended() {
        return recommended;
    }

    @JsonProperty("recommended")
    public void setRecommended(List<Recommended> recommended) {
        this.recommended = recommended;
    }

    @JsonProperty("limited_stock")
    public List<LimitedStock> getLimitedStock() {
        return limitedStock;
    }

    @JsonProperty("limited_stock")
    public void setLimitedStock(List<LimitedStock> limitedStock) {
        this.limitedStock = limitedStock;
    }

}
