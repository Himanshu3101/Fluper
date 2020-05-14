package com.himanshu.fluper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<ProductFields_Note> productFields_notes  = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        ProductFields_Note fields_note = productFields_notes.get(position);
            holder.name.setText(fields_note.getName());
            holder.description.setText(fields_note.getDescription());
            holder.stores.setText(fields_note.getStores());
            holder.regularPrice.setText(Integer.toString(fields_note.getRegularPrice()));
            holder.salePrice.setText(Integer.toString(fields_note.getSalePrice()));
            holder.product_Image.setImageBitmap(convertToBitmap(fields_note.getProductPhoto()));
            String[] colorList = fields_note.getColors().split(",");
            for (int i = 0; i <= colorList.length - 1; i++) {
                LinearLayoutCompat.LayoutParams vp = new LinearLayoutCompat.LayoutParams(30, 30);
                vp.setMarginStart(10);
                ImageView image = new ImageView(holder.colorLayout.getContext());
                holder.colorLayout.addView(image);
                image.setLayoutParams(vp);
                image.setMaxHeight(20);
                image.setMaxWidth(20);
                image.setMinimumHeight(20);
                image.setMinimumWidth(20);
                image.setBackgroundColor(Integer.parseInt(colorList[i]));
            }
    }

    @Override
    public int getItemCount() {
        return productFields_notes.size();
    }

    public void setProductFields_notes(List<ProductFields_Note> productFields_notes) {
        this.productFields_notes = productFields_notes;
        notifyDataSetChanged();
    }

    public ProductFields_Note getDataAt(int position){
        return productFields_notes.get(position);
    }

    private Bitmap convertToBitmap(byte[] b){
        if (b == null) return null;
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView product_Image;
        private AppCompatTextView name, description, stores, regularPrice, salePrice;
        private LinearLayoutCompat colorLayout;
        private LinearLayout fullLayout;

        public NoteHolder(@NonNull final View itemView) {
            super(itemView);
            product_Image = itemView.findViewById(R.id.product_Image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            stores = itemView.findViewById(R.id.stores);
            regularPrice = itemView.findViewById(R.id.regularPrice);
            salePrice = itemView.findViewById(R.id.salePrice);
            colorLayout = itemView.findViewById(R.id.colorLayout);
            fullLayout = itemView.findViewById(R.id.fullLayout);

            fullLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id =  productFields_notes.get(getAdapterPosition()).getId();
                    String name = productFields_notes.get(getAdapterPosition()).getName();
                    String description = productFields_notes.get(getAdapterPosition()).getDescription();
                    String stores = productFields_notes.get(getAdapterPosition()).getStores();
                    Integer regPrice = productFields_notes.get(getAdapterPosition()).getRegularPrice();
                    Integer salePrice = productFields_notes.get(getAdapterPosition()).getSalePrice();
                    byte[] productImage = productFields_notes.get(getAdapterPosition()).getProductPhoto();
                    String colors = productFields_notes.get(getAdapterPosition()).getColors();

                    Intent intent = new Intent(itemView.getContext(),Form.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("name", name);
                    intent.putExtra("description", description);
                    intent.putExtra("stores", stores);
                    intent.putExtra("regPrice", regPrice);
                    intent.putExtra("salePrice", salePrice);
                    intent.putExtra("productImage", productImage);
                    intent.putExtra("colors", colors);
                    itemView.getContext().startActivity(intent);
                }
            });

            product_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (convertToBitmap(productFields_notes.get(getAdapterPosition()).getProductPhoto()) != null) {
                        ShowProduct.imagePreviewLayout.setVisibility(View.VISIBLE);
                        ShowProduct.imagePreview.setImageBitmap(convertToBitmap(productFields_notes
                                .get(getAdapterPosition()).getProductPhoto()));
                    }
                }
            });


        }
    }
}
