package com.paysomee.terminal.protocol.service;

import android.support.annotation.NonNull;

import com.paysomee.terminal.protocol.PaymentsApi;

import java.util.concurrent.TimeUnit;

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

    private static final String BASE_URL = "http://18.195.210.240/api/bank/";

    @NonNull
    public static PaymentsApi getCardsApi() {
        return getRetrofit().create(PaymentsApi.class);
    }

    @NonNull
    public static Retrofit getRetrofit() {
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
