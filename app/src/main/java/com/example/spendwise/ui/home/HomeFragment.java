package com.example.spendwise.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.spendwise.R;
import com.example.spendwise.repository.SpendingRepository;

public class HomeFragment extends Fragment {
    private TextView tvTotalBalance;
    private SpendingRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvTotalBalance = view.findViewById(R.id.tvTotalBalance);
        repository = new SpendingRepository(getContext());

        updateBalance();

        return view;
    }

    // Hàm cập nhật số dư mỗi khi vào màn hình
    private void updateBalance() {
        double total = repository.getTotalBalance();
        tvTotalBalance.setText(String.format("%,.0f đ", total));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBalance(); // Cập nhật lại nếu vừa thêm giao dịch mới
    }
}