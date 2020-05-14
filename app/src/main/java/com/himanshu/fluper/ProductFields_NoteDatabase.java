package com.himanshu.fluper;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ProductFields_Note.class}, version =1)
public abstract class ProductFields_NoteDatabase extends RoomDatabase {
    private static ProductFields_NoteDatabase instance;
    public abstract ProductFields_NoteDao productFields_noteDao();

    public static synchronized ProductFields_NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ProductFields_NoteDatabase.class,
                    "product_fields")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductFields_NoteDao productFields_noteDao;
        private ProductFields_Note productFields_note;

        private PopulateDbAsyncTask(ProductFields_NoteDatabase productFields_noteDatabase){
            productFields_noteDao = productFields_noteDatabase.productFields_noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                productFields_noteDao.insert(productFields_note);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


}
