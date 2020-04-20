package com.example.projet_laurin_marc.database.async.user;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.User;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

public class DeleteUser extends AsyncTask<User, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteUser(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(User... params) {
        try {
            for (User user : params)
                ((BaseApp) application).getDatabase().userDao()
                        .delete(user);
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
