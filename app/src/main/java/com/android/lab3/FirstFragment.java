package com.android.lab3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.Toast;
import android.text.method.PasswordTransformationMethod;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.content.Context;

public class FirstFragment extends Fragment {

    private EditText passwordEditText;
    private RadioGroup passwordVisibilityGroup;
    private RadioButton showPasswordRadioButton, starPasswordRadioButton;
    private Button submitButton, openButton;
    private OnPasswordEnteredListener mListener;

    public interface OnPasswordEnteredListener {
        void onPasswordEntered(String password);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        passwordEditText = rootView.findViewById(R.id.passwordEditText);
        passwordVisibilityGroup = rootView.findViewById(R.id.passwordVisibilityGroup);
        showPasswordRadioButton = rootView.findViewById(R.id.showPasswordRadioButton);
        starPasswordRadioButton = rootView.findViewById(R.id.starPasswordRadioButton);
        submitButton = rootView.findViewById(R.id.submitButton);
        openButton = rootView.findViewById(R.id.openButton);

        starPasswordRadioButton.setChecked(true);
        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        passwordVisibilityGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.showPasswordRadioButton) {
                passwordEditText.setTransformationMethod(null);
            } else {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        submitButton.setOnClickListener(v -> {
            String password = passwordEditText.getText().toString().trim();
            if (password.isEmpty()) {
                Toast.makeText(getActivity(), "Будь ласка, введіть пароль.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean saved = FileStorage.savePassword(getActivity(), password);
            if (saved) {
                Toast.makeText(getActivity(), "Пароль успішно збережено у сховище", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Помилка при збереженні паролю", Toast.LENGTH_SHORT).show();
            }

            if (mListener != null) {
                mListener.onPasswordEntered(password);
            }
        });

        openButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StorageViewActivity.class);
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPasswordEnteredListener) {
            mListener = (OnPasswordEnteredListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnPasswordEnteredListener");
        }
    }
}