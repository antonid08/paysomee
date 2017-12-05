package com.paysomee.client.protocol.service;

import java.util.concurrent.TimeUnit;

import com.paysomee.client.protocol.CardsApi;
import com.paysomee.client.protocol.TokensApi;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Provider for server APIs.
 *
 * @author antonid08
 */
public class ApiProvider {

    private static final String BASE_URL = "http://18.195.179.3/api/bank/";

    @NonNull
    public static CardsApi getCardsApi() {
        return getRetrofit().create(CardsApi.class);
    }

    @NonNull
    public static TokensApi getTokensApi() {
        return getRetrofit().create(TokensApi.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().
            connectTimeout(5, TimeUnit.SECONDS).
            build();

        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }
}
