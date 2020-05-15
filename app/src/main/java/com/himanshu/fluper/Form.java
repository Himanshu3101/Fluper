package com.himanshu.fluper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import petrov.kristiyan.colorpicker.ColorPicker;

public class Form extends AppCompatActivity {
    String[] cameraPermissions;
    Boolean cameraPermissionBoolean;
    CardView cardViewPhoto;
    static final int requestCodeCamera = 101;
    CircleImageView imageViewPhoto;
    AppCompatButton addColor,submit;
    TextInputEditText productName, description, regularPrice, salesPrice, store;
    LinearLayoutCompat colorLayout;
    private static final int CAMERA_REQUEST_CODE = 500;
    byte[] picByteArray;
    String colorCode = "";
    int pRegular = 0, pSale = 0;

    Intent intent;
    String name, descriptionUpdate, stores, colors;
    int regPrice, salePrice, id;
    byte[] productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_color_picker);
        initalize();
        intent = getIntent();
        if(intent.hasExtra("ID")){
            setTitle("Update Product");
            getData();
            setData();
            submit.setText("Update");
        }else{
            setTitle("Add Product");
        }


        cameraPermissions = new String[]{Manifest.permission.CAMERA};
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
    }

    private void initalize() {
        cardViewPhoto = findViewById(R.id.cardViewPhoto);
        colorLayout = findViewById(R.id.colorLayout);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        productName = findViewById(R.id.productName);
        description = findViewById(R.id.description);
        regularPrice = findViewById(R.id.regularPrice);
        salesPrice = findViewById(R.id.salesPrice);
        store = findViewById(R.id.store);
        addColor = findViewById(R.id.addColor);
        submit = findViewById(R.id.submit);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        cameraPermissionBoolean = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return cameraPermissionBoolean;
    }

    private void getData() {
        id = getIntent().getIntExtra("ID",-1);
        name = getIntent().getStringExtra("name");
        descriptionUpdate = getIntent().getStringExtra("description");
        stores = getIntent().getStringExtra("stores");
        regPrice = getIntent().getIntExtra("regPrice",0);
        salePrice = getIntent().getIntExtra("salePrice",0);
        productImage = getIntent().getByteArrayExtra("productImage");
        colors = getIntent().getStringExtra("colors");
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        try{
            picByteArray = productImage;
            imageViewPhoto.setImageBitmap(convertToBitmap(productImage));
            productName.setText(name);
            description.setText(descriptionUpdate);
            regularPrice.setText(Integer.toString(regPrice));
            salesPrice.setText(Integer.toString(salePrice));
            store.setText(stores);
            colorCode = colors;
            String[] colorList = colorCode.split(",");
            for(int i=0; i<=colorList.length-1;i++){
                LinearLayoutCompat.LayoutParams vp = new LinearLayoutCompat.LayoutParams(30, 30);
                vp.setMarginStart(10);
                ImageView image = new ImageView(getApplicationContext());
                colorLayout.addView(image);
                image.setLayoutParams(vp);
                image.setMaxHeight(20);
                image.setMaxWidth(20);
                image.setMinimumHeight(20);
                image.setMinimumWidth(20);
                image.setBackgroundColor(Integer.parseInt(colorList[i]));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Bitmap convertToBitmap(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public void startDialog(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, requestCodeCamera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeCamera && resultCode == Activity.RESULT_OK) {
            try {
                Bitmap fileBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (fileBitmap != null) {
                    fileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                }
                picByteArray = stream.toByteArray();
                imageViewPhoto.setImageBitmap(Bitmap.createScaledBitmap(Objects.requireNonNull(fileBitmap), imageViewPhoto.getWidth(),
                        imageViewPhoto.getHeight(), false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void colorPickerCode(View view) {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                if(colorCode.equals("")){
                    colorCode = String.valueOf(color);
                } else {
                    colorCode = colorCode+","+color;
                }
                ImageView image = new ImageView(getApplicationContext());
                LinearLayoutCompat.LayoutParams vp = new LinearLayoutCompat.LayoutParams(30, 30);
                vp.setMarginStart(10);
                colorLayout.addView(image);
                image.setLayoutParams(vp);
                image.setMaxHeight(20);
                image.setMaxWidth(20);
                image.setMinimumHeight(20);
                image.setMinimumWidth(20);
                image.setBackgroundColor(color);
            }
            @Override
            public void onCancel() {
                Toast.makeText(Form.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fromSubmit(View view) {
        String pName = Objects.requireNonNull(productName.getText()).toString();
        String pDescription = Objects.requireNonNull(description.getText()).toString();
        if ((!regularPrice.getText().toString().equals("")) && (!salesPrice.getText().toString().equals(""))){
            pRegular = Integer.parseInt(regularPrice.getText().toString());
            pSale = Integer.parseInt(Objects.requireNonNull(salesPrice.getText()).toString());
        }

        String pStore = Objects.requireNonNull(store.getText()).toString();
        colorCode = colorCode.trim();


        try{
            if (pName.trim().isEmpty() || picByteArray == null || pDescription.trim().isEmpty() || pStore.trim().isEmpty() || colorCode.trim().isEmpty() || pRegular == 0 || pSale == 0){
                Toast.makeText(this, "Please insert all details & Colors.", Toast.LENGTH_SHORT).show();
            }else{
                ProductFields_Note fields_note = new ProductFields_Note( pName, pDescription, pRegular, pSale, picByteArray, colorCode, pStore);
                ProductFieldsNoteViewModel viewModel  = new ViewModelProvider(this).get(ProductFieldsNoteViewModel.class);
                if(intent.hasExtra("ID")){
                    fields_note.setId(id);
                    viewModel.update(fields_note);
                    Toast.makeText(this, "Product Updated Successfully.", Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.insert(fields_note);
                    Toast.makeText(this, "Product Saved Successfully.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }





    }
}
