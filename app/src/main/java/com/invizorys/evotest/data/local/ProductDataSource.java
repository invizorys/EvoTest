package com.invizorys.evotest.data.local;

import com.invizorys.evotest.model.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Roma on 27.02.2017.
 */

public class ProductDataSource {
    private Realm realm;

    public ProductDataSource() {
        this.realm = Realm.getDefaultInstance();
    }

    public void saveFavorite(Product product) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(product);
        realm.commitTransaction();
    }

    public void deleteFavorite(Product product) {
        realm.beginTransaction();
        RealmResults<Product> results = realm.where(Product.class).equalTo("id", product.getId()).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public List<Product> getFavorites() {
        RealmResults<Product> realmResults = realm.where(Product.class).findAll();
        return realm.copyFromRealm(realmResults);
    }

    public boolean isExistFavorites() {
        RealmResults<Product> realmResults = realm.where(Product.class).findAll();
        return !realmResults.isEmpty();
    }

    public boolean isFavorite(Product product) {
        RealmResults<Product> realmResults = realm.where(Product.class).equalTo("id", product.getId()).findAll();
        return !realmResults.isEmpty();
    }

    public void close() {
        if (realm != null) {
            realm.close();
        }
    }
}
