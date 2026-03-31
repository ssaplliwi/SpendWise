package com.example.spendwise.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import com.example.spendwise.repository.SpendingRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private EditText etTitle, etPrice, etNote;
    private Spinner spCategory;
    private SpendingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        repository = new SpendingRepository(this);
        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etNote = findViewById(R.id.etNote); // Bây giờ ID này đã tồn tại trong XML
        spCategory = findViewById(R.id.spCategory);
        Button btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Ăn uống", "Di chuyển", "Mua sắm", "Giải trí", "Khác"};
        spCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString();
            String priceStr = etPrice.getText().toString();
            String note = etNote.getText().toString();

            if (title.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            String category = spCategory.getSelectedItem().toString();
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

            repository.insert(new Transaction(0, title, category, price, date, note));
            finish();
        });
    }
}