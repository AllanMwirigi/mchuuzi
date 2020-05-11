package com.mchuuzi;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mchuuzi.adapters.ProductsAdapter;
import com.mchuuzi.adapters.VendorsAdapter;
import com.mchuuzi.interfaces.OnProductItemClickedListener;
import com.mchuuzi.models.ProductsModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class ProductFragment extends Fragment  {
    private  Repository repository;
    private ProductsAdapter adapter;
    private RecyclerView recyclerView;
    private List<ProductsModel> productsModelList;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       recyclerView = view.findViewById(R.id.products_recycler);
       initComponents();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabClickListener.goToCart();
            }
        });
    }

    public interface OnFabClickListener{
        void goToCart();
    }
    private OnFabClickListener fabClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fabClickListener = (OnFabClickListener)context;
        } catch (Exception e){
            Log.e("ProductFragment", "on attach cast error");
        }
    }

    void initComponents(){

        repository = new Repository();
        productsModelList = repository.getProductsModelList();
        adapter = new ProductsAdapter(productsModelList, new OnProductItemClickedListener() {
            @Override
            public void onProductItemClicked(ProductsModel productsModel) {
                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                intent.putExtra("product", productsModel);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));

    }


}
