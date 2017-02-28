package com.invizorys.evotest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.invizorys.evotest.presentation.presenter.CartPresenter;
import com.invizorys.evotest.presentation.view.CartView;
import com.invizorys.evotest.ui.adapter.CartAdapter;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartView {
    private RecyclerView rvProducts;
    private CartPresenter presenter = new CartPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rvProducts = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.getCart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }


    @Override
    public void setCart(List<Product> products) {
        CartAdapter adapter = new CartAdapter(products);
        rvProducts.setAdapter(adapter);
    }
}
