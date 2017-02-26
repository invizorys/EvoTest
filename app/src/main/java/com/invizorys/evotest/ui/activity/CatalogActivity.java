package com.invizorys.evotest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.invizorys.evotest.presentation.presenter.CatalogPresenter;
import com.invizorys.evotest.presentation.view.CatalogView;
import com.invizorys.evotest.ui.adapter.ProductAdapter;

import java.util.List;

public class CatalogActivity extends AppCompatActivity implements CatalogView {
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private CatalogPresenter presenter = new CatalogPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProducts = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.getCatalog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void setCatalogItems(List<Product> catalogItems) {
        productAdapter = new ProductAdapter(catalogItems);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.setAdapter(productAdapter);
    }
}
