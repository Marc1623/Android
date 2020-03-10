package com.example.projet_laurin_marc.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.AppDatabase;
import com.example.projet_laurin_marc.database.dao.AddressDao;
import com.example.projet_laurin_marc.database.entity.Address;


import java.util.List;

public class AddressRepository {

    private AddressDao addressDao;
    private LiveData <List<Address>> addresseslive;

    public AddressRepository(Application app){
        AppDatabase database = AppDatabase.getInstance(app);
        addressDao = database.addressDao();
        addresseslive = addressDao.getAll();
    }

    public void insert(Address address){
        new InsertAddressAsyncTask(addressDao).execute(address);
    }

    public void update(Address address){
        new UpdateAddressAsyncTask(addressDao).execute(address);
    }

    public void delete(Address address){
        new DeleteAddressAsyncTask(addressDao).execute(address);
    }

    public LiveData<List<Address>> getAllAddress() {
        return addresseslive;
    }

    private static class InsertAddressAsyncTask extends AsyncTask <Address, Void, Void> {
        private AddressDao addressDao;

        public InsertAddressAsyncTask(AddressDao addressDao) {
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.insert(addresses[0]);
            return null;
        }
    }

        private static class UpdateAddressAsyncTask extends AsyncTask <Address, Void, Void>{
            private AddressDao addressDao;

            public UpdateAddressAsyncTask(AddressDao addressDao){
                this.addressDao = addressDao;
            }

            @Override
            protected Void doInBackground(Address... addresses){
                try{
                    for(Address address : addresses){
                        addressDao.update(address);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }

    private static class DeleteAddressAsyncTask extends AsyncTask <Address, Void, Void>{
        private AddressDao addressDao;

        public DeleteAddressAsyncTask(AddressDao addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses){
            try{
                for(Address address : addresses){
                    addressDao.delete(address);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
