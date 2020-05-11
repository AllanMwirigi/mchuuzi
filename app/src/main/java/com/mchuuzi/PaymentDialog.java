package com.mchuuzi;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PaymentDialog extends DialogFragment implements View.OnClickListener {

    Button btnOk;
    TextView tvConfirmCode, tvRecipient, tvRecipientPhoneNo, tvAmount, tvBalance, tvTransactionFee, tvDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_payment_dialog, container, false);
        tvConfirmCode = view.findViewById(R.id.tv_paymentdialog_confirmation_code);
        tvRecipient = view.findViewById(R.id.tv_paymentdialog_recipient);
        tvRecipientPhoneNo = view.findViewById(R.id.tv_paymentdialog_recipient_phone);
        tvAmount = view.findViewById(R.id.tv_paymentdialog_amount);
        tvBalance = view.findViewById(R.id.tv_paymentdialog_balance);
        tvTransactionFee = view.findViewById(R.id.tv_paymentdialog_transaction_fee);
        tvDate = view.findViewById(R.id.tv_paymentdialog_date);
        btnOk = view.findViewById(R.id.btn_paymentdialog_ok);
        btnOk.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            String [] arr = bundle.getStringArray("MPESA DATA");
            // in the following order // String [] arr = {confirmationCode, amount, recipientName, recipientPhone, date, time, balance, fee};
            tvConfirmCode.setText(arr[0]);
            tvAmount.setText(arr[1]);
            tvRecipient.setText(arr[2]);
            tvRecipientPhoneNo.setText(arr[3]);
            tvDate.setText(arr[4] + " " + arr[5]);
            tvBalance.setText(arr[6]);
            tvTransactionFee.setText(arr[7]);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_paymentdialog_ok){
            dismiss();
        }
    }
}
