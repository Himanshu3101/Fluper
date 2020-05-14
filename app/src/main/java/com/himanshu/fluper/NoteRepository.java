package com.himanshu.fluper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private ProductFields_NoteDao productFields_noteDao;
    private LiveData<List<ProductFields_Note>> allNotes;

    public NoteRepository(Application application){
        ProductFields_NoteDatabase database = ProductFields_NoteDatabase.getInstance(application);
        productFields_noteDao = database.productFields_noteDao();
        allNotes = productFields_noteDao.getAllProductFields();
    }

    public void insert(ProductFields_Note note){
        new InsertProductAsyncTask(productFields_noteDao).execute(note);
    }

    public void update(ProductFields_Note note){
        new UpdateProductAsyncTask(productFields_noteDao).execute(note);
    }

    public void delete(ProductFields_Note note){
        new DeleteProductAsyncTask(productFields_noteDao).execute(note);
    }

    public void deleteall(){
        new DeleteAllNotesAsyncTask(productFields_noteDao).execute();
    }

    public LiveData<List<ProductFields_Note>> getAllNotes(){
        return allNotes;
    }

    LiveData<Integer> getNumFiles() {
        return productFields_noteDao.getCount();
    }

    private static class InsertProductAsyncTask extends AsyncTask<ProductFields_Note, Void, Void> {
        private ProductFields_NoteDao productFields_noteDao;

        private InsertProductAsyncTask(ProductFields_NoteDao productFields_noteDao){
            this.productFields_noteDao = productFields_noteDao;
        }

        @Override
        protected Void doInBackground(ProductFields_Note... productFields_notes) {
            productFields_noteDao.insert(productFields_notes[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<ProductFields_Note, Void, Void> {
        private ProductFields_NoteDao productFields_noteDao;

        private UpdateProductAsyncTask(ProductFields_NoteDao productFields_noteDao){
            this.productFields_noteDao = productFields_noteDao;
        }
        @Override
        protected Void doInBackground(ProductFields_Note... productFields_notes) {
            productFields_noteDao.update(productFields_notes[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<ProductFields_Note, Void, Void> {
        private ProductFields_NoteDao productFields_noteDao;

        private DeleteProductAsyncTask(ProductFields_NoteDao productFields_noteDao){
            this.productFields_noteDao = productFields_noteDao;
        }
        @Override
        protected Void doInBackground(ProductFields_Note... productFields_notes) {
            productFields_noteDao.delete(productFields_notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductFields_NoteDao productFields_noteDao;

        private DeleteAllNotesAsyncTask(ProductFields_NoteDao productFields_noteDao) {
            this.productFields_noteDao = productFields_noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            productFields_noteDao.deleteAllNotes();
            return null;
        }
    }
}
