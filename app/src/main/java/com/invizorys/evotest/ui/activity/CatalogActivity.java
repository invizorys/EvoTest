package com.invizorys.evotest.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.invizorys.evotest.R;
import com.invizorys.evotest.model.Product;
import com.invizorys.evotest.presentation.presenter.CatalogPresenter;
import com.invizorys.evotest.presentation.view.CatalogView;
import com.invizorys.evotest.ui.adapter.ProductAdapter;
import com.invizorys.evotest.util.EndlessRecyclerOnScrollListener;
import com.invizorys.evotest.util.SharedPrefHelper;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CatalogActivity extends AppCompatActivity implements CatalogView {
    private RecyclerView rvProducts;
    private SwipeRefreshLayout swipeContainer;
    private ProductAdapter productAdapter;
    private MenuItem menuItemGrid;
    private MaterialSearchView searchView;
    private CatalogPresenter presenter = new CatalogPresenter();
    private String[] productNamesArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvProducts = (RecyclerView) findViewById(R.id.recycler_view);
        initGridDisplay();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCatalog(0);
            }
        });

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(CatalogActivity.this, R.string.will_be_implemented_in_the_next_version,
                        Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.catalog_menu, menu);

        menuItemGrid = menu.findItem(R.id.action_grid_on);
        if (!SharedPrefHelper.isGridOn(this)) {
            setMenuItemGrid();
        }

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid_on:
                boolean isGridOn = SharedPrefHelper.isGridOn(this);
                if (isGridOn) {
                    initListDisplay();
                    setMenuItemGrid();
                } else {
                    initGridDisplay();
                    setMenuItemList();
                }
                SharedPrefHelper.saveGridOn(this, !isGridOn);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
        swipeContainer.setRefreshing(true);
        presenter.getCatalog(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    private void setMenuItemGrid() {
        menuItemGrid.setIcon(R.drawable.ic_grid);
        menuItemGrid.setTitle(R.string.grid);
    }

    private void setMenuItemList() {
        menuItemGrid.setIcon(R.drawable.ic_list);
        menuItemGrid.setTitle(R.string.list);
    }

    @Override
    public void setCatalogItems(List<Product> catalogItems) {
        swipeContainer.setRefreshing(false);

        productAdapter = new ProductAdapter(catalogItems);
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        rvProducts.setAdapter(productAdapter);
        addProductName4LocalSearch(catalogItems, true);
    }

    @Override
    public void addCatalogItems(List<Product> catalogItems) {
        swipeContainer.setRefreshing(false);
        productAdapter.addProducts(catalogItems);
        addProductName4LocalSearch(catalogItems, false);
    }

    private void addProductName4LocalSearch(List<Product> products, boolean isNewList) {
        if (isNewList) {
            productNamesArr = new String[products.size()];
            for (int i = 0; i < products.size(); i++) {
                productNamesArr[i] = products.get(i).getName();
            }
        } else {
            String[] tempArr = new String[products.size()];
            for (int i = 0; i < products.size(); i++) {
                tempArr[i] = products.get(i).getName();
            }
            List<String> both = new ArrayList<>(productNamesArr.length + tempArr.length);
            Collections.addAll(both, productNamesArr);
            Collections.addAll(both, tempArr);
            productNamesArr = both.toArray(new String[both.size()]);
        }
        searchView.setSuggestions(productNamesArr);
    }

    @Override
    public void setFailureCatalogUpdate(int code) {
        swipeContainer.setRefreshing(false);
        Toast.makeText(this, getString(R.string.error_occurred_while_update_catalog) +
                "\n" + getString(R.string.code) + code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFailureCatalogUpdate(String message) {
        swipeContainer.setRefreshing(false);
        Toast.makeText(this, getString(R.string.error_occurred_while_update_catalog) +
                "\n" + message, Toast.LENGTH_SHORT).show();
    }

    private void initGridDisplay() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int totalItemCount) {
                swipeContainer.setRefreshing(true);
                presenter.getCatalog(totalItemCount);
            }
        });
    }

    private void initListDisplay() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
        rvProducts.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int totalItemCount) {
                swipeContainer.setRefreshing(true);
                presenter.getCatalog(totalItemCount);
            }
        });
    }
}
