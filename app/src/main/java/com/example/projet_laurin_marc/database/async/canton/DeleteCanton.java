package com.example.projet_laurin_marc.database.async.canton;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.Canton;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

public class DeleteCanton extends AsyncTask<Canton, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteCanton(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Canton... params) {
        try {
            for (Canton canton : params)
                ((BaseApp) application).getDatabase().cantonDao()
                        .delete(canton);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
