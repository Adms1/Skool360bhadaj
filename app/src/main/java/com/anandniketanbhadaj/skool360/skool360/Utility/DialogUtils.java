package com.anandniketanbhadaj.skool360.skool360.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.anandniketanbhadaj.skool360.R;


public class DialogUtils {


    private static Dialog dialog;


    public static void showGIFDialog(Context context, @NonNull String username){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_birthday_wish);
        GifView gifView = dialog.findViewById(R.id.gifview);

        Button btnClose = dialog.findViewById(R.id.btn_close);
        gifView.setImageResource(R.drawable.gif_brthday);

        TextView userName = dialog.findViewById(R.id.tv_username);

        userName.setText(username);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
