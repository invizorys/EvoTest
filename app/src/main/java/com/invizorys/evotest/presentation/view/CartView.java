package com.invizorys.evotest.presentation.view;

import com.invizorys.evotest.model.Product;

import java.util.List;

/**
 * Created by Roma on 28.02.2017.
 */

public interface CartView extends BaseView {
    void setCart(List<Product> products);
}
