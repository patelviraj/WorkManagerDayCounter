package com.daycounter.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.daycounter.R;
import com.daycounter.databinding.DialogProgressBinding;

public class ProgressDialog extends AlertDialog {
    private String message;

    public ProgressDialog(Context context) {
        this(context, null);
    }

    public ProgressDialog(Context context, String message) {
        super(context, R.style.ProgressDialog);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogProgressBinding mBinder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_progress, null, false);
        setContentView(mBinder.getRoot());
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        if (message != null) mBinder.tvMessage.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
