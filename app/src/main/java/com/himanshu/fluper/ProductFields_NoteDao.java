package com.himanshu.fluper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductFields_NoteDao {

    @Insert
    void insert(ProductFields_Note productFields_note);

    @Update
    void update(ProductFields_Note productFields_note);

    @Delete
    void delete(ProductFields_Note productFields_note);

    @Query("DELETE FROM product_fields")
    void deleteAllNotes();

    @Query("SELECT * FROM product_fields ORDER BY id DESC")
    LiveData<List<ProductFields_Note>> getAllProductFields();

    @Query("SELECT COUNT(*) FROM product_fields")
    LiveData<Integer> getCount();
}
