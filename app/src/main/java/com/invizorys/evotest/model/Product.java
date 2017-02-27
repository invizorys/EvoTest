package com.invizorys.evotest.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Roma on 25.02.2017.
 */

@RealmClass
public class Product implements RealmModel {
    @PrimaryKey
    private int id;
    @SerializedName("price_currency")
    private String priceCurrency;
    @SerializedName("url_main_image_200x200")
    private String imageUrl;
    private String name;
    @SerializedName("discounted_price")
    private double discountedPrice;
    private double price;

    private boolean isFavorite;

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getPriceAndCurrency() {
        return price + " " + priceCurrency;
    }
}
