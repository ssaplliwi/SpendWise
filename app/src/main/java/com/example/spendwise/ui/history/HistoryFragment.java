package com.example.spendwise.ui.history;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import com.example.spendwise.repository.SpendingRepository;

public class HistoryFragment extends Fragment {
    private RecyclerView rvHistory;
    private TransactionAdapter adapter;
    private SpendingRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = view.findViewById(R.id.rvHistory);
        repository = new SpendingRepository(getContext());

        loadData();
        return view;
    }

    private void loadData() {
        adapter = new TransactionAdapter(repository.getAll(), t -> {
            // Hiển thị hộp thoại xác nhận xóa
            new AlertDialog.Builder(getContext())
                    .setTitle("Xóa chi tiêu?")
                    .setMessage("Bạn có chắc muốn xóa '" + t.getTitle() + "' không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        repository.delete(t.getId());
                        loadData(); // Load lại danh sách
                        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.setAdapter(adapter);
    }
}
