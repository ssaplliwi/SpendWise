package com.example.spendwise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.spendwise.model.Transaction;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SpendWise.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE transactions(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, category TEXT, price REAL, date TEXT, note TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }

    public long addTransaction(Transaction t) {
        ContentValues v = new ContentValues();
        v.put("title", t.getTitle());
        v.put("category", t.getCategory());
        v.put("price", t.getPrice());
        v.put("date", t.getDate());
        v.put("note", t.getNote());
        return getWritableDatabase().insert("transactions", null, v);
    }

    public List<Transaction> getAll() {
        List<Transaction> list = new ArrayList<>();
        Cursor c = getReadableDatabase().query("transactions", null, null, null, null, null, "date DESC");
        while (c != null && c.moveToNext()) {
            list.add(new Transaction(c.getInt(0), c.getString(1), c.getString(2),
                    c.getDouble(3), c.getString(4), c.getString(5)));
        }
        if (c != null) c.close();
        return list;
    }

    public int delete(int id) {
        return getWritableDatabase().delete("transactions", "id = ?", new String[]{String.valueOf(id)});
    }
}
