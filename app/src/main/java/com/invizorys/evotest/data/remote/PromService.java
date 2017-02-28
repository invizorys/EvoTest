package com.invizorys.evotest.data.remote;

import com.invizorys.evotest.model.CatalogResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Roma on 25.02.2017.
 */

public interface PromService {

    String catalogBody = "[{:catalog [{:results [:id :name :price_currency :discounted_price " +
            ":price :url_main_image_200x200 ]}]}]";

    @Headers("Content-Type: application/json")
    @POST("request")
    Call<CatalogResponse> getCatalog(@Body RequestBody body, @Query("limit") int limit,
                                     @Query("offset") int offset, @Query("category") int category,
                                     @Query("sort") String sort);

}
