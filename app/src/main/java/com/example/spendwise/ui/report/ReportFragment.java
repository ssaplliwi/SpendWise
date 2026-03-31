package com.example.spendwise.ui.report;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import com.example.spendwise.repository.SpendingRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportFragment extends Fragment {
    private PieChart pieChart;
    private SpendingRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        pieChart = view.findViewById(R.id.pieChart);
        repository = new SpendingRepository(getContext());

        setupPieChart();
        return view;
    }

    private void setupPieChart() {
        List<Transaction> list = repository.getAll();
        if (list.isEmpty()) {
            pieChart.setNoDataText("Chưa có dữ liệu để phân tích");
            return;
        }

        Map<String, Double> categoryMap = new HashMap<>();
        for (Transaction t : list) {
            String cat = t.getCategory();
            categoryMap.put(cat, categoryMap.getOrDefault(cat, 0.0) + t.getPrice());
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Hạng mục");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f); // Sửa từ 14sp thành 14f

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setCenterText("Chi tiêu");
        pieChart.setCenterTextSize(18f);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(800);
        pieChart.invalidate();
    }
}