package com.daycounter.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daycounter.R;
import com.daycounter.databinding.ItemDayBinding;
import com.daycounter.db.entity.DaysTableModel;
import com.daycounter.db.utils.DayStatusEnum;

import java.util.ArrayList;
import java.util.List;

import static com.daycounter.db.utils.DayStatusEnum.PRESENT;

public class DayListAdapter extends RecyclerView.Adapter {

    private Context mContext;

    private List<DaysTableModel> mData = new ArrayList<>();

    public DayListAdapter(Context context, ArrayList<DaysTableModel> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemDayBinding mBinder = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.item_day, parent, false);
        return new MyViewHolder(mBinder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DaysTableModel item = mData.get(position);

        if (DayStatusEnum.fromCode(item.getDayStatus()) != null && DayStatusEnum.fromCode(item.getDayStatus()).equals(PRESENT)) {
            ((MyViewHolder) holder).mBinder.tvDayTitle.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).mBinder.tvDayTitle.setText(mContext.getResources().getString(R.string.today));
        } else {
            ((MyViewHolder) holder).mBinder.tvDayTitle.setVisibility(View.GONE);
            ((MyViewHolder) holder).mBinder.tvDayTitle.setText("");
        }

        ((MyViewHolder) holder).mBinder.tvDescription.setText(item.getDescription());
        ((MyViewHolder) holder).mBinder.tvDayIndicator.setText(mContext.getResources().getString(R.string.day_, String.valueOf(item.getDay())));
        switch (DayStatusEnum.fromCode(item.getDayStatus())) {
            case COMPLETED:
                ((MyViewHolder) holder).mBinder.tvDayIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Apple));
                break;
            case SKIP:
                ((MyViewHolder) holder).mBinder.tvDayIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black_opacity_90));
                break;
            case FUTURE:
                ((MyViewHolder) holder).mBinder.tvDayIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.CherryBlossom));
                break;
            case PRESENT:
            default:
                ((MyViewHolder) holder).mBinder.tvDayIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.DragonPink));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ItemDayBinding mBinder;

        MyViewHolder(ItemDayBinding mBinder) {
            super(mBinder.getRoot());
            this.mBinder = mBinder;

            itemView.setTag(getAdapterPosition());
        }
    }

    public void setDayList(ArrayList<DaysTableModel> dayList) {
        this.mData = dayList;
        notifyDataSetChanged();
    }
}
