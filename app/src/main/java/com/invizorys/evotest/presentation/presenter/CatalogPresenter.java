package com.invizorys.evotest.presentation.presenter;

import android.text.TextUtils;

import com.invizorys.evotest.Constants;
import com.invizorys.evotest.data.remote.PromService;
import com.invizorys.evotest.data.remote.RestClient;
import com.invizorys.evotest.model.CatalogResponse;
import com.invizorys.evotest.presentation.view.CatalogView;

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

    public CatalogPresenter() {
        promService = new RestClient().getPromService();
    }

    public void getCatalog() {
        MediaType mediaType = MediaType.parse(Constants.TEXT_PLAIN);
        RequestBody body = RequestBody.create(mediaType, PromService.catalogBody);

        Call<CatalogResponse> call = promService.getCatalog(body, 60, 0, 35402, Constants.SORT_PRICE);
        call.enqueue(new Callback<CatalogResponse>() {
            @Override
            public void onResponse(Call<CatalogResponse> call, Response<CatalogResponse> response) {
                if (isViewAttached()) {
                    if (response.isSuccessful()) {
                        getView().setCatalogItems(response.body().getCatalog().getResults());
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

}
