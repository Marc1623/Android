package com.example.projet_laurin_marc.database.async.person;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_laurin_marc.BaseApp;
import com.example.projet_laurin_marc.database.entity.Person;
import com.example.projet_laurin_marc.database.util.OnAsyncEventListener;

public class DeletePerson extends AsyncTask<Person, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeletePerson(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Person... params) {
        try {
            for (Person person : params)
                ((BaseApp) application).getDatabase().personDao()
                        .delete(person);
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
