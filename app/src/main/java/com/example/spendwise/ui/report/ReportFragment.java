package com.example.spendwise.ui.report;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.spendwise.R;
import com.example.spendwise.repository.SpendingRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportFragment extends Fragment {
    private PieChart pieChart;
    private SpendingRepository repository;
    private Button btnAIAdvice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        
        pieChart = view.findViewById(R.id.pieChart);
        btnAIAdvice = view.findViewById(R.id.btnAIAdvice);
        repository = new SpendingRepository(getContext());

        setupPieChart();
        loadPieChartData();

        btnAIAdvice.setOnClickListener(v -> {
            String advice = repository.getAIAdvice();
            Toast.makeText(getContext(), "AI: " + advice, Toast.LENGTH_LONG).show();
        });

        return view;
    }

    private void setupPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterText("Chi tiêu (%)");
        pieChart.setCenterTextSize(18f);
    }

    private void loadPieChartData() {
        Map<String, Double> stats = repository.getCategoryStats();
        if (stats.isEmpty()) {
            pieChart.setNoDataText("Chưa có dữ liệu để hiển thị biểu đồ!");
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        // Sửa lỗi typo: VORDEL_COLORS -> VORDIPLOM_COLORS
        for (int c : ColorTemplate.VORDIPLOM_COLORS) colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS) colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);

        PieDataSet dataSet = new PieDataSet(entries, "Danh mục");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate(); // Refresh biểu đồ
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPieChartData();
    }
}
