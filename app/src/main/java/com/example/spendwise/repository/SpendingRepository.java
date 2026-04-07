package com.example.spendwise.repository;

import android.content.Context;
import com.example.spendwise.database.SQLiteHelper;
import com.example.spendwise.model.Transaction;
import java.util.List;
import java.util.Map;

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

    public int update(Transaction t) {
        return db.update(t);
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

    public Map<String, Double> getCategoryStats() {
        return db.getCategoryTotals();
    }

    public String getAIAdvice() {
        Map<String, Double> stats = getCategoryStats();
        if (stats.isEmpty()) return "Bắt đầu nhập chi tiêu để nhận lời khuyên từ AI nhé!";
        
        String maxCategory = "";
        double maxAmount = 0;
        for (Map.Entry<String, Double> entry : stats.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxAmount = entry.getValue();
                maxCategory = entry.getKey();
            }
        }

        switch (maxCategory) {
            case "Ăn uống":
                return "Bạn chi khá nhiều cho Ăn uống. Thử tự nấu ăn tại nhà để tiết kiệm hơn nhé!";
            case "Mua sắm":
                return "Mua sắm đang chiếm phần lớn ngân sách. Hãy cân nhắc kỹ trước khi 'chốt đơn'!";
            case "Giải trí":
                return "Giải trí là tốt, nhưng hãy đảm bảo nó không ảnh hưởng đến các khoản tiết kiệm.";
            default:
                return "Bạn đang quản lý chi tiêu khá ổn định. Tiếp tục phát huy nhé!";
        }
    }
}
