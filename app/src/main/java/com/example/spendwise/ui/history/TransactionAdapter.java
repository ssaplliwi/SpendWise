package com.example.spendwise.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spendwise.R;
import com.example.spendwise.model.Transaction;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<Transaction> list;
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Transaction t);
    }

    public TransactionAdapter(List<Transaction> list, OnItemLongClickListener listener) {
        this.list = list;
        this.longClickListener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction t = list.get(position);
        holder.tvTitle.setText(t.getTitle());
        holder.tvPrice.setText(String.format("-%,.0f đ", t.getPrice()));
        holder.tvDate.setText(t.getDate());

        // Đổi icon dựa trên Category
        switch (t.getCategory()) {
            case "Ăn uống": holder.ivIcon.setImageResource(android.R.drawable.ic_menu_gallery); break;
            case "Di chuyển": holder.ivIcon.setImageResource(android.R.drawable.ic_menu_directions); break;
            case "Mua sắm": holder.ivIcon.setImageResource(android.R.drawable.ic_menu_manage); break;
            default: holder.ivIcon.setImageResource(android.R.drawable.ic_menu_info_details); break;
        }

        // Bắt sự kiện nhấn giữ để xóa
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onItemLongClick(t);
            return true;
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvDate;
        ImageView ivIcon;
        public TransactionViewHolder(@NonNull View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvItemTitle);
            tvPrice = v.findViewById(R.id.tvItemPrice);
            tvDate = v.findViewById(R.id.tvItemDate);
            ivIcon = v.findViewById(R.id.ivCategoryIcon);
        }
    }
}