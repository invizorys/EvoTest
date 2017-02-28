package com.invizorys.evotest.presentation.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.invizorys.evotest.Constants;
import com.invizorys.evotest.data.local.ProductDataSource;
import com.invizorys.evotest.data.remote.PromService;
import com.invizorys.evotest.data.remote.RestClient;
import com.invizorys.evotest.model.CatalogResponse;
import com.invizorys.evotest.model.Product;
import com.invizorys.evotest.presentation.view.CatalogView;
import com.invizorys.evotest.util.SharedPrefHelper;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roma on 25.02.2017.
 */

public class CatalogPresenter extends BasePresenter<CatalogView> {
    private PromService promService;
    private ProductDataSource productDS;
    private final int limit = 60;
    private final int categoryId = 35402;

    public CatalogPresenter() {
        promService = new RestClient().getPromService();
    }

    @Override
    public void attachView(CatalogView view) {
        super.attachView(view);
        productDS = new ProductDataSource();
        getView().setCartBadge(productDS.getCartSize());
    }

    @Override
    public void detachView() {
        productDS.close();
        super.detachView();
    }

    public void getCatalog(Context context, final int offset) {
        MediaType mediaType = MediaType.parse(Constants.TEXT_PLAIN);
        RequestBody body = RequestBody.create(mediaType, PromService.catalogBody);

        String sortType = SharedPrefHelper.getSortType(context);
        Call<CatalogResponse> call = promService.getCatalog(body, limit, offset, categoryId, sortType);
        call.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                if (isViewAttached()) {
                    if (response.isSuccessful()) {
                        if (offset == 0) {
                            getView().setCatalogItems(response.body().getCatalog().getResults());
                        } else {
                            getView().addCatalogItems(response.body().getCatalog().getResults());
                        }
                    } else {
                        getView().setFailureCatalogUpdate(response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<CatalogResponse> call, Throwable t) {
                if (!isViewAttached()) {
                    return;
                }
                String failureText;
                if (!TextUtils.isEmpty(t.getMessage())) {
                    failureText = t.getMessage();
                } else {
                    failureText = t.toString();
                }
                getView().setFailureCatalogUpdate(failureText);
            }
        });
    }

    public void saveFavorite(Product product) {
        if (product.isFavorite()) {
            productDS.save(product);
        } else {
            productDS.deleteFavorite(product);
        }
    }

    public void save2Cart(Product product) {
        if (product.isAddedInCart()) {
            productDS.save(product);
        } else {
            productDS.deleteFromCart(product);
        }
    }

    public List<Product> setFavoriteAndCartFlags(List<Product> catalogItems) {
        ProductDataSource productDS = new ProductDataSource();

        boolean isExistFavorites = productDS.isExistFavorites();
        boolean isExistProductInCart = productDS.isExistProductInCart();

        if (isExistFavorites || isExistProductInCart) {
            for (int i = 0; i < catalogItems.size(); i++) {
                Product product = catalogItems.get(i);
                if (isExistFavorites && productDS.isFavorite(product)) {
                    product.setFavorite(true);
                }
                if (isExistProductInCart && productDS.isAdded2Cart(product)) {
                    product.setAddedInCart(true);
                }
            }
        }

        return catalogItems;
    }
}
