package com.example.sikarwartechservices.Utilities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.sikarwartechservices.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class LayoutUtils {


    public static void hideKeyboardAndClearFocus(View root) {
        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) root.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(root.getWindowToken(), 0);

        // Clear focus from all input fields
        root.clearFocus();
    }


    public static boolean validateInputs(EditText... editTexts) {
        boolean isFormValid = true;

        for (EditText editText : editTexts) {
            if (editText.getText().toString().isEmpty()) {
                isFormValid = false;
                shakeView(editText);
            }
        }

        return isFormValid;
    }

    public static void shakeView(View view) {
        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
        view.startAnimation(shake);
        if (view instanceof EditText) {
            showError((EditText) view);
        }
    }

    public static void snackBarCreator(View view, String message, int duration, int textColor, int backgroundColor) {
        Snackbar.make(view, message, duration)
                .setActionTextColor(textColor)
                .setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), backgroundColor))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .show();
    }

    public static void snackBarCreator(View view, String message, int duration, int textColor, int backgroundColor,  View anchorView) {
        Snackbar.make(view, message, duration)
                .setActionTextColor(textColor)
                .setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), backgroundColor))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAnchorView(anchorView)
                .show();
    }
    public static void snackBarCreator( View view,String message, int duration, int textColor, int backgroundColor, String actionText, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration)
                .setActionTextColor(textColor)
                .setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), backgroundColor))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAction(actionText, onClickListener)
                .show();
    }

    public static void snackBarCreator( View view,String message, int duration, int textColor, int backgroundColor, View anchorView, String actionText, View.OnClickListener onClickListener) {
        Snackbar.make(view, message, duration)
                .setActionTextColor(textColor)
                .setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), backgroundColor))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAnchorView(anchorView)
                .setAction(actionText, onClickListener)
                .show();
    }

    private static void showError(final EditText editText) {
        final int originalHintColor = editText.getCurrentHintTextColor();
        editText.setHintTextColor(Color.RED);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setHintTextColor(originalHintColor);
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No implementation needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setHintTextColor(originalHintColor);
            }
        });
    }
}
