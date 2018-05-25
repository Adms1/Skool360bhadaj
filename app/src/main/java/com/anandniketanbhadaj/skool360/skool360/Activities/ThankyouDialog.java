package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class ThankyouDialog extends DialogFragment {

    private SparseIntArray layoutsArray;
    private PagerAdapter pagerAdapter;
    int currentPage = 0;

    @Override
    public void onResume() {
        DisplayMetrics display = this.getResources().getDisplayMetrics();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Dialog dialog = getDialog();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = (int) (display.widthPixels * 0.8);
        lp.height = (int) (display.heightPixels * 0.9);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // layoutsArray = new SparseIntArray();
        // layoutsArray.append(0, R.layout.on_boarding_1);
        // layoutsArray.append(1, R.layout.fragment_treasurehunt_intro2);
        // layoutsArray.append(2, R.layout.fragment_treasurehunt_intro3);
        // layoutsArray.append(3, R.layout.fragment_treasurehunt_intro5);

//        binding = DialogOnBoardingBinding.inflate(LayoutInflater.from(getContext()), null, false);
//
//        return binding.getRoot();
        return null;
    }
}
