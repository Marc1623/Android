package com.example.projet_laurin_marc.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.projet_laurin_marc.database.AppDatabase;
import com.example.projet_laurin_marc.database.dao.PersonDao;
import com.example.projet_laurin_marc.database.entity.Person;

import java.util.List;

public class PersonRepository {

    private PersonDao personDao;
    private LiveData <List<Person>> personslive;

    public PersonRepository(Application app){
        AppDatabase database = AppDatabase.getInstance(app);
        //personDao = database.personDao();
        personslive = personDao.getAll();
    }

    public void insert(Person person){
        new InsertPersonAsyncTask(personDao).execute(person);
    }

    public void update(Person person){
        new UpdatePersonAsyncTask(personDao).execute(person);
    }

    public void delete(Person person){
        new DeletePersonAsyncTask(personDao).execute(person);
    }

    public LiveData<List<Person>> getAllPersons() {
        return personslive;
    }

    private static class InsertPersonAsyncTask extends AsyncTask <Person, Void, Void> {
        private PersonDao personDao;

        public InsertPersonAsyncTask(PersonDao personDao) {
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... persons) {
            personDao.insert(persons[0]);
            return null;
        }
    }

        private static class UpdatePersonAsyncTask extends AsyncTask <Person, Void, Void>{
            private PersonDao personDao;

            public UpdatePersonAsyncTask(PersonDao personDao){
                this.personDao = personDao;
            }

            @Override
            protected Void doInBackground(Person... persons){
                try{
                    for(Person person : persons){
                        personDao.update(person);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }

    private static class DeletePersonAsyncTask extends AsyncTask <Person, Void, Void>{
        private PersonDao personDao;

        public DeletePersonAsyncTask(PersonDao personDao){
            this.personDao = personDao;
        }

        @Override
        protected Void doInBackground(Person... persons){
            try{
                for(Person person : persons){
                    personDao.delete(person);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
