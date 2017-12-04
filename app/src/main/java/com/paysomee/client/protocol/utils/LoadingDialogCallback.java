package com.paysomee.client.protocol.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Retrofit callback with showing "loading dialog".
 * Shows not cancelled dialog with text while waiting for response.
 *
 * @see HandleErrorsCallback
 *
 * @param <T> request result type
 * @author antonid
 */
public abstract class LoadingDialogCallback<T> extends HandleErrorsCallback<T> {

    private ProgressDialog loadingDialog;

    public LoadingDialogCallback(@NonNull Context context) {
        super(context);

        loadingDialog = new ProgressDialog(context);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage("Loading...");
        loadingDialog.show();
    }

    @CallSuper
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        super.onFailure(call, t);

        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
