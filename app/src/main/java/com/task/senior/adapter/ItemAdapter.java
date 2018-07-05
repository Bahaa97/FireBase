package com.task.senior.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.task.senior.Model.Item;
import com.task.senior.R;
import com.task.senior.callback.OnItemLongClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private Context mContext;
    private List<Item> list;
    private OnItemLongClickListener listener;

    public ItemAdapter(Context mContext, List<Item> list, OnItemLongClickListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new ItemHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        final Item item = list.get(position);

        holder.nameTV.setText(item.getName());
        holder.descriptionTV.setText(item.getDescription());
        holder.rateTV.setText(String.valueOf(item.getRate()));
        holder.priceTV.setText(String.valueOf(item.getPrice()));

        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClicked(item);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Item> items) {
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_TV)
        TextView nameTV;
        @BindView(R.id.description_TV)
        TextView descriptionTV;
        @BindView(R.id.price_TV)
        TextView priceTV;
        @BindView(R.id.rate_TV)
        TextView rateTV;
        @BindView(R.id.root)
        LinearLayout root;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
