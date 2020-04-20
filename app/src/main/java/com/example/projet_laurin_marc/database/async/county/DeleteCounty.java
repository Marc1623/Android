package com.example.projet_laurin_marc.database.async.county;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.County;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

public class DeleteCounty extends AsyncTask<County, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteCounty(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(County... params) {
        try {
            for (County county : params)
                ((BaseApp) application).getDatabase().countyDao()
                        .delete(county);
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
