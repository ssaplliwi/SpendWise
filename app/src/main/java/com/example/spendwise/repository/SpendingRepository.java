package com.example.spendwise.repository;

import android.content.Context;
import com.example.spendwise.database.SQLiteHelper;
import com.example.spendwise.model.Transaction;
import java.util.List;

public class SpendingRepository {
    private SQLiteHelper db;

    public SpendingRepository(Context context) {
        db = new SQLiteHelper(context);
    }

    public long insert(Transaction t) {
        return db.addTransaction(t);
    }

    public List<Transaction> getAll() {
        return db.getAll();
    }

    public int delete(int id) {
        return db.delete(id);
    }

    public double getTotalBalance() {
        double total = 0;
        List<Transaction> list = db.getAll();
        for (Transaction t : list) {
            total += t.getPrice();
        }
        return total;
    }
}
