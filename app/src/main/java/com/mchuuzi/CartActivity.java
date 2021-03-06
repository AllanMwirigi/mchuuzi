package com.mchuuzi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartFragment.OnCheckoutClick, CustomAlert.OnPaymentDialogOkClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setAllowEnterTransitionOverlap(true);
//        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,CartFragment.newInstance()).commit();

        Hover.initialize(this);
        Hover.updateActionConfigs(new Hover.DownloadListener() {
            @Override
            public void onError(String s) {
                Log.e("Hover", "configs update failed "+ s);
            }

            @Override
            public void onSuccess(ArrayList<HoverAction> arrayList) {
                Log.e("Hover", "configs updated" + arrayList);
            }
        }, this);

    }



    @Override
    public void onClick() { // checkout clicked
        try {
            Intent i = new HoverParameters.Builder(CartActivity.this)
                    .request("81ed16e5")
//                        .request("d3c76363")
                    .extra("phoneNo", "0708915797")
                    .extra("amount", "1")
                    .buildIntent();
            startActivityForResult(i, 0);
        }catch (Exception e){
            Toast.makeText(CartActivity.this, "Hover Error", Toast.LENGTH_SHORT).show();
            Log.e("Hover", e.getMessage());
        }
//                displayDialog(null); // to see how the dialog will look
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                Repository.clearCart();
                displayAlert("Payment", "You will receive an MPESA Confirmation message shortly", null);
                String[] sessionTextArr = data.getStringArrayExtra("session_messages");
                String uuid = data.getStringExtra("uuid");
//                Toast.makeText(this, "You will receive an MPESA confirmation message shortly", Toast.LENGTH_LONG).show();
                Log.e("Hover success", "uuid: "+ uuid);
//            Log.e("Hover success", "session_messages: " + Arrays.toString(sessionTextArr));
            } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
                Repository.clearCart();

                Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
                Log.e("Hover error", data.getStringExtra("error"));
            }else{
                Repository.clearCart();

                Log.e("Hover error", "result code "+ resultCode);
            }
        } catch (Exception e){
            Repository.clearCart();

            Toast.makeText(CartActivity.this, "Hover Result Error", Toast.LENGTH_SHORT).show();
            Log.e("Hover", e.getMessage());
        }

    }

    public void displayAlert(String title, String message, String posBtnText){
        CustomAlert customAlert = CustomAlert.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("AlertFragment");
        if (prev != null) {
            ft.remove(prev);
        }
//        ft.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);

        if(posBtnText != null)
            bundle.putString("posBtnText", posBtnText);
        else
            bundle.putString("posBtnText", "OK");

        customAlert.setArguments(bundle);
        customAlert.setCancelable(true);

        customAlert.show(ft, "AlertFragment");
    }

    @Override
    public void closeCart() {
        finish();
    }
}
