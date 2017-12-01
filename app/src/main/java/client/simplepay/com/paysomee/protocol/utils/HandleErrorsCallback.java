package client.simplepay.com.paysomee.protocol.utils;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.widget.Toast;
import client.simplepay.com.paysomee.R;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Retrofit callback with error processing.
 * On request failure shows toast with error message.
 *
 * @param <T> request result type
 * @author antonid
 */
public abstract class HandleErrorsCallback<T> implements Callback<T> {

    private Context context;

    public HandleErrorsCallback(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be not null!");
        }

        this.context = context;
    }

    @CallSuper
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, R.string.api_error, Toast.LENGTH_SHORT).show();
    }

}
