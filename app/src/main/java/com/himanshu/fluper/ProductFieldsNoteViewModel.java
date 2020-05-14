package com.himanshu.fluper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductFieldsNoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<ProductFields_Note>> listLiveData;
    private LiveData<Integer> integerCountData;

    public ProductFieldsNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        listLiveData = repository.getAllNotes();
        integerCountData = repository.getNumFiles();
    }

    public void insert(ProductFields_Note productFields_note){
        repository.insert(productFields_note);
    }
    public void update(ProductFields_Note productFields_note){
        repository.update(productFields_note);
    }
    public void delete(ProductFields_Note productFields_note){
        repository.delete(productFields_note);
    }

    public void deleteall(){
        repository.deleteall();
    }

    public LiveData<List<ProductFields_Note>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<Integer> getIntegerCountData() { return integerCountData; }
}
