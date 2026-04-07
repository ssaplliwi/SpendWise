package com.example.spendwise.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import com.example.spendwise.repository.SpendingRepository;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {
    private TextInputEditText etTitle, etPrice, etNote;
    private Spinner spCategory;
    private SpendingRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        repository = new SpendingRepository(this);
        
        // Tìm đúng ID của TextInputEditText
        etTitle = findViewById(R.id.etTitle);
        etPrice = findViewById(R.id.etPrice);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        Button btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Ăn uống", "Di chuyển", "Mua sắm", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            if (etTitle.getText() == null || etPrice.getText() == null) return;
            
            String title = etTitle.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            String note = (etNote.getText() != null) ? etNote.getText().toString().trim() : "";

            if (title.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                String category = spCategory.getSelectedItem().toString();
                String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

                repository.insert(new Transaction(0, title, category, price, date, note));
                Toast.makeText(this, "Đã lưu chi tiêu thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Quay lại màn hình chính
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
