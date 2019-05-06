/*
 *  Copyright © 2018, Cognizant Technology Solutions.
 *  Written under contract by Robosoft Technologies Pvt. Ltd.
 */

package com.kdyadav.androidutilities;


import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class DeviceUtil {

    public static final float FABLET_MIN_SCALE = (float) 6.00;
    public static final float FABLET_MAX_SCALE = (float) 7.99;
    /**
     * To get device width.
     *
     * @return Device width
     */
    public static int getDeviceWidth(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * To get device heigth.
     *
     * @return Device heigth
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

/*    public static int getStatusBarHeight(Context activityContext) {
        int result = 0;
        int resourceId = activityContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activityContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }*/

    public static Point getSearchDialogDimensions(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        int height = DeviceUtil.getDeviceHeight(context);
        width = (int) (width * .91);
        height = (int) (height * .72);
        return new Point(width, height);
    }

    public static Point getContestListingImageDimensions(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        width = width - (int) context.getResources().getDimension(R.dimen._16sdp) * 2;
        int height = (int) (width * .25);
        return new Point(width, height);
    }

    public static Point getContestDetailImage(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        width = width;
        int height = (int) (width * .25);
        return new Point(width, height);
    }


    public static Point getContestDetailImageDimensions(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        int height = width * 9 / 16;
        return new Point(width, height);
    }

    public static Point getNewsBeepListingImageDimensions(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        width = width - (int) context.getResources().getDimension(R.dimen._10sdp) * 2;
        int height = (int) (width * .75);
        return new Point(width, height);
    }

    public static Point getImageDimension(Context context, int widthRatio, int heightRatio,
                                          int rightMargin, int leftMargin) {
        int width = DeviceUtil.getDeviceWidth(context);
        width = width - (rightMargin + leftMargin);
        int height = width * heightRatio / widthRatio;
        return new Point(width, height);
    }

    public static Point getImageDimensionForfourthreeRatio(Context context,int width) {
        int widthRatio = 4 ;
        int heightRatio = 3;
        int height = width * heightRatio / widthRatio;
        return new Point(width, height);
    }

    public static int getMovieSessionSpanCount(Context context) {
        int width = DeviceUtil.getDeviceWidth(context);
        // (16dp *2 + 10dp *2 +34dp) = 88dp
        // Each session text width = 50dp;
        width = width - context.getResources()
                .getDimensionPixelSize(R.dimen._80sdp);
        int textWidth = context.getResources()
                .getDimensionPixelSize(R.dimen._40sdp);
        return width / textWidth;
    }

    public static int convertDipToPixels(float dips, Context context) {
        return (int) (dips * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int getDeviceType(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        if(screenInches < FABLET_MIN_SCALE){
            return DeviceType.SMART_PHONE;
        }else if(screenInches > FABLET_MIN_SCALE && screenInches < FABLET_MAX_SCALE){
            return DeviceType.FABLET;
        }else {
            return DeviceType.TABLET;
        }

    }

    public interface DeviceType {
        int SMART_PHONE = 1;
        int FABLET = 2;
        int TABLET = 3;
    }
}
