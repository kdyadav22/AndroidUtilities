package com.kdyadav.androidutilities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;


public class AlertDialogManager {

    public static void showAlertDialog(Activity activity, String title, String msg) {
        try {
            final Dialog mdialog = new Dialog(activity);
            Objects.requireNonNull(mdialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.alertdialog);
            //mdialog.getWindow().getAttributes().windowAnimations = R.style.PaaswordDilaogAnimation;
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(wlp);
            //mdialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView okButton = mdialog.findViewById(R.id.okButton);
            TextView titleTextView = mdialog.findViewById(R.id.title);
            if (Utility.isNotNullNotEmptyNotWhiteSpace(title)) {
                titleTextView.setVisibility(View.VISIBLE);
                titleTextView.setText(title);
            }else{
                titleTextView.setVisibility(View.GONE);
            }

            TextView message = mdialog.findViewById(R.id.msg);
            message.setText(msg);

            okButton.setOnClickListener(v -> {
                mdialog.dismiss();
            });

            //dialog = builder.create();
            //dialog.setCancelable(false);
            mdialog.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void showAlertDialogWithCallBack(Activity activity, String title, String msg, OkButtonCallBack okButtonCallBack) {
        try {
            final Dialog mdialog = new Dialog(activity);
            Objects.requireNonNull(mdialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            mdialog.setContentView(R.layout.alertdialog);
            //mdialog.getWindow().getAttributes().windowAnimations = R.style.PaaswordDilaogAnimation;
            mdialog.setCancelable(false);
            mdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Window window = mdialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            //wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(wlp);
            //mdialog.getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView okButton = mdialog.findViewById(R.id.okButton);
            TextView titleTextView = mdialog.findViewById(R.id.title);
            if (Utility.isNotNullNotEmptyNotWhiteSpace(title)) {
                titleTextView.setVisibility(View.VISIBLE);
                titleTextView.setText(title);
            }else{
                titleTextView.setVisibility(View.GONE);
            }

            TextView message = mdialog.findViewById(R.id.msg);
            message.setText(msg);

            okButton.setOnClickListener(v -> {
                if (okButtonCallBack != null) {
                    okButtonCallBack.onOKClick();
                }
                mdialog.dismiss();
            });

            //dialog = builder.create();
            //dialog.setCancelable(false);
            mdialog.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public interface OkButtonCallBack {
        void onOKClick();
    }
}