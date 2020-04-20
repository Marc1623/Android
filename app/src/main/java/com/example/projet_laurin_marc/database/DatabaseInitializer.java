package com.example.projet_laurin_marc.database;

import android.os.AsyncTask;
import android.util.Log;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    /*
    private static void addCantons(final AppDatabase db, final String email, final String firstName,
                                  final String lastName, final String password) {
        ClientEntity client = new ClientEntity(email, firstName, lastName, password);
        db.clientDao().insert(client);
    }

    private static void addCounties(final AppDatabase db, final String name, final Double balance,
                                   final String owner) {
        AccountEntity account = new AccountEntity(name, balance, owner);
        db.accountDao().insert(account);
    }

    private static void populateWithTestData(AppDatabase db) {

    }*/

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //populateWithTestData(database);
            return null;
        }

    }
}
