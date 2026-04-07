package com.example.spendwise.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import com.example.spendwise.repository.SpendingRepository;
import com.example.spendwise.ui.history.TransactionAdapter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TextView tvTotalBalance, tvAIAdvice;
    private RecyclerView rvRecent;
    private SpendingRepository repository;
    private TransactionAdapter adapter;
    private List<Transaction> recentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvTotalBalance = view.findViewById(R.id.tvTotalBalance);
        tvAIAdvice = view.findViewById(R.id.tvAIAdvice);
        rvRecent = view.findViewById(R.id.rvRecentTransactions);
        
        repository = new SpendingRepository(getContext());
        recentList = new ArrayList<>();
        
        // Thiết lập RecyclerView cho hoạt động gần đây
        adapter = new TransactionAdapter(recentList, t -> {
            // Có thể thêm logic xem chi tiết ở đây nếu cần
        });
        rvRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecent.setAdapter(adapter);

        updateUI();

        return view;
    }

    private void updateUI() {
        // 1. Cập nhật số dư
        double total = repository.getTotalBalance();
        tvTotalBalance.setText(String.format("%,.0f đ", total));
        
        // 2. Cập nhật lời khuyên AI
        String advice = repository.getAIAdvice();
        if (tvAIAdvice != null) {
            tvAIAdvice.setText(advice);
        }

        // 3. Cập nhật danh sách gần đây (lấy tối đa 5 mục)
        List<Transaction> all = repository.getAll();
        recentList.clear();
        for (int i = 0; i < Math.min(all.size(), 5); i++) {
            recentList.add(all.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
