package com.kdyadav.androidutilities;

import android.text.TextUtils;
import android.util.Log;


public final class Logger {
    private static final String GENERAL_TAG = "KDYADAV22: ";

    private String mTag;

    public static Logger getLogger(Class aClass) {
        return new Logger(aClass);
    }

    private Logger(Class aClass) {
        mTag = GENERAL_TAG + aClass.getSimpleName();
    }

    public void info(String aMessage) {
        info(aMessage, null);
    }

    public void info(String aMessage, Throwable aThrowable) {
        if (!TextUtils.isEmpty(aMessage)) {
            if (aThrowable != null) {
                Log.i(mTag, aMessage, aThrowable);
            } else {
                Log.i(mTag, aMessage);
            }
        }
    }

    public void warn(String aMessage) {
        warn(aMessage, null);
    }

    public void warn(String aMessage, Throwable aThrowable) {
        if (!TextUtils.isEmpty(aMessage)) {
            if (aThrowable != null) {
                Log.w(mTag, aMessage, aThrowable);
            } else {
                Log.w(mTag, aMessage);
            }
        }
    }

    public void error(String aMessage) {
        error(aMessage, null);
    }

    public void error(String aMessage, Throwable aThrowable) {
        if (!TextUtils.isEmpty(aMessage)) {
            if (aThrowable != null) {
                Log.e(mTag, aMessage, aThrowable);
            } else {
                Log.e(mTag, aMessage);
            }
        }
    }

    public void debug(String aMessage) {
        debug(aMessage, null);
    }

    public void debug(String aMessage, Throwable aThrowable) {
        if (!TextUtils.isEmpty(aMessage)) {
            if (aThrowable != null) {
                Log.d(mTag, aMessage, aThrowable);
            } else {
                Log.d(mTag, aMessage);
            }
        }
    }
}
