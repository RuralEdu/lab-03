package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void editCity(int position, City city);
    }

    private EditCityDialogListener listener;
    private EditText EditCityName;
    private EditText EditProvinceName;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    public static EditCityFragment newInstance(int position, City city) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("cityName", city.getName());
        args.putString("provinceName", city.getProvince());

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override


    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText EditCityName = view.findViewById(R.id.edit_text_city_text);
        EditText EditProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        if (args != null) {
            String cityName = args.getString("cityName");
            String provinceName = args.getString("provinceName");
            EditCityName.setText(cityName);
            EditProvinceName.setText(provinceName);

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String cityName = EditCityName.getText().toString();
                    String provinceName = EditProvinceName.getText().toString();
                    if (args != null) {
                        int position = args.getInt("position");
                        listener.editCity(position, new City(cityName, provinceName));
                    }
                })
                .create();

    }

}