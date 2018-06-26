package com.daycounter.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import com.daycounter.utils.SessionManager;


public class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;

    public TextView title;

    public SessionManager session;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
        session = new SessionManager(mContext);
    }
}