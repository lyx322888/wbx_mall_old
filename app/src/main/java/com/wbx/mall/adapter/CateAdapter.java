package com.wbx.mall.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wbx.mall.R;
import com.wbx.mall.bean.CateInfo2;

import java.util.List;

public class CateAdapter extends BaseQuickAdapter<CateInfo2, BaseViewHolder> {

    public CateAdapter(int layoutResId, @Nullable List<CateInfo2> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, CateInfo2 item) {
        ImageView icon_im = helper.getView(R.id.type_icon_im);
        if (item.getCate_id() == -3) {
            helper.getView(R.id.type_icon_im).setVisibility(View.VISIBLE);
            icon_im.setImageResource(R.drawable.mian_icon);
        } else if (item.getCate_id() == -2) {
            helper.getView(R.id.type_icon_im).setVisibility(View.VISIBLE);
            icon_im.setImageResource(R.drawable.pro_icon);
        } else if (item.getCate_id() == -1) {
            helper.getView(R.id.type_icon_im).setVisibility(View.VISIBLE);
            icon_im.setImageResource(R.drawable.seckill_icon_type);
        } else {
            helper.getView(R.id.type_icon_im).setVisibility(View.GONE);
        }
        TextView tvName = helper.getView(R.id.tv_name);
        tvName.setText(item.getCate_name());
        TextView tvNum = helper.getView(R.id.tv_num);
        if (item.getBuy_num() > 0) {
            helper.getView(R.id.tv_num).setVisibility(View.VISIBLE);
            if (item.getBuy_num() < 100) {
                tvNum.setText(String.valueOf(item.getBuy_num()));
            } else {
                tvNum.setText("99+");
            }
        } else {
            helper.getView(R.id.tv_num).setVisibility(View.GONE);
        }
    }

//    Context context;
//    List<CateInfo2> list;
//    public static int getPosition;
//
//    public static int getGetPosition() {
//        return getPosition;
//    }
//
//    public static void setGetPosition(int getPosition) {
//        CateAdapter.getPosition = getPosition;
//    }
//
//    public CateAdapter(Context context, List<CateInfo2> list) {
//        this.context = context;
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        final View view = LayoutInflater.from(context).inflate(R.layout.layout_cate, parent, false);
//        final VH vh = new VH(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerViewItemClieck.recyclerViewItemClieck(vh.getAdapterPosition(),view,vh);
//            }
//        });
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VH holder, int position) {
//        if (list.get(position).getCate_id()==-3) {
//            holder.type_icon_im.setVisibility(View.VISIBLE);
//            holder.type_icon_im.setImageResource(R.drawable.mian_icon);
//        } else if (list.get(position).getCate_id()==-2) {
//            holder.type_icon_im.setVisibility(View.VISIBLE);
//            holder.type_icon_im.setImageResource(R.drawable.pro_icon);
//        } else if (list.get(position).getCate_id()==-1) {
//            holder.type_icon_im.setVisibility(View.VISIBLE);
//            holder.type_icon_im.setImageResource(R.drawable.seckill_icon_type);
//        } else {
//            holder.type_icon_im.setVisibility(View.GONE);
//        }
//        holder.tv_name.setText(list.get(position).getCate_name());
//        if (list.get(position).getBuy_num() > 0) {
//            holder.tv_num.setVisibility(View.VISIBLE);
//            if (list.get(position).getBuy_num() < 100) {
//                holder.tv_num.setText(String.valueOf(list.get(position).getBuy_num()));
//            } else {
//                holder.tv_num.setText("99+");
//            }
//        } else {
//            holder.tv_num.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class VH extends RecyclerView.ViewHolder {
//        TextView tv_num;
//        TextView tv_name;
//        LinearLayout item_layout;
//        ImageView type_icon_im;
//
//        public VH(View itemView) {
//            super(itemView);
//            tv_num = itemView.findViewById(R.id.tv_num);
//            tv_name = itemView.findViewById(R.id.item_menu_tv);
//            item_layout = itemView.findViewById(R.id.item_layout);
//            type_icon_im = itemView.findViewById(R.id.type_icon_im);
//        }
//    }
//
//    RecyclerViewItemClieck recyclerViewItemClieck;
//    public interface RecyclerViewItemClieck{
//        void recyclerViewItemClieck(int position, View view, RecyclerView.ViewHolder viewHolder);
//    }
//
//    public void setRecyclerViewItemClieck(RecyclerViewItemClieck recyclerViewItemClieck) {
//        this.recyclerViewItemClieck = recyclerViewItemClieck;
//    }

}
