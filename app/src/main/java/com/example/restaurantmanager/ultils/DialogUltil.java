package com.example.restaurantmanager.ultils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.restaurantmanager.R;

public class DialogUltil {

    public static Dialog bottomDialog = null;

    public static void showDialog(Context context, String title, View v) {
        final Context mContext = context;
        v.setPadding(0, 0, 0, 0);

        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_normal, null);
        LinearLayout content = contentView.findViewById(R.id.content);
        content.removeAllViews();
        content.addView(v);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }
}
