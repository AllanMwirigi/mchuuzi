package com.mchuuzi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mchuuzi.adapters.CartAdapter;
import com.mchuuzi.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartAdapter adapter;
    private RecyclerView recyclerView;
    private List<OrderItem> orderItemList;
    private OnCheckoutClick listener;
    private Button checkOut;
    private TextView tvTotal;


    interface OnCheckoutClick {
        void onClick();

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(OnCheckoutClick)context;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.cart_recycler);
        checkOut=view.findViewById(R.id.checkout);
        tvTotal = view.findViewById(R.id.tv_cart_total);
        int total = Repository.total();
        tvTotal.setText("Total: Ksh."+String.valueOf(total));
        initComponents();

       /* view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CartFragment.this)
                        .navigate(R.id.action_CartFragment_to_ProductFragment);
            }
        });*/
    }


   private void initComponents(){

        ///repository = new Repository();
        orderItemList = Repository.orderItems ;

        adapter = new CartAdapter(orderItemList,getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick();
                //checkout here
            }
        });


    }

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
