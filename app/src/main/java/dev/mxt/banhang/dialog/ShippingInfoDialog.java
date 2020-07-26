package dev.mxt.banhang.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import dev.mxt.banhang.R;

public class ShippingInfoDialog extends AppCompatDialogFragment {

    private EditText etAdress;
    private EditText etPhone;
    private ShippingInfoDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        etAdress = (EditText) view.findViewById(R.id.edit_address);
        etPhone = (EditText) view.findViewById(R.id.edit_phone);

        builder.setView(view)
                .setTitle("Thay đổi thông tin giao hàng")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneRegex = "^0\\d{9}$";
                        String address = etAdress.getText().toString();
                        String phone = etPhone.getText().toString();

                        if (TextUtils.isEmpty(etAdress.getText().toString().trim())) {
                            etAdress.setError("Please enter Address");
                            Toast.makeText(getContext(), "Thông tin không thay đổi", Toast.LENGTH_SHORT).show();
                        }
                        else if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
                            etPhone.setError("Please enter Phone number");
                            Toast.makeText(getContext(), "Thông tin không thay đổi", Toast.LENGTH_SHORT).show();
                        }
                        else if (!phone.matches(phoneRegex)) {
                            etPhone.setError("Please enter a valid phone number");
                            Toast.makeText(getContext(), "Thông tin không thay đổi", Toast.LENGTH_SHORT).show();
                        }
                        else listener.applyTexts(address, phone);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ShippingInfoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ShippingInfoDialogListener");
        }

    }

    public interface ShippingInfoDialogListener {
        void applyTexts(String address, String phone);
    }
}
