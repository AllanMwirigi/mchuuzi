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

public class CartActivity extends AppCompatActivity implements CartFragment.OnCheckoutClick, PaymentDialog.OnPaymentDialogOkClickListener {

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
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("HoverReceiver"));
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
                String[] sessionTextArr = data.getStringArrayExtra("session_messages");
                String uuid = data.getStringExtra("uuid");
                Toast.makeText(this, "You will receive an MPESA confirmation message shortly", Toast.LENGTH_LONG).show();
                Log.e("Hover success", "uuid: "+ uuid);
//            Log.e("Hover success", "session_messages: " + Arrays.toString(sessionTextArr));
            } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
                Log.e("Hover error", data.getStringExtra("error"));
            }
        } catch (Exception e){
            Toast.makeText(CartActivity.this, "Hover Result Error", Toast.LENGTH_SHORT).show();
            Log.e("Hover", e.getMessage());
        }

    }

    // receives data parsed from MPESA message via the Hover parser
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String [] data = intent.getStringArrayExtra("HOVER MESSAGE");
//            Log.e("Hover broadcast", "data: "+ Arrays.toString(data));
            if(data!= null){
                Bundle bundle = new Bundle();
                bundle.putStringArray("MPESA DATA", data);
                displayDialog(bundle);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    public void displayDialog(Bundle args){
        PaymentDialog dialogFragment = new PaymentDialog();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("PaymentDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.setArguments(args);

        dialogFragment.setCancelable(false);
        dialogFragment.show(ft, "PaymentDialog");
    }

    @Override
    public void closeCart() {
        finish();
    }
}
