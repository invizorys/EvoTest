package com.invizorys.evotest.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.invizorys.evotest.presentation.presenter.CatalogPresenter;
import com.invizorys.evotest.presentation.view.CatalogView;
import com.invizorys.evotest.ui.adapter.ProductAdapter;

import java.util.List;

public class CatalogActivity extends AppCompatActivity implements CatalogView {
    private RecyclerView rvProducts;
    private SwipeRefreshLayout swipeContainer;
    private ProductAdapter productAdapter;
    private CatalogPresenter presenter = new CatalogPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvProducts = (RecyclerView) findViewById(R.id.recycler_view);
        initGridDisplay();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCatalog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        swipeContainer.setRefreshing(true);
        presenter.getCatalog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void setCatalogItems(List<Product> catalogItems) {
        swipeContainer.setRefreshing(false);

        productAdapter = new ProductAdapter(catalogItems);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.setAdapter(productAdapter);
    }

    @Override
    public void setFailureCatalogResponse(String message) {
        swipeContainer.setRefreshing(false);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initGridDisplay() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
    }

    private void initListDisplay() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
    }
}
