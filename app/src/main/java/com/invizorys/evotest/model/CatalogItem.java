package com.invizorys.evotest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roma on 25.02.2017.
 */

public class CatalogItem {
    private int id;
    @SerializedName("price_currency")
    private String priceCurrency;
    @SerializedName("url_main_image_200x200")
    private String imageUrl;
    private String name;
    @SerializedName("discounted_price")
    private double discountedPrice;
    private double price;

    public int getId() {
        return id;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public double getPrice() {
        return price;
    }
}
