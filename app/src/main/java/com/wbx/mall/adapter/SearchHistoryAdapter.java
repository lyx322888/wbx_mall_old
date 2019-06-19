package com.wbx.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbx.mall.R;
import com.wbx.mall.base.AppConfig;
import com.wbx.mall.bean.SearchInfo;

import java.util.ArrayList;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<SearchInfo> historys;

    public SearchHistoryAdapter(Context context, ArrayList<SearchInfo> historys) {
        this.context = context;
        this.historys = historys;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String typeStr = historys.get(position).getType() == AppConfig.SearchType.GOODS ? "商品" : "店铺";
        holder.historyInfo.setText(historys.get(position).getHistorys() + "   [" + typeStr + "]");

        holder.historyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClickListener.onItemClick(historys.get(position).getHistorys(), historys.get(position).getType());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClickListener.onItemDeleteClick(historys.get(position).getHistorys(), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historys.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView historyInfo;
        ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            historyInfo = view.findViewById(R.id.tv_item_search_history);
            delete = view.findViewById(R.id.iv_item_search_delete);
        }
    }

    private IOnItemClickListener iOnItemClickListener;

    public void setOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        this.iOnItemClickListener = iOnItemClickListener;
    }

    public interface IOnItemClickListener {
        void onItemClick(String keyword, int searchType);

        void onItemClick(String keyword);

        void onItemDeleteClick(String keyword, int position);
    }
}
