package com.mchuuzi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.mchuuzi.R;
import com.mchuuzi.models.ProductsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter <ProductsAdapter.ProductsViewHolder>{

    private List<ProductsModel> productsList = new ArrayList<>();

    public ProductsAdapter(List<ProductsModel> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productsview = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsadapterlayout,parent,false);
        return new ProductsViewHolder(productsview);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.product_name.setText(productsList.get(position).getName());
        String amount = "KSh: " + productsList.get(position).getPrice();
        holder.product_amount.setText(amount);
        Picasso.get().load(productsList.get(position).getImage())

                .into(holder.products_image);

    }

    @Override
    public int getItemCount() {
        if(productsList != null){
            return productsList.size();
        }else
            return 0;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView product_amount,product_name;
        private ImageView products_image;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            product_amount = itemView.findViewById(R.id.products_amount_tv);
            product_name = itemView.findViewById(R.id.products_name_tv);
            products_image  = itemView.findViewById(R.id.products_imageview);
        }
    }
}
