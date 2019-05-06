package com.kdyadav.androidutilities;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


public class DialogUtils {

    public static void showLogoutAlert(Activity context, String message, DialogButtonClickListener callback) {
        showAlertDialog(context, R.mipmap.ic_launcher,
                context.getString(R.string.app_name),
                message,
                context.getString(R.string.dialog_yes),
                context.getString(R.string.dialog_cancel),
                null,
                callback, false);
    }

    public static void showRetryAlert(@NonNull Activity context, String message, DialogButtonClickListener callback) {
        showAlertDialog(context, R.mipmap.ic_launcher, context.getString(R.string.app_name),
                message, context.getString(R.string.dialog_yes),
                null,
                null,
                callback, false);
    }

    public static void showAlertDialog(Activity context, int icon, String title, String message,
                                       String positiveButtonText, String negativeButtonText,
                                       String neutralButtonText, DialogButtonClickListener callback,
                                       boolean isCancelable) {
        if (context != null && !context.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setCancelable(false);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (icon >= 0) {
                builder.setIcon(icon);
            }
            if (!TextUtils.isEmpty(positiveButtonText)) {
                builder.setPositiveButton(positiveButtonText, (dialog, id) -> {
                    dialog.dismiss();
                    if (callback != null) callback.onClickPositiveButton();
                });
            }
            if (!TextUtils.isEmpty(negativeButtonText)) {
                builder.setNegativeButton(negativeButtonText, (dialog, id) -> {
                    dialog.dismiss();
                    if (callback != null) callback.onclickNegativeButton();
                });
            }
            if (!TextUtils.isEmpty(neutralButtonText)) {
                builder.setNeutralButton(neutralButtonText, (dialog, id) -> {
                    dialog.dismiss();
                    if (callback != null) callback.onClickNeutralButton();
                });
            }
            AlertDialog alert = builder.create();
            alert.setCancelable(isCancelable);
            alert.show();
        }
    }

    public static void showErrorDialog(@NonNull Activity context,
                                       String message,
                                       DialogButtonClickListener callback) {
        showAlertDialog(context, -1,
                null,
                message,
                context.getString(R.string.ok),
                null,
                null,
                callback,
                false);
    }

    public interface DialogButtonClickListener {
        void onClickPositiveButton();

        void onclickNegativeButton();

        void onClickNeutralButton();
    }
}
