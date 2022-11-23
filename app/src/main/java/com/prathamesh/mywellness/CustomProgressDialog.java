package com.prathamesh.mywellness;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomProgressDialog {
    private AlertDialog showAlertDialog;

    public void showCustomProgressDialog(Context context){
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.progress_dialog,null);
        ImageView imageView = view.findViewById(R.id.IV_CustomProgress);
        Glide.with(context).load(R.drawable.progress).into(imageView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        showAlertDialog = alertDialog.create();
        showAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        showAlertDialog.show();
    }

    public void dismissCustomProgressDialog(){
        showAlertDialog.dismiss();
    }
}
