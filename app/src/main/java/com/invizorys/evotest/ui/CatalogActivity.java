package com.invizorys.evotest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.CatalogItem;
import com.invizorys.evotest.presentation.presenter.CatalogPresenter;
import com.invizorys.evotest.presentation.view.CatalogView;

import java.util.List;

public class CatalogActivity extends AppCompatActivity implements CatalogView {
    private CatalogPresenter presenter = new CatalogPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void setCatalogItems(List<CatalogItem> catalogItems) {

    }
}
