package com.example.spendwise;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.spendwise.ui.AddTransactionActivity;
import com.example.spendwise.ui.home.HomeFragment;
import com.example.spendwise.ui.history.HistoryFragment;
import com.example.spendwise.ui.report.ReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        // Mở màn hình Thêm khi bấm nút nổi
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
        });

        loadFragment(new HomeFragment());

        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.mHome) fragment = new HomeFragment();
            else if (id == R.id.mHistory) fragment = new HistoryFragment();
            else if (id == R.id.mReport) fragment = new ReportFragment();
            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}
