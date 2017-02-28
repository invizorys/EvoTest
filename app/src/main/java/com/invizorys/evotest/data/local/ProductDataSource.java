package com.invizorys.evotest.data.local;

import com.invizorys.evotest.model.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Roma on 27.02.2017.
 */

public class ProductDataSource {
    private final String ID = "id";
    private final String IS_FAVORITE = "isFavorite";
    private final String IS_ADDED_IN_CART = "isAddedInCart";
    private Realm realm;

    public ProductDataSource() {
        this.realm = Realm.getDefaultInstance();
    }

    public void save(Product product) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(product);
        realm.commitTransaction();
    }

    public void deleteFavorite(Product product) {
        realm.beginTransaction();
        if (product.isAddedInCart()) {
            realm.copyToRealmOrUpdate(product);
        } else {
            RealmResults<Product> results = realm.where(Product.class).equalTo(ID, product.getId()).findAll();
            results.deleteAllFromRealm();
        }
        realm.commitTransaction();
    }

    public void deleteFromCart(Product product) {
        realm.beginTransaction();
        if (product.isFavorite()) {
            realm.copyToRealmOrUpdate(product);
        } else {
            RealmResults<Product> results = realm.where(Product.class).equalTo(ID, product.getId()).findAll();
            results.deleteAllFromRealm();
        }
        realm.commitTransaction();
    }

    public List<Product> getFavorites() {
        RealmResults<Product> realmResults = realm.where(Product.class).equalTo(IS_FAVORITE, true).findAll();
        return realm.copyFromRealm(realmResults);
    }

    public List<Product> getCart() {
        RealmResults<Product> realmResults = realm.where(Product.class).equalTo(IS_ADDED_IN_CART, true).findAll();
        return realm.copyFromRealm(realmResults);
    }

    public int getCartSize() {
        RealmResults<Product> realmResults = realm.where(Product.class).equalTo(IS_ADDED_IN_CART, true).findAll();
        return realmResults.size();
    }

    public boolean isExistFavorites() {
        RealmResults<Product> realmResults = realm.where(Product.class)
                .equalTo(IS_FAVORITE, true)
                .findAll();
        return !realmResults.isEmpty();
    }

    public boolean isExistProductInCart() {
        RealmResults<Product> realmResults = realm.where(Product.class)
                .equalTo(IS_ADDED_IN_CART, true)
                .findAll();
        return !realmResults.isEmpty();
    }

    public boolean isFavorite(Product product) {
        RealmResults<Product> realmResults = realm.where(Product.class)
                .equalTo(ID, product.getId())
                .equalTo(IS_FAVORITE, true)
                .findAll();
        return !realmResults.isEmpty();
    }

    public boolean isAdded2Cart(Product product) {
        RealmResults<Product> realmResults = realm.where(Product.class)
                .equalTo(ID, product.getId())
                .equalTo(IS_ADDED_IN_CART, true)
                .findAll();
        return !realmResults.isEmpty();
    }

    public void close() {
        if (realm != null) {
            realm.close();
        }
    }
}
