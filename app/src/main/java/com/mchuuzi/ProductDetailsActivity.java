package com.mchuuzi;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mchuuzi.models.ProductsModel;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView textCartItemCount;
    /// int mCartItemCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check if we're running on Android 5.0 or higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setAllowEnterTransitionOverlap(true);
//        }

        //here get the passed product
        ///clicked by use
        // fr now its hard coded
        ProductsModel product = (ProductsModel) getIntent().getSerializableExtra("product");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(product.getName());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, ProductDetailsFragment.newInstance(product)).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_cart) {

            if (Repository.itemCount() > 0) {
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    // set an exit transition // Apply activity transition
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ProductDetailsActivity.this).toBundle());
//                } else {
//                    // Swap without transition
//                }
            } else {
                Toast.makeText(this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (item.getItemId() == android.R.id.home) {


//            onBackPressed();
            finishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupBadge() {

        if (textCartItemCount != null) {
            if (Repository.itemCount() == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(Repository.itemCount(), 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public void updateCart() {
        setupBadge();
    }


}
