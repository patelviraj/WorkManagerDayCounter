package com.daycounter.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daycounter.R;
import com.daycounter.adapter.DayListAdapter;
import com.daycounter.databinding.FragmentDayListBinding;
import com.daycounter.db.entity.DaysTableModel;
import com.daycounter.ui.BaseFragment;
import com.daycounter.viewmodel.DayListViewModel;

import java.util.ArrayList;

public class FragmentDayList extends BaseFragment {

    FragmentDayListBinding mBinder;
    View view;

    ArrayList<DaysTableModel> dayList = new ArrayList<>();
    DayListAdapter dayListAdapter;

    private DayListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            mBinder = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_day_list, container, false);
            view = mBinder.getRoot();

            setHasOptionsMenu(true);
            prepareLayout();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(DayListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(DayListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getDayList().observe(this, dayList -> {

            if (dayList != null) {
                this.dayList = new ArrayList<>(dayList);
                dayListAdapter.setDayList(this.dayList);
            }
        });
    }

    private void prepareLayout() {

        LinearLayoutManager lm = new LinearLayoutManager(mActivity);
        mBinder.recyclerView.setLayoutManager(lm);
        dayListAdapter = new DayListAdapter(mActivity, dayList);
        mBinder.recyclerView.setAdapter(dayListAdapter);
    }
}
