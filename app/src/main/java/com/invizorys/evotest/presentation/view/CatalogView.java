package com.invizorys.evotest.presentation.view;

import com.invizorys.evotest.model.CatalogItem;

import java.util.List;

/**
 * Created by Roma on 25.02.2017.
 */
public interface CatalogView extends BaseView {
    void setCatalogItems(List<CatalogItem> catalogItems);
}