package com.himanshu.fluper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProductFieldsNoteViewModel viewModel;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppCompatButton createProduct = findViewById(R.id.createProduct);
        AppCompatButton showProduct = findViewById(R.id.showProduct);

        viewModel = new ViewModelProvider(this).get(ProductFieldsNoteViewModel.class);
        viewModel.getIntegerCountData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int rowCount = new Integer(integer.intValue()+1);
                createProduct.setText("Create Product "+ rowCount);
            }
        });

        showProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ShowProduct.class);
                startActivity(intent1);
            }
        });

        createProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Form.class);
                startActivity(intent1);
            }
        });
    }
}
