package com.android.lab3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

public class SecondFragment extends Fragment {

    private TextView passwordOutputTextView;
    private Button cancelButton;

    public static SecondFragment newInstance(String password) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("password", password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        passwordOutputTextView = rootView.findViewById(R.id.passwordOutputTextView);
        cancelButton = rootView.findViewById(R.id.cancelButton);

        String password = getArguments().getString("password");
        passwordOutputTextView.setText("Введений пароль: " + password);

        cancelButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FirstFragment())
                    .commit();
        });

        return rootView;
    }
}