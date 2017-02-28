package com.invizorys.evotest.presentation.presenter;

import com.invizorys.evotest.data.local.ProductDataSource;
import com.invizorys.evotest.presentation.view.CartView;

/**
 * Created by Roma on 28.02.2017.
 */

public class CartPresenter extends BasePresenter<CartView> {

    public void getCart() {
        ProductDataSource productDS = new ProductDataSource();
        getView().setCart(productDS.getCart());
    }
}
