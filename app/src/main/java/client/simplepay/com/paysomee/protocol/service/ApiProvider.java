package client.simplepay.com.paysomee.protocol.service;

import java.util.concurrent.TimeUnit;

import android.support.annotation.NonNull;
import client.simplepay.com.paysomee.protocol.CardsApi;
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

    private static final String BASE_URL = "http://pay.somee.com/api/";

    @NonNull
    public static CardsApi getCardsApi() {
        return getRetrofit().create(CardsApi.class);
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
