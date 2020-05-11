package com.mchuuzi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomAlert extends DialogFragment {

    public static CustomAlert newInstance(){
        return new CustomAlert();
    }

    interface OnPaymentDialogOkClickListener{
        void closeCart();
    }
    private OnPaymentDialogOkClickListener paymentDialogOkClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        paymentDialogOkClickListener = (OnPaymentDialogOkClickListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        String posBtnText = bundle.getString("posBtnText");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle(title).setMessage(message)
                .setPositiveButton(posBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        paymentDialogOkClickListener.closeCart(); // exit cart when payment successful
                        dialog.dismiss();
                    }
                });
        return alertBuilder.create();
    }
}
