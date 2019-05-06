package com.kdyadav.androidutilities;

import android.app.Activity;
import android.widget.Toast;

public class MyToast {

    public static void showToast(Activity activity) {
        Toast.makeText(activity, "Hiiii", Toast.LENGTH_SHORT).show();
    }

}
