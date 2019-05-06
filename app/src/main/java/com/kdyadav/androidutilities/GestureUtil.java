package com.kdyadav.androidutilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.Calendar;

/**
 * Created by krishnakumar on 02/08/18.
 */

public class GestureUtil implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    Context context;
//    private GestureDetectorCompat mDetector;

    /*public void hideOrShowBottomBar(BaseFragment context, View view) {
        this.context = context;
        setOnTouchListner(view);
    }*/

    public void hideKeyboardOnOutsideTouch(Context context, View view) {
        this.context = context;
        setupUI(view);
    }

    /*private void setOnTouchListner(View view) {
        mDetector = new GestureDetectorCompat(context.getActivity(), this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        if(activity!=null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            if(inputMethodManager!=null) {
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }*/

    private void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                final int MAX_CLICK_DURATION = 400;
                final int MAX_CLICK_DISTANCE = 5;
                long startClickTime = 0;
                float x1 = 0;
                float y1 = 0;
                float x2 = 0;
                float y2 = 0;
                float dx = 0;
                float dy = 0;

                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startClickTime = Calendar.getInstance().getTimeInMillis();
                            x1 = event.getX();
                            y1 = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                            x2 = event.getX();
                            y2 = event.getY();
                            dx = x2 - x1;
                            dy = y2 - y1;
                            if (clickDuration < MAX_CLICK_DURATION && dx < MAX_CLICK_DISTANCE && dy < MAX_CLICK_DISTANCE)
                                //hideSoftKeyboard(context.getActivity());
                                break;
                    }
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d("Gesture", "tap");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        /*if (motionEvent1.getY(0) >= motionEvent.getY(0))
            context.hideBottomBarBasedOnState();    // Hide bottom bar on scroll down
        else
            context.showBottomBarBasedOnState();// Show bottom bar on scroll up*/


        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
