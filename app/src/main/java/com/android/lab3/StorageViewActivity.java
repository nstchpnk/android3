package com.android.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class StorageViewActivity extends AppCompatActivity {

    private ListView passwordsListView;
    private TextView emptyMessageTextView;
    private Button deleteAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_view);

        passwordsListView = findViewById(R.id.passwordsListView);
        emptyMessageTextView = findViewById(R.id.emptyMessageTextView);
        deleteAllButton = findViewById(R.id.deleteAllButton);

        loadPasswords();

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        deleteAllButton.setOnClickListener(v -> {
            boolean deleted = FileStorage.deleteAllPasswords(this);
            if (deleted) {
                Toast.makeText(this, "Всі паролі видалено", Toast.LENGTH_SHORT).show();
                loadPasswords();
            } else {
                Toast.makeText(this, "Помилка при видаленні паролів", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPasswords() {
        List<String> passwords = FileStorage.readPasswords(this);

        if (passwords.isEmpty()) {
            emptyMessageTextView.setVisibility(View.VISIBLE);
            passwordsListView.setVisibility(View.INVISIBLE);
            deleteAllButton.setEnabled(false);
        } else {
            emptyMessageTextView.setVisibility(View.GONE);
            passwordsListView.setVisibility(View.VISIBLE);
            deleteAllButton.setEnabled(true);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    passwords
            );
            passwordsListView.setAdapter(adapter);
        }
    }

}